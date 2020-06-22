package provider.service;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import provider.common.CycleErrorCode;
import provider.common.CycleTools;
import provider.common.HostHolder;
import provider.common.JsonTool;
import provider.common.MailClient;
import provider.common.RedisKeyUtil;
import provider.domain.TicketDO;
import provider.domain.UserDO;
import provider.repository.impl.UserDORepositoryImpl;
import provider.request.ActivationRequest;
import provider.request.LoginRequest;
import provider.request.RegisterRequest;
import provider.request.TicketRequest;
import provider.request.UserRequest;
import provider.response.BaseResponse;
import provider.vo.ActivationVO;
import provider.vo.IdenCodeVO;
import provider.vo.ProfileVO;
import provider.vo.TicketVO;
import provider.vo.UserVO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static provider.common.CycleConstant.AUTHORITY_ADMIN;
import static provider.common.CycleConstant.AUTHORITY_MODERATOR;
import static provider.common.CycleConstant.AUTHORITY_USER;
import static provider.common.CycleConstant.DEFAULT_EXPIRED_SECONDS;
import static provider.common.CycleConstant.ENTITY_TYPE_USER;
import static provider.common.CycleConstant.REMEMBER_EXPIRED_SECONDS;
import static provider.common.CycleTools.generateUUID;
import static provider.convertor.UserConvertor.convertorVO;

@Service
public class UserService {

    @Autowired
    private UserDORepositoryImpl userDORepository;

    @Autowired
    private MailClient mailClient;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private FollowService followService;


    public UserVO findUserById(int id) {
        UserDO user = userDORepository.getById(id);
        if(ObjectUtils.isEmpty(user)){
            return null;
        }
        return convertorVO(user);
    }

    public BaseResponse register(RegisterRequest request){
        return register(request.getUser());

    }

    public BaseResponse register(UserVO user) {

        if (ObjectUtils.isEmpty(user)) {
            return CycleErrorCode.REQUEST_MISSING.getResponse("参数不能为空!");
        }
        if (ObjectUtils.isEmpty(user.getUsername())) {
            return CycleErrorCode.REQUEST_MISSING.getResponse("usernameMsg", "账号不能为空!");
        }
        if (ObjectUtils.isEmpty(user.getPassword())) {
            return CycleErrorCode.REQUEST_MISSING.getResponse("passwordMsg", "密码不能为空!");
        }
        if (ObjectUtils.isEmpty(user.getEmail())) {
            return CycleErrorCode.REQUEST_MISSING.getResponse("emailMsg", "邮箱不能为空!");
        }

        UserDO u = userDORepository.getByName(user.getUsername());
        if (!ObjectUtils.isEmpty(u)) {
            return CycleErrorCode.REQUEST_MISSING.getResponse("usernameMsg", "该账号已存在!");
        }

        u = userDORepository.getByEmail(user.getEmail());
        if (u != null) {
            return CycleErrorCode.REQUEST_MISSING.getResponse("emailMsg", "该邮箱已被注册!");
        }
        UserDO userDO = new UserDO().builder()
                .salt(generateUUID().substring(0, 5))
                .password(CycleTools.md5(user.getPassword() + user.getSalt()))
                .type(0)
                .status(0)
                .activationCode(generateUUID())
                .headerUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)))
                .createTime(new Date())
                .build();
        userDORepository.add(userDO);
        ActivationVO activationVO = ActivationVO.builder()
                .email(user.getEmail())
                .url(domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode()).build();
        mailClient.sendMail(user.getEmail(), "激活账号",activationVO.getUrl());
        return BaseResponse.builder().success(true).result(activationVO).build();
    }

    public BaseResponse activation(ActivationRequest request) {
        return activation(request.getId(), request.getCode());
    }

    private BaseResponse activation(int userId, String code) {
        UserDO user = userDORepository.getById(userId);
        if (user.getStatus() == 1) {
            return CycleErrorCode.REPEAT_ACTIVATION.getResponse();
        } else if (user.getActivationCode().equals(code)) {
            userDORepository.updateStatus(userId, 1);
            return BaseResponse.builder().success(true).build();
        } else {
            return CycleErrorCode.FAIL_ACTIVATION.getResponse();
        }
    }

    public BaseResponse login(LoginRequest request){
        String kaptcha = null;
        String kaptchaOwner = request.getKaptchaOwner();
        String idenCode = request.getIdenCode();
        if (StringUtils.isNotBlank(kaptchaOwner)) {
            String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
            kaptcha = (String) redisTemplate.opsForValue().get(redisKey);
        }
        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(idenCode) || !kaptcha.equalsIgnoreCase(idenCode)) {
            return CycleErrorCode.IDENTIFY_CODE_ERROE.getResponse();
        }
        Integer expiredSeconds = request.getRemember() ?  REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;
        return login(request.getUserName(), request.getPassword(), Long.valueOf(expiredSeconds));
    }

    private BaseResponse login(String username, String password, Long expiredSeconds) {
        if (ObjectUtils.isEmpty(username)) {
            return CycleErrorCode.REQUEST_MISSING.getResponse("usernameMsg", "账号不能为空!");
        }
        if (ObjectUtils.isEmpty(password)) {
            return CycleErrorCode.REQUEST_MISSING.getResponse("passwordMsg", "密码不能为空!");
        }

        UserDO user = userDORepository.getByName(username);
        if (user == null) {
            CycleErrorCode.REQUEST_MISSING.getResponse("usernameMsg", "该账号不存在!");
        }

        if (user.getStatus() == 0) {
            return CycleErrorCode.NO_ACTIVATION.getResponse();
        }

        password = CycleTools.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)) {
            return CycleErrorCode.REQUEST_ERROR.getResponse("passwordMsg", "密码不正确!");
        }

        TicketVO ticketVO = TicketVO.builder()
                .userId(user.getId())
                .ticket(generateUUID())
                .status(0)
                .expired(new Date(System.currentTimeMillis() + expiredSeconds * 1000))
                .build();

        String redisKey = RedisKeyUtil.getTicketKey(ticketVO.getTicket());
        redisTemplate.opsForValue().set(redisKey, JsonTool.toJson(ticketVO));

        return BaseResponse.builder().success(true).result(ticketVO).build();
    }

    public BaseResponse logout(TicketRequest request) {
        logout(request.getTicket());
        return BaseResponse.builder().success(true).build();
    }
    private void logout(String ticket) {
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        TicketVO ticketVO = JsonTool.fromJson((String) redisTemplate.opsForValue().get(redisKey), TicketVO.class);
        ticketVO.setStatus(1);
        redisTemplate.opsForValue().set(redisKey, JsonTool.toJson(ticket));
    }

    public TicketVO getTicket(String ticket) {
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        return JsonTool.fromJson((String) redisTemplate.opsForValue().get(redisKey), TicketVO.class);
    }

    public UserVO findUserByName(String username) {
        UserDO user = userDORepository.getByName(username);
        if(ObjectUtils.isEmpty(user)){
            return null;
        }
        return convertorVO(user);
    }

    public Collection<? extends GrantedAuthority> getAuthorities(int userId) {
        UserVO user = this.findUserById(userId);

        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                switch (user.getType()) {
                    case 1:
                        return AUTHORITY_ADMIN;
                    case 2:
                        return AUTHORITY_MODERATOR;
                    default:
                        return AUTHORITY_USER;
                }
            }
        });
        return list;
    }

    public BaseResponse getKaptcha() {
        String idenCode = generateUUID().substring(0, 5);
        String kaptchaOwner = generateUUID();
        String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
        redisTemplate.opsForValue().set(redisKey, idenCode, 60, TimeUnit.SECONDS);
        return BaseResponse.builder().success(true).result(IdenCodeVO.builder().idenCode(idenCode)
                .kaptchaOwner(kaptchaOwner).build()).build();
    }

    public BaseResponse setUrl(UserRequest request) {
        return BaseResponse.builder().success(true).build();
    }

    public BaseResponse getProfilePage(UserRequest request) {
        UserVO user = convertorVO(getByname(request.getUser().getUsername()));
        Integer userId = user.getId();
        Integer likeCount = likeService.count(userId);
        Long followeeCount = followService.findFolloweeCount(userId, ENTITY_TYPE_USER);
        Long followerCount = followService.findFollowerCount(ENTITY_TYPE_USER, userId);
        boolean hasFollowed = false;
        if (!ObjectUtils.isEmpty(hostHolder.getUser())) {
            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, userId);
        }
        ProfileVO profile = ProfileVO.builder()
                .user(user)
                .likeCount(likeCount)
                .followeeCount(followeeCount)
                .followerCount(followerCount)
                .hasFollowed(hasFollowed)
                .build();
        return BaseResponse.builder().success(true).result(profile).build();
    }

    public UserDO getByname(String name) {
        return userDORepository.getByName(name);
    }

    public TicketDO findLoginTicket(String ticket) {
        return null;
    }
}

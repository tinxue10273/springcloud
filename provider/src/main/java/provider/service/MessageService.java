package provider.service;

import provider.common.HostHolder;
import provider.common.SensitiveFilter;
import provider.domain.MessageDO;
import provider.repository.impl.MessageDORepositoryImpl;
import provider.request.MessageRequest;
import provider.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import provider.response.BaseTableVO;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageDORepositoryImpl messageDORepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private HostHolder hostHolder;

    public BaseResponse listConversations(MessageRequest request){
        Integer current = request.getCurrent();
        Integer limit = request.getPageSize();
        Integer offset = (current - 1) * limit;
        Integer userId = userService.getByname(request.getToName()).getId();
        Integer total = countConversation(userId);
        List<MessageDO> messageDOS = listConversations(userId, offset, limit);
        BaseTableVO<MessageDO> result = new BaseTableVO<>();
        result.setItems(messageDOS);
        result.initPagination(current, limit, total);
        return BaseResponse.builder().success(true).result(result).build();
    }

    public List<MessageDO> listConversations(int userId, int offset, int limit) {
        return messageDORepository.listConversations(userId, offset, limit);
    }

    public int countConversation(int userId) {
        return messageDORepository.countConversation(userId);
    }

    public BaseResponse listLetters(MessageRequest request){
        Integer offset = (request.getCurrent() - 1) * request.getPageSize();
        return BaseResponse.builder().success(true).result(listLetters(request.getConversationId(), offset,
                request.getPageSize())).build();
    }

    private List<MessageDO> listLetters(String conversationId, int offset, int limit) {
        return messageDORepository.listLetters(conversationId, offset, limit);
    }

    public int countLetter(String conversationId) {
        return messageDORepository.countLetter(conversationId);
    }

    public int countUnreadLetter(int userId, String conversationId) {
        return messageDORepository.countUnreadLetter(userId, conversationId);
    }

    public BaseResponse add(MessageRequest request) {
        Integer toId = userService.getByname(request.getToName()).getId();
        Integer fromId = hostHolder.getUser().getId();
        return BaseResponse.builder().success(true).result(add(MessageDO.builder().toId(toId).fromId(fromId)
                .content(request.getContent()).createTime(new Date()).build())).build();

    }

    public int add(MessageDO message) {
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveFilter.filter(message.getContent()));
        if (message.getFromId() < message.getToId()) {
            message.setConversationId(message.getFromId() + "_" + message.getToId());
        } else {
            message.setConversationId(message.getToId() + "_" + message.getFromId());
        }
        return messageDORepository.add(message);
    }

    public int read(List<Integer> ids) {
        return messageDORepository.update(ids, 1);
    }

    public BaseResponse getLatestNotice(MessageRequest request) {
        Integer userId = userService.getByname(request.getToName()).getId();
        return BaseResponse.builder().success(true).result(getLatestNotice(userId, request.getTopic())).build();
    }

    public MessageDO getLatestNotice(int userId, String topic) {
        return messageDORepository.selectLatestNotice(userId, topic);
    }

    public int countNotice(int userId, String topic) {
        return messageDORepository.countNotice(userId, topic);
    }

    public int countUnreadNotice(int userId, String topic) {
        return messageDORepository.countUnreadNotice(userId, topic);
    }

    public BaseResponse listNotice(MessageRequest request){
        Integer offset = (request.getCurrent() - 1) * request.getPageSize();
        Integer userId = userService.getByname(request.getToName()).getId();
        return BaseResponse.builder().success(true).result(listNotice(userId, request.getTopic(), offset,
                request.getPageSize())).build();
    }

    private List<MessageDO> listNotice(int userId, String topic, int offset, int limit) {
        return messageDORepository.listNotices(userId, topic, offset, limit);
    }

    public int findLetterUnreadCount(int id, Object o) {
        return  0;
    }

    public int findNoticeUnreadCount(int id, Object o) {
        return 0;
    }
}

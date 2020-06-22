package provider.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import provider.common.HostHolder;
import provider.request.BaseRequest;
import provider.service.TicketService;
import provider.vo.TicketVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gouhuo on 2020/05/19.
 */
@Aspect
@Component
public class Authorization {
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private TicketService ticketService;

    @Around("@annotation(provider.annotation.LoginAnnotation)")
    public Object loginCheck(ProceedingJoinPoint joinPoint) throws Throwable{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        Object[] args = joinPoint.getArgs();
        BaseRequest baseRequest = getBaseRequest(args);
        if(null != baseRequest){
            String ticket = baseRequest.getTicket();
            TicketVO ticketVO = ticketService.setTicket(ticket);
        }
        return joinPoint.proceed();
    }

    private BaseRequest getBaseRequest(Object[] args){
        for(Object arg : args){
            if(!(arg instanceof HttpServletRequest) && arg instanceof BaseRequest){
                return (BaseRequest) arg;
            }
        }
        return null;
    }
}

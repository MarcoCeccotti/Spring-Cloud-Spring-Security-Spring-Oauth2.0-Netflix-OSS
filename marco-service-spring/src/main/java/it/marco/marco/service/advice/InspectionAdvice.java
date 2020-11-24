package it.marco.marco.service.advice;

import javax.inject.Inject;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import it.marco.marco.bean.mail.Mail_server;
import it.marco.marco.cloud.config.properties.MailProperties;

@Aspect
@Component
public class InspectionAdvice {
	
	@Inject
	private MailProperties mailProps;
	
	@Inject
	private UserRepository userRepository;

	@Around("execution(* it..*.Mail_server_DAO.save(..))")
	public Object setMailSentToTrue(ProceedingJoinPoint pjp) throws Throwable {

		Object[] args = pjp.getArgs();
		
		if (!mailProps.isEnable()) {
			Mail_server mail_server = (Mail_server) args[0];
			mail_server.setSent(true);
		}
		
		return pjp.proceed(args);
	}
	
	@Around("execution(* *..MailServiceImpl.send*Email(..))")
	public void checkSendEmail(ProceedingJoinPoint pjp) throws Throwable {
		
		if (mailProps.isEnable()) {
			pjp.proceed();
		}
	}

	@Before(value = "@annotation(org.springframework.security.access.prepost.PreAuthorize)") 
	public void checkAuthMethods(JoinPoint jp) throws Throwable {
	    // conditional logging based on annotation contents
	    MethodSignature signature = (MethodSignature) jp.getSignature();
	    for (Annotation annotation : signature.getMethod().getAnnotations()) {
	    	if (annotation.toString().toUpperCase().contains("ROLE_SCHOOL")) {
	    		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    		for (GrantedAuthority authority : auth.getAuthorities()) {
	    			if (authority.getAuthority().equalsIgnoreCase("ROLE_SCHOOL")) {
	    	    			UserNew user = this.userRepository.findByUsernameIgnoreCase(auth.getPrincipal().toString());
	    			}
	    		}
	    		
		    	System.out.println("Found method with ROLE_SCHOOL as annotation");
	    	}
	    }
	}
}

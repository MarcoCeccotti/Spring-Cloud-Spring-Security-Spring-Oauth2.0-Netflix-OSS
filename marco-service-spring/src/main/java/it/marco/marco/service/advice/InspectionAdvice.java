package it.marco.marco.service.advice;

import javax.inject.Inject;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import it.marco.marco.bean.mail.Mail_server;
import it.marco.marco.cloud.configuration.properties.MailProperties;

@Aspect
@Component
public class InspectionAdvice {
	
	@Inject
	private MailProperties mailProps;

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
}

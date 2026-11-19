package jp.co.sss.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class CancelOrderRequestEmailService {

	@Autowired private JavaMailSender mailSender;
    
	@Value("${spring.mail.username}")
	private String fromEmail;
	
	public void sendCancelOrderRequestEmail(String toEmail, String orderId) {
		
		
		
		
	}
	
	
}

package jp.co.sss.shop.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/client/user/regist/email/")
public class EmailVerificationRestController {

	
	@GetMapping("verify")
	public ResponseEntity<?> sendVerificationEmail(
		HttpSession session	){
		
		int verificationCode = (int) session.getAttribute("verificationCode");
		return ResponseEntity.ok(verificationCode);
	}
	
	
	
}

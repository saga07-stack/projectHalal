package jp.co.sss.shop.form;

import org.springframework.format.annotation.NumberFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmailForm {

	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
    @NumberFormat
	private String verificationCode;
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	
	
	
	
	
}

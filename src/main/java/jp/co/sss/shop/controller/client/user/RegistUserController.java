package jp.co.sss.shop.controller.client.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.form.EmailForm;
import jp.co.sss.shop.form.UserForm;
import jp.co.sss.shop.service.EmailCodeCreater;
import jp.co.sss.shop.service.EmailService;

@Controller
public class RegistUserController {

	@Autowired
	private EmailService emailService;
	
	@RequestMapping (path ="/client/user/regist/email", method = RequestMethod.GET)
	public String registEmail(
			@Valid @ModelAttribute EmailForm emailForm , BindingResult result, Model model , HttpSession session
			) {
		
		model.addAttribute("emailVerificationForm", new EmailForm());
		EmailCodeCreater randomNumberGenerator = new EmailCodeCreater();
		int randomNumber = randomNumberGenerator.RandomNumberGenerator();
		//session.setAttribute("verificationCode", randomNumber);
		
		
System.out.println(randomNumber);		
		return "client/user/regist_email";
	}
	
	
	@RequestMapping (path = "/client/user/regist/email/send", method = {RequestMethod.POST, RequestMethod.GET})
	public String confirmCode(
			HttpSession session,
			@RequestParam("email") String email,
			Model model
			) {
		EmailCodeCreater randomNumberGenerator = new EmailCodeCreater();
		int randomNumber = randomNumberGenerator.RandomNumberGenerator();
		emailService.sendVerificationCode(email,randomNumber);
		session.setAttribute("verificationCode", randomNumber);
	//	session.setAttribute("email", email);
		model.addAttribute("email", email);
	System.out.println("from enter section"+session.getAttribute("verificationCode"));
		return"client/user/regist_code";
		
	}
	
	
	
	
	
	@RequestMapping(path ="/client/user/regist/input/init", method = RequestMethod.GET)
	public String registInputInit(
		@Valid @ModelAttribute  UserForm userForm , BindingResult result , 	Model model  , HttpSession session
			
			) {
		
		
		
		return "client/user/regist_input";
	}
	
	
	@RequestMapping(path ="/client/user/regist/check", method = RequestMethod.POST)
	public String registCheck () {
		
		
		return "client/user/regist_check";
	}
	
}

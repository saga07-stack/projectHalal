package jp.co.sss.shop.controller.client.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.form.EmailForm;
import jp.co.sss.shop.form.UserForm;

@Controller
public class RegistUserController {

	
	@RequestMapping (path ="/client/user/regist/email", method = RequestMethod.GET)
	public String registEmail(
			@Valid @ModelAttribute EmailForm emailForm , BindingResult result, Model model , HttpSession session
			) {
		
		model.addAttribute("emailVerificationForm", new EmailForm());
		return "client/user/regist_email";
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

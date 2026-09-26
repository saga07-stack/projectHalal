package jp.co.sss.shop.controller.client.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.mail.Session;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.User;
import jp.co.sss.shop.form.EmailForm;
import jp.co.sss.shop.form.UserForm;
import jp.co.sss.shop.repository.UserRepository;
import jp.co.sss.shop.service.EmailCodeCreater;
import jp.co.sss.shop.service.EmailService;

@Controller
public class RegistUserController {

	@Autowired
	private EmailService emailService;

	@Autowired(required = false)
	private UserBean userBean;

	
	@Autowired
	private UserRepository userRepo;
	
	
	@RequestMapping(path = "/client/user/regist/email", method = RequestMethod.GET)
	public String registEmail(@Valid @ModelAttribute EmailForm emailForm, BindingResult result, Model model,
			HttpSession session) {

		model.addAttribute("emailVerificationForm", new EmailForm());
		EmailCodeCreater randomNumberGenerator = new EmailCodeCreater();
		int randomNumber = randomNumberGenerator.RandomNumberGenerator();
		// session.setAttribute("verificationCode", randomNumber);

		System.out.println(randomNumber);
		return "client/user/regist_email";
	}

	@RequestMapping(path = "/client/user/regist/email/send", method = { RequestMethod.POST, RequestMethod.GET })
	public String confirmCode(HttpSession session, @RequestParam("email") String email, Model model) {
		
		User emailFromDb = userRepo.findByEmail(email);
		Boolean error = false;
		if(emailFromDb == null) {
		
		
		EmailCodeCreater randomNumberGenerator = new EmailCodeCreater();
		int randomNumber = randomNumberGenerator.RandomNumberGenerator();
		emailService.sendVerificationCode(email, randomNumber);
		session.setAttribute("verificationCode", randomNumber);
		session.setAttribute("email", email);
		model.addAttribute("email", email);
		model.addAttribute("emailVerificationForm", new EmailForm());
		System.out.println("from enter section" + session.getAttribute("verificationCode"));
		return "client/user/regist_code"; }
		else { 
			error = true; 
			if(error) {
				model.addAttribute("error", true);
				model.addAttribute("errorMessage", "このメールアドレスは既に登録されています。");
				System.out.println("email already exists in db ");
			}
			model.addAttribute("emailVerificationForm", new EmailForm());
			model.addAttribute("errorMessage", "このメールアドレスは既に登録されています。");
			return "client/user/regist_email";
		}
 
	} 

	@RequestMapping(path = "/client/user/regist/email/re_send", method = { RequestMethod.POST, RequestMethod.GET })
	public String resendCode(HttpSession session, Model model) {
		String email = (String) session.getAttribute("email");
		if (email == null) {
			// session expired or invalid direct access
			return "redirect:/client/user/regist";
		}
		EmailCodeCreater randomNumberGenerator = new EmailCodeCreater();
		int randomNumber = randomNumberGenerator.RandomNumberGenerator();
		emailService.sendVerificationCode(email, randomNumber);
		session.setAttribute("verificationCode", randomNumber);
		System.out.println("from resend section" + randomNumber);
		model.addAttribute("email", email);
		return "client/user/regist_code";
	}

	@RequestMapping(path = "/client/user/regist/input/init", method = RequestMethod.GET)
	public String registInputInit(HttpSession session) {
		System.out.println("inside the userform " + session.getAttribute("userForm"));

		session.removeAttribute("userForm");

		return "redirect:/client/user/regist/input";
	}

//	@RequestMapping(path ="/client/user/regist/input", method = RequestMethod.GET)
//	public String registInputInit(
//			@Valid @ModelAttribute ("userForm")  UserForm userForm, BindingResult result ,
//			 	Model model  , HttpSession session
//			) {
//		UserBean userBeans = (UserBean) session.getAttribute("userBean");
//		if (userBeans != null) {
//			userBeans = new UserBean();
//			
//		}
//		model.addAttribute("userForm", userBeans);
//		
//		
//		return "client/user/regist_input";
//	}
	@RequestMapping("/client/user/regist/input")
	public String registInput(Model model, HttpSession session) {

		UserBean userBean = (UserBean) session.getAttribute("registUser");
		if (userBean == null) {
			userBean = new UserBean();
		}

		model.addAttribute("userForm", userBean);
		return "client/user/regist_input";
	}

	@RequestMapping(path = "/client/user/regist/check", method = RequestMethod.POST)
	public String registCheck(@Valid @ModelAttribute UserForm userForm, BindingResult result, Model model,
			HttpSession session) {
		UserBean userBean = new UserBean();
		userBean.setName(userForm.getName());
		userBean.setEmail(userForm.getEmail());
		userBean.setPassword(userForm.getPassword());
		userBean.setPostalCode(userForm.getPostalCode());
		userBean.setAddress(userForm.getAddress());
		userBean.setAuthority(2);
		session.setAttribute("registUser", userBean);
		
		return "client/user/regist_check";
	}

	@RequestMapping(path = "/client/user/regist/complete", method = RequestMethod.POST)
	public String registComplete(
			HttpSession session 
			
			) {
     UserBean user = (UserBean) session.getAttribute("registUser"); 
     if(user == null ) {
    	 return "redirect:/syserror";
     }
     User userObj = new User();
     userObj.setName(user.getName());
     userObj.setEmail(user.getEmail());
     userObj.setPassword(user.getPassword());
     userObj.setPostalCode(user.getPostalCode());
     userObj.setAddress(user.getAddress());
   
     userRepo.save(userObj);
     session.removeAttribute("registUser");
		return "client/user/regist_complete";
	}

}

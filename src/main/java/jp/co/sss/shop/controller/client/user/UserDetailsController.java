package jp.co.sss.shop.controller.client.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.User;
import jp.co.sss.shop.form.UserForm;
import jp.co.sss.shop.repository.UserRepository;

@Controller
public class UserDetailsController {

	@Autowired
	private UserRepository userRepository;
	
	
	@GetMapping("client/user/detail")
	public String userDetails(
		Model model,
		HttpSession session
			) {
		
		Integer userId = ((UserBean)session.getAttribute("user")).getId();
		
		if(userId == null) {
			return "redirect:/client/user/login";
		}
		
		
	Optional<User> user = userRepository.findById(userId);
		
	if (user.isPresent()) {
			
			UserBean userBean = new UserBean();
		
			userBean.setName(user.get().getName());
			userBean.setEmail(user.get().getEmail());
			userBean.setAddress(user.get().getAddress());
			userBean.setPhoneNumber(user.get().getPhoneNumber());
			
			// 他の必要なプロパティも設定する
			model.addAttribute("userBean", userBean);
			session.setAttribute("userBean", userBean);
		} else {
			// ユーザーが見つからない場合の処理
			return "redirect:/client/user/login";
		}
		
	//	session.setAttribute("userBean", user);
		
		
		
		return "client/user/detail";
	}
	
	@PostMapping("client/user/update/input")
	public String userUpdateInput(
			Model model,
            HttpSession session			
			) {
		
		return "redirect:/client/user/update/input/init";
	}
	
	@GetMapping ("client/user/update/input/init")
	public String userUpdateInputInit(
			Model model,
			HttpSession session			
			) {
		
		UserBean userBean = (UserBean) session.getAttribute("userBean");
		
		if(userBean == null) {
			return "redirect:/client/user/login";
		}
		
		model.addAttribute("userForm", userBean);
		
		return "client/user/update_input";
	}
	
	@PostMapping("client/user/update/check")
	public String userUpdateCheck( HttpSession session,
			@RequestParam ("email") String email,
			@RequestParam ("password") String password,
			@RequestParam ("name") String name,
			@RequestParam ("postalCode") String postalCode,
			@RequestParam ("address") String address,
			@RequestParam ("phoneNumber") String phoneNumber,
			
			@Valid @ModelAttribute UserForm userForm, BindingResult result, Model model
			) {
		
		
		
		Boolean hasErrors = result.hasErrors();
		hasErrors = false;
		
		
		Integer userId = ((UserBean) session.getAttribute("user")).getId(); //(live user id )
		User hasAlreadyUser = userRepository.findByEmail(email); //(email already exist cha ki nai check garna )
        
		if(hasAlreadyUser == null) {
	    	
			return "redirect:/client/user/update/email";
			
	      }
		
		
		
		
		if(hasAlreadyUser != null) {  // email check bhayo ani found bhayo 
			 hasErrors = true;
			 if(hasErrors) {
				 model.addAttribute("errorMessage", "email is  already exist you can user another email");
				 result.hasErrors();
				 return "client/user/update_input";
			 }
		                          
		       
			 //model.addAttribute("errorMessage", "このメールアドレスは既に使用されています");
		 }
	
		
				
	
	
		return "client/user/update_check";
	
	
	}
	
	@GetMapping("client/user/update/email")
	public String userUpdateEmail() {
		
		return "redirect:/client/user/email/update/input";
	}
	
	
	
	
}

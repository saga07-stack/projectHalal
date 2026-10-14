package jp.co.sss.shop.controller.client.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.shop.bean.OrderItemBean;
import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.User;
import jp.co.sss.shop.form.OrderForm;
import jp.co.sss.shop.repository.UserRepository;

@Controller
public class UserOrderAddressInputController {


	@Autowired
	private UserRepository userRepository;
	@RequestMapping(path = "/client/order/address/input",method = {RequestMethod.GET,RequestMethod.POST})
	public String addressInput(
			Model model,
			HttpSession session
			
			) {
		
		if(session.getAttribute("user") == null) {
			return "redirect:/login";
			
			
		}
		
		OrderForm orderForm = new OrderForm();
		
		UserBean loginUser = (UserBean) session.getAttribute("user");
		
		User user = userRepository.getReferenceById(loginUser.getId());
		
		orderForm.setId(user.getId());
		orderForm.setPostalCode(user.getPostalCode());
		orderForm.setAddress(user.getAddress());
		orderForm.setName(user.getName());
		orderForm.setPhoneNumber(user.getPhoneNumber());
		orderForm.setPayMethod(1);
		
		session.setAttribute("orderForm", orderForm);
		
		
	
		return "redirect:/client/address/input/init";
	}
	
	@RequestMapping(path = "/client/address/input/init", method = {RequestMethod.GET,RequestMethod.POST})
	public String addressConfirm(Model model , HttpSession session) {
		
		OrderForm orderForm = (OrderForm) session.getAttribute("orderForm");
		
		
		
		model.addAttribute("orderForm", orderForm);	
		return "client/order/address_input";
	}
	
	
	@RequestMapping(path = "/client/order/payment/input", method = {RequestMethod.GET,RequestMethod.POST})
	public String paymentInput() {
		
		return "client/order/payment_input";
	}
	
	@RequestMapping(path = "client/order/check", method = {RequestMethod.GET,RequestMethod.POST})
	public String OrderCheck(
			Model model, 
			
			HttpSession session
			) {
		
		OrderForm newOrderForm = new OrderForm();
		OrderForm orderForm = (OrderForm) session.getAttribute("orderForm");
		newOrderForm.setId(orderForm.getId());
		newOrderForm.setPostalCode(orderForm.getPostalCode());
		newOrderForm.setAddress(orderForm.getAddress());
		newOrderForm.setName(orderForm.getName());
		newOrderForm.setPhoneNumber(orderForm.getPhoneNumber());
		
		session.setAttribute("orderForm", newOrderForm);
		
		
		return "redirect:/client/order/confirm";
	}
	
	@RequestMapping(path = "/client/order/confirm", method = {RequestMethod.GET,RequestMethod.POST})
	public String orderConfirm(
			Model model,
			HttpSession session) {
		OrderForm orderForm = (OrderForm) session.getAttribute("orderForm");
		model.addAttribute("orderItemBeans", orderForm);
		
		return "client/order/check";
	}
	
}

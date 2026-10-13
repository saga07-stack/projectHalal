package jp.co.sss.shop.controller.client.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.shop.form.OrderForm;

@Controller
public class UserOrderAddressInputController {

	@RequestMapping(path = "/client/order/address/input",method = {RequestMethod.GET,RequestMethod.POST})
	public String addressInput(
			Model model,
			HttpSession session
			
			) {
		
	model.addAttribute("orderForm", new OrderForm());
		return "client/order/address_input";
	}
	
	@RequestMapping(path = "/client/order/payment/input", method = {RequestMethod.GET,RequestMethod.POST})
	public String paymentInput() {
		
		return "client/order/payment_input";
	}
	
	@RequestMapping(path = "client/order/check", method = {RequestMethod.GET,RequestMethod.POST})
	public String OrderCheck() {
		
		return "client/order/check";
	}
	
	
}

package jp.co.sss.shop.controller.client.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.shop.bean.OrderItemBean;
import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.CartItems;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.entity.User;
import jp.co.sss.shop.form.OrderForm;
import jp.co.sss.shop.repository.CartRepository;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.repository.UserRepository;

@Controller
public class UserOrderAddressInputController {


	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private CartRepository cartRepo;
	
	@Autowired
	private ItemRepository itemRepository;
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
		
		List<CartItems > cartItems = cartRepo.findAllByUserId(loginUser.getId());
		System.out.println("cartItems: " + cartItems);
		
		session.setAttribute("cartItems", cartItems);
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
	public String paymentInput(Model model , HttpSession session) {
		
		
		return "client/order/payment_input";
	}
	
	
	@RequestMapping(path = "/client/order/payment/back", method = { RequestMethod.GET, RequestMethod.POST })
	public String paymentInputBack(Model model) {

		return "redirect:/client/address/input/init";
	}
	 
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path = "client/order/check", method = {RequestMethod.GET,RequestMethod.POST})
	public String OrderCheck(
			Model model, 
			@RequestParam("payMethod") Integer payMethod,
			HttpSession session
			) {
		
		if(session.getAttribute("user") == null) {
			return "redirect:/login";
		}
		
		OrderForm newOrderForm = new OrderForm();
		OrderForm orderForm = (OrderForm) session.getAttribute("orderForm");
		newOrderForm.setId(orderForm.getId());
		newOrderForm.setPostalCode(orderForm.getPostalCode());
		newOrderForm.setAddress(orderForm.getAddress());
		newOrderForm.setName(orderForm.getName());
		newOrderForm.setPhoneNumber(orderForm.getPhoneNumber());
		newOrderForm.setPayMethod(payMethod);
		int totalPrice=0;
	//	session.getAttribute("cartItems");
		System.out.println("inside cart items" + session.getAttribute("cartItems"));
		List<OrderItemBean> orderItemBeans = new ArrayList<>();
		List<CartItems> cartItems = (List<CartItems>) session.getAttribute("cartItems");
		for(CartItems cartItem : cartItems) {
		Item item = itemRepository.findById(cartItem.getItemId()).orElse(null);
		if(item == null) {
			continue;
			
		}
	     
		
		OrderItemBean orderItemBean = new OrderItemBean();// Create a new OrderItemBean for each cart item
		orderItemBean.setImage(item.getImage());
		orderItemBean.setName(item.getName());
		orderItemBean.setPrice(item.getPrice());
		orderItemBean.setSubtotal(item.getPrice() * cartItem.getQuantity());
		orderItemBean.setQuantity(cartItem.getQuantity());
	
		totalPrice += orderItemBean.getSubtotal(); // Accumulate the total price
		orderItemBeans.add(orderItemBean);
		
		
		}
		 
		//System.out.println("orderitemimg" + orderItemBean.getImage());
		//model.addAttribute("orderItemBeans", orderItemBeans);
		
		session.setAttribute("orderItemBeans", orderItemBeans);
		session.setAttribute("orderForm", newOrderForm);
		session.setAttribute("totalPrice", totalPrice);
		
		return "redirect:/client/order/confirm";
	}
	
	@RequestMapping(path = "/client/order/confirm", method = {RequestMethod.GET,RequestMethod.POST})
	public String orderConfirm(
			Model model,
			HttpSession session) {
	    List<OrderItemBean> orderItemBeans = (List<OrderItemBean>)session.getAttribute("orderItemBeans");
		OrderForm orderForm = (OrderForm) session.getAttribute("orderForm");
		model.addAttribute("orderItemBeans",orderItemBeans);
		model.addAttribute("orderForm", orderForm);
		model.addAttribute("totalPrice", session.getAttribute("totalPrice"));
		session.setAttribute("paymentType", orderForm.getPayMethod());
		return "client/order/check";
	}
	
	@RequestMapping(path = "/client/order/payment/check", method = {RequestMethod.GET,RequestMethod.POST})
	public String paymentMethodCheck(
			Model model,
			HttpSession session
			){
		if(session.getAttribute("user") == null) {
			return "redirect:/login";
		}
		
		System.out.println("paymentType: " + session.getAttribute("paymentType"));
		if(session.getAttribute("paymentType").equals(1))
		{	
			
			return "client/order/Credit";
		}	else if(session.getAttribute("paymentType").equals(2)) {
			
		
			
			//System.out.println("paymentType: " + session.getAttribute("bank tranfer"));
			
			return "client/order/BankTransfer";
		
		}else if(session.getAttribute("paymentType").equals(3)) {
		
			System.out.println("other  3");
			
			return "client/order/onarrival";
		}else if(session.getAttribute("paymentType").equals(4)) {
			
			System.out.println("other  4");
			return "client/order/emoney";
			
		}else if(session.getAttribute("paymentType").equals(5)) {
			
			System.out.println("other 5 ");
			return "client/order/convenience";
		}
		
		return"";
	}
	
	
	
	
}

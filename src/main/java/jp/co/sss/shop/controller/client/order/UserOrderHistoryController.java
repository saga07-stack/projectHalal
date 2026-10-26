package jp.co.sss.shop.controller.client.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.shop.bean.BasketBean;
import jp.co.sss.shop.bean.OrderBean;
import jp.co.sss.shop.bean.OrderItemBean;
import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.Order;
import jp.co.sss.shop.entity.OrderItem;
import jp.co.sss.shop.repository.OrderItemRepository;
import jp.co.sss.shop.repository.OrderRepository;
import jp.co.sss.shop.repository.UserRepository;

@Controller
public class UserOrderHistoryController {

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired private UserRepository userRepository;
 
	
	
	@GetMapping("/client/order/list")
	public String orderList(HttpSession session, Model model) {
	    UserBean user = (UserBean) session.getAttribute("user");
	    
	    if(user == null) {
	    	
	    	return "redirect:/client/login";
	    }
	    List<Order> orders = orderRepository.findByUserIdPendingFirst(user.getId());
	    Boolean isnull = false;
	    if(orders.isEmpty()) {
	    	isnull = true;
	    		        model.addAttribute("message", "Your Order history is empty.");
	    				model.addAttribute("isnull", isnull);
	    				
	    }else {
			isnull = false;
			model.addAttribute("isnull", isnull);
			model.addAttribute("orders", orders);
						
		}
	    
	    
	    return "client/basket/basket";
	}   

	@PostMapping("/client/order/detail")
	public String orderDetail(
			@RequestParam("id") Integer orderId,
			Model model,
		HttpSession session
			) {
		//Order order = orderRepository.findById(orderId).orElse(null);
		 UserBean user = (UserBean) session.getAttribute("user");
		    
		    if(user == null) {
		    	
		    	return "redirect:/client/login";
		    }
		
		
	    List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
	   Order orders = orderRepository.findById(orderId).orElse(null);
	    
	    String orderStatus;
	    Boolean cancelStatus = false;
	    switch (orders.getStatus()) {
	        case "out for delivery":
	            orderStatus = "out_for_delivery";
	            break;
	        case "delivered":
	            orderStatus = "delivered";
	            break;
	        default: // pending ra aru sabai
	            orderStatus = "processing";
	            cancelStatus = true;
	    }
	    model.addAttribute("cancelStatus", cancelStatus);
	    model.addAttribute("orderStatus", orderStatus);
        model.addAttribute("orderNumber", orderId);
			model.addAttribute("orderItems", orderItems);
		
		return"client/basket/details";
	}
	
}

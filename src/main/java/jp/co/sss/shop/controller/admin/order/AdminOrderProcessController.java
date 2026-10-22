package jp.co.sss.shop.controller.admin.order;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.shop.entity.Order;
import jp.co.sss.shop.entity.OrderItem;
import jp.co.sss.shop.repository.OrderItemRepository;
import jp.co.sss.shop.repository.OrderRepository;

@Controller
public class AdminOrderProcessController {

	@Autowired private OrderRepository orderRepository;
	@Autowired private OrderItemRepository orderItemRepository;
	
	@GetMapping("/admin/order/process/form/{orderId}")
	public String processForm(
			@PathVariable("orderId") Integer orderId,
			Model model ) {
		
		Optional<Order> order = orderRepository.findById(orderId);
		List<OrderItem> orderItems =  orderItemRepository.findByOrderId(orderId);
		// System.out.println("orderItems: " + orderItems.size());
	  System.out.println("orderItems :" + orderItems);
		model.addAttribute("order", order);
		model.addAttribute("orderItems", orderItems);
		return "admin/order/orderProcess"; 
	}

	
	
	 
}

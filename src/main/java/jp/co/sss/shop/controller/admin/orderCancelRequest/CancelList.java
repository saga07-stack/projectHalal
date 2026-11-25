package jp.co.sss.shop.controller.admin.orderCancelRequest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import jakarta.servlet.http.HttpSession;
import jp.co.sss.shop.entity.Order;
import jp.co.sss.shop.entity.OrderItem;
import jp.co.sss.shop.repository.OrderItemRepository;
import jp.co.sss.shop.repository.OrderRepository;

@Controller	
public class CancelList {

	@Autowired private OrderRepository orderRepository;
	
  @Autowired private OrderItemRepository orderItemRepository;
	
	@RequestMapping(path = "/admin/cancel/list", method = {RequestMethod.POST , RequestMethod.GET})
	public String list (HttpSession session,
        Model model 
			)
	{
		List<Order> orders = orderRepository.findByCancelFlag(3);
		Boolean cancelOrders = false;
		if(orders.isEmpty()) {
			cancelOrders = true;
			 model.addAttribute("cancelOrders", cancelOrders);
			model.addAttribute("message", "No Cancelled Orders Found.");
		}else {
			cancelOrders = false;
			 model.addAttribute("cancelOrders", cancelOrders);
			model.addAttribute("orders", orders);
		}
		
		
		return "admin/order/cancel/list";
		
	}
	
	
	@RequestMapping(path = "/admin/order/cancel/detail/{id}", method = {RequestMethod.POST, RequestMethod.GET})
	public String details (
			Model model 
			) {
		OrderItem orderItem =  orderItemRepository.findById(1).orElse(null);
		if(orderItem == null) {
			return "redirect:/";
			
		}
		model.addAttribute("order", orderItem);
		return "admin/order/cancel/details";
	}
}

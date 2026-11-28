package jp.co.sss.shop.controller.admin.orderCancelRequest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import jakarta.servlet.http.HttpSession;
import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.entity.Order;
import jp.co.sss.shop.entity.OrderItem;
import jp.co.sss.shop.entity.User;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.repository.OrderItemRepository;
import jp.co.sss.shop.repository.OrderRepository;

@Controller	
public class CancelList {
    
	@Autowired private HttpSession session;
	@Autowired private OrderRepository orderRepository;
	@Autowired private ItemRepository itemRepository;
  @Autowired private OrderItemRepository orderItemRepository;
	
	@RequestMapping(path = "/admin/order/cancel/list", method = {RequestMethod.POST , RequestMethod.GET})
	public String list (HttpSession session,
        Model model 
			)
	{
		List<Order> orders = orderRepository.findByCancelFlag(1);
		  UserBean user = (UserBean) session.getAttribute("user");
		  if(user == null) {
			  return "redirect:/login";
		  }
		/* trying to print the total price of the order and the order item id but it is not working as expected.
		 * for(Order order : orders) { for(OrderItem orderItem :
		 * order.getOrderItemsList()) { System.out.println("orderTotal : " +
		 * orderItem.getTotal()); System.out.println("orderItem : " +
		 * orderItem.getId()); } System.out.println("orderId : " + order.getId());
		 * 
		 * }
		 */		
		
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
			Model model ,
			@PathVariable Integer id,
			HttpSession session
			) { 
		UserBean user = (UserBean) session.getAttribute("user");
		 if(user == null) {
			  return "redirect:/login";
		  }
		OrderItem orderItem =  orderItemRepository.findById(id).orElse(null);

		Order order = orderRepository.findById(id).orElse(null);
		 
		
		model.addAttribute("user",user);
		model.addAttribute("cancelStatus", order.getCancelFlag());
		model.addAttribute("orders", order); 
		model.addAttribute("order", orderItem);
		return "admin/order/cancel/details";
	}
	
	@RequestMapping (path ="/admin/order/cancel/approve/{id}", method = {RequestMethod.POST, RequestMethod.GET})
	public String cancelApprove(
			@PathVariable Integer id, 
			HttpSession session
			) {
		  UserBean users = (UserBean) session.getAttribute("user");
	      if(users == null) {
		
	    	  return "redirect:/login";
	      }
		Order order = orderRepository.findById(id).orElse(null);
		
		List <OrderItem> orderItems = orderItemRepository.findByOrderIdQuery(id);
	    
		
	    
		for(OrderItem orderItem : orderItems) {
			Item item = itemRepository.findById(orderItem.getItem().getId()).orElse(null);
	        item.setStock(orderItem.getQuantity() + item.getStock());
	        itemRepository.save(item);		
			System.out.println("orderItemId : " + orderItem.getId());
			System.out.println("orderItem"+ orderItem.getItem().getId());
		}

		
		
		  order.setStatus("cancelled Approved");
		  order.setCancelFlag(2);
		  orderRepository.save(order);
		 return "redirect:/admin/order/cancel/list";
	}
	
	@RequestMapping (path ="/admin/order/cancel/reject/{id}", method= {RequestMethod.POST, RequestMethod.GET})
	public String cancelReject(
			@PathVariable Integer id
			) {
		UserBean users = (UserBean) session.getAttribute("user");
		if(users == null) {
			return "redirect:/login";
		}
		
		Order order = orderRepository.findById(id).orElse(null);
		
		
		  order.setStatus("cancelled Rejected");
		  System.out.println("userDetails" + order.getUser().getEmail());
		  order.setCancelFlag(5);
		  orderRepository.save(order);
		
		
		return "redirect:/admin/order/cancel/list";
	}
	
}

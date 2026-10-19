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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.shop.bean.BasketBean;
import jp.co.sss.shop.bean.OrderBean;
import jp.co.sss.shop.bean.OrderItemBean;
import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.Order;
import jp.co.sss.shop.entity.OrderItem;
import jp.co.sss.shop.repository.OrderItemRepository;
import jp.co.sss.shop.repository.OrderRepository;

@Controller
public class UserOrderHistoryController {

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderRepository orderRepository;
 
	/*
	 * @RequestMapping(path = "/client/order/list", method = { RequestMethod.GET,
	 * RequestMethod.POST }) public String basketList(HttpSession session, Model
	 * model) {
	 * 
	 * int userId = ((UserBean) session.getAttribute("user")).getId();
	 * 
	 * if(userId == null) { return "redirect:/login"; }
	 * 
	 * 
	 * List<Order> orders = orderRepository.findById(userId); List<OrderBean>
	 * orderBeans = new ArrayList<>();
	 * 
	 * if(orders.isEmpty()) { model.addAttribute("message", "注文履歴はありません。"); }
	 * 
	 * for(Order order : orders) { OrderBean orderBean = new OrderBean();
	 * orderBean.setId(order.getId());
	 * orderBean.setPostalCode(order.getPostalCode());
	 * orderBean.setAddress(order.getAddress());
	 * 
	 * orderBeans.add(orderBean); } model.addAttribute("orders", orderBeans);
	 * 
	 * return "client/basket/basket"; }
	 */
	
	
	@GetMapping("/client/order/list")
	public String orderList(HttpSession session, Model model) {
	    UserBean user = (UserBean) session.getAttribute("user");
	    List<Order> orders = orderRepository.findByUserIdPendingFirst(user.getId());
	    
	    model.addAttribute("orders", orders);
	    return "client/basket/basket";
	}

}

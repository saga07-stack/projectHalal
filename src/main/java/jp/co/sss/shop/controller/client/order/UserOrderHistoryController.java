package jp.co.sss.shop.controller.client.order;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.shop.bean.BasketBean;
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
	
	@RequestMapping(path = "/client/order/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String basketList(HttpSession session, Model model) {
		
		int userId = ((UserBean) session.getAttribute("user")).getId();
		System.out.println("userId: " + userId);
		
		Optional<Order> orders = orderRepository.findById(userId);
		
		
		// list型にsessionからbasketBeanの値を取得している。
		List<BasketBean> basketBeans = (List<BasketBean>) session.getAttribute("basketBeans");
		// requestスコープにbasketBeansの値を代入
		model.addAttribute("basketBeans", basketBeans);
		System.out.println("basketBeans: ");
System.out.println("basketlist triggered");
		return "client/basket/list";
	}
	
	
	
	
	
}

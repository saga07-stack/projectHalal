package jp.co.sss.shop.controller.client.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;
import jp.co.sss.shop.bean.BasketBean;
import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.CartItems;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.repository.CartRepository;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.repository.OrderRepository;

@Controller
public class BasketController {

	@Autowired OrderRepository orderRepository;
	
	@Autowired CartRepository cartRepository;
	
	@Autowired HttpSession session;
	
	@Autowired ItemRepository itemRepository;

//	@RequestMapping(path ="client/basket/list", method = { RequestMethod.GET,RequestMethod.POST})
//	public String basketList(HttpSession session,
//			Model model  ) throws InterruptedException {
//		if(session.getAttribute("user") == null) {
//			return "redirect:/login";
//		}
//		
//		List<BasketBean> basketBeans = (List<BasketBean>) session.getAttribute("basketBeans");
//		// requestスコープにbasketBeansの値を代入
//		model.addAttribute("basketBeans", basketBeans);
//		
//		
//		return "client/basket/list";
//	}
//	
	
	@RequestMapping(path ="client/basket/list", method = { RequestMethod.GET,RequestMethod.POST})
	public String basketList(HttpSession session,
			Model model  ) {
		if(session.getAttribute("user") == null) {
			return "redirect:/login";
		}
		 int  userId = ((UserBean) session.getAttribute("user")).getId();
					
		List<CartItems> cartItems =  cartRepository.findAllByUserId(userId);
	//	List<Item> items = itemRepository.find
		System.out.println("カートの中身"+cartItems.get(0).getItemId());
		System.out.println("カートの中身"+cartItems.get(1).getItemId());
		

	System.out.println("カートの中身"+cartItems.get(0).getQuantity());
		
		 return "client/basket/list";
	} 
	  
	   
	
	
}

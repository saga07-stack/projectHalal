package jp.co.sss.shop.controller.client.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;
import jp.co.sss.shop.repository.OrderRepository;

@Controller
public class BasketController {

	@Autowired OrderRepository orderRepository;
	
	@Autowired HttpSession session;
	
	
	@RequestMapping(path ="client/basket/list", method = { RequestMethod.GET,RequestMethod.POST})
	public String basketList(HttpSession session,
			Model model  ) {
		if(session.getAttribute("user") == null) {
			return "redirect:/login";
		}
		
		return "client/basket/list";
	}
	
	
	
	
	
}

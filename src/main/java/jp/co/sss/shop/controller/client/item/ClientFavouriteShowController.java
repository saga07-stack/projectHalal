package jp.co.sss.shop.controller.client.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class ClientFavouriteShowController {

	@Autowired HttpSession session;
	
	@GetMapping("/items/favourites/list")
	public String favouriteList(
		HttpSession session 
		, Model model 
			) {
		
		if(session.getAttribute("user") == null) {
				return "redirect:/login";
			}
		return "client/favourite/list";
		
	}
	
	
	
	
}

package jp.co.sss.shop.controller.client.header;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HeaderController {

	@GetMapping("/Restaurant/store")
	public String showStores(
			Model model )
	{
		model.addAttribute("activePage", "store");
		return "Restaurant/stores";
	}
	
	
	@GetMapping("/Restaurant/about")
	public String showAbout(
			Model model )
	{
		model.addAttribute("activePage", "about");
		return "Restaurant/aboutUs";
	}
}

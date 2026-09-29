package jp.co.sss.shop.controller.client.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.shop.repository.ItemRepository;

@Controller
public class ItemDetails {

	
	@Autowired ItemRepository itemRepository;
	
	
	@GetMapping("/items/details/{id}")
	public String itemDetails(
			@RequestParam ("id") int id,
			Model model
			) {
		
	 model.addAttribute("item", itemRepository.findById(id).orElse(null));
	  return "client/item/details";
	}
	
	
}

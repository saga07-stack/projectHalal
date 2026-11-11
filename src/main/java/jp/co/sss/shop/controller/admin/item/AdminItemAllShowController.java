package jp.co.sss.shop.controller.admin.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.util.Constant;

@Controller
public class AdminItemAllShowController {
	
	
	@Autowired
	private ItemRepository itemRepository;
	
	@GetMapping("/admin/item/search")
	public String showAllItems( 
			@RequestParam("keyword") String keyword,
			HttpSession session 
			
			) { 
		session.setAttribute("keyword", keyword);
		
		return"redirect:/admin/item/listAll";
		
	}
	
	@GetMapping("admin/item/listAll")
	public String showAllItemsList(
		
		HttpSession session,
		Model model
			) {
		System.out.println("keyword: " +session.getAttribute("keyword"));
    String keyword = (String) session.getAttribute("keyword");
		List<Item> items = itemRepository.findByNameContainingAndDeleteFlag(keyword,0);
		
		model.addAttribute("items", items);
		session.removeAttribute("keyword");
		
		return "admin/item/listAll";
	}
	
	@GetMapping("/admin/item/search/list")
	public String showAllItemsLists(
			@RequestParam("keyword") String keyword,
			HttpSession session,
			Model model
				) {
		//	System.out.println("keyword: " +session.getAttribute("keyword"));
	    //String keyword = (String) session.getAttribute("keyword");
			List<Item> items = itemRepository.findByNameContainingAndDeleteFlag(keyword,0);
			
			model.addAttribute("items", items);
			
			
			return "admin/item/listAll";
		}
	
	
}

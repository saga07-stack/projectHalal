package jp.co.sss.shop.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.sss.shop.DTOService.ItemService;
import jp.co.sss.shop.RestControllerDTO.ItemDto;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.repository.ItemRepository;

@RestController
@RequestMapping("/items")
public class ItemsRestController {

	@Autowired ItemRepository itemRepository;

	@Autowired ItemService itemService;
	
	@GetMapping("/all")
	public List<ItemDto> getItems() {
		
		
		
		return itemService.getAllItems();
		
	}
	
	
	
	

}

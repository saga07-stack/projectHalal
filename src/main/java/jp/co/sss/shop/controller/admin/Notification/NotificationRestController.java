package jp.co.sss.shop.controller.admin.Notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.sss.shop.RestControllerDTO.ItemDto;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.repository.ItemRepository;

@RestController
@RequestMapping("/admin/notification")
public class NotificationRestController {

	@Autowired
	private ItemRepository itemRepo;
	
	@GetMapping("/list")
	ResponseEntity<?> getNotificationList(){
		
		List<Item> items = itemRepo.findByStockLessThenFiveAndDeleteFlagOrderByInsertDateDesc();
		
		
			
			List<ItemDto> itemDtos = items.stream()
					.map(item -> new ItemDto(
							item.getStock(),
							item.getName(),
							item.getDescription(),
							item.getPrice(),
							item.getImage(),
							item.getId(),
							item.getCategory().getName()))
					.toList();
			
			return ResponseEntity.ok(itemDtos);
		
		
	}
	
	
}

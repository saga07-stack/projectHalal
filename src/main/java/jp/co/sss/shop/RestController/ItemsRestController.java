package jp.co.sss.shop.RestController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.shop.DTOService.ItemService;
import jp.co.sss.shop.RestControllerDTO.ItemDto;
import jp.co.sss.shop.bean.BasketBean;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.repository.OrderRepository;
import jp.co.sss.shop.repository.UserRepository;

@RestController
@RequestMapping("/items")
public class ItemsRestController {

	@Autowired ItemRepository itemRepository;

	@Autowired ItemService itemService;
	
	@Autowired UserRepository userRepository;
	
	@Autowired
	HttpSession session;
	
	@Autowired 
	OrderRepository orderRepository;
	@GetMapping("/all")
	public List<ItemDto> getItems() {
		
		
		
		return itemService.getAllItems();
		
	}
	
	@GetMapping("/cart/add/{id}")
	public ResponseEntity<?> addToCart(@PathVariable int id, HttpSession session) {
		List<BasketBean> basketBeans = (List<BasketBean>) session.getAttribute("basketBeans");
		
		if(session.getAttribute("user") == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("notLogin");
		}
			System.out.println("ユーザーがログインしています");
		ItemDto item = itemService.getItemById(id);
			if (item == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("商品が見つかりません");
			}else {
			//	List<BasketBean> basketBeans = (List<BasketBean>) session.getAttribute("basketBeans");
				if (basketBeans == null) {
					// 買い物かごが空の場合
					basketBeans = new ArrayList<>();
					BasketBean basketBeanNew = new BasketBean(item.getId(),item.getName(),item.getStock());
					basketBeans.add(basketBeanNew);
				}
				
				System.out.println("basketBeans:" + basketBeans.size());
				orderRepository.save(basketBeans);
				
			
			// カートに商品を追加する処理をここに実装する
			session.setAttribute("basketBeans", basketBeans);
			  
			return ResponseEntity.ok(itemService.getItemById(id));
		}
		
	}
	

	
	@GetMapping("/cart/list")
	public ResponseEntity<?> getCartItems(HttpSession session) {
	    if (session.getAttribute("basketBeans") == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("カートに商品がありません");
	    }

	    // ✅ Session बाट actual cart data निकाल्ने
	    List<BasketBean> basketBeans = (List<BasketBean>) session.getAttribute("basketBeans");

	    return ResponseEntity.ok(basketBeans);
	}
	
	@GetMapping("/favorite/list")
	public ResponseEntity<?> getFavoriteList(HttpSession session) {
		
		if(session.getAttribute("user") == null) {
			System.out.println("ユーザーがログインしていません");
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("notLogin");
		}else {
			System.out.println("ユーザーがログインしています");
		//ItemDto item = itemService.getItemById(id);
			
			// カートに商品を追加する処理をここに実装する
			
			  
			return ResponseEntity.ok("お気に入りリストを取得しました");
		}
	
	
	}
	
	@GetMapping("/search")

	public ResponseEntity<?> searchItems(@RequestParam String keyword) {
		
		List<ItemDto> items = itemService.getAllItems().stream()
				.filter(item -> item.getName().toLowerCase().startsWith(keyword.toLowerCase()))
				.toList();
		if(items.isEmpty()) {
			System.out.println("商品が見つかりません");

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("商品が見つかりません");
		}else {
			return ResponseEntity.ok(itemService.searchItems(keyword));
		}
		
		
	}
	
	
	
	
//	@GetMapping("/detail/{id}")
//	public ItemDto getItemsDetail(@PathVariable int id) {
//		System.out.println("triggred");
//		return itemService.itemsDetails(id);
//	}
//	
}

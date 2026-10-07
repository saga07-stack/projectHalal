package jp.co.sss.shop.DTOService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.shop.RestControllerDTO.ItemDto;
import jp.co.sss.shop.bean.BasketBean;
import jp.co.sss.shop.entity.CartItems;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.repository.CartRepository;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.repository.OrderItemRepository;

@Service
public class ItemService {
	
	private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

	@Autowired
  private	ItemRepository itemRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	HttpSession session;
	
	
	public List<ItemDto> getAllItems() {
		List<ItemDto> items = itemRepository.findByDeleteFlagNot(1).stream()
				.map(item -> new ItemDto(
						item.getName(),
						item.getDescription(),
						item.getPrice(),
						item.getImage(),
						item.getId(),
						item.getCategory() != null ? item.getCategory().getName() : "Unknown"))
				.collect(Collectors.toList());
		logger.info("getAllItems() returned {} items", items.size());
		return items;
	}
	
	
	public ItemDto getItemById(int id) {
	    return itemRepository.findById(id)
	            .map(itemEntity -> new ItemDto(
	                    itemEntity.getName(),
	                    itemEntity.getDescription(),
	                    itemEntity.getPrice(),
	                    itemEntity.getImage(),
	                    itemEntity.getId(),
	                    itemEntity.getCategory() != null ? itemEntity.getCategory().getName() : "Unknown"))
	            .orElse(null);
	}
		
	@Autowired
	private CartRepository cartRepository;

	@Transactional
	public void addToCart(Integer userId, Integer itemId) {

	    Item item = itemRepository.findById(itemId).orElse(null);
	    if (item == null || item.getDeleteFlag() == 1) {
	        throw new IllegalArgumentException("item chaina");
	    }

	    CartItems cart = cartRepository.findByUserIdAndItemId(userId, itemId);

	    if (cart == null) {
	        cart = new CartItems();
	        cart.setUserId(userId);
	        cart.setItemId(itemId);
	        cart.setQuantity(1);
	    } else {
	        if (cart.getQuantity() + 1 > item.getStock()) {
	            throw new IllegalStateException("stock pugena");
	        }
	        cart.setQuantity(cart.getQuantity() + 1);
	    }
	    cartRepository.save(cart);
	}
	
	
	public List<BasketBean> getCartItems() {
		List<BasketBean> basketBeans = (List<BasketBean>) session.getAttribute("basketBeans");
		return basketBeans;
	}
	

	public ItemDto itemsDetails (int id ) {
		
		ItemDto item = itemRepository.findById(id)
				.map(itemEntity -> new ItemDto(
						itemEntity.getName(),
						itemEntity.getDescription(),
						itemEntity.getPrice(),
						itemEntity.getImage(),
						itemEntity.getId(),
						itemEntity.getCategory() != null ? itemEntity.getCategory().getName() : "Unknown"))
				.orElse(null);
		return item;
				
	}

	public List<ItemDto> searchItems(String keyword){
		
		return itemRepository.findByNameContainingAndDeleteFlag(keyword, 0).stream()
				.map(item -> new ItemDto(
						item.getName(),
						item.getDescription(),
						item.getPrice(),
						item.getImage(),
						item.getId(),
						item.getCategory() != null ? item.getCategory().getName() : "Unknown"))
				.collect(Collectors.toList());
						
	}
	
	public List<ItemDto> getItemsStock(){
		
		List <Item> items = itemRepository.findByStockLessThenFiveAndDeleteFlagOrderByInsertDateDesc();
				
		
		return items.stream()
				.map(item -> new ItemDto(
						item.getName(),
						item.getDescription(),
						item.getPrice(),
						item.getImage(),
						item.getId(),
						item.getCategory() != null ? item.getCategory().getName() : "Unknown"))
				.collect(Collectors.toList());
	}


	
	

}

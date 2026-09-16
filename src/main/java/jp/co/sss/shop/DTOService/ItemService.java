package jp.co.sss.shop.DTOService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.shop.RestControllerDTO.ItemDto;
import jp.co.sss.shop.bean.BasketBean;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.repository.OrderItemRepository;

@Service
public class ItemService {

	@Autowired
  private	ItemRepository itemRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	HttpSession session;
	
	
	public List<ItemDto> getAllItems() {
		return itemRepository.findAll().stream()
				.map(item -> new ItemDto(
						item.getName(),
						item.getDescription(),
						item.getPrice(),
						item.getImage(),
						item.getId(),
						item.getCategory().getName()))
				.collect(Collectors.toList());
	}
	
	
	public ItemDto getItemById(int id) {
		
		ItemDto item = itemRepository.findById(id)
				.map(itemEntity -> new ItemDto(
						itemEntity.getName(),
						itemEntity.getDescription(),
						itemEntity.getPrice(),
						itemEntity.getImage(),
						itemEntity.getId(),
						itemEntity.getCategory().getName()))
				.orElse(null);
		
		
		List<BasketBean> basketBeans = (List<BasketBean>) session.getAttribute("basketBeans");
		
		if(basketBeans == null) {
			basketBeans = new ArrayList<>();
			BasketBean newBasketBeans = new BasketBean(item.getId(),item.getName(),item.getPrice(),item.getImagePath());
			basketBeans.add(newBasketBeans);
			session.setAttribute("basketBeans", basketBeans);
		} else {
				
			for(int i = 0; i<basketBeans.size(); i++) {
				
				if(basketBeans.get(i).getId() == item.getId()) {
					
					basketBeans.get(i).setOrderNum(basketBeans.get(i).getOrderNum() + 1);
					session.setAttribute("basketBeans", basketBeans);
					break;
				} else {
					BasketBean newBasketBeans = new BasketBean(item.getId(),item.getName(),item.getPrice(),item.getImagePath());
					basketBeans.add(newBasketBeans);
					session.setAttribute("basketBeans", basketBeans);
					break;
					
					
				}
			}
				
			}
			
		session.setAttribute("basketBeans", basketBeans);
		
		return item;
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
						itemEntity.getCategory().getName()))
				.orElse(null);
		return item;
				
	}
}

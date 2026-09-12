package jp.co.sss.shop.DTOService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.shop.RestControllerDTO.ItemDto;
import jp.co.sss.shop.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired
  private	ItemRepository itemRepository;
	
	public List<ItemDto> getAllItems() {
		return itemRepository.findAll().stream()
				.map(item -> new ItemDto(
						item.getName(),
						item.getDescription(),
						item.getPrice(),
						item.getImage(),
						item.getCategory().getName()))
				.collect(Collectors.toList());
	}
	
	
	
	
}

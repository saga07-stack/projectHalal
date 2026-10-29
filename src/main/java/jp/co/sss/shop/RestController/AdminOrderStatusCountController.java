package jp.co.sss.shop.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/")
public class AdminOrderStatusCountController {

	
	@RequestMapping("menu")
	ResponseEntity<?> getOrderStatusCount(){
		
		
		return ResponseEntity.ok("ok");
	}
	
	
}

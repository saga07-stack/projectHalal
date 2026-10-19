package jp.co.sss.shop.controller.admin.Expired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExpiredList {

	@GetMapping("/admin/expired/list/all")
	public String expiredList() {
		
		return "admin/expired/list";
		
	}
	
	
	
}
package jp.co.sss.shop.controller.client.order;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class CancleOrderController {

	
	@RequestMapping(path = "cancel/order/input/form", method = {RequestMethod.GET, RequestMethod.POST})
	public String cancleOrderProcess(
			Model  model ,
			HttpSession session
			) {
		
		return "client/order/cancleOrderInputForm";
	}
	
	@RequestMapping(path ="client/order/cancel/confirmation", method = {RequestMethod.GET, RequestMethod.POST})
	public String cancelOrderConfirmation(
			@RequestParam (name = "reasons", required = false) List<String> reasons ,
			@RequestParam (name ="note", required = false ) String note,
			Model model,
			HttpSession session
			) throws Exception {
		System.out.println("note : " + note);
		Boolean errorFlag = false;
		if(reasons == null || reasons.isEmpty() ) {
			errorFlag = true;
			model.addAttribute("error", "Please Select One Of The Reason Below To Process The Cancel" );
			return "client/order/cancleOrderInputForm";
		}else  {
			for(String reason : reasons) {
				if(reason.equalsIgnoreCase("Other") && note.trim().isEmpty() ) {
					errorFlag = true;
					model.addAttribute("error", "You Have Select The Other Options So Please Provide The Reason For The Cancel");
					
				return "client/order/cancleOrderInputForm";
				}
			}
		} {
		for(String reason : reasons) {
			
			model.addAttribute("reasons", reason);
		}
			
		}
		
		return "client/order/orderCancelConfirmation";
	}
	
}

package jp.co.sss.shop.controller.client.order;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.shop.entity.Order;
import jp.co.sss.shop.entity.OrderInquiries;
import jp.co.sss.shop.repository.OrderInquiriesRepository;
import jp.co.sss.shop.repository.OrderRepository;

@Controller
public class CancleOrderController {

	@Autowired private OrderInquiriesRepository oIRepository; 
	@Autowired private OrderRepository oRepo;
	
	@RequestMapping(path = "cancel/order/input/form", method = {RequestMethod.GET, RequestMethod.POST})
	public String cancleOrderProcess(
			@RequestParam(name ="orderNumber", required = false ) Integer orderId,
			Model  model ,
			HttpSession session
			) {
		model.addAttribute("orderNumber", orderId);
		model.addAttribute("orderId", orderId);
		System.out.println("orderId : " + orderId);
		
		return "client/order/cancleOrderInputForm";
	}
	
	@RequestMapping(path ="client/order/cancel/confirmation", method = {RequestMethod.GET, RequestMethod.POST})
	public String cancelOrderConfirmation(
			@RequestParam (name = "reasons", required = false) List<String> reasons ,
			@RequestParam (name ="note", required = false ) String note,
			@RequestParam(name="orderNumber" ,required = false)  Integer orderId,
			Model model,
			HttpSession session
			) throws Exception {
		session.setAttribute("reasons", reasons);
		session.setAttribute("note", note);
		System.out.println("orderId : " + orderId);
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
		session.setAttribute("cancelOrderNumber", orderId );
		return "client/order/orderCancelConfirmation";
	}
	
	@RequestMapping(value = "/client/order/cancel/request/send", method = {RequestMethod.GET, RequestMethod.POST})
    public String sendCancelRequest(
         HttpSession session    		
    		)	{
 
		session.getAttribute("cancelOrderNumber");
		Integer orderId = (Integer) session.getAttribute("cancelOrderNumber");
		
		Order order = oRepo.findById(orderId).orElse(null);
		order.setStatus("cancelled Requested");
		order.setCancelFlag(1);
	  Date currentDate = new Date();
		oRepo.save(order);
		OrderInquiries orderInquiries = new OrderInquiries();
		orderInquiries.setOrderId(orderId);
		orderInquiries.setCustomerName(order.getName());
		orderInquiries.setCancelStatus(1);
		orderInquiries.setInquiryCode("CNCL" + orderId);
		orderInquiries.setUsedFlag(0);
		orderInquiries.setSentBy(1);
		orderInquiries.setSentAt(currentDate);
		orderInquiries.setMemo(session.getAttribute("reason"));
		
		oIRepository.save(orderInquiries);
		session.removeAttribute("cancelOrderNumber");
		return "client/order/cancelRequestComplete";
	}
	
	
}

package jp.co.sss.shop.controller.client.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/client/order/payment")
public class PaymentController {
	
	
	@Value("${stripe.Secret.key}")
	private String stripeSecretKey;
	
	@RequestMapping(path = "/check", method = {RequestMethod.GET,RequestMethod.POST})
	public String paymentMethodCheck(
			Model model,
			HttpSession session
			){
		if(session.getAttribute("user") == null) {
			return "redirect:/login";
		}
		
		//System.out.println("paymentType: " + session.getAttribute("paymentType"));
//		if(session.getAttribute("paymentType").equals(1))
//		{	
//			
//			return "client/order/Credit";
//		}	else if(session.getAttribute("paymentType").equals(2)) {
//			
//		
//			
//			//System.out.println("paymentType: " + session.getAttribute("bank tranfer"));
//			
//			return "client/order/BankTransfer";
//		
//		}else if(session.getAttribute("paymentType").equals(3)) {
//		
//			System.out.println("other  3");
//			
//			return "client/order/onarrival";
//		}else if(session.getAttribute("paymentType").equals(4)) {
//			
//			System.out.println("other  4");
//			return "client/order/emoney";
//			
//		}else if(session.getAttribute("paymentType").equals(5)) {
//			
//			System.out.println("other 5 ");
//			return "client/order/convenience";
//		}
//		
//		return"";
//	}
		return "client/order/Credit";
	}
	
	
	 @PostMapping("/checkout")
	    public String checkout(
	    		HttpSession session,
	    		Model model 
	    		) throws StripeException {
	        Stripe.apiKey = stripeSecretKey;
      int totalPrice = (int) session.getAttribute("totalPrice");
	        SessionCreateParams params = SessionCreateParams.builder()
	            .setMode(SessionCreateParams.Mode.PAYMENT)
	            .setSuccessUrl("http://localhost:55000/shared_shop/client/order/payment/success")
	            .setCancelUrl("http://localhost:55000/shared_shop/client/order/payment/cancel")
	            .addLineItem(SessionCreateParams.LineItem.builder()
	                .setQuantity(1L)
	                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
	                    .setCurrency("jpy")
	                    .setUnitAmount((long) totalPrice)
	                    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
	                        .setName("Test Item")
	                        .build())
	                    .build())
	                .build())
	            .build();
         model.addAttribute("totalPrice", totalPrice);
	        Session sessions = Session.create(params);
	        return "redirect:" + sessions.getUrl();
	    }
	 
	 @GetMapping("/success")
	   public String success(
			   HttpSession session,
			   Model model
			   ) {
		 
		 
	        return "client/order/list";
	    }

	    @GetMapping("/cancel")
	
	    public String cancel() {
	        return "redirect:/client/order/payment/check";
	    }
	
}

package jp.co.sss.shop.controller.client.payment;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import jakarta.transaction.Transactional;
import jp.co.sss.shop.bean.OrderItemBean;
import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.CartItems;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.entity.Order;
import jp.co.sss.shop.entity.OrderItem;
import jp.co.sss.shop.form.OrderForm;
import jp.co.sss.shop.repository.CartRepository;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.repository.OrderItemRepository;
import jp.co.sss.shop.repository.OrderRepository;
import jp.co.sss.shop.repository.UserRepository;

@Controller
@RequestMapping("/client/order/payment")
public class PaymentController {
	 
	@Autowired private CartRepository cartRepository;
	@Autowired private UserRepository userRepository;
	
	@Autowired private OrderRepository orderRepository;
	@Autowired private OrderItemRepository orderItemRepository;
	@Autowired private ItemRepository itemRepository;
	
	@Value("${stripe.Secret.key}")
	private String stripeSecretKey;
	
	@RequestMapping(path = "/check", method = {RequestMethod.GET,RequestMethod.POST})
	public String paymentMethodCheck(
			Model model,
			HttpSession session
			){
		if(session.getAttribute("user") == null) {
			model.addAttribute("message", "Session time out");
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
	    	// Check if session has timed out
	    	if(session.getAttribute("user") == null) {
	    		model.addAttribute("message", "Session time out");
	    		return "redirect:/login";
	    	}
	    	
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
	 @Transactional
	 public String success(HttpSession session, Model model) {

	     UserBean userBean = (UserBean) session.getAttribute("user");
	     if(userBean == null) {
	    	 return "redirect:/login";
	     }
	     OrderForm orderForm = (OrderForm) session.getAttribute("orderForm");
	     Integer userId = userBean.getId();
         List<OrderItemBean> orderItemBeans = (List<OrderItemBean>) session.getAttribute("orderItemBeans");
	     List<CartItems> cartItems = cartRepository.findByUserId(userId);
	     

	     // refresh garda duplicate order nabanos
	     if (cartItems.isEmpty() || orderForm == null) {
	         return "redirect:/client/order/list";
	     }

	     // 1. Order EK PATAK matra banaune (loop bahira)
	     Order order = new Order();
	     order.setPostalCode(orderForm.getPostalCode());
	     order.setAddress(orderForm.getAddress());
	     order.setName(orderForm.getName());
	     order.setPhoneNumber(orderForm.getPhoneNumber());
	     order.setPayMethod(orderForm.getPayMethod());
	     order.setStatus("pending");
	     order.setUser(userRepository.getReferenceById(userId));
	     order = orderRepository.save(order);
	     
	     
	     for (CartItems ci : cartItems) {

	         // DB bata asal item (sabai field sahit)
	         Item item = itemRepository.findById(ci.getItemId())
	                 .orElseThrow(() -> new IllegalStateException("Item chaina"));

	         int qty = ci.getQuantity();

	         if (item.getStock() < qty) {
	             throw new IllegalStateException(item.getName() + " ko stock pugena");
	         }

	         // 1. stock ghataune (tyahi object edit, new Item() hoina)
	         item.setStock(item.getStock() - qty);

	         // 2. order item banaune
	         OrderItem oi = new OrderItem();
	         oi.setOrder(order);
	         oi.setItem(item);
	         oi.setQuantity(qty);
	         oi.setPrice(item.getPrice());
	         orderItemRepository.save(oi);
	     }
	    	
	    	 
	     
	     
	     

			/*
			 * // 2. Cart ko har item ko lagi OrderItem for (CartItems ci : cartItems) {
			 * OrderItem orderItem = new OrderItem();
			 * 
			 * orderItem.setOrder(order);
			 * 
			 * Integer item = ci.getItemId(); orderItem.setItem(item);
			 * orderItem.setQuantity(ci.getQuantity()); orderItem.setPrice(item.getPrice());
			 * orderItemRepository.save(orderItem);
			 * 
			 * 
			 * // stock ghataune item.setStock(item.getStock() - ci.getQuantity()); }
			 */

	     // 3. Cart khali, session saaf
	     cartRepository.deleteAll(cartItems);
	     session.removeAttribute("orderForm");
	     session.removeAttribute("orderItemBeans");

	     model.addAttribute("order", order);
	     return "client/order/complete";
	 }
	    @GetMapping("/cancel")
	
	    public String cancel() {
	        return "redirect:/client/order/payment/check";
	    }
	
}

package jp.co.sss.shop.controller.client.item;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.shop.bean.BasketBean;
import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.CartItems;
import jp.co.sss.shop.entity.Item;
import jp.co.sss.shop.repository.CartRepository;
import jp.co.sss.shop.repository.ItemRepository;
import jp.co.sss.shop.repository.OrderRepository;

@Controller
public class BasketController {

	@Autowired OrderRepository orderRepository;
	
	@Autowired CartRepository cartRepository;
	
	@Autowired HttpSession session;
	
	@Autowired ItemRepository itemRepository;

	@Autowired
     (required = false)	UserBean userBean;
//	@RequestMapping(path ="client/basket/list", method = { RequestMethod.GET,RequestMethod.POST})
//	public String basketList(HttpSession session,
//			Model model  ) throws InterruptedException {
//		if(session.getAttribute("user") == null) {
//			return "redirect:/login";
//		}
//		
//		List<BasketBean> basketBeans = (List<BasketBean>) session.getAttribute("basketBeans");
//		// requestスコープにbasketBeansの値を代入
//		model.addAttribute("basketBeans", basketBeans);
//		
//		
//		return "client/basket/list";
//	}
//	
	
	@GetMapping("/client/basket/list")
	public String list(HttpSession session, Model model) {

	    UserBean userBean = (UserBean) session.getAttribute("user");   // tapai ko asli key
	    if (userBean == null) {
	        return "redirect:/login";
	    }

	    List<CartItems> cartItems = cartRepository.findByUserId(userBean.getId());
     if(cartItems.isEmpty()) {
    	 System.out.println("カートの中身が空です");
    	 
		 model.addAttribute("basketBeans", null);
		 session.setAttribute("basketBeans", null);
		 return "client/basket/list";
	 }else {
		 
	 
		 List<BasketBean> basketBeans = new ArrayList<>();
	    
	    for (CartItems c : cartItems) {
	        Item item = itemRepository.findById(c.getItemId()).orElse(null);
	        if (item == null || item.getDeleteFlag() == 1) {
	            continue;                         // item hateko bhae skip
	        }
	        BasketBean b = new BasketBean();
	        b.setId(item.getId());
	        b.setName(item.getName());
	        b.setStock(item.getStock());
	        b.setOrderNum(c.getQuantity());       // cart_items bata
	        basketBeans.add(b);
	    }
	 
	    model.addAttribute("basketBeans", basketBeans);
	   // return "client/basket/list";
	
	 }
	//	List<Item> items = itemRepository.find
//		System.out.println("カートの中身"+cartItems.get(0).getItemId());
//		System.out.println("カートの中身"+cartItems.get(1).getItemId());
//		
//
//	System.out.println("カートの中身"+cartItems.get(0).getQuantity());
//		
		session.setAttribute("basketBeans", cartItems);       
		 return "client/basket/list";
	} 
	  
	   @PostMapping("/client/basket/delete")
	   public String deleteCartItem(
			   @RequestParam("id") int id ,
			   HttpSession session) {
		   Integer userBean = (((UserBean) session.getAttribute("user")).getId());   // tapai ko asli key
			  
		   CartItems c = cartRepository.findByUserIdAndItemId(userBean, id);
		    if (c != null) {
		        cartRepository.delete(c);
		    }
		    return "redirect:/client/basket/list";
	   }
	
	
}

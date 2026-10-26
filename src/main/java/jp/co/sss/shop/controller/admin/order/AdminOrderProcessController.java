package jp.co.sss.shop.controller.admin.order;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.sss.shop.bean.OrderInquiriesBean;
import jp.co.sss.shop.entity.Order;
import jp.co.sss.shop.entity.OrderInquiries;
import jp.co.sss.shop.entity.OrderItem;
import jp.co.sss.shop.form.OrderInquiriesForm;
import jp.co.sss.shop.repository.OrderInquiriesRepository;
import jp.co.sss.shop.repository.OrderItemRepository;
import jp.co.sss.shop.repository.OrderRepository;
import jp.co.sss.shop.repository.UserRepository;

@Controller
public class AdminOrderProcessController {
	
	
	@Autowired 
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderInquiriesRepository orderInquiriesRepository;

	@GetMapping("/admin/order/process/form/{orderId}")
	public String processForm(@PathVariable("orderId") Integer orderId, HttpSession session

	) {

		session.setAttribute("orderId", orderId);
		session.removeAttribute("orderInquiriesForm");
System.out.println("orderId: " + orderId);

		return "redirect:/admin/order/orderProcess";
	}

	@GetMapping("/admin/order/orderProcess")
	public String orderProcess(

			Model model, HttpSession session) {
		Integer orderId = (Integer) session.getAttribute("orderId");

		Order order = orderRepository.findById(orderId).orElse(null);

		List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

		OrderInquiriesForm form = (OrderInquiriesForm) session.getAttribute("orderInquiriesForm");
		model.addAttribute("orderInquiriesForm", form!=null ? form : new OrderInquiriesForm());

		
	
		model.addAttribute("order", order);
		model.addAttribute("orderItems", orderItems); 
		return "admin/order/orderProcess";
	}

	@PostMapping("/admin/order/process/confirm")
	public String processConfirm(
			@Valid @ModelAttribute OrderInquiriesForm orderInquiriesForm, BindingResult bindingResult,

			HttpSession session, Model model) {
		


		Integer orderId = (Integer) session.getAttribute("orderId");
		Order order = orderRepository.findById(orderId).orElse(null);
		List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);


//		OrderInquiriesForm form = (OrderInquiriesForm) session.getAttribute("orderInquiriesForm");
		//model.addAttribute("orderInquiriesForm", form!=null ? form : new OrderInquiriesForm());

		
	
		model.addAttribute("order", order);
		model.addAttribute("orderItems", orderItems);
		
		session.setAttribute("orderInquiriesForm", orderInquiriesForm);

		return "admin/order/orderProcessConfirm";
	}
	
	@PostMapping("/admin/order/process/success")
	public String processSuccess(
			HttpSession session, Model model
			)
	{ 
		
		//Integer userId = (Integer) session.getAttribute("userId");
		OrderInquiries oq = new OrderInquiries();
        OrderInquiriesForm oqf= (OrderInquiriesForm) session.getAttribute("orderInquiriesForm");
        oq.setOrderId(session.getAttribute("orderId") != null ? (Integer) session.getAttribute("orderId") : null);
		oq.setInquiryCode(oqf.getInquiryCode());
		oq.setCustomerName(oqf.getCustomerName());
		oq.setCustomerPhone(oqf.getCustomerPhone());  
		oq.setCustomerAddress(oqf.getCustomerAddress());
		oq.setMemo(oqf.getMemo());
		oq.setSentBy(oqf.getSentBy()); 
		oq.setSentAt(oqf.getSentAt());
		oq.setExpiresAt(oqf.getExpiresAt());
		oq.setUsedFlag(0);
		
         orderInquiriesRepository.save(oq);
         
         Order order = orderRepository.findById((Integer) session.getAttribute("orderId")).orElse(null);
         order.setStatus("out for delivery");
        	orderRepository.save(order);	 
         
         		session.removeAttribute("orderInquiriesForm");
         		session.removeAttribute("orderId");
         		session.removeAttribute("orderItems");
         		session.removeAttribute("order");
		
		return "admin/order/processSuccess";
	}
}

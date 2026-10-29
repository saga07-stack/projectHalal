package jp.co.sss.shop.DTOService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.shop.RestControllerDTO.ItemTrackingDetailsDto;
import jp.co.sss.shop.entity.OrderInquiries;
import jp.co.sss.shop.repository.OrderInquiriesRepository;


@Service
public class TrackingDetailsService {
	
	@Autowired private OrderInquiriesRepository oR;
	
	public ItemTrackingDetailsDto  getTrackingDetails(int orderId) {
		
	 OrderInquiries inq = oR.findByOrderId(orderId);
   
	 return new ItemTrackingDetailsDto(
			 inq.getOrderId(),
			 inq.getCustomerAddress(),
			 inq.getCustomerName(),
			 inq.getCustomerPhone(),
			 inq.getInquiryCode()
			 
			 
			 );

	}
	
	

}

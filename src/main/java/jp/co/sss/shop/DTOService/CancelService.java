package jp.co.sss.shop.DTOService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.shop.entity.OrderInquiries;
import jp.co.sss.shop.repository.OrderInquiriesRepository;

@Service
public class CancelService {

	
	@Autowired private OrderInquiriesRepository oR;
	
//	public List<OrderInquiries> getCancelDetails()
//	{
//		List<OrderInquiries> cancelDetails = oR.findByCancelStatus();
//	}
}

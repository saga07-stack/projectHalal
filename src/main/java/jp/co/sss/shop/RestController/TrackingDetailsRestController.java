package jp.co.sss.shop.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.sss.shop.DTOService.TrackingDetailsService;

@RestController
@RequestMapping("client/order/details/")
public class TrackingDetailsRestController {

	@Autowired private TrackingDetailsService trackingDetailsService;
	
	
   @GetMapping("trackingDetails/{orderId}")
   public ResponseEntity<?> getTrackingDetails(
		   @PathVariable("orderId") Integer orderId
		   ){
	   
return ResponseEntity.ok(trackingDetailsService.getTrackingDetails(orderId));	   
	   
   }
	
	
	
}

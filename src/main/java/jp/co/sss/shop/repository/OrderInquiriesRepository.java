package jp.co.sss.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.shop.entity.OrderInquiries;

public interface OrderInquiriesRepository extends JpaRepository<OrderInquiries, Integer> {
	// ここに必要なクエリメソッドを定義できます
	
	
OrderInquiries findByOrderId(Integer orderId);

//Optional<OrderInquiries> findByOrderId(Integer orderId);
	
}

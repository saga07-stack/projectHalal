package jp.co.sss.shop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sss.shop.entity.OrderInquiries;
import jp.co.sss.shop.entity.OrderItem;

public interface OrderInquiriesRepository extends JpaRepository<OrderInquiries, Integer> {
	// ここに必要なクエリメソッドを定義できます
	
	

//Optional<OrderInquiries> findByOrderId(Integer orderId);

	@Query("select o from OrderInquiries o where o.orderId = :orderId")
 Optional<OrderInquiries> findByOrderId( Integer orderId);
}

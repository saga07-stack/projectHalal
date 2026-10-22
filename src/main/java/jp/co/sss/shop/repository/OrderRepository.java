package jp.co.sss.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.sss.shop.entity.Order;

/**
 * ordersテーブル用リポジトリ
 *
 * @author System Shared
 */
@Repository

public interface OrderRepository extends JpaRepository<Order, Integer> {

	/**
	 * 注文日付降順で注文情報すべてを検索(管理者機能で利用)
	 * @param pageable ページング情報
	 * @return 注文エンティティのページオブジェクト
	 */
	@Query("SELECT o FROM Order o ORDER BY o.insertDate DESC,o.id DESC")
	Page<Order> findAllOrderByInsertdateDescIdDesc(Pageable pageable);
	
	

	    @Query("""
	        SELECT o FROM Order o
	        WHERE o.user.id = :userId
	        ORDER BY CASE WHEN LOWER(o.status) = 'pending' THEN 0 ELSE 1 END,
	                 o.insertDate DESC
	        """)
	    List<Order> findByUserIdPendingFirst(@Param("userId") Integer userId);
	
  Long countByStatus(String status);


@Query("""
		select count(o) from Order o where o.status = 'pending'
		""")
  List<Integer> findByStatusCount();


}



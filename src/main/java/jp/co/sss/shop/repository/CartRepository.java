package jp.co.sss.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.sss.shop.bean.UserBean;
import jp.co.sss.shop.entity.CartItems;


@Repository
public interface CartRepository  extends JpaRepository<CartItems, Integer> {

	List<CartItems> findByUserId(int userId);

	CartItems findByUserIdAndItemId(Integer userBean, int id2);

	//CartItems findByUserIdAndItemId(Integer userBean, int id);
	
	
	

}

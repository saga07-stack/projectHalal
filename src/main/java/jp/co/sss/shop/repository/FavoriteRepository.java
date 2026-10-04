package jp.co.sss.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.sss.shop.entity.FavoriteItem;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteItem, Integer> {

	// এক user-এর সব favourite
	List<FavoriteItem> findByUserId(Integer userId);

	// এই user এই item আগে favourite করেছে কি না
	FavoriteItem findByUserIdAndItemId(Integer userId, Integer itemId);
}
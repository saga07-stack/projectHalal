package jp.co.sss.shop.RestController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import jp.co.sss.shop.bean.CategoryBean;
import jp.co.sss.shop.entity.Category;
import jp.co.sss.shop.repository.CategoryRepository;

@RestController
@RequestMapping("/api/categories")
public class CategoriesRestController {
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	
	@GetMapping("/list")
	ResponseEntity<List<CategoryBean>> getCategories() {
		List<Category> categories = categoryRepo.findByDeleteFlagOrderByInsertDateDescIdDesc(0);
		
		List<CategoryBean> categoryBeans = new ArrayList<>();
		
		categories.forEach(cat->{
			CategoryBean categoryBean = new CategoryBean();
			categoryBean.setName(cat.getName());
			categoryBean.setId(cat.getId());
			
			categoryBeans.add(categoryBean);
			
		});
		
		return ResponseEntity.ok(categoryBeans);
	}

}

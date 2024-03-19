package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@RestController
@RequestMapping("/product/*")
public class ProductRestController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	public ProductRestController() {
		System.out.println(this.getClass());
	}
	
	@RequestMapping( value="json/getProduct/{prodNo}", method = RequestMethod.GET)
	public Product getProduct( @PathVariable int prodNo) throws Exception {
		
		System.out.println("/product/json/getProduct/ : GET	");
		
		return productService.getProduct(prodNo);
	}
	
	@RequestMapping(value="json/listProduct", method = RequestMethod.GET)
	public Map listProduct( @ModelAttribute("search") Search search, Model model
										,HttpServletRequest request) throws Exception {
		
		if(search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map = productService.getProductList(search);
		
//		Page resultPage = new Page(search.getCurrentPage(), 
//								((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
//		
//		
//		model.addAttribute("list", map.get("list"));
//		model.addAttribute("resultPage",resultPage);
//		model.addAttribute("search", search);
//		
		
		return map;
	}
	
	
	
	

}

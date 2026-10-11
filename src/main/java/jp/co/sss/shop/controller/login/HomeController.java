package jp.co.sss.shop.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  
public class HomeController {

	/**
	 * Renders the Thymeleaf template at templates/Restaurant/index.html
	 * (NOT the static copy under static/Restaurant/index.html) so the shared
	 * common/header fragment, layout.css and stylesheet.css are processed.
	 * The URL stays "/Restaurant/home", so this page's own relative asset
	 * links (css/style.css, js/data.js, js/app.js, menu.html, reserve.html...)
	 * still correctly resolve to the files under static/Restaurant/.
	 */
	@GetMapping("/Restaurant/home")
	public String showHome() {
		return "Restaurant/index";   
	 }

	/*
	 * The rest of the "Himal Kitchen" mini-site (menu / dish detail / about /
	 * reserve) was originally a set of plain static .html files under
	 * static/Restaurant/ with their own standalone header. To show the SAME
	 * shared shop header (common/header.html) on every page of this
	 * mini-site, each page below now has a matching Thymeleaf template under
	 * templates/Restaurant/ that includes the fragment.
	 *
	 * These @GetMapping paths deliberately end in ".html" so that none of the
	 * existing in-page links (href="menu.html", href="item.html?id=...",
	 * href="reserve.html", href="index.html", the brand link, etc.) needed to
	 * be changed — an explicit @GetMapping always takes priority over the
	 * static-resource handler for the same path, so these controller methods
	 * "shadow" the old static duplicates and are used instead.
	 */
	@GetMapping("/Restaurant/index.html")
	public String showIndexHtml() {
		return "Restaurant/index";
	}

	@GetMapping("/Restaurant/menu.html")   
	public String showMenu() {
		return "Restaurant/menu";
	}
 
	@GetMapping("/Restaurant/item.html")
	public String showItem() { 
		return "Restaurant/item"; 
	}

	@GetMapping("/Restaurant/contact.html")
	public String showContact() {
		return "Restaurant/contact";
	}

	@GetMapping("/Restaurant/reserve.html")
	public String showReserve() {
		return "Restaurant/reserve";
	}
	
}
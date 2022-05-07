package ua.ferret.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

	@GetMapping({"", "/", "/main", "/index"})
	public String main() {
		return "index";
	}
}

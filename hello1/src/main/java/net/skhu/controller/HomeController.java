package net.skhu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.skhu.dto.ArticleDto;
import net.skhu.dto.UserDto;
import net.skhu.service.MainService;

@Controller
public class HomeController {
	
	@Autowired
	private MainService mainService;

	@GetMapping("/")
	public String MainPage() {
		
		return "login";
	}
	
	//@SuppressWarnings("unused")
	@PostMapping("/")
	public String Login(@RequestParam(value="userId") String userId,
			@RequestParam(value="password") String password) throws Exception {
		
		
		try {
			System.out.println(userId+ "  "+ password);
			UserDto temp = mainService.Login(userId, password);
			
			if(temp == null) {
				throw new NullPointerException();
			}
			else {
				
				//세션에 로그인 정보 저장하는 코드 추가하기
				
				
				return "redirect:/redirect";
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			return "redirect:/";
		}catch (Exception e) {
			return "redirect:/";
			
		}
	}
	
	@GetMapping("/redirect")
	public String Board(Model model) {
		
		model.addAttribute("List", mainService.BoardList());
		return "board";
	}
}

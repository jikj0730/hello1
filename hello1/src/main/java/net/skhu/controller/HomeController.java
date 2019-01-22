package net.skhu.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

	/*
	//@SuppressWarnings("unused")
	@PostMapping("/")
	public String Login(@RequestParam(value="userId") String userId,
			@RequestParam(value="password") String password,
			HttpSession session) throws Exception {


		try {
			System.out.println(userId+ "  "+ password);
			UserDto temp = mainService.Login(userId, password);

			if(temp == null) {
				throw new NullPointerException();
			}
			else {

				//세션에 로그인 정보 저장하는 코드 추가하기
				session.setAttribute("loginUser", temp);

				return "redirect:/board";
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			return "redirect:/";
		}catch (Exception e) {
			return "redirect:/";

		}
	}*/

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/board")
	public String Board(Model model) {

		model.addAttribute("List", mainService.BoardList());
		return "board/board";
	}

	@PostMapping("/signup")
	@ResponseBody
	public ResponseEntity<String> SignUp( @RequestBody UserDto user  ){

		//System.out.println(user.getUserId()+"  "+user.getPassword());
		int num = mainService.userInsert(user);
		if(num>0)
			return new ResponseEntity<>("ok",  HttpStatus.OK);
		else
			return new ResponseEntity<>("실패",  HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/create")
	public String Create() {

		return "board/create";
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PostMapping("/create")
	public String CreateArticle(@RequestParam(value="title") String title,
			@RequestParam(value="content") String content,
			HttpSession session) {

		try {
			UserDto user = (UserDto)session.getAttribute("loginUser");
			Date d = new Date();
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//System.out.println("제목 : "+title);
			//System.out.println("작성자 : "+user.getUserId());
			//System.out.println("현재날짜 : "+ simple.format(d));
			//System.out.println("내용 : "+content);
			int num = mainService.articleInsert(title, simple.format(d), user.getUserId(), content, user.getId());

			if(num>0) {

				return "redirect:/board";
			}
			else {
				throw new Exception();
			}
		} catch (Exception e) {
			return "redirect:/create";

		}
	}

	@Secured({"ROLE_ADMIN"})
	@GetMapping("admin/testadmin")
	public String testadmin() {
		return "admin/testadmin";
	}
}

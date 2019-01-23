package net.skhu.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.skhu.dto.ReplyDto;
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

	//@Secured({"ROLE_ADMIN", "ROLE_USER"})
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

	//@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("board/create")
	public String Create() {

		return "board/create";
	}

	//@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PostMapping("board/create")
	public String CreateArticle(@RequestParam(value="title") String title,
			@RequestParam(value="content") String content,
			Authentication authentication, SecurityContextHolder test) {
		/*
		System.out.println("-----------");
		System.out.println(test.getContext());
		System.out.println(test.getContext().getAuthentication());
		System.out.println(test.getContext().getAuthentication().getAuthorities());
		System.out.println(test.getContext().getAuthentication().getPrincipal());
		System.out.println( (UserDto)test.getContext().getAuthentication().getPrincipal() );
		System.out.println( ((UserDto)test.getContext().getAuthentication().getPrincipal()).getUserId() );
		System.out.println("-----------");
		System.out.println(authentication.getName()); 
		System.out.println(authentication.getPrincipal()); 
		System.out.println(((UserDto)authentication.getPrincipal()).getUserId() ); 
		System.out.println(authentication.getDetails()); 
		System.out.println(authentication.getAuthorities()); 
		System.out.println("-----------");
		*/
		
		try {
			UserDto user = ((UserDto)authentication.getPrincipal());
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
			return "redirect:/board/create";

		}
	}

	//@Secured("hasRole('ROLE_ADMIN')")
	@GetMapping("admin/testadmin")
	public String testadmin() {
		return "admin/testadmin";
	}

	//@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("board/article")
	public String ReadArticle(@RequestParam(value="id") int id, Model model) {

		model.addAttribute("article", mainService.ReadArticle(id));
		model.addAttribute("ReplyList", mainService.ReplyList(id));
		return "board/article";
	}
	
	@PostMapping("board/reply")
	@ResponseBody
	public ResponseEntity<Boolean> AddReply(@RequestBody ReplyDto reply,
			Authentication authentication){
		
		try {
			UserDto user = ((UserDto)authentication.getPrincipal());
			Date d = new Date();
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int num = mainService.AddReply(reply.getContent(), simple.format(d), 
					reply.getArticleId(), user.getId(), user.getUserId());
			if(num>0) {

				return new ResponseEntity<>(true,  HttpStatus.OK);
			}
			else {
				throw new Exception();
			}
		} catch (Exception e) {
			return new ResponseEntity<>(false,  HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		
	}
	
	@GetMapping("board/reply/delete")
	public String DeleteReply(@RequestParam(value="id") int id, 
			@RequestParam(value="aid") int aid, RedirectAttributes rttr) throws Exception{
		rttr.addAttribute("id",aid);
		try {
			int num = mainService.DeleteReply(id);
			if(num>0) {
				
				//model.addAttribute("id", aid);
				return "redirect:/board/article";
			}else {
				throw new Exception();
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			return "redirect:/board/article";
		}
		
	}
	
	@GetMapping("board/delete")
	public String DeleteArticle(@RequestParam(value="id") int id, RedirectAttributes rttr) {
		
		boolean check = mainService.DeleteArticle(id);
		if(check) {
			System.out.println("삭제성공");
			return "redirect:/board";
		}
		else {
			rttr.addAttribute("id",id);
			return "redirect:/board/article";
		}
			
	}
	
	@GetMapping("board/edit")
	public String EditArticle(@RequestParam(value="id") int id, Model model) {
		
		model.addAttribute("article", mainService.ReadArticle(id));
		return "board/edit";
			
	}
	
	@PostMapping("board/edit")
	public String EditArticlePost(@RequestParam(value="title") String title,
			@RequestParam(value="content") String content, RedirectAttributes rttr,
			@RequestParam(value="id") int id) {
		
		System.out.println("edit");
		//model.addAttribute("article", mainService.ReadArticle(id));
		//return "board/edit";
		rttr.addAttribute("id",id);
		try {
			Date d = new Date();
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int num = mainService.EditArticle(id, title, simple.format(d), content);
			if(num>0)
				return "redirect:/board/article";
			else
				throw new Exception();
			
		} catch (Exception e) {
			// TODO: handle exception
			return "redirect:/board/article";
		}
			
	}
}

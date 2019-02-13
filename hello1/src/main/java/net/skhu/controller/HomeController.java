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

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import net.skhu.Exception.UserTypeException;
import net.skhu.dto.ReplyDto;
import net.skhu.dto.UserDto;
import net.skhu.service.MainService;
import net.skhu.utils.DateUtils;
import net.skhu.utils.EncryptionUtils;

/***********************************************
 * 
 * @author Promise
 * 제목 : HomeController
 * 설명 : 사용자의 요청을 받는 역할을 담당한다
 * 설계자 : 전경준
 * 작성자 : 전경준
 * 
 *
 **********************************************/
@Controller
public class HomeController {

	@Autowired
	private MainService mainService; //서비스 객체

	@GetMapping("/")
	public String mainPage() {
		
		//String temp = "1";
		//System.out.println(EncryptionUtils.encryptSHA256("1"));
		//System.out.println(EncryptionUtils.encryptSHA256("2"));
		//System.out.println(EncryptionUtils.encryptSHA256("3"));
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

	/*
	 * 게시판에서 볼 게시글의 목록을 가져온다
	 */
	//@Secured({"ROLE_ADMIN, "ROLE_USER"})
	@GetMapping("/board")
	public String board(Model model) {

		model.addAttribute("List", mainService.boardList());
		return "board/board";
	}

	/*
	 * 회원가입 요청을 받아 회원가입을 처리한다
	 */
	@PostMapping("/signup")
	@ResponseBody
	public ResponseEntity<String> signUp( @RequestBody UserDto user  ){

		//System.out.println(user.getUserId()+"  "+user.getPassword());
		int check = mainService.checkId(user.getUserId());
		//System.out.println(check);
		//가입된 아이디가 없다면
		if(check==0) {
			int num = mainService.userInsert(user);
			if(num>0)
				return new ResponseEntity<>("회원가입에 성공하였습니다.",  HttpStatus.OK);
			else
				return new ResponseEntity<>("회원가입에 실패하였습니다.",  HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else { //가입된 아이디가 있다면
			return new ResponseEntity<>("중복된 아이디가 존재합니다.",  HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	//@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("board/create")
	public String create() {

		return "board/create";
	}

	
	/*
	 * 게시글을 작성하여 디비에 넣는다
	 */
	//@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PostMapping("board/create")
	public String CreateArticle(@RequestParam(value="title") String title,
			@RequestParam(value="content") String content,
			Authentication authentication, SecurityContextHolder test) {
		
		//스프링 시큐리티 정보 출력, 삭제 x
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
			//Date d = new Date();
			//SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//System.out.println("제목 : "+title);
			//System.out.println("작성자 : "+user.getUserId());
			//System.out.println("현재날짜 : "+ simple.format(d));
			//System.out.println("내용 : "+content);
			int num = mainService.articleInsert(title, DateUtils.nowDate(), user.getUserId(), content, user.getNo());
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
	@GetMapping("admin/userlist")
	public String testadmin(Model model) {
		model.addAttribute("userList", mainService.userList());
		return "admin/userlist";
	}

	//@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("board/article")
	public String readArticle(@RequestParam(value="no") int no, Model model) {

		model.addAttribute("article", mainService.readArticle(no));
		model.addAttribute("ReplyList", mainService.replyList(no));
		return "board/article";
	}
	
	@PostMapping("board/reply")
	@ResponseBody
	public ResponseEntity<Boolean> addReply(@RequestBody ReplyDto reply,
			Authentication authentication){
		//System.out.println("dd");
		try {
			UserDto user = ((UserDto)authentication.getPrincipal());
			//Date d = new Date();
			//SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//System.out.println("컨트롤러1");
			int num = mainService.addReply(reply.getContent(), /*simple.format(d)*/ DateUtils.nowDate(), 
					reply.getArticleNo(), user.getNo(), user.getUserId());
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
	
	/*
	 * 댓글을 삭제하기 위한 메소드
	 * RedirectAttributes를 통해 댓글 삭제 후, 해당 게시글 번호를 전송한다.
	 */
	@GetMapping("board/reply/delete")
	public String deleteReply(@RequestParam(value="no") int no, 
			@RequestParam(value="ano") int ano, RedirectAttributes rttr){
		rttr.addAttribute("no",ano);
		try {
			int num = mainService.deleteReply(no);
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
	
	/*
	 * 게시글 삭제 - 댓글까지 모두 삭제해야 한다
	 */
	@GetMapping("board/delete")
	public String deleteArticle(@RequestParam(value="no") int no, RedirectAttributes rttr) {
		
		boolean check = mainService.deleteArticle(no);
		if(check) {
			//System.out.println("삭제성공");
			return "redirect:/board";
		}
		else {
			rttr.addAttribute("no",no);
			return "redirect:/board/article";
		}
			
	}
	
	@GetMapping("board/edit")
	public String editArticle(@RequestParam(value="no") int no, Model model) {
		
		model.addAttribute("article", mainService.readArticle(no));
		return "board/edit";
			
	}
	
	@PostMapping("board/edit")
	public String editArticlePost(@RequestParam(value="title") String title,
			@RequestParam(value="content") String content, RedirectAttributes rttr,
			@RequestParam(value="no") int no) {
		
		//System.out.println("edit");
		//model.addAttribute("article", mainService.ReadArticle(id));
		//return "board/edit";
		rttr.addAttribute("no",no);
		try {
			Date d = new Date();
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int num = mainService.editArticle(no, title, simple.format(d), content);
			if(num>0)
				return "redirect:/board/article";
			else
				throw new Exception();
			
		} catch (Exception e) {
			// TODO: handle exception
			return "redirect:/board/article";
		}
			
	}
	
	@GetMapping("admin/useredit")
	public String editUser(@RequestParam(value="no") int no, Model model) {
		model.addAttribute("user", mainService.getUser(no));
		return "admin/editUser";
	}
	
	@GetMapping("admin/userpassreset")
	@ResponseBody
	public ResponseEntity<String> userPassReset(@RequestParam(value="no") int no){
		//System.out.println(no);
		
		int num = mainService.userPassReset(no);
		if(num>0)
			return new ResponseEntity<>("비밀번호를 아이디와 동일하게 초기화 하였습니다.",  HttpStatus.OK);
		else
			return new ResponseEntity<>("실패하였습니다.",  HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("admin/usertypereset")
	@ResponseBody
	public ResponseEntity<String> userTypeReset(@RequestParam(value="no") int no, @RequestParam(value="select") int select){
		System.out.println(no);
		System.out.println(select);
		int num = mainService.userTypeReset(select, no);
		if(num>0)
			return new ResponseEntity<>("유저 권한을 변경하였습니다.",  HttpStatus.OK);
		else
			return new ResponseEntity<>("실패하였습니다.",  HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("admin/deleteuser")
	@ResponseBody
	public ResponseEntity<String> deleteUser(@RequestParam(value="no") int no){
		System.out.println(no);
		
		try {
			mainService.deleteUser(no);
			
			return new ResponseEntity<>("삭제하였습니다.",  HttpStatus.OK);
			
		} catch (UserTypeException e) {
			System.out.println(e.getMessage());
			System.out.println("---------");
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(),  HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			System.out.println("---------");
			e.printStackTrace();
			return new ResponseEntity<>("실패하였습니다.",  HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping("/board/changepass")
	public String changePass(@RequestParam(value="no") int no, Model model) {
		
	}
	
}

package net.skhu.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.skhu.Exception.PasswordException;
import net.skhu.Exception.UserTypeException;
import net.skhu.dto.ArticleDto;
import net.skhu.dto.ReplyDto;
import net.skhu.dto.UserDto;
import net.skhu.mapper.MainMapper;
import net.skhu.utils.EncryptionUtils;

@Service
public class MainService {
	
	private static final String TYPE_ADMIN = "ROLE_ADMIN";
	private static final String TYPE_TEST = "ROLE_TEST";
	private static final String TYPE_USER = "ROLE_USER";

	@Autowired
	private MainMapper mainMapper;

	public UserDto Login(String userId, String password) {

		//System.out.println(userId);
		//System.out.println(password);
		//System.out.println(EncryptionUtils.encryptSHA256(password));
		UserDto user =  mainMapper.login(userId, EncryptionUtils.encryptSHA256(password));
		if (user == null)
			return null;
		return user;

	}

	public List<ArticleDto> boardList(String search , String select){
		return mainMapper.boardList(search, select);
	}

	public int userInsert(UserDto user) {

		//비밀번호 암호화 알고리즘 적용
		user.setPassword(EncryptionUtils.encryptSHA256(user.getPassword()));
		int num = mainMapper.userInsert(user);
		return num;
	}

	public int articleInsert(String title, String date, String content, int userId) {

		int num = mainMapper.articleInsert(title, date, content, userId);
		return num;
	}
	
	public ArticleDto readArticle(int no) {
		return mainMapper.readArticle(no);
	}
	
	public int addReply(String content, String date, int articleNo, int userId) {
		int num = mainMapper.addReply(content, date, articleNo, userId);
		//System.out.println("num="+num);
		return num;
	}
	
	public List<ReplyDto> replyList(int articleNo){
		return mainMapper.replyList(articleNo);
	}
	
	public int deleteReply( int no ) {
		int num = mainMapper.deleteReply(no);
		return num;
	}
	
	/*
	 * 게시글을 삭제할 때 삭제 순서가
	 * 댓글 삭제->게시글 삭제로 가야 하기 때문에 트렌잭션 처리
	 */
	@Transactional(rollbackFor={Exception.class})
	public boolean deleteArticle( int no ) {
		
		int replyCount = mainMapper.replycount(no); 	//댓글이 존재하는지 여부 판단
		
		//댓글이 한개 이상 존재할 경우
		if(replyCount>0) {
			//댓글부터 싹 삭제
			int num1 = mainMapper.deleteArticleReply(no);
			
			//게시글 삭제
			int num2 = mainMapper.deleteArticle(no);
			
			if(num1>0 && num2>0)
				return true;
			else
				return false;
		}
		else { //댓글이 존재하지 않을 경우
			//게시글 삭제
			int num2 = mainMapper.deleteArticle(no);
			
			if( num2>0)
				return true;
			else
				return false;
		}
		
	}
	
	public int editArticle(int id, String title, String date, String content) {
		return mainMapper.editArticle(id, title, date, content);
	}
	
	public int checkId(String userId) {
		int check=0;
		check = mainMapper.checkId(userId);
		
		return check;
	}
	
	public List<UserDto> userList(){
		return mainMapper.userList();
	}
	
	public UserDto getUser(int no) {
		return mainMapper.getUser(no);
	}
	
	/*
	 * 회원 아이디를 가져와, 아이디와 동일하게 
	 * 비밀번호를 설정해준다
	 */
	public int userPassReset(int no) {
		
		int num=0;
		String userId = mainMapper.getUser(no).getUserId();
		
		num = mainMapper.userPassReset(no, EncryptionUtils.encryptSHA256(userId));
		
		return num;
	}
	
	/*
	 * 유저의 권한 타입을 변경한다
	 */
	public int userTypeReset(int select, int no) {
		
		int num = 0;
		if(select==1) {
			num = mainMapper.userTypeReset(no, TYPE_USER);
			return num;
		}
		else if(select==2) {
			num = mainMapper.userTypeReset(no, TYPE_TEST);
			return num;
		}
		else if(select ==3) {
			num = mainMapper.userTypeReset(no, TYPE_ADMIN);
			return num;
		}
		else {
			return num;
		}
	}
	
	/*
	 * 유저를 삭제한다.
	 * 만약 유저의 권한이 관리자라면 -1을 리턴하고, 삭제 불가능이라고 알림 띄움
	 * 유저가 쓴 댓글, 게시글, 계정 순으로 삭제해야함
	 */
	@Transactional(rollbackFor={Exception.class})
	public void deleteUser(int no) throws Exception {
		
		String type = mainMapper.getUserType(no);
		
		if(type.equals(TYPE_ADMIN)) //삭제하려는 계정이 관리자일 경우 삭제 불가능
			throw new UserTypeException("관리자는 삭제가 불가능합니다");
		else {
			mainMapper.deleteReply(no);
			mainMapper.deleteArticle(no);
			mainMapper.deleteUser(no);
		}
		
		
	}
	
	/*
	 * 비밀번호를 변경한다.
	 * 기존의 비밀번호가 디비와 일치하면 변경을 진행하고
	 * 다르다면 변경하지 않는다.
	 */
	public void changePassword(String pass1, String pass2, UserDto user) throws Exception  {
		
		pass1 = EncryptionUtils.encryptSHA256(pass1); //입력받은 기존 비밀번호
		pass2 = EncryptionUtils.encryptSHA256(pass2); //바꿀 비빌 번호
		
		String currentPass = mainMapper.getPassword(user.getNo()); //디비에 저장된 비빌번호
		
		if(currentPass.equals(pass1)) {
			int num = mainMapper.userPassReset(user.getNo(), pass2);
			if(!(num>0)) {
				throw new SQLException("디비 업데이트 오류");
			}
		}else {
			throw new PasswordException("기존 비밀번호가 일치하지 않습니다.");
		}
		
	}
}

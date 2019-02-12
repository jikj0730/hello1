package net.skhu.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.skhu.dto.ArticleDto;
import net.skhu.dto.ReplyDto;
import net.skhu.dto.UserDto;
import net.skhu.mapper.MainMapper;

@Service
public class MainService {

	@Autowired
	private MainMapper mainMapper;

	public UserDto Login(String userId, String password) {

		UserDto user =  mainMapper.login(userId, password);
		if (user == null)
			return null;
		return user;

	}

	public List<ArticleDto> boardList(){
		return mainMapper.boardList();
	}

	public int userInsert(UserDto user) {

		int num = mainMapper.userInsert(user);
		return num;
	}

	public int articleInsert(String title, String date, String writer, String content, int userId) {

		int num = mainMapper.articleInsert(title, date, writer, content, userId);
		return num;
	}
	
	public ArticleDto readArticle(int no) {
		return mainMapper.readArticle(no);
	}
	
	public int addReply(String content, String date, int articleNo, int userId, String writer) {
		int num = mainMapper.addReply(content, date, articleNo, userId, writer);
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
}

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
		if (user == null) return null;
		return user;

	}

	public List<ArticleDto> BoardList(){
		return mainMapper.BoardList();
	}

	public int userInsert(UserDto user) {

		int num = mainMapper.userInsert(user);
		return num;
	}

	public int articleInsert(String title, String date, String writer, String content, int userId) {

		int num = mainMapper.articleInsert(title, date, writer, content, userId);
		return num;
	}
	
	public ArticleDto ReadArticle(int id) {
		return mainMapper.ReadArticle(id);
	}
	
	public int AddReply(String content, String date, int articleId, int userId, String writer) {
		int num= mainMapper.AddReply(content, date, articleId, userId, writer);
		return num;
	}
	
	public List<ReplyDto> ReplyList(int articleId){
		return mainMapper.ReplyList(articleId);
	}
	
	public int DeleteReply( int id ) {
		int num = mainMapper.DeleteReply(id);
		return num;
	}
	
	@Transactional(rollbackFor={Exception.class})
	public boolean DeleteArticle( int id ) {
		
		int replyCount = mainMapper.replycount(id);
		if(replyCount>0) {
			//댓글부터 싹 삭제
			int num1 = mainMapper.DeleteArticleReply(id);
			
			//게시글 삭제
			int num2 = mainMapper.DeleteArticle(id);
			
			if(num1>0 && num2>0)
				return true;
			else
				return false;
		}
		else {
			//게시글 삭제
			int num2 = mainMapper.DeleteArticle(id);
			
			if( num2>0)
				return true;
			else
				return false;
		}
		
	}
	
	public int EditArticle(int id, String title, String date, String content) {
		return mainMapper.EditArticle(id, title, date, content);
	}
}

package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.skhu.dto.ArticleDto;
import net.skhu.dto.ReplyDto;
import net.skhu.dto.UserDto;

@Mapper
public interface MainMapper {

	UserDto login(@Param("userId") String userId, @Param("password") String password);
	List<ArticleDto> BoardList();
	int userInsert(UserDto user);
	int articleInsert(@Param("title") String title, @Param("date") String date, @Param("writer") String writer,
			@Param("content") String content, @Param("userId") int userId  );
	ArticleDto ReadArticle(@Param("id") int id );
	int AddReply(@Param("content") String content, @Param("date") String date, 
			@Param("articleId") int articleId, @Param("userId") int userId, @Param("writer") String writer );
	List<ReplyDto> ReplyList(@Param("articleId") int articleId);
	int DeleteReply(@Param("id") int id );
	int DeleteArticleReply(@Param("articleId") int articleId);
	int DeleteArticle(@Param("id") int id);
	int EditArticle(@Param("id") int id, @Param("title") String title, @Param("date") String date,@Param("content") String content);
	int replycount(@Param("articleId") int articleId);
}

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
	List<ArticleDto> boardList(@Param("search") String search, @Param("select") String select );
	int userInsert(UserDto user);
	int articleInsert(@Param("title") String title, @Param("date") String date,
			@Param("content") String content, @Param("userNo") int userNo  );
	ArticleDto readArticle(@Param("no") int no );
	int addReply(@Param("content") String content, @Param("date") String date, 
			@Param("articleNo") int articleNo, @Param("userNo") int userNo);
	List<ReplyDto> replyList(@Param("articleNo") int articleNo);
	int deleteReply(@Param("no") int no );
	int deleteArticleReply(@Param("articleNo") int articleNo);
	int deleteArticle(@Param("no") int no);
	int editArticle(@Param("no") int no, @Param("title") String title, @Param("date") String date,@Param("content") String content);
	int replycount(@Param("articleNo") int articleNo);
	int checkId(@Param("userId") String userId);
	List<UserDto> userList();
	UserDto getUser(@Param("no")int no);
	int userPassReset(@Param("no")int no, @Param("password") String password);
	int userTypeReset(@Param("no")int no, @Param("userType") String userType);
	String getUserType(@Param("no")int no);
	void deleteUser(@Param("no")int no);
	void deleteUserReply(@Param("no")int no);
	void deleteUserArticle(@Param("no")int no);
	String getPassword(@Param("no")int no);
}

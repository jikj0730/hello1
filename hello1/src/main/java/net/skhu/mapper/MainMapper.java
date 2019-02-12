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
	List<ArticleDto> boardList();
	int userInsert(UserDto user);
	int articleInsert(@Param("title") String title, @Param("date") String date, @Param("writer") String writer,
			@Param("content") String content, @Param("userNo") int userNo  );
	ArticleDto readArticle(@Param("no") int no );
	int addReply(@Param("content") String content, @Param("date") String date, 
			@Param("articleNo") int articleNo, @Param("userNo") int userNo, @Param("writer") String writer );
	List<ReplyDto> replyList(@Param("articleNo") int articleNo);
	int deleteReply(@Param("no") int no );
	int deleteArticleReply(@Param("articleNo") int articleNo);
	int deleteArticle(@Param("no") int no);
	int editArticle(@Param("no") int no, @Param("title") String title, @Param("date") String date,@Param("content") String content);
	int replycount(@Param("articleNo") int articleNo);
	int checkId(@Param("userId") String userId);
}

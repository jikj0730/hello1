package net.skhu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.skhu.dto.ArticleDto;
import net.skhu.dto.UserDto;

@Mapper
public interface MainMapper {

	UserDto login(@Param("userId") String userId, @Param("password") String password);
	List<ArticleDto> BoardList();
}

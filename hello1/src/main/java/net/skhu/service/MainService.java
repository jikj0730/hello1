package net.skhu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.skhu.dto.ArticleDto;
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
}

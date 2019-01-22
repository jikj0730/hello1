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
		
		 return mainMapper.login(userId, password);
	}
	
	public List<ArticleDto> BoardList(){
		return mainMapper.BoardList();
	}
}

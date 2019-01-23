package net.skhu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import net.skhu.dto.UserDto;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private MainService mainService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String loginId = authentication.getName();
		String passwd = authentication.getCredentials().toString();
		return authenticate(loginId, passwd);
	}

	public Authentication authenticate(String loginId, String password) throws AuthenticationException {
		UserDto user = mainService.Login(loginId, password);
		if (user == null) return null;

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		String role = "";
		switch (user.getUserType()) {
		case "ROLE_ADMIN": role = "ROLE_ADMIN"; break;
		case "ROLE_USER": role = "ROLE_USER"; break;
		case "ROLE_TEST": role = "ROLE_TEST"; break;
		//case "ROLE_ADMIN": role = "hasRole('ROLE_ADMIN')"; break;
		//case "ROLE_USER": role = "hasRole('ROLE_USER')"; break;
		}
		grantedAuthorities.add(new SimpleGrantedAuthority(role));
		return new MyAuthenticaion(loginId, password, grantedAuthorities, user);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	public class MyAuthenticaion extends UsernamePasswordAuthenticationToken {
		private static final long serialVersionUID = 1L;
		UserDto user;

		public MyAuthenticaion (String loginId, String passwd, List<GrantedAuthority> grantedAuthorities, UserDto user) {
			//super(loginId, passwd, grantedAuthorities); //authentication.getPrincipal()에 loginId 저장
			super(user, passwd, grantedAuthorities); //authentication.getPrincipal()에 user 객체 저장
			this.user = user;
		}

		public UserDto getUser() {
			return user;
		}

		public void setUser(UserDto user) {
			this.user = user;
		}
	}
}

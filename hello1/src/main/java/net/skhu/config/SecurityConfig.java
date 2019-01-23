package net.skhu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import net.skhu.service.MyAuthenticationProvider;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired MyAuthenticationProvider myAuthenticationProvider;

	@Override
	public void configure(WebSecurity web) throws Exception
	{
		web.ignoring().antMatchers("/res/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests()
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/board/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
		//.antMatchers("/board/**").hasRole("ROLE_ADMIN")
		//.antMatchers("/board/**").hasRole("ROLE_USER")
		.antMatchers("/").permitAll()
		.antMatchers("/signup").permitAll()
		.antMatchers("/**").authenticated();

		http.csrf().disable();

		http.formLogin()
		.loginPage("/")
		.loginProcessingUrl("/login_processing")
		.failureUrl("/")
		.defaultSuccessUrl("/board", true)
		.usernameParameter("userId")
		.passwordParameter("password");

		http.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout_processing"))
		.logoutSuccessUrl("/")
		.invalidateHttpSession(true);

		http.authenticationProvider(myAuthenticationProvider);
	}
}



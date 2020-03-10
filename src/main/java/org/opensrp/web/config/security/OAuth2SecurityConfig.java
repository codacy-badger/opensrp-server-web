/**
 * 
 */
package org.opensrp.web.config.security;

import org.opensrp.web.config.Role;
import org.opensrp.web.security.OauthAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author Samuel Githengi created on 03/10/20
 */
@EnableWebSecurity
@Configuration
@Profile("oauth2")
public class OAuth2SecurityConfig extends BasicAuthSecurityConfig{
	
	@Autowired
	public void setOpensrpAuthenticationProvider(OauthAuthenticationProvider opensrpAuthenticationProvider) {
		super.setOpensrpAuthenticationProvider(opensrpAuthenticationProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		configureOpenSRPBasicSecurity(http)
				.antMatchers("/login**").anonymous()
				.mvcMatchers("/**").hasRole(Role.OPENMRS)
		        .and()
		        	.formLogin()
		        	.usernameParameter("j_username")
		        	.passwordParameter("j_password")
		        	.loginProcessingUrl("/login.do")
		        	.loginPage("/login.jsp")
		        	.defaultSuccessUrl("/index.html")
		        	.failureUrl("/login.jsp?authentication_error=true")
		        .and()
		        	.logout()
		        	.logoutUrl("/logout.do")
		        	.logoutSuccessUrl("/index.html")
		        .and()
		        	.httpBasic()
		        .and()
		        	.sessionManagement()
		        	.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
		        	.enableSessionUrlRewriting(true)
		        .and()
		        	.exceptionHandling().accessDeniedPage("/login.jsp?authentication_error=true");
	}
	
}

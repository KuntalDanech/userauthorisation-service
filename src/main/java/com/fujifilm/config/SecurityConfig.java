package com.fujifilm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Most important class for Security
 * 
 * @author pce16
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private InvalidAuthEntryPoint entryPoint;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}
	
	@Bean
    @Override
    public UserDetailsService userDetailsService() {
 
        //User Role
        UserDetails theUser = User.withUsername("sergey")
                        .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
                        .password("12345678").roles("USER").build();
        
        //Manager Role 
        UserDetails theManager = User.withUsername("john")
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
                .password("87654321").roles("MANAGER").build();
        
  
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
              
        userDetailsManager.createUser(theUser);
        userDetailsManager.createUser(theManager);
        
        return userDetailsManager;
    }
	/**
	 * Authentication - user details will be fetched here
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		// Initially there are two users - InMemory users
		
		// First user
		.passwordEncoder(encoder)
		.withUser("fujicustomer-1")
		.password(encoder.encode("password"))
		.authorities("CUSTOMER")
		.and()
		// Second User
		.withUser("fujicustomer-2")
		.password(encoder.encode("password"))
		.authorities("CUSTOMER");
		
	}
	
	/**
	 * Authorization
	 * Enable Which URL for which Roles.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
		.csrf().disable()
		.authorizeRequests()

		// Allow all users to use that endpoint
		.antMatchers("/token")
		.permitAll()
		.anyRequest()
		.authenticated()
		
		//If error then rest API response will be given
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(entryPoint)
		
		// Session creation policy will be state less
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);		
		
	}
}

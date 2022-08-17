package de.hsba.bi.grp3;

import de.hsba.bi.grp3.user.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity

public class SecSecurityConfig extends WebSecurityConfigurerAdapter {


   @Override
    protected void configure(final HttpSecurity http) throws Exception {

       http.csrf().disable();
       http.authorizeRequests()
               .antMatchers("/", "/js/**", "/css/**").permitAll()
               .antMatchers(HttpMethod.GET, "/easy-recipe/**").permitAll()
               .antMatchers(HttpMethod.GET, "/recipe/**").permitAll()
               .antMatchers(HttpMethod.GET, "/recipe/edit/**").authenticated()
               .antMatchers(HttpMethod.POST, "/recipe/edit/**").authenticated()
               .antMatchers(HttpMethod.GET, "/user").authenticated()
               .antMatchers(HttpMethod.GET, "/user/**").authenticated()
               .anyRequest().authenticated()
               .and()
               .formLogin()
               .loginPage("/login")
               .permitAll()
               .and()
               .logout()
               .logoutSuccessUrl("/")
               .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/h2-console/**");
    }

}

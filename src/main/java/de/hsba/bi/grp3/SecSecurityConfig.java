package de.hsba.bi.grp3;

import de.hsba.bi.grp3.user.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity

public class SecSecurityConfig extends WebSecurityConfigurerAdapter {



    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1").password(passwordEncoder().encode("user1Pass")).roles("USER")
                .and()
                .withUser("user2").password(passwordEncoder().encode("user2Pass")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN");
    }
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
            http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/anonymous*").anonymous()
                .antMatchers("/recipes/**" ,"/").permitAll()
                //.antMatchers("/recipes/editRecipe/**").hasRole("USER")
                //.anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .permitAll();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}

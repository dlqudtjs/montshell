package com.lbs.montshell.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

                // csrf 공격에 대한 방어를 해제
        return http.csrf().disable()
                // HTTP 요청에 대한 권한 부여 설정을 시작
                .authorizeHttpRequests()
                // '/user/**' 경로에 대한 요청은 인증된 사용자만 허용
                .requestMatchers("/user/**").authenticated()
                // '/admin/**' 경로에 대한 요청은 ROLE_ADMIN 권한을 가진 사용자만 허용
                .requestMatchers("/admin/**").hasRole("ADMIN")
                // 나머지 요청에 대해서는 인증 없이 접근이 허용
                .anyRequest().permitAll()
                .and()
                // 아이디와 비밀번호를 입력해 들어오는 로그인 형태를 지원함, 따라서 아래와 같은 메소드를 이용할 수 있음
                .formLogin()
                // 로그인 페이지의 URL 을 '/loginPage' 으로 설정
                .loginPage("/loginPage")
                // login 요청을 처리할 URL을 'loginProc' 로 설정
                .loginProcessingUrl("/loginProc")
                // 로그인 성공 시 이동할 URL을 '/'으로 설정
                .defaultSuccessUrl("/")
                // 객체를 빌드하고 반환
                .and().build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


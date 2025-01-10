package com.example.moviecommu.config;

import com.example.moviecommu.dto.UserDto;
import com.example.moviecommu.entity.User;
import com.example.moviecommu.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000"); // React 앱의 도메인
        configuration.addAllowedMethod("*"); // 모든 HTTP 메서드 허용
        configuration.addAllowedHeader("*"); // 모든 헤더 허용
        configuration.setAllowCredentials(true); // 자격 증명 허용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf->csrf.disable());
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.authorizeHttpRequests(auth->auth
                .requestMatchers("/","/login","/join","/posts/**","/movie","/movie/**","/search/**","/api/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        );

        http.exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                })); //권한 부족 일 시 401

        http.formLogin(auth->auth
                .loginPage("/login")
                .successHandler((request, response, authentication) -> {
                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                    long userId = Long.parseLong(userDetails.getUsername());
                    User user = userRepository.findByUserId(userId);
                    UserDto userDto = new UserDto();
                    userDto.setNickname(user.getNickname());
                    userDto.setId(user.getId());
                    userDto.setRole(user.getRole());
                    userDto.setPhone(user.getPhone());
                    userDto.setBirth(user.getBirth());

                    // UserDto를 JSON으로 변환
                    ObjectMapper objectMapper = new ObjectMapper();
                    String userDtoJson = objectMapper.writeValueAsString(userDto);

                    ObjectNode jsonResponse = objectMapper.readValue(userDtoJson, ObjectNode.class);
                    jsonResponse.put("userId", userId);

                    // JSON 반환 설정
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write(jsonResponse.toString());
                })
                .failureHandler((request, response, exception) -> {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 실패 시 401 Unauthorized
                    response.getWriter().write("Login Failed");  // 실패 메시지 응답 처리
                })
        );
        http.userDetailsService(userDetailsService);

        http.logout(auth->auth
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("Logged out successfully");
                }));

        return http.build();
    }
}

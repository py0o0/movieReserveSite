package com.example.moviecommu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
        // "http://192.168.0.2:3000"안되면 추가
        // addMapping : CROS를 적용할 url의 패턴을 정의 ("/**"로 모든 패턴 가능)
        // allowedOrigins : 허용할 origin ("*"로 모든 origin 허용 가능, 여러 개도 지정 가능)
        // allowedMethods : 허용할 HTTP Method ("*"로 모든 Method 허용 가능)

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///C:/file_upload_test/")
                .setCachePeriod(3600)
                .resourceChain(true);
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}

package com.ssafy.community.common;


import com.ssafy.community.user.config.StaticResourcePath;
import com.ssafy.community.user.encoder.BCryptPasswordEncoder;
import com.ssafy.community.user.jwt.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.*;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final AuthInterceptor authInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("addResourceHandlers");
        for (StaticResourcePath path : StaticResourcePath.values()) {
            ResourceHandlerRegistration resourceHandlerRegistration = registry.addResourceHandler(path.getPath());
            resourceHandlerRegistration.addResourceLocations("classpath:/static/" + path.getDirectory() + "/")
                    .setCacheControl(CacheControl.maxAge(60L * 60L * 24L * 365L, TimeUnit.SECONDS).cachePublic());
        }
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("addCorsMappings");
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .maxAge(6000);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("addInterceptors");
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/resources/**");

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
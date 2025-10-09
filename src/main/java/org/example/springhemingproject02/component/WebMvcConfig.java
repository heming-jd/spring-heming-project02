package org.example.springhemingproject02.component;

import lombok.RequiredArgsConstructor;
import org.example.springhemingproject02.interceptor.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;
    private final AdminInterceptor adminInterceptor;
    private final AdminCollegeInterceptor adminCollegeInterceptor;
    private  final TeacherInterceptor teacherInterceptor;
    private final StudentInterceptor studentInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/open/**");
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/api/admin/**");
        registry.addInterceptor(adminCollegeInterceptor)
                .addPathPatterns("/api/admincollege/**");
        registry.addInterceptor(teacherInterceptor)
                .addPathPatterns("/api/teacher/**");
        registry.addInterceptor(studentInterceptor)
                .addPathPatterns("/api/student/**");
    }
}

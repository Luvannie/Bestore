package com.luvannie.springbootbookecommerce.config;

import com.luvannie.springbootbookecommerce.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@EnableWebMvc
public class WebSecurityConfig {
    @Autowired
    private JwtTokenFilter jwtTokenFilter;
    @Value("${api.prefix}")
    private String apiPrefix;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests ->{
                    requests

                            .requestMatchers(GET,
                                    String.format("%s/book-category**", apiPrefix)).permitAll()
                            .requestMatchers(
                                    String.format("%s/users/register", apiPrefix),
                                    String.format("%s/users/login", apiPrefix))
                            .permitAll()
                            .requestMatchers(GET,
                                    String.format("%s/roles**", apiPrefix)).permitAll()
                            .requestMatchers(GET,
                                    String.format("%s/books/**", apiPrefix)).permitAll()
                            .requestMatchers(GET,
                                    String.format("%s/orders/**", apiPrefix)).permitAll()
                            .requestMatchers(GET,
                                    String.format("%s/order_items/**", apiPrefix)).permitAll()
                            .requestMatchers(GET,
                                    String.format("%s/coupons/**", apiPrefix)).permitAll()
                            .requestMatchers(GET,
                                    String.format("%s/countries**", apiPrefix)).permitAll()
                            .requestMatchers(GET,
                                    String.format("%s/cities**", apiPrefix)).permitAll()
                            .requestMatchers(POST,
                                    String.format("%s/checkout/**", apiPrefix)).permitAll()
                            .requestMatchers(GET,
                                    String.format("%s/cities/**", apiPrefix)).permitAll()
                            .requestMatchers(POST,
                                    String.format("%s/checkout**", apiPrefix)).permitAll()
                            .anyRequest()
                            .authenticated();

                });

        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                corsConfiguration.setAllowedOrigins(List.of("*"));
                corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                corsConfiguration.setAllowedHeaders(List.of("*"));
                corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization","content-type","x-auth-token"));
                corsConfiguration.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", corsConfiguration);
                httpSecurityCorsConfigurer.configurationSource(request -> corsConfiguration);
            }
        } );

        return http.build();
    }
}

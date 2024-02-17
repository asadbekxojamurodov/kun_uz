package com.example.config;

import com.example.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity()
public class SpringSecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    public static final String [] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/auth/**",
            "/init/admin",
            "/init/**",
            "/region/byLang/",
            "/articleLike/**",
            "/comment",
            "/comment/**",
            "/commentLike",
            "/commentLike/**",
            "/savedArticle/**",
            "/savedArticle/*",
    };

    @Bean
    public AuthenticationProvider authenticationProvider() {
        /*// authentication
        String password = UUID.randomUUID().toString();
        System.out.println("User Pasword mazgi: " + password);

        UserDetails user = User.builder()
                .username("user")
                .password("{noop}" + password)
//                .password("{noop}" + 12345)
                .roles("ADMIN")
                .build();

        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(new InMemoryUserDetailsManager(user));
        return authenticationProvider;*/

        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return MD5Util.encode(rawPassword.toString()).equals(encodedPassword);
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            authorizationManagerRequestMatcherRegistry
                    .requestMatchers(AUTH_WHITELIST).permitAll()
//                    .requestMatchers("/region/adm/**").hasRole("ADMIN")
                    .requestMatchers("*/adm/*").hasRole("ADMIN")
                    .requestMatchers("/article/publish").hasAnyRole("ADMIN","PUBLISHER")
                    .requestMatchers("/profile/adm","profile/adm/*").hasRole("ADMIN")
                    .anyRequest()
                    .authenticated();
        });

//        http.httpBasic(Customizer.withDefaults());
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        return http.build();
    }

}

package com.nakahama.simpenbackend.Auth.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        private JwtAuthFilter jwtAuthFilter;

        @Bean
        public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {
                http
                                .securityMatcher("/**")
                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(auth -> {
                                        // Auth, User, Tag
                                        auth.requestMatchers("auth/login").permitAll();
                                        auth.requestMatchers("auth/logout").authenticated();
                                        auth.requestMatchers("auth/test").authenticated();
                                        auth.requestMatchers("auth/test-role").hasAuthority("superadmin");
                                        auth.requestMatchers(HttpMethod.POST, "user").hasAuthority("superadmin");
                                        auth.requestMatchers(HttpMethod.GET, "user").hasAnyAuthority("operasional",
                                                        "superadmin", "akademik");
                                        auth.requestMatchers(HttpMethod.PUT, "user").hasAnyAuthority("pengajar",
                                                        "operasional", "akademik");
                                        auth.requestMatchers(HttpMethod.DELETE, "user/**").hasAuthority("superadmin");
                                        auth.requestMatchers(HttpMethod.PUT, "user/**").hasAnyAuthority("operasional",
                                                        "superadmin");
                                        auth.requestMatchers(HttpMethod.GET, "user/**").hasAnyAuthority("operasional",
                                                        "superadmin");
                                        auth.requestMatchers(HttpMethod.POST, "tag").hasAnyAuthority("operasional",
                                                        "akademik");
                                        auth.requestMatchers(HttpMethod.GET, "tag").hasAnyAuthority("operasional",
                                                        "akademik");
                                        auth.requestMatchers(HttpMethod.PUT, "tag").hasAnyAuthority("operasional",
                                                        "akademik");
                                        auth.requestMatchers(HttpMethod.DELETE, "tag/**").hasAnyAuthority("operasional",
                                                        "akademik");
                                        auth.requestMatchers(HttpMethod.POST, "tag/assign")
                                                        .hasAnyAuthority("operasional", "akademik");
                                        auth.requestMatchers(HttpMethod.GET, "tag/assign")
                                                        .hasAnyAuthority("operasional", "akademik");
                                        auth.requestMatchers(HttpMethod.DELETE, "tag/assign")
                                                        .hasAnyAuthority("operasional", "akademik");

                                        // Kelas, Program
                                        auth.requestMatchers("kelas/jenis/**").hasAnyAuthority("operasional",
                                                        "superadmin");
                                        auth.requestMatchers("kelas/program/**").hasAnyAuthority("operasional",
                                                        "superadmin");
                                        auth.requestMatchers(HttpMethod.POST, "kelas").hasAnyAuthority("operasional",
                                                        "superadmin");
                                        auth.requestMatchers(HttpMethod.GET, "kelas").hasAnyAuthority("pengajar",
                                                        "operasional",
                                                        "akademik", "superadmin");
                                        auth.requestMatchers(HttpMethod.GET, "kelas/**").hasAnyAuthority("pengajar",
                                                        "operasional",
                                                        "akademik", "superadmin");
                                        auth.requestMatchers(HttpMethod.PUT, "kelas/**").hasAnyAuthority("operasional",
                                                        "superadmin");
                                        auth.requestMatchers(HttpMethod.DELETE, "kelas/**")
                                                        .hasAnyAuthority("operasional", "superadmin");

                                        // Announcement
                                        auth.requestMatchers(HttpMethod.POST, "announcement")
                                                        .hasAnyAuthority("operasional", "akademik");
                                        auth.requestMatchers(HttpMethod.DELETE, "announcement/**")
                                                        .hasAnyAuthority("operasional", "akademik");
                                        auth.requestMatchers(HttpMethod.GET, "announcement")
                                                        .hasAnyAuthority("operasional", "akademik", "pengajar");
                                        auth.requestMatchers(HttpMethod.GET, "announcement/**")
                                                        .hasAnyAuthority("operasional", "akademik", "pengajar");

                                        // Murid
                                        auth.requestMatchers(HttpMethod.POST, "murid/**").hasAnyAuthority("operasional",
                                                        "superadmin", "akademik");
                                        auth.requestMatchers("murid").authenticated();

                                        // Sesi
                                        auth.requestMatchers("sesi").authenticated();

                                        // absen
                                        auth.requestMatchers(HttpMethod.POST, "absen-pengajar")
                                                        .hasAnyAuthority("pengajar");
                                        auth.requestMatchers(HttpMethod.GET, "absen-pengajar").hasAnyAuthority(
                                                        "pengajar",
                                                        "operasional", "akademik");
                                        auth.requestMatchers(HttpMethod.GET, "absen-pengajar/**")
                                                        .hasAnyAuthority("pengajar", "operasional", "akademik");

                                        // Perubahan Kelas
                                        auth.requestMatchers(HttpMethod.POST, "/ganti-pengajar/**")
                                                        .hasAnyAuthority("pengajar");
                                        auth.requestMatchers(HttpMethod.PUT, "/ganti-pengajar/**").hasAnyAuthority(
                                                        "operasional");
                                        auth.requestMatchers(HttpMethod.GET, "/ganti-pengajar/**")
                                                        .hasAnyAuthority("operasional");
                                        auth.requestMatchers(HttpMethod.POST, "/reschedule/**")
                                                        .hasAnyAuthority("pengajar");
                                        auth.requestMatchers(HttpMethod.PUT, "/reschedule/**").hasAnyAuthority(
                                                        "operasional");
                                        auth.requestMatchers(HttpMethod.GET, "/reschedule/**")
                                                        .hasAnyAuthority("operasional");
                                        auth.requestMatchers(HttpMethod.GET, "/permintaan-perubahan")
                                                        .hasAnyAuthority("operasional");

                                        // Feedback & Rating
                                        auth.requestMatchers(HttpMethod.GET, "/rating/**")
                                                        .hasAnyAuthority("akademik", "pengajar");
                                        auth.requestMatchers(HttpMethod.GET, "/feedback")
                                                        .hasAuthority("akademik");
                                        auth.requestMatchers(HttpMethod.PUT, "/feedback")
                                                        .hasAuthority("akademik");
                                        auth.requestMatchers(HttpMethod.GET, "/feedback/**")
                                                        .hasAnyAuthority("akademik", "pengajar");
                                        auth.requestMatchers(HttpMethod.DELETE, "/feedback/**")
                                                        .hasAnyAuthority("akademik");
                                        // TODO: set the appropriate authorities for the corresponding endpoints
                                        auth.anyRequest().permitAll();

                                })
                                .sessionManagement(sessionAuthenticationStrategy -> sessionAuthenticationStrategy
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                                .httpBasic(Customizer.withDefaults());
                return http.build();
        }

        @Autowired
        public void confAuthentication(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder)
                        throws Exception {
                auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }

}

package med.vol.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final SecurityFilter securityFilter;

    @Autowired
    public SecurityConfiguration(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    ///* PERMIT LOGIN - REJECT ANY
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(req -> {
                        req.requestMatchers(HttpMethod.POST, "/login").permitAll();
                        req.anyRequest().authenticated();
                    })
                    .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration ac) throws Exception {
        return ac.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

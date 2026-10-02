package cl.rutaexpress.audit;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    @ConditionalOnExpression("'${spring.security.oauth2.resourceserver.jwt.issuer-uri:}' != ''")
    SecurityFilterChain securedChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth.requestMatchers("/actuator/health").permitAll().anyRequest().authenticated())
                .oauth2ResourceServer(oauth -> oauth.jwt(jwt -> {})).csrf(csrf -> csrf.disable()).build();
    }
    @Bean
    @ConditionalOnExpression("'${spring.security.oauth2.resourceserver.jwt.issuer-uri:}' == ''")
    SecurityFilterChain developmentChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()).csrf(csrf -> csrf.disable()).build();
    }
}

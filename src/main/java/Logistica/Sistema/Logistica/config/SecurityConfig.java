package Logistica.Sistema.Logistica.config;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtEncodingException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.ignoringRequestMatchers(toH2Console()).disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                .requestMatchers(toH2Console()).permitAll()
                .requestMatchers(HttpMethod.POST,"/usuario", "/login", "/produto").permitAll()
//                .requestMatchers("/usuario").hasAnyRole("ADMIN", "RH")
                .requestMatchers("/usuario", "/produto", "/produto/unico").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/usuario", "/produto").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/usuario", "/produto").hasAnyRole("ADMIN", "RH")
                .requestMatchers(HttpMethod.PATCH, "/usuario/role").hasAnyRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/produto").permitAll()
//                .requestMatchers(HttpMethod.PUT, "/produto").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/produto").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/produto").permitAll()
                .anyRequest().authenticated())
                .headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
                .httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException)
                        -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")));

        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        String SECRET = "6f9b1e76f552ed443a37c55ca6e8bbd48671ad2119c57461466c2c622503a232";
        byte[] secretKeyBytes = Base64.getDecoder().decode(SECRET);
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    @Bean
    JwtEncoder jwtEncoder() {
        String SECRET = "6f9b1e76f552ed443a37c55ca6e8bbd48671ad2119c57461466c2c622503a232";
        return new JwtEncoder() {
            @Override
            public Jwt encode(JwtEncoderParameters parameters) throws JwtEncodingException {
                byte[] secretKeyBytes;
                secretKeyBytes = Base64.getDecoder().decode(SECRET);
                SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA256");
                
                try {
                    MACSigner signer = new MACSigner(secretKeySpec);
                    
                    JWTClaimsSet.Builder claimsSetBuilder = new JWTClaimsSet.Builder();
                    parameters.getClaims().getClaims().forEach((key, value) ->
                            claimsSetBuilder.claim(key, value instanceof Instant ? Date.from((Instant) value) : value)
                    );
                    JWTClaimsSet claimsSet = claimsSetBuilder.build();
                    
                    JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
                    
                    SignedJWT signedJWT = new SignedJWT(header, claimsSet);
                    signedJWT.sign(signer);
                    
                    return Jwt.withTokenValue(signedJWT.serialize())
                            .header("alg", header.getAlgorithm().getName())
                            .subject(claimsSet.getSubject())
                            .issuer(claimsSet.getIssuer())
                            .claims(claims -> claims.putAll(claimsSet.getClaims()))
                            .issuedAt(claimsSet.getIssueTime().toInstant())
                            .expiresAt(claimsSet.getExpirationTime().toInstant())
                            .build();
                } catch (Exception e) {
                    throw new IllegalStateException("Error while signing the JWT", e);
                }   }
        };
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        grantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

        return authenticationConverter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

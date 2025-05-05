package Logistica.Sistema.Logistica.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Logistica.Sistema.Logistica.dto.LoginRequestDto;
import Logistica.Sistema.Logistica.service.AuthenticationService;


@RestController
@RequestMapping("/autenticacao")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationManager authenticationManager, AuthenticationService authenticationService) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getEmail(),
                            loginRequestDto.getPassword()
                    )
            );
            return ResponseEntity.status(HttpStatus.OK).body(authenticationService.authenticate(authentication));
        } catch (Exception e) {
            e.printStackTrace(); // ou log
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login falhou: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<String> authentication(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
        String tokenFormated = token.substring(7); // remove "Bearer "
        return ResponseEntity.ok(tokenFormated);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token ausente ou mal formatado");
        }
    }
}

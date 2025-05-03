package comida.loja.fome10.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import comida.loja.fome10.dto.LoginRequestDto;
import comida.loja.fome10.service.AuthenticationService;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationManager authenticationManager, AuthenticationService authenticationService) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public String authenticate(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getEmail(),
                            loginRequestDto.getPassword()
                    )
            );
            return authenticationService.authenticate(authentication);
        } catch (Exception e) {
            e.printStackTrace(); // ou log
            return "Login falhou: " + e.getMessage();
        }
    }
}

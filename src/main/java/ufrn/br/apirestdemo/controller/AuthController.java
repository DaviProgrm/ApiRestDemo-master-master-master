package ufrn.br.apirestdemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ufrn.br.apirestdemo.auth.AuthenticationResponse;
import ufrn.br.apirestdemo.auth.AuthenticationResquest;
import ufrn.br.apirestdemo.auth.AuthenticationService;
import ufrn.br.apirestdemo.auth.RegisterRequest;
import ufrn.br.apirestdemo.config.LoginDTO;
import ufrn.br.apirestdemo.repository.UsuarioRepository;
import ufrn.br.apirestdemo.service.TokenService;

@RestController
@RequestMapping("auth")
public class AuthController {


    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    private final AuthenticationService service;

    private final UsuarioRepository repository;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager, AuthenticationService service, UsuarioRepository repository) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.service = service;
        this.repository = repository;
    }

    @PostMapping("/login")
    public AuthenticationResponse token(@RequestBody LoginDTO loginDTO) {
        System.out.println("entrou no login");
        System.out.println(loginDTO.username());
        System.out.println(repository.findByUsuario(loginDTO.username()).orElseThrow());
         authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password())
                );


        return tokenService.generateToken(repository.findByUsuario(loginDTO.username()).orElseThrow());
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(service.register(request));
    }

    /*
    Basic Authentication
    @PostMapping("/token")
    public String token(Authentication authentication) {
        String token = tokenService.generateToken(authentication);
        return token;
    }
     */

}
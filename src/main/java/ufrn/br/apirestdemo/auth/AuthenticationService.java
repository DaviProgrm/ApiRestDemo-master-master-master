package ufrn.br.apirestdemo.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ufrn.br.apirestdemo.domain.Role;
import ufrn.br.apirestdemo.domain.Usuario;
import ufrn.br.apirestdemo.repository.UsuarioRepository;
import ufrn.br.apirestdemo.service.TokenService;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;

    private final UsuarioRepository repository;

    private final TokenService tokenService;

    private final PasswordEncoder passwordEncoder;


    public AuthenticationResponse register(RegisterRequest request){
        var user = Usuario.builder()
                .nome(request.getNome())
                .idade(request.getIdade())
                .usuario(request.getUsuario())
                .senha(passwordEncoder.encode(request.getSenha()))
                .role(Role.USER)
                .build();


        repository.save(user);
        var token =  tokenService.generateToken(user).toString();
        return AuthenticationResponse.builder().token(token).build();

    }

}

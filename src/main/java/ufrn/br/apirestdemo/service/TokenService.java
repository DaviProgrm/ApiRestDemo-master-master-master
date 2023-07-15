package ufrn.br.apirestdemo.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import ufrn.br.apirestdemo.auth.AuthenticationResponse;
import ufrn.br.apirestdemo.repository.UsuarioRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final UsuarioRepository repository;
    private final JwtEncoder encoder;

    public TokenService(UsuarioRepository repository, JwtEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }


    public AuthenticationResponse generateToken(UserDetails userDetails) {

        Instant now = Instant.now();
        String scope = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("ApiRestDemo")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(userDetails.getUsername())
                .claim("scope", scope)
                .build();


        return AuthenticationResponse.builder().token(this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue()).build();
    }



}

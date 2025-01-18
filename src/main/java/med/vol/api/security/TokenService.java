package med.vol.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.persistence.EntityNotFoundException;
import med.vol.api.dto.LoginUsuario;
import med.vol.api.entity.Usuario;
import med.vol.api.repository.UsuarioRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class TokenService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Value("${api.security.secret}")
    private String  apiSecret;

    public String generarToken(Usuario usuario) {
        String token;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            token = JWT.create()
                    .withIssuer("vol med api")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(generarFechaExpiracion())
                    .withClaim("id", usuario.getId())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException(exception);
        }
        return token;
    }

    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }

    public String getSubject(String token){
        if(!StringUtils.hasText(token))
            throw new RuntimeException("el token esta vacio");

        String subject = null;

        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            DecodedJWT verifier = JWT.require(algorithm)
                    .withIssuer("vol med api")
                    .build()
                    .verify(token);
            subject = verifier.getSubject();
        } catch (JWTVerificationException exception){
            System.out.println(exception);
        }

        if(subject == null){
            throw new RuntimeException("verifier invalido");
        }
        return subject;
    }
}

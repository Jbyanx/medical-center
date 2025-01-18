package med.vol.api.controller;

import jakarta.validation.Valid;
import med.vol.api.dto.LoginUsuario;
import med.vol.api.dto.UsuarioAutenticado;
import med.vol.api.entity.Usuario;
import med.vol.api.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioAutenticado> autenticarUsuario(@RequestBody @Valid LoginUsuario usuario) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                usuario.login(),
                usuario.clave()
        );
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var jwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        UsuarioAutenticado ua = new UsuarioAutenticado(((Usuario) usuarioAutenticado.getPrincipal()).getId(),jwtToken);
        return ResponseEntity.ok(ua);
    }
}

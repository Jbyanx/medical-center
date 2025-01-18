package med.vol.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.vol.api.dto.LoginUsuario;
import med.vol.api.entity.Usuario;
import med.vol.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");

        try {
            // Verificar si el encabezado Authorization está presente y tiene texto
            if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
                var token = authHeader.replace("Bearer ", "");

                // Obtener el subject desde el token
                var subject = tokenService.getSubject(token);

                if (subject != null) {
                    // Buscar al usuario en la base de datos
                    var usuario = usuarioRepository.findByLogin(subject);
                    if (usuario != null) {
                        // Configurar la autenticación
                        var authentication = new UsernamePasswordAuthenticationToken(
                                usuario,
                                null,
                                usuario.getAuthorities()
                        );
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        throw new IllegalArgumentException("Usuario no encontrado para el token.");
                    }
                } else {
                    throw new IllegalArgumentException("Token inválido o subject nulo.");
                }
            }

            // Continuar con la cadena de filtros si todo está bien
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            // Registrar el error
            e.printStackTrace();
            System.err.println("Error en la autenticación del token: " + e.getMessage());

            // Responder con un 401 Unauthorized y detener el flujo
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Error: Token inválido o acceso no autorizado.");
            return; // Detener el flujo
        }
    }

}

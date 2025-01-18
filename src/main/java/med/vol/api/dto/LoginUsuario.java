package med.vol.api.dto;

import jakarta.validation.constraints.NotBlank;
import med.vol.api.entity.Usuario;

public record LoginUsuario(
        @NotBlank
        String login,
        @NotBlank
        String clave
) {
    public LoginUsuario(Usuario usuario) {
        this(
                usuario.getLogin(),
                usuario.getClave()
        );
    }
}

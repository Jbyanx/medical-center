package med.vol.api.dto;

public record UsuarioAutenticado(
        Long id,
        String token
) {
    public UsuarioAutenticado (String token) {
        this(null,token);
    }
}

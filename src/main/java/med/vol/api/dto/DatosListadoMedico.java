package med.vol.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.vol.api.entity.Especialidad;
import med.vol.api.entity.Medico;

public record DatosListadoMedico(
        Long id,
        @NotBlank
        String nombre,
        @NotNull
        Especialidad especialidad,
        @NotBlank
        String documento,
        @Email @NotBlank
        String email
) {
    public DatosListadoMedico(Medico medico) {
        this(
                medico.getId(),
                medico.getNombre(),
                medico.getEspecialidad(),
                medico.getDocumento(),
                medico.getEmail()
        );
    }
}

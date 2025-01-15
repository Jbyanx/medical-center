package med.vol.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.vol.api.entity.Especialidad;
import med.vol.api.entity.Medico;

public record DatosRegistroMedico(
        @NotBlank
        String nombre,
        @Email @NotBlank
        String email,
        @NotBlank
        String documento,
        @NotBlank
        String telefono,
        @NotNull
        Especialidad especialidad,
        @NotNull
        DatosDireccion direccion
) {
    public DatosRegistroMedico(Medico medico) {
        this(
                medico.getNombre(),
                medico.getEmail(),
                medico.getDocumento(),
                medico.getTelefono(),
                medico.getEspecialidad(),
                new DatosDireccion(medico.getDireccion())
        );
    }
}

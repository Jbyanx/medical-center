package med.vol.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.vol.api.entity.Especialidad;
import med.vol.api.entity.Medico;

public record DatosRegistroMedico(
        @NotBlank(message = "{nombre.obligatorio}")
        String nombre,
        @Email(message = "{email.invalido}") @NotBlank(message = "{email.obligatorio}")
        String email,
        @NotBlank(message = "{documento.obligatorio}")
        String documento,
        @NotBlank(message = "{telefono.obligatorio}")
        String telefono,
        @NotNull(message = "{especialidad.obligatorio}")
        Especialidad especialidad,
        @NotNull(message = "{direccion.obligatorio}")
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

package med.vol.api.dto;

import med.vol.api.entity.Especialidad;

public record DatosMedico(
        String nombre,
        String email,
        String documento,
        Especialidad especialidad,
        DatosDireccion direccion
) {
}

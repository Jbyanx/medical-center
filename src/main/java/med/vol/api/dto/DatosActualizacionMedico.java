package med.vol.api.dto;

import med.vol.api.entity.Medico;

public record DatosActualizacionMedico(
        String nombre,
        String documento,
        String telefono,
        DatosDireccion direccion
) {
    public DatosActualizacionMedico(Medico m){
        this(
                m.getNombre(),
                m.getDocumento(),
                m.getTelefono(),
                new DatosDireccion(m.getDireccion())
        );
    }
}

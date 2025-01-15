package med.vol.api.dto;

import jakarta.validation.constraints.NotBlank;
import med.vol.api.entity.Direccion;

public record DatosDireccion(
        @NotBlank
        String calle,
        @NotBlank
        String distrito,
        @NotBlank
        String ciudad,
        @NotBlank
        String complemento
) {
        public DatosDireccion (Direccion direccion){
                this(
                        direccion.getCalle(),
                        direccion.getDistrito(),
                        direccion.getCiudad(),
                        direccion.getComplemento()
                );
        }
}

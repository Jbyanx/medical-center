package med.vol.api.entity;

import jakarta.persistence.*;
import lombok.*;
import med.vol.api.dto.DatosDireccion;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Direccion {
    private String calle;
    private String distrito;
    private String ciudad;
    private String complemento;

    public Direccion(DatosDireccion direccion) {
        this.calle = direccion.calle();
        this.distrito = direccion.distrito();
        this.ciudad = direccion.ciudad();
        this.complemento = direccion.complemento();
    }
}

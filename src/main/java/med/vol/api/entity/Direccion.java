package med.vol.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import med.vol.api.dto.DatosDireccion;

@Embeddable
public class Direccion {
    private String calle;
    private String distrito;
    private String ciudad;
    private String complemento;

    public Direccion() {
    }

    public Direccion(DatosDireccion direccion) {
        this.calle = direccion.calle();
        this.distrito = direccion.distrito();
        this.ciudad = direccion.ciudad();
        this.complemento = direccion.complemento();
    }

    public String getCalle() {
        return calle;
    }

    public String getDistrito() {
        return distrito;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getComplemento() {
        return complemento;
    }

    @Override
    public String toString() {
        return "calle='" + calle + '\'' +
                ", distrito='" + distrito + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", complemento='" + complemento;
    }

    public Direccion actualizarDatos(@NotNull DatosDireccion direccion) {
        return new Direccion(direccion);
    }
}

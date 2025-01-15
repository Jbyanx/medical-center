package med.vol.api.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import med.vol.api.dto.DatosActualizacionMedico;
import med.vol.api.dto.DatosRegistroMedico;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Entity
@Table(name = "medicos")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    private Especialidad  especialidad;
    @Embedded
    private Direccion direccion;
    @Column(name = "is_activo")
    private Boolean isActivo;

    public Medico(DatosRegistroMedico datosRegistroMedico) {
        this.nombre = datosRegistroMedico.nombre();
        this.email = datosRegistroMedico.email();
        this.documento = datosRegistroMedico.documento();
        this.telefono = datosRegistroMedico.telefono();
        this.especialidad = datosRegistroMedico.especialidad();
        this.direccion = new Direccion(datosRegistroMedico.direccion());
        this.isActivo = true;
    }

    public Medico() {

    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }
    public Boolean getisActivo() {return isActivo;}

    public String getDocumento() {
        return documento;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Medico medico)) return false;
        return Objects.equals(id, medico.id) && Objects.equals(nombre, medico.nombre) && Objects.equals(email, medico.email) && Objects.equals(telefono, medico.telefono) && Objects.equals(documento, medico.documento) && especialidad == medico.especialidad && Objects.equals(direccion, medico.direccion);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return  "  nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", documento='" + documento + '\'' +
                ", especialidad=" + especialidad +
                ", direccion=" + direccion;
    }

    public void actualizarDatos(@Valid DatosActualizacionMedico datosActualizacionMedico) {
        if(StringUtils.hasText(datosActualizacionMedico.nombre())){
            this.nombre = datosActualizacionMedico.nombre();
        }
        if(StringUtils.hasText(datosActualizacionMedico.telefono())){
            this.telefono = datosActualizacionMedico.telefono();
        }
        if(StringUtils.hasText(datosActualizacionMedico.documento())) {
            this.documento = datosActualizacionMedico.documento();
        }
        if (datosActualizacionMedico.direccion() != null) {
            this.direccion = this.direccion.actualizarDatos(datosActualizacionMedico.direccion());
        }
    }

    public void desactivar() {
        this.isActivo = false;
    }
}

package med.vol.api.entity;

import jakarta.persistence.*;
import lombok.*;
import med.vol.api.dto.DatosMedico;

@Entity
@Table(name = "medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String documento;
    @Enumerated(EnumType.STRING)
    private Especialidad  especialidad;
    @Embedded
    private Direccion direccion;

    public Medico(DatosMedico datosMedico) {
        this.nombre = datosMedico.nombre();
        this.email = datosMedico.email();
        this.documento = datosMedico.documento();
        this.especialidad = datosMedico.especialidad();
        this.direccion = new Direccion(datosMedico.direccion());
    }
}

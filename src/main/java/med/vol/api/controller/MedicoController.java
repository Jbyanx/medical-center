package med.vol.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.vol.api.dto.DatosActualizacionMedico;
import med.vol.api.dto.DatosListadoMedico;
import med.vol.api.dto.DatosRegistroMedico;
import med.vol.api.entity.Medico;
import med.vol.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository medicoRepository;

    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> getAllMedicos(
        @PageableDefault(size = 2)
            Pageable pageable
    ) {
        //Page<DatosListadoMedico> datosListadoMedicos = medicoRepository.findAll(pageable).map(DatosListadoMedico::new);
        Page<DatosListadoMedico> datosListadoMedicos = medicoRepository.findByIsActivoTrue(pageable)
                .map(DatosListadoMedico::new);
        return ResponseEntity.ok(datosListadoMedicos);
    }

    @GetMapping("/{idMedico}")
    public ResponseEntity<DatosListadoMedico> getMedicoById(@PathVariable Long idMedico) {
        Optional<Medico> medico = medicoRepository.findById(idMedico);

        DatosListadoMedico datosMedico = new DatosListadoMedico(medico.get());

        return ResponseEntity.ok(datosMedico);
    }

    @PostMapping
    public ResponseEntity<DatosListadoMedico> createMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico) {
        Medico medicoSaved = medicoRepository.save(new Medico(datosRegistroMedico));

        DatosListadoMedico datosListadoMedico = new DatosListadoMedico(medicoSaved);

        URI newMedico = ServletUriComponentsBuilder.fromCurrentRequest().path("/{medicoId}")
                .buildAndExpand(medicoSaved.getId()).toUri();

        return ResponseEntity.created(newMedico).body(datosListadoMedico);
    }

    @PutMapping("/{idMedico}")
    @Transactional
    public ResponseEntity<DatosListadoMedico> updateMedico(@RequestBody @Valid DatosActualizacionMedico datosActualizacionMedico, @PathVariable Long idMedico) {

        return medicoRepository.findById(idMedico)
                .map(m -> {

                    m.actualizarDatos(datosActualizacionMedico);
                    DatosListadoMedico datosListadoMedico = new DatosListadoMedico(m);

                    return ResponseEntity.ok(datosListadoMedico);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //ELIMINADO FISICO
//    @DeleteMapping("/{idMedico}")
//    @Transactional
//    public ResponseEntity<?> deleteMedico(@PathVariable Long idMedico) {
//        Optional<Medico> medicoToDelete = medicoRepository.findById(idMedico);
//
//        if (medicoToDelete.isPresent()) {
//            medicoRepository.deleteById(idMedico);
//            return ResponseEntity.ok().build();
//        }else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    //eliminado logico
    @DeleteMapping("/{idMedico}")
    @Transactional
    public ResponseEntity<?> desactivateMedico(@PathVariable Long idMedico) {
        Optional<Medico> medicoToDesactivate = medicoRepository.findById(idMedico);

        Medico medico = medicoToDesactivate.get();

        medico.desactivar();
        return ResponseEntity.noContent().build();
    }
}

package med.vol.api.controller;

import med.vol.api.dto.DatosMedico;
import med.vol.api.entity.Medico;
import med.vol.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository medicoRepository;

    @GetMapping
    public ResponseEntity<List<Medico>> getAllMedicos() {
        return ResponseEntity.ok(medicoRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Medico> createMedico(@RequestBody DatosMedico datosMedico) {
        Medico medicoSaved = medicoRepository.save(new Medico(datosMedico));

        return ResponseEntity.ok(medicoSaved);
    }
}

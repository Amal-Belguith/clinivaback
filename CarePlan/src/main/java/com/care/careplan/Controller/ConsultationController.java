package com.care.careplan.Controller;


import com.care.careplan.Entity.Consultation;
import com.care.careplan.Service.ConsultService;
import com.care.careplan.dto.ConsultationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/careplan")
public class ConsultationController {

    @Autowired
    private ConsultService Conser;

    //add
    @PostMapping(value = "/add-consultation")
    public ResponseEntity<Consultation> addcon(@RequestBody Consultation Con) {
        Conser.saveorUpdate(Con);
        return ResponseEntity.ok(Con);
    }

    //GetAll
    @GetMapping("/all-consultation")
    public List<Consultation> getConsultation() {
        return Conser.getConsultations();
    }

    @GetMapping(value = "/user-consultation/{userKy}")
    public ResponseEntity<List<Consultation>> getConsultationsByUserKy(@PathVariable Integer userKy) {
        List<Consultation> consultations = Conser.getConsultationsByUserKy(userKy);
        if (consultations != null && !consultations.isEmpty()) {
            return ResponseEntity.ok(consultations);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/view-consultation/{id}")
    public ConsultationDTO getConsultationDetails(@PathVariable Integer id) {
        return Conser.getConsultationDetails(id);
    }
}

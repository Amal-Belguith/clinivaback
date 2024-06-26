package com.care.careplan.Service;


import com.care.careplan.Client.AnalysisFeignClient;
import com.care.careplan.Client.MedicationFeignClient;
import com.care.careplan.Client.SurgicalFeignClient;
import com.care.careplan.Client.VaccinationFeignClient;
import com.care.careplan.Entity.Consultation;
import com.care.careplan.Repository.ConsultRepo;
import com.care.careplan.dto.ConsultationDTO;
import com.example.parameterization.Entity.BioAnalyses;
import com.example.parameterization.Entity.Medication;
import com.example.parameterization.Entity.SurgicalProcedure;
import com.example.parameterization.Entity.Vaccination;
import com.example.parameterization.dto.MedicationResponse;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ConsultService {

    @Autowired
    private final ConsultRepo conRepo;

    @Autowired
    private MedicationFeignClient medicationFeignClient;

    @Autowired
    private AnalysisFeignClient analysesFeignClient;

    @Autowired
    private SurgicalFeignClient surgicalFeignClient;

    @Autowired
    private VaccinationFeignClient vaccinationFeignClient;
    public void saveorUpdate(Consultation Con) {

        conRepo.save(Con);
    }

    public List<Consultation> getConsultations () {
        return conRepo.findAll();
    }

    public List<Consultation> getConsultationsByUserKy(Integer userKy) {
        return conRepo.findByUserKy(userKy);
    }

    public ConsultationDTO getConsultationDetails(Integer id) {
        Consultation consultation = conRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Consultation not found"));

        ConsultationDTO consultationDTO = new ConsultationDTO();
        BeanUtils.copyProperties(consultation, consultationDTO);

        List<MedicationResponse> medications = consultation.getMedicationIds().stream()
                .map(medicationFeignClient::getMedicationById)
                .collect(Collectors.toList());
        consultationDTO.setMedications(medications);

        List<BioAnalyses> analyses = consultation.getAnalysesIds().stream()
                .map(analysesFeignClient::getBioAnalysesById)
                .collect(Collectors.toList());
        consultationDTO.setAnalyses(analyses);

        List<SurgicalProcedure> surgicals = consultation.getSurgicalIds().stream()
                .map(surgicalFeignClient::getProcedureById)
                .collect(Collectors.toList());
        consultationDTO.setSurgicals(surgicals);

        List<Vaccination> vaccinations = consultation.getVaccinationIds().stream()
                .map(vaccinationFeignClient::getVaccinationById)
                .collect(Collectors.toList());
        consultationDTO.setVaccinations(vaccinations);

        return consultationDTO;
    }


}

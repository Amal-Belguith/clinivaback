package com.care.careplan.dto;

import com.example.parameterization.Entity.BioAnalyses;
import com.example.parameterization.Entity.SurgicalProcedure;
import com.example.parameterization.Entity.Vaccination;
import com.example.parameterization.dto.MedicationResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConsultationDTO {
    private Integer con_ky;
    private String doctorName;
    private String descCon;
    private String descSur;
    private String comment;
    private Integer userKy;

    private List<MedicationResponse> medications;
    private List<BioAnalyses> analyses;
    private List<SurgicalProcedure> surgicals;
    private List<Vaccination> vaccinations;

    // Getters and Setters
}


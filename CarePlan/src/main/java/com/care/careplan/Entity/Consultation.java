package com.care.careplan.Entity;


import com.example.parameterization.Entity.*;
import com.example.parameterization.dto.MedicationResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "consultation")
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer con_ky;

    private String doctorName;

    private String descCon;

    private String descSur;
    private String descPre;

    private String comment;

    @Column(name="user_ky")
    private Integer userKy;

    //med
    @ElementCollection
    private List<Integer> medicationIds = new ArrayList<>(); // List of Medication IDs

    @Transient
    private List<MedicationResponse> medications = new ArrayList<>(); // Transient as it will be fetched from Medication microservice
    //analyses
    @ElementCollection
    private List<Long> analysesIds = new ArrayList<>();

    @Transient
    private List<BioAnalyses> analyses = new ArrayList<>();
    //surgical
    @ElementCollection
    private List<Long> surgicalIds = new ArrayList<>();

    @Transient
    private List<SurgicalProcedure> surgicals = new ArrayList<>();

    //vaccination
    @ElementCollection
    private List<Long> vaccinationIds = new ArrayList<>();

    @Transient
    private List<Vaccination> vaccinations = new ArrayList<>();

}

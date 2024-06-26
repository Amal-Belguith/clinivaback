package com.example.parameterization.Entity;
import com.example.parameterization.Enum.DosageForm;
import com.example.parameterization.Enum.MedicationStrength;
import com.example.parameterization.Enum.MedicationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "medication")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medication_ky", unique = true)
    private Integer medicationKy;

    @Column(name = "medication_code", nullable = false)
    private String medicationCode;

    @Column(name = "medication_name")
    private String medicationName;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "medication_type")
    private MedicationType medicationType;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "medication_strength")
    private MedicationStrength medicationStrength;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "medication_dosage_form")
    private DosageForm medicationDosageForm;

    @OneToMany(mappedBy = "med")
    @JsonIgnoreProperties("med")
    private List<MedicIngredientLink> medicIngredientLinks;



}

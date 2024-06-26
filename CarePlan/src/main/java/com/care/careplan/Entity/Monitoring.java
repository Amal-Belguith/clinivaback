package com.care.careplan.Entity;


import com.example.parameterization.Entity.Allergy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "monitoring")
public class Monitoring {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer mon_ky;

    private String genInf;

    private String height;
    private String weight;
    private String length_w;
    private String width_w;
    private String depth_w;
    private String length_s;
    private String width_s;
    private String depth_s;
    private String temperature;
    private String respiratory;
    private String heart;
    private String systolic;
    private String diastolic;
    private String gly;
    private String comment;

    @Column(name="user_ky")
    private Integer userKy;

    @ElementCollection
    private List<Long> allergyIds = new ArrayList<>(); // List of Allergy IDs

    @Transient
    private List<Allergy> allergies = new ArrayList<>(); // Transient as it will be fetched from Allergy microservice


}

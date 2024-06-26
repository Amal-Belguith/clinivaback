package com.care.careplan.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "careplan")
public class CarePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer care_ky;

    private String phyAss;

    private String psyAss;
    private String painAss;
    private String vital;
    private String obq1;
    private String nurCare;
    private String medAdd;
    private String medProc;
    private String techCare;
    private String obq2;
    private String specGol;
    private String shortGol;
    private String longGol;
    private String obq3;
    @Column(name="user_ky")
    private Integer userKy;

}

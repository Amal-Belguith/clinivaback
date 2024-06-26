package com.care.careplan.Entity;


import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer his_ky;

    private String q1;
    private String q2;
    private String q3;
    private String q4;
    private String q5;
    private String q6;
    private String q7;
    private String med_details;
    private String q8;
    private String q9;
    private String q10;
    private String q11;
    private String all_details;
    private String q12;
    private String sur_details;
    private String q13;
    private String q14;
    private String q15;
    @Column(name="user_ky")
    private Integer userKy;

}

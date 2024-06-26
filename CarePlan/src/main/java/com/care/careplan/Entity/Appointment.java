package com.care.careplan.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Integer app_ky;

    private String first;

    private String last;

    private String gender;

    private String mobile;

    private String address;

    private String email;

    private Date dob;

    private String doctor;

    private Date doa ;

    private String timeslot;

    private String injury;

    private Integer user_ky;


}
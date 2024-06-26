package com.care.careplan.Repository;

import com.care.careplan.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Appointment a " +
            "WHERE a.doctor = :doctor AND a.doa = :doa AND a.timeslot = :timeslot AND a.email = :email")
    boolean existsByDoctorAndDoaAndTimeslotAndEmail(@Param("doctor") String doctor,
                                                    @Param("doa") Date doa,
                                                    @Param("timeslot") String timeslot,
                                                    @Param("email") String email);
}



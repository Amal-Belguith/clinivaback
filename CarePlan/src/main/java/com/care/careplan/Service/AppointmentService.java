package com.care.careplan.Service;

import com.care.careplan.Entity.Appointment;
import com.care.careplan.Repository.AppointmentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AppointmentService {

    @Autowired
    private final AppointmentRepo Apprepo;

    @Autowired
    private JavaMailSender mailSender;


    public void sendConfirmationEmail(Appointment appointment) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(appointment.getEmail());
        message.setSubject("Confirmation of appointment");
        message.setText("Hello " + appointment.getFirst() +" "+ appointment.getLast()+ ",\n\nYour appointment is confirmed for " +
                appointment.getDoa() + " at " + appointment.getTimeslot() + ".\n\nThank You.");

        mailSender.send(message);
    }


    public Appointment saveorUpdate(Appointment App) {
        return Apprepo.save(App);
    }


    public void delete(Integer app_ky) {

        Apprepo.deleteById(app_ky);
    }
    public List<Appointment> getAppointment() {
        return Apprepo.findAll();
    }

    public boolean existsByDoctorAndDoaAndEmail(String doctor, Date doa, String timeslot, String email) {
        return Apprepo.existsByDoctorAndDoaAndTimeslotAndEmail(doctor, doa, timeslot, email);
    }
    public Appointment findByApp_ky(Integer app_ky) {
        Optional<Appointment> appointmentOptional = Apprepo.findById(app_ky);
        return appointmentOptional.orElse(null);
    }





}

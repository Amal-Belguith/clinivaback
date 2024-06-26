package com.care.careplan.Service;


import com.care.careplan.Client.AllergyFeignClient;
import com.care.careplan.Entity.CarePlan;
import com.care.careplan.Entity.Monitoring;
import com.care.careplan.Repository.MonitoringRepo;
import com.example.parameterization.Entity.Allergy;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class MonitoringService {

    @Autowired
    private final MonitoringRepo monRepo;

    @Autowired
    private AllergyFeignClient allergyFeignClient;

    public void saveorUpdate(Monitoring Mon) {

        monRepo.save(Mon);
    }

    public Monitoring getMonitoringWithAllergies(Integer id) {
        Monitoring monitoring = monRepo.findById(id).orElseThrow(() -> new NotFoundException("Monitoring not found"));
        List<Allergy> allergies = new ArrayList<>();
        for (Long allergyId : monitoring.getAllergyIds()) {
            Allergy allergy = allergyFeignClient.getAllergyById(allergyId);
            allergies.add(allergy);
        }
        monitoring.setAllergies(allergies);
        return monitoring;
    }

    public List<Monitoring> getMonitoriesByUserKy(Integer userKy) {
        return monRepo.findByUserKy(userKy);
    }

}

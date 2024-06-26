package com.care.careplan.Service;

import com.care.careplan.Entity.Appointment;
import com.care.careplan.Entity.CarePlan;
import com.care.careplan.Repository.CareRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CareService {

    @Autowired
    private final CareRepo careRepo;

    public void saveorUpdate(CarePlan care) {

        careRepo.save(care);
    }

    public List<CarePlan> getCareplans() {
        return careRepo.findAll();
    }

    public void delete(Integer care_ky) {

        careRepo.deleteById(care_ky);
    }
    public List<CarePlan> getCareplansByUserKy(Integer userKy) {
        return careRepo.findByUserKy(userKy);
    }
}

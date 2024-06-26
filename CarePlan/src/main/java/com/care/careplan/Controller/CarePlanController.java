package com.care.careplan.Controller;


import com.care.careplan.Entity.Appointment;
import com.care.careplan.Entity.CarePlan;
import com.care.careplan.Service.CareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/careplan")
public class CarePlanController {


    @Autowired
    private CareService careServ;

    //add
    @PostMapping(value = "/add-careplan")
    public ResponseEntity<CarePlan> addCare(@RequestBody CarePlan care)
    {
        careServ.saveorUpdate(care);
        return ResponseEntity.ok(care);
    }
    @GetMapping("/all-careplan")
    public List<CarePlan> getCareplans() {
        return careServ.getCareplans();
    }
    @DeleteMapping("/delete-careplan/{care_ky}")
    public void deleteCare(@PathVariable("care_ky") Integer care_ky) {
        careServ.delete(care_ky);
    }

    @GetMapping(value = "/user-careplan/{userKy}")
    public ResponseEntity<List<CarePlan>> getCareplansByUserKy(@PathVariable Integer userKy) {
        List<CarePlan> careplans = careServ.getCareplansByUserKy(userKy);
        if (careplans != null && !careplans.isEmpty()) {
            return ResponseEntity.ok(careplans);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

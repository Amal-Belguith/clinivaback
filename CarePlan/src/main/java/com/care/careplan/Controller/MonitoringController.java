package com.care.careplan.Controller;

import com.care.careplan.Entity.CarePlan;
import com.care.careplan.Entity.Monitoring;
import com.care.careplan.Service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/careplan")
public class MonitoringController {

    @Autowired
    private MonitoringService monSer;



    //add
    @PostMapping(value = "/add-monitoring")
    public ResponseEntity<Monitoring> addMon(@RequestBody Monitoring mon)
    {
        monSer.saveorUpdate(mon);
        return ResponseEntity.ok(mon);
    }

    @GetMapping("/view-monitoring/{id}")
    public Monitoring getMonitoring(@PathVariable Integer id) {
        return monSer.getMonitoringWithAllergies(id);
    }

    @GetMapping(value = "/user-monitoring/{userKy}")
    public ResponseEntity<List<Monitoring>> getMonitoringByUserKy(@PathVariable Integer userKy) {
        List<Monitoring> monitoring = monSer.getMonitoriesByUserKy(userKy);
        if (monitoring != null && !monitoring.isEmpty()) {
            return ResponseEntity.ok(monitoring);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}

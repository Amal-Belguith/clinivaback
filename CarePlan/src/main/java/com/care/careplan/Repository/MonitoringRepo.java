package com.care.careplan.Repository;


import com.care.careplan.Entity.CarePlan;
import com.care.careplan.Entity.Monitoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitoringRepo extends JpaRepository<Monitoring,Integer> {
    List<Monitoring> findByUserKy(Integer userKy);
}

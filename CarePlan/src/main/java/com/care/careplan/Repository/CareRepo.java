package com.care.careplan.Repository;

import com.care.careplan.Entity.CarePlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareRepo extends JpaRepository<CarePlan,Integer> {
    List<CarePlan> findByUserKy(Integer userKy);
}

package com.care.careplan.Repository;


import com.care.careplan.Entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepo extends JpaRepository<History, Integer> {
    History findByUserKy(Integer userKy);

    boolean existsByUserKy(Integer userKy);
}

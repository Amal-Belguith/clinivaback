package com.care.careplan.Controller;



import com.care.careplan.Entity.History;
import com.care.careplan.Service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/careplan")
public class HistoryController {


    @Autowired
    private HistoryService hisServ;


    @PostMapping(value = "/add-history")
    public ResponseEntity<History> addHis(@RequestBody History his) {
        Integer userKy = his.getUserKy();
        if (hisServ.historyExistsForUser(userKy)) {
            return ResponseEntity.badRequest().body(null);
        } else {
            hisServ.saveOrUpdate(his);
            return ResponseEntity.ok(his);
        }
    }

    @GetMapping(value = "/check-history/{userKy}")
    public boolean checkHistoryForUser(@PathVariable Integer userKy) {
        return hisServ.historyExistsForUser(userKy);
    }

    // Exemple de méthode pour récupérer l'historique déchiffré
    @GetMapping(value = "/all-history")
    public List<History> getAllHistories() {
        return hisServ.getHistories();
    }


    @GetMapping(value = "/user-history/{userKy}")
    public ResponseEntity<History> getHistoryByUserKy(@PathVariable Integer userKy) {
        History history = hisServ.getHistoryByUserKy(userKy);
        if (history != null) {
            return ResponseEntity.ok(history);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}

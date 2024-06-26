package com.care.careplan.Service;



import com.care.careplan.Entity.History;
import com.care.careplan.Repository.HistoryRepo;
import com.care.careplan.security.VigenereCipher;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class HistoryService {

    @Autowired
    private final HistoryRepo hisRepo;


    @Autowired
    private final VigenereCipher vigenereCipher;

    private static final String KEY = "PMOLKémlkèAçolkjhgvsdr64576IJH?NBml?kXRFYGUIOJDZ097XDT62";

    public History saveOrUpdate(History his) {
        his.setQ1(vigenereCipher.encrypt(his.getQ1(), KEY));
        his.setQ2(vigenereCipher.encrypt(his.getQ2(), KEY));
        his.setQ3(vigenereCipher.encrypt(his.getQ3(), KEY));
        his.setQ4(vigenereCipher.encrypt(his.getQ4(), KEY));
        his.setQ5(vigenereCipher.encrypt(his.getQ5(), KEY));
        his.setQ6(vigenereCipher.encrypt(his.getQ6(), KEY));
        his.setQ7(vigenereCipher.encrypt(his.getQ7(), KEY));
        his.setQ8(vigenereCipher.encrypt(his.getQ8(), KEY));
        his.setQ9(vigenereCipher.encrypt(his.getQ9(), KEY));
        his.setQ10(vigenereCipher.encrypt(his.getQ10(), KEY));
        his.setQ11(vigenereCipher.encrypt(his.getQ11(), KEY));
        his.setQ12(vigenereCipher.encrypt(his.getQ12(), KEY));
        his.setQ13(vigenereCipher.encrypt(his.getQ13(), KEY));
        his.setQ14(vigenereCipher.encrypt(his.getQ14(), KEY));
        his.setQ15(vigenereCipher.encrypt(his.getQ15(), KEY));
        his.setMed_details(vigenereCipher.encrypt(his.getMed_details(), KEY));
        his.setAll_details(vigenereCipher.encrypt(his.getAll_details(), KEY));
        his.setSur_details(vigenereCipher.encrypt(his.getSur_details(), KEY));
        hisRepo.save(his);
        return his;
    }
    public boolean historyExistsForUser(Integer userKy) {
        return hisRepo.existsByUserKy(userKy);
    }
    public List<History> getHistories() {
        List<History> histories = hisRepo.findAll();
        for (History his : histories) {
            his.setQ1(vigenereCipher.decrypt(his.getQ1(), KEY));
            his.setQ2(vigenereCipher.decrypt(his.getQ2(), KEY));
            his.setQ3(vigenereCipher.decrypt(his.getQ3(), KEY));
            his.setQ4(vigenereCipher.decrypt(his.getQ4(), KEY));
            his.setQ5(vigenereCipher.decrypt(his.getQ5(), KEY));
            his.setQ6(vigenereCipher.decrypt(his.getQ6(), KEY));
            his.setQ7(vigenereCipher.decrypt(his.getQ7(), KEY));
            his.setQ8(vigenereCipher.decrypt(his.getQ8(), KEY));
            his.setQ9(vigenereCipher.decrypt(his.getQ9(), KEY));
            his.setQ10(vigenereCipher.decrypt(his.getQ10(), KEY));
            his.setQ11(vigenereCipher.decrypt(his.getQ11(), KEY));
            his.setQ12(vigenereCipher.decrypt(his.getQ12(), KEY));
            his.setQ13(vigenereCipher.decrypt(his.getQ13(), KEY));
            his.setQ14(vigenereCipher.decrypt(his.getQ14(), KEY));
            his.setQ15(vigenereCipher.decrypt(his.getQ15(), KEY));
            his.setMed_details(vigenereCipher.decrypt(his.getMed_details(), KEY));
            his.setAll_details(vigenereCipher.decrypt(his.getAll_details(), KEY));
            his.setSur_details(vigenereCipher.decrypt(his.getSur_details(), KEY));
        }
        return histories;
    }

    public History getHistoryByUserKy(Integer userKy) {
        History history = hisRepo.findByUserKy(userKy);
        if (history != null) {
            history.setQ1(vigenereCipher.decrypt(history.getQ1(), KEY));
            history.setQ2(vigenereCipher.decrypt(history.getQ2(), KEY));
            history.setQ3(vigenereCipher.decrypt(history.getQ3(), KEY));
            history.setQ4(vigenereCipher.decrypt(history.getQ4(), KEY));
            history.setQ5(vigenereCipher.decrypt(history.getQ5(), KEY));
            history.setQ6(vigenereCipher.decrypt(history.getQ6(), KEY));
            history.setQ7(vigenereCipher.decrypt(history.getQ7(), KEY));
            history.setQ8(vigenereCipher.decrypt(history.getQ8(), KEY));
            history.setQ9(vigenereCipher.decrypt(history.getQ9(), KEY));
            history.setQ10(vigenereCipher.decrypt(history.getQ10(), KEY));
            history.setQ11(vigenereCipher.decrypt(history.getQ11(), KEY));
            history.setQ12(vigenereCipher.decrypt(history.getQ12(), KEY));
            history.setQ13(vigenereCipher.decrypt(history.getQ13(), KEY));
            history.setQ14(vigenereCipher.decrypt(history.getQ14(), KEY));
            history.setQ15(vigenereCipher.decrypt(history.getQ15(), KEY));
            history.setMed_details(vigenereCipher.decrypt(history.getMed_details(), KEY));
            history.setAll_details(vigenereCipher.decrypt(history.getAll_details(), KEY));
            history.setSur_details(vigenereCipher.decrypt(history.getSur_details(), KEY));
        }
        return history;
    }



}


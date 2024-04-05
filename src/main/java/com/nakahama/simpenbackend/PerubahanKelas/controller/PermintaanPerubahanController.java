package com.nakahama.simpenbackend.PerubahanKelas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.nakahama.simpenbackend.PerubahanKelas.service.GantiPengajarService;
import com.nakahama.simpenbackend.PerubahanKelas.service.RescheduleService;
import com.nakahama.simpenbackend.util.ResponseUtil;
import com.nakahama.simpenbackend.PerubahanKelas.dto.PermintaanPerubahanMapper;
import com.nakahama.simpenbackend.PerubahanKelas.model.PengajarMenggantikan;
import com.nakahama.simpenbackend.PerubahanKelas.model.Reschedule;

import java.util.*;

@RestController
@RequestMapping("/permintaan-perubahan")
public class PermintaanPerubahanController {

    @Autowired
    RescheduleService rescheduleService;

    @Autowired
    GantiPengajarService gantiPengajarService;

    @GetMapping("")
    public ResponseEntity<Object> getAllPermintaanPerubahan() {
        List<Reschedule> listReschedule = rescheduleService.getAll();
        List<PengajarMenggantikan> listPengajarMenggantikan = gantiPengajarService.getAll();
        return ResponseUtil.okResponse(
                PermintaanPerubahanMapper.toListReadPermintaanPerubahan(listReschedule, listPengajarMenggantikan),
                "Success");
    }

}

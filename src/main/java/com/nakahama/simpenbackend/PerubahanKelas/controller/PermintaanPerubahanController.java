package com.nakahama.simpenbackend.PerubahanKelas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import com.nakahama.simpenbackend.PerubahanKelas.service.RescheduleService;
import com.nakahama.simpenbackend.util.ResponseUtil;
import com.nakahama.simpenbackend.PerubahanKelas.dto.PermintaanPerubahanMapper;
import com.nakahama.simpenbackend.PerubahanKelas.model.Reschedule;

import java.util.*;

@RestController
@RequestMapping("/permintaan-perubahan")
public class PermintaanPerubahanController {

    @Autowired
    RescheduleService rescheduleService;

    @GetMapping("")
    public ResponseEntity<Object> getAllRescheduleByKelas() {
        // TODO: Implement ganti Pengajar
        List<Reschedule> response = rescheduleService.getAll();
        return ResponseUtil.okResponse(PermintaanPerubahanMapper.toListReadPermintaanPerubahan(response), "Success");
    }

}

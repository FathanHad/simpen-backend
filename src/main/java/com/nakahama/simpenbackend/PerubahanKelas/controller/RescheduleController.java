package com.nakahama.simpenbackend.PerubahanKelas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nakahama.simpenbackend.PerubahanKelas.service.RescheduleService;
import com.nakahama.simpenbackend.util.ResponseUtil;
import com.nakahama.simpenbackend.Kelas.model.SesiKelas;
import com.nakahama.simpenbackend.Kelas.service.SesiKelasService;
import com.nakahama.simpenbackend.PerubahanKelas.dto.Reschedule.CreateReschedule;
import com.nakahama.simpenbackend.PerubahanKelas.dto.Reschedule.RescheduleMapper;

import jakarta.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/reschedule")
public class RescheduleController {

    @Autowired
    RescheduleService rescheduleService;

    @Autowired
    SesiKelasService sesiKelasService;

    @PostMapping("/create/{kelasId}")
    public ResponseEntity<Object> createReschedule(@Valid @RequestBody List<CreateReschedule> rescheduleRequest) {
        rescheduleService.save(rescheduleRequest);
        return ResponseUtil.okResponse(null, rescheduleRequest.size() + " Reschedule created");
    }

    @GetMapping("/{kelasId}")
    public ResponseEntity<Object> getAllRescheduleByKelas(@PathVariable int kelasId) {
        List<SesiKelas> response = sesiKelasService.getByKelasId(kelasId);
        return ResponseUtil.okResponse(RescheduleMapper.toReadDetailReschedule(response), "Success");
    }

}

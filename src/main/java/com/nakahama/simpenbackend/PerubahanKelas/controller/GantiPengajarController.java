package com.nakahama.simpenbackend.PerubahanKelas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nakahama.simpenbackend.PerubahanKelas.service.GantiPengajarService;
import com.nakahama.simpenbackend.util.ResponseUtil;
import com.nakahama.simpenbackend.Kelas.model.SesiKelas;
import com.nakahama.simpenbackend.Kelas.service.SesiKelasService;
import com.nakahama.simpenbackend.PerubahanKelas.dto.GantiPengajar.CreateGantiPengajar;
import com.nakahama.simpenbackend.PerubahanKelas.dto.GantiPengajar.GantiPengajarMapper;

import jakarta.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/ganti-pengajar")
public class GantiPengajarController {

    @Autowired
    GantiPengajarService gantiPengajarService;

    @Autowired
    SesiKelasService sesiKelasService;

    @PostMapping("/create/{kelasId}")
    public ResponseEntity<Object> createGantiPengajar(
            @Valid @RequestBody List<CreateGantiPengajar> listGantiPengajarRequest) {
        gantiPengajarService.save(listGantiPengajarRequest);
        return ResponseUtil.okResponse(null, listGantiPengajarRequest.size() + " Reschedule created");
    }

    @GetMapping("/{kelasId}")
    public ResponseEntity<Object> getAllRescheduleByKelas(@PathVariable int kelasId) {
        List<SesiKelas> response = sesiKelasService.getByKelasId(kelasId);
        return ResponseUtil.okResponse(GantiPengajarMapper.toReadDetailGantiPengajar(response), "Success");
    }

}

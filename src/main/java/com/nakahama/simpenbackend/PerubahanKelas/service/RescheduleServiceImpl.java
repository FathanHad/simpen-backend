package com.nakahama.simpenbackend.PerubahanKelas.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nakahama.simpenbackend.Kelas.model.Kelas;
import com.nakahama.simpenbackend.Kelas.model.SesiKelas;
import com.nakahama.simpenbackend.Kelas.service.KelasService;
import com.nakahama.simpenbackend.Kelas.service.SesiKelasService;
import com.nakahama.simpenbackend.PerubahanKelas.dto.Reschedule.CreateReschedule;
import com.nakahama.simpenbackend.PerubahanKelas.dto.Reschedule.RescheduleMapper;
import com.nakahama.simpenbackend.PerubahanKelas.model.Reschedule;
import com.nakahama.simpenbackend.PerubahanKelas.repository.RescheduleDb;

@Service
public class RescheduleServiceImpl implements RescheduleService {

    @Autowired
    RescheduleDb rescheduleDb;

    @Autowired
    SesiKelasService sesiKelasService;

    @Autowired
    KelasService kelasService;

    @Override
    public List<Reschedule> getAll() {
        return rescheduleDb.findAll();
    }

    @Override
    public void save(List<CreateReschedule> rescheduleRequest) {
        for (CreateReschedule request : rescheduleRequest) {
            SesiKelas sesiKelasRequested = sesiKelasService.getById(request.getSesiKelasId());
            rescheduleDb.save(RescheduleMapper.toEntity(request, sesiKelasRequested));
        }
    }

    @Override
    public Reschedule getById(UUID id) {
        Reschedule reschedule = rescheduleDb.findById(id).orElse(null);
        if (reschedule == null)
            throw new NoSuchElementException("Reschedule with id " + id + " not found");

        return reschedule;
    }

    @Override
    public List<Reschedule> getAllByKelasId(int kelasId) {
        Kelas kelas = kelasService.getById(kelasId);
        return rescheduleDb.findAllByKelas(kelas);
    }

}

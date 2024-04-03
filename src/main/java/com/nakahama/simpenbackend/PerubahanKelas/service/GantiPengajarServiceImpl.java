package com.nakahama.simpenbackend.PerubahanKelas.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nakahama.simpenbackend.Kelas.model.Kelas;
import com.nakahama.simpenbackend.Kelas.model.SesiKelas;
import com.nakahama.simpenbackend.Kelas.service.KelasService;
import com.nakahama.simpenbackend.Kelas.service.SesiKelasService;
import com.nakahama.simpenbackend.PerubahanKelas.dto.GantiPengajar.CreateGantiPengajar;
import com.nakahama.simpenbackend.PerubahanKelas.dto.GantiPengajar.GantiPengajarMapper;
import com.nakahama.simpenbackend.PerubahanKelas.model.PengajarMenggantikan;
import com.nakahama.simpenbackend.PerubahanKelas.repository.PengajarMenggantikanDb;

@Service
public class GantiPengajarServiceImpl implements GantiPengajarService {

    @Autowired
    PengajarMenggantikanDb pengajarMenggantikanDb;

    @Autowired
    SesiKelasService sesiKelasService;

    @Autowired
    KelasService kelasService;

    @Override
    public List<PengajarMenggantikan> getAll() {
        return pengajarMenggantikanDb.findAll();
    }

    @Override
    public void save(List<CreateGantiPengajar> pengajarMenggantikanRequest) {
        List<PengajarMenggantikan> listPengajarMenggantikanToCreate = new ArrayList<>();
        for (CreateGantiPengajar request : pengajarMenggantikanRequest) {
            SesiKelas sesiKelasRequested = sesiKelasService.getById(request.getSesiKelasId());
            if (!sesiKelasRequested.getStatus().equals("Scheduled")) {
                throw new IllegalArgumentException(
                        "SesiKelas with id " + request.getSesiKelasId() + " is not scheduled");
            }
            listPengajarMenggantikanToCreate.add(GantiPengajarMapper.toEntity(request, sesiKelasRequested));
        }
        for (PengajarMenggantikan pengajarMenggantikan : listPengajarMenggantikanToCreate) {
            pengajarMenggantikan.getSesiKelas().setStatus("Perubahan Pengajar Requested");
            pengajarMenggantikanDb.save(pengajarMenggantikan);
        }

    }

    @Override
    public PengajarMenggantikan getById(UUID id) {
        PengajarMenggantikan pengajarMenggantikan = pengajarMenggantikanDb.findById(id).orElse(null);
        if (pengajarMenggantikan == null)
            throw new NoSuchElementException("Pengajar Menggantikan with id " + id + " not found");

        return pengajarMenggantikan;
    }

    @Override
    public List<PengajarMenggantikan> getAllByKelasId(int kelasId) {
        Kelas kelas = kelasService.getById(kelasId);
        return pengajarMenggantikanDb.findAllByKelas(kelas);
    }

}

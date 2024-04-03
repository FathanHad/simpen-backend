package com.nakahama.simpenbackend.PerubahanKelas.service;

import java.util.List;
import java.util.UUID;

import com.nakahama.simpenbackend.PerubahanKelas.dto.GantiPengajar.CreateGantiPengajar;
import com.nakahama.simpenbackend.PerubahanKelas.model.PengajarMenggantikan;

public interface GantiPengajarService {

    public List<PengajarMenggantikan> getAll();

    public void save(List<CreateGantiPengajar> listPengajarMenggantikanRequest);

    public PengajarMenggantikan getById(UUID id);

    public List<PengajarMenggantikan> getAllByKelasId(int kelasId);
}

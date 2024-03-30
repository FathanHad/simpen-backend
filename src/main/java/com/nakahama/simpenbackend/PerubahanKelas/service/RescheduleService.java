package com.nakahama.simpenbackend.PerubahanKelas.service;

import java.util.List;
import java.util.UUID;

import com.nakahama.simpenbackend.PerubahanKelas.dto.Reschedule.CreateReschedule;
import com.nakahama.simpenbackend.PerubahanKelas.model.Reschedule;

public interface RescheduleService {

    public List<Reschedule> getAll();

    public void save(List<CreateReschedule> rescheduleRequest);

    public Reschedule getById(UUID id);

    public List<Reschedule> getAllByKelasId(int kelasId);
}

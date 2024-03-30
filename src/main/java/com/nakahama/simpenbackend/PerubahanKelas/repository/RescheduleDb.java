package com.nakahama.simpenbackend.PerubahanKelas.repository;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nakahama.simpenbackend.Kelas.model.Kelas;
import com.nakahama.simpenbackend.PerubahanKelas.model.Reschedule;

import java.util.*;

@Repository
@Transactional
public interface RescheduleDb extends JpaRepository<Reschedule, UUID> {
    Optional<Reschedule> findById(UUID id);

    List<Reschedule> findAllByKelas(Kelas kelas);

}

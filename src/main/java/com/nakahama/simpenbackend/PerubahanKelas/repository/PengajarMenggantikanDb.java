package com.nakahama.simpenbackend.PerubahanKelas.repository;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nakahama.simpenbackend.Kelas.model.Kelas;
import com.nakahama.simpenbackend.PerubahanKelas.model.PengajarMenggantikan;

import java.util.*;

@Repository
@Transactional
public interface PengajarMenggantikanDb extends JpaRepository<PengajarMenggantikan, UUID> {
    Optional<PengajarMenggantikan> findById(UUID id);

    List<PengajarMenggantikan> findAllByKelas(Kelas kelas);

}

package com.nakahama.simpenbackend.User.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nakahama.simpenbackend.User.model.Availability;
import com.nakahama.simpenbackend.User.model.Pengajar;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface AvailabilityDb extends JpaRepository<Availability, UUID> {
    @Modifying
    @Query("DELETE FROM Availability m WHERE m.pengajar = :pengajar")
    void deleteByPengajar(@Param("pengajar") Pengajar pengajar);

    @Query("SELECT m FROM Availability m WHERE m.pengajar = :pengajar")
    List<Availability> findByPengajar(@Param("pengajar") Pengajar pengajar);

    @Modifying
    @Query("DELETE FROM Availability m WHERE m.pengajar = :pengajar AND m.waktu BETWEEN :waktustart AND :waktuEnd")
    void deleteByPengajarAndWaktu(@Param("pengajar") Pengajar pengajar, @Param("waktustart") LocalDateTime waktuStart,
            @Param("waktuEnd") LocalDateTime waktuEnd);

}

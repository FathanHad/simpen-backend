package com.nakahama.simpenbackend.PerubahanKelas.dto.Reschedule;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

import com.nakahama.simpenbackend.Kelas.dto.SesiKelas.SesiKelasDTO;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ReadReschedule {

    private UUID id;

    private SesiKelasDTO sesiKelas;

    private LocalDateTime waktuAwal;

    private LocalDateTime waktuBaru;

    private String alasan;

    private String status;
}

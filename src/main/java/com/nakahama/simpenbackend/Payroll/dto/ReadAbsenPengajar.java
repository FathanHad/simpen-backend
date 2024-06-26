package com.nakahama.simpenbackend.Payroll.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ReadAbsenPengajar {
    private String programName;

    private String jenisKelasName;

    private String pengajar;

    private int kodeKelas;

    private LocalDateTime tanggalAbsen;

    private int fee;

    private UUID id;

    private Date tanggalMulai;

    private Date tanggalSelesai;


}

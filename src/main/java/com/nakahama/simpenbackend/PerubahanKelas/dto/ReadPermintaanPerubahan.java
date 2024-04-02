package com.nakahama.simpenbackend.PerubahanKelas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonFormat;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ReadPermintaanPerubahan {

    private UUID permintaanId;

    private int kelasId;

    private String namaPengajar;

    private UUID pengajarId;

    private String status;

    private String tipePermintaan;

    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime waktuPermintaan;
}

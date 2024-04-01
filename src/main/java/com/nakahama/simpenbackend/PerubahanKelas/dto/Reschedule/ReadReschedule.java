package com.nakahama.simpenbackend.PerubahanKelas.dto.Reschedule;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonFormat;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ReadReschedule {

    private UUID id;

    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime waktuAwal;

    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime waktuBaru;

    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime waktuPermintaan;

    private String alasan;

    private String status;
}

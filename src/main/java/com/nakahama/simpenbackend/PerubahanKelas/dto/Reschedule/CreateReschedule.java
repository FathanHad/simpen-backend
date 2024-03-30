package com.nakahama.simpenbackend.PerubahanKelas.dto.Reschedule;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class CreateReschedule {

    @NotNull(message = "Id Sesi is mandatory")
    private UUID sesiKelasId;

    @NotNull(message = "Waktu Awal is mandatory")
    private LocalDateTime waktuAwal;

    @NotNull(message = "Waktu Baru is mandatory")
    private LocalDateTime waktuBaru;

    @NotNull(message = "Alasan is mandatory")
    private String alasan;

    private String status = "Requested";
}

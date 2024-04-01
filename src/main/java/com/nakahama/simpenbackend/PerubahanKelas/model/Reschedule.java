package com.nakahama.simpenbackend.PerubahanKelas.model;

import com.nakahama.simpenbackend.Kelas.model.Kelas;
import com.nakahama.simpenbackend.Kelas.model.SesiKelas;
import java.time.LocalDateTime;
import java.util.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reschedule")
public class Reschedule {

    @Id
    private UUID id = UUID.randomUUID();

    @NotNull
    @ManyToOne
    private Kelas kelas;

    @NotNull
    @ManyToOne
    private SesiKelas sesiKelas;

    @NotNull
    private LocalDateTime waktuAwal;

    @NotNull
    private LocalDateTime waktuBaru;

    @NotNull
    private String alasan;

    @NotNull
    private String status = "Requested";

    @NotNull
    private LocalDateTime waktuPermintaan;

}

package com.nakahama.simpenbackend.PerubahanKelas.model;

import com.nakahama.simpenbackend.Kelas.model.Kelas;
import com.nakahama.simpenbackend.Kelas.model.SesiKelas;
import com.nakahama.simpenbackend.User.model.Pengajar;

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
@Table(name = "pengajar_menggantikan")
public class PengajarMenggantikan {

    @Id
    private UUID id = UUID.randomUUID();

    @NotNull
    @ManyToOne
    private Kelas kelas;

    @NotNull
    @ManyToOne
    private SesiKelas sesiKelas;

    private Pengajar pengajarPenganti;

    @NotNull
    private String alasan;

    @NotNull
    private String status = "Requested";

    @NotNull
    private LocalDateTime waktuPermintaan;

}

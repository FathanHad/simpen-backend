package com.nakahama.simpenbackend.Kelas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nakahama.simpenbackend.PerubahanKelas.model.PengajarMenggantikan;
import com.nakahama.simpenbackend.PerubahanKelas.model.Reschedule;
import com.nakahama.simpenbackend.User.model.Pengajar;
import java.time.LocalDateTime;
import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sesi_kelas")
public class SesiKelas {
    @Id
    private UUID Id = UUID.randomUUID();

    @NotNull
    private Integer nomorPertemuan;

    @NotNull
    @ManyToOne(cascade = { CascadeType.ALL })
    @JsonIgnore
    private Pengajar pengajar; // need to change after Pengajar model is created

    @ManyToOne(cascade = { CascadeType.ALL })
    @JsonIgnore
    private Pengajar pengajarPengganti; // need to change after Pengajar model is created

    @ManyToOne
    @NotNull
    @JsonIgnore
    private Kelas kelas;

    @NotNull
    private String platform; // need to change after platform model is created (Sprint 2)

    @NotNull
    private LocalDateTime waktuPelaksanaan;

    @NotNull
    private String status;

    @OneToMany(mappedBy = "sesiKelas", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MuridSesi> listMuridSesi;

    private double persentaseKehadiran;

    private double averageRating;

    @OneToMany(mappedBy = "sesiKelas", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reschedule> listReschedule;

    @OneToMany(mappedBy = "sesiKelas", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PengajarMenggantikan> listPengajarMenggantikan;
}

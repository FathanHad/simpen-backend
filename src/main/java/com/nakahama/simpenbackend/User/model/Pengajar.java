package com.nakahama.simpenbackend.User.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nakahama.simpenbackend.Kelas.model.Kelas;
import com.nakahama.simpenbackend.Kelas.model.SesiKelas;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("Pengajar")
public class Pengajar extends UserModel {

    @Size(max = 100)
    @Column(name = "alamat_ktp")
    private String alamatKTP;

    @Size(max = 100)
    @Column(name = "domisili_kota")
    private String domisiliKota;
  
    @Column(name = "foto_diri", columnDefinition = "TEXT")
    private String fotoDiri;
    
    @Size(max = 100)
    @Column(name = "backup_phone_num")
    private String backupPhoneNum;

    @Size(max = 100)
    @Column(name = "no_rekening_bank")
    private String noRekBank;

    @Size(max = 100)
    @Column(name = "nama_bank")
    private String namaBank;

    @Size(max = 100)
    @Column(name = "nama_pemilik_rekening")
    private String namaPemilikRek;

    @Column(name = "foto_buku_tabungan", columnDefinition = "TEXT")
    private String fotoBukuTabungan;

    @Size(max = 100)
    @Column(name = "pendidikan_terkahir")
    private String pendidikanTerakhir;

    @Column(name = "tgl_masuk_kontrak")
    private Date tglMasukKontrak;

    @Size(max = 100)
    @Column(name = "pekerjaan_lainnya")
    private String pekerjaanLainnya;

    @Size(max = 100)
    @Column(name = "nik")
    private String nik;
   
    @Column(name = "foto_ktp", columnDefinition = "TEXT")
    private String fotoKtp;

    @Size(max = 100)
    @Column(name = "npwp")
    private String npwp;

    @Column(name = "foto_npwp", columnDefinition = "TEXT")
    private String fotoNpwp;

    @Size(max = 100)
    @Column(name = "nama_kontak_darurat")
    private String namaKontakDarurat;

    @Size(max = 100)
    @Column(name = "no_telp_darurat")
    private String noTelpDarurat;

    @ManyToMany(mappedBy = "listPengajar", fetch = FetchType.LAZY)
    @JsonIgnore
    List<Tag> listTag;
    
    @OneToMany
    @JsonIgnore
    private List<SesiKelas> sesiKelas;

    @OneToMany
    @JsonIgnore
    private List<Kelas> kelas;
}

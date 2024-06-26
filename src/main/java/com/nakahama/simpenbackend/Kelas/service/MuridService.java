package com.nakahama.simpenbackend.Kelas.service;

import java.util.List;

import com.nakahama.simpenbackend.Kelas.dto.Murid.CreateMurid;
import com.nakahama.simpenbackend.Kelas.dto.Murid.UpdateMurid;
import com.nakahama.simpenbackend.Kelas.model.Murid;

public interface MuridService {

        public List<Murid> getAll();

        public Murid save(CreateMurid murid);

        public Murid update(UpdateMurid murid);

        public Murid getById(int id);

        public void delete(int id);

        public Murid getByNama(String nama);

        public Murid getByNamaOrtu(String namaOrtu);

        public Murid getByEmailOrtu(String emailOrtu);

        public Murid getByNoHpOrtu(String noHpOrtu);

}

package com.nakahama.simpenbackend.Kelas.dto.Kelas;

import com.nakahama.simpenbackend.Kelas.dto.Murid.MuridMapper;
import com.nakahama.simpenbackend.Kelas.dto.SesiKelas.SesiKelasDTO;
import com.nakahama.simpenbackend.Kelas.dto.SesiKelas.SesiKelasMapper;
import com.nakahama.simpenbackend.Kelas.model.JenisKelas;
import com.nakahama.simpenbackend.Kelas.model.Kelas;
import com.nakahama.simpenbackend.Kelas.model.MuridKelas;
import com.nakahama.simpenbackend.Kelas.model.Program;
import com.nakahama.simpenbackend.Kelas.model.SesiKelas;
import com.nakahama.simpenbackend.User.model.Pengajar;
import com.nakahama.simpenbackend.User.model.UserModel;

import java.util.*;

public class KelasMapper {

    public static Kelas toEntity(CreateKelas request, Program program, JenisKelas jenisKelas,
            Pengajar pengajar, int kelasId, List<MuridKelas> listMurid) {
        Kelas response = new Kelas();
        response.setKelasId(kelasId);
        response.setProgram(program);
        response.setJenisKelas(jenisKelas);
        response.setOperasional(request.getOperasional());

        response.setPengajar(pengajar);
        response.setLevel(request.getLevel());
        response.setTanggalMulai(request.getTanggalMulai());
        response.setTanggalSelesai(request.getTanggalSelesai());
        response.setJumlahMurid(request.getListMurid().size());

        if (request.getLinkGroup() != null) {
            response.setLinkGroup(request.getLinkGroup());
        }

        response.setMuridKelas(listMurid);
        response.setListsesiKelas(new ArrayList<SesiKelas>());
        return response;
    }

    public static Kelas toEntity(CreateKelas request, Program program, JenisKelas jenisKelas,
            Pengajar pengajar, int kelasId) {
        Kelas response = new Kelas();
        response.setKelasId(kelasId);
        response.setProgram(program);
        response.setJenisKelas(jenisKelas);
        response.setOperasional(request.getOperasional());

        response.setPengajar(pengajar);
        response.setLevel(request.getLevel());
        response.setTanggalMulai(request.getTanggalMulai());
        response.setTanggalSelesai(request.getTanggalSelesai());
        response.setJumlahMurid(request.getListMurid().size());

        if (request.getLinkGroup() != null) {
            response.setLinkGroup(request.getLinkGroup());
        }

        response.setListsesiKelas(new ArrayList<SesiKelas>());
        return response;
    }

    public static Kelas toEntity(UpdateKelas request, Kelas existingKelas, Pengajar pengajar,
            List<MuridKelas> listMurid) {
        Kelas response = existingKelas;
        response.setTanggalMulai(request.getTanggalMulai());
        response.setTanggalSelesai(request.getTanggalSelesai());
        response.setPengajar(pengajar);
        response.setLinkGroup(request.getLinkGroup());
        response.setLinkPlaylist(request.getLinkPlaylist());
        response.setMuridKelas(listMurid);
        response.setJumlahMurid(request.getListMurid().size());
        response.setLevel(request.getLevel());
        return response;
    }

    public static ReadDetailKelas toDetailDto(Kelas createdKelas, List<SesiKelas> listSesiKelas, UserModel pengajar,
            List<MuridKelas> listMurid) {

        ReadDetailKelas response = new ReadDetailKelas();
        response.setProgramName(createdKelas.getProgram().getNama());
        response.setProgramId(createdKelas.getProgram().getId());
        response.setJenisKelasName(createdKelas.getJenisKelas().getNama());
        response.setJenisKelasId(createdKelas.getJenisKelas().getId());
        response.setListSesi(new ArrayList<SesiKelasDTO>());
        response.setListSesi(SesiKelasMapper.toListDto(listSesiKelas));
        response.setNamaPengajar(pengajar.getNama());
        response.setTanggalMulai(createdKelas.getTanggalMulai());
        response.setTanggalSelesai(createdKelas.getTanggalSelesai());
        response.setPengajarId(createdKelas.getPengajar().getId());
        response.setLinkGroup(createdKelas.getLinkGroup());
        response.setLinkPlaylist(createdKelas.getLinkPlaylist());
        response.setListMurid(MuridMapper.toListMuridKelas(listMurid));
        response.setLevel(createdKelas.getLevel());
        response.setStatus(createdKelas.getStatus());
        // TODO: IMPLEMENT AVERAGE RATING
        // TODO: IMPLEMENT PLATFORM
        response.setPlatform("TODO: IMPLEMENT PLATFORM");
        return response;

    }

    public static ReadKelas toReadDto(Kelas kelas) {
        ReadKelas response = new ReadKelas();
        response.setPengajar(kelas.getPengajar().getNama());
        response.setJenisKelasName(kelas.getJenisKelas().getNama());
        response.setProgramName(kelas.getProgram().getNama());
        response.setTanggalMulai(kelas.getTanggalMulai());
        response.setTanggalSelesai(kelas.getTanggalSelesai());
        response.setId(kelas.getKelasId());
        response.setJumlah_murid(kelas.getJumlahMurid());
        response.setStatus(kelas.getStatus());
        response.setModa("Online");

        return response;
    }

}

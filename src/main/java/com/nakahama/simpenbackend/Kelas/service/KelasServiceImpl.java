package com.nakahama.simpenbackend.Kelas.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nakahama.simpenbackend.Kelas.dto.Kelas.CreateKelas;
import com.nakahama.simpenbackend.Kelas.dto.Kelas.KelasMapper;
import com.nakahama.simpenbackend.Kelas.dto.Kelas.UpdateKelas;
import com.nakahama.simpenbackend.Kelas.model.JenisKelas;
import com.nakahama.simpenbackend.Kelas.model.Kelas;
import com.nakahama.simpenbackend.Kelas.model.MuridKelas;
import com.nakahama.simpenbackend.Kelas.model.Program;
import com.nakahama.simpenbackend.Kelas.model.SesiKelas;
import com.nakahama.simpenbackend.Kelas.repository.KelasDb;
import com.nakahama.simpenbackend.Kelas.repository.ProgramDb;
import com.nakahama.simpenbackend.Kelas.repository.JenisKelas.JenisKelasDb;
import com.nakahama.simpenbackend.User.model.Pengajar;
import com.nakahama.simpenbackend.User.model.UserModel;
import com.nakahama.simpenbackend.User.repository.PengajarDb;
import com.nakahama.simpenbackend.User.service.AvailabilityService;
import com.nakahama.simpenbackend.User.service.UserService;

@Service
public class KelasServiceImpl implements KelasService {

    @Autowired
    KelasDb kelasDb;

    @Autowired
    JenisKelasDb jenisKelasDb;

    @Autowired
    ProgramDb programDb;

    @Autowired
    PengajarDb pengajarDb;

    @Autowired
    ProgramService programService;

    @Autowired
    JenisKelasService jenisKelasService;

    @Autowired
    UserService userService;

    @Autowired
    SesiKelasService sesiKelasService;

    @Autowired
    MuridKelasService muridKelasService;

    @Autowired
    AvailabilityService availabilityService;

    @Override
    public List<Kelas> getAll() {
        return kelasDb.findAll();
    }

    @Override
    public List<Kelas> getAllKelasPengajar(UserModel userModel) {
        return kelasDb.findAllByPengajar((Pengajar) userModel);
    }

    @Override
    public Kelas save(CreateKelas request) {

        Program program = programService.getById(request.getProgramId());
        JenisKelas jenisKelas = jenisKelasService.getById(request.getJenisKelasId());
        Pengajar pengajar = (Pengajar) userService.getUserById(request.getPengajarId());

        Kelas createdKelas = KelasMapper.toEntity(request, program, jenisKelas,
                pengajar, generateKelasId());
        kelasDb.save(createdKelas);

        List<MuridKelas> listMurid = muridKelasService.createListMuridKelas(request.getListMurid(), createdKelas);
        createdKelas.setMuridKelas(listMurid);
        kelasDb.save(createdKelas);

        List<SesiKelas> listSesiKelas = sesiKelasService.createListSesiKelas(request.getJadwalKelas(), createdKelas,
                pengajar, listMurid);
        createdKelas.setListsesiKelas(listSesiKelas);

        kelasDb.save(createdKelas);
        availabilityService.deleteAvailability(pengajar, listSesiKelas.get(0).getWaktuPelaksanaan());

        if (jenisKelas.getKelas().size() == 0) {
            jenisKelas.setKelas(new ArrayList<Kelas>());
        }
        jenisKelas.getKelas().add(createdKelas);
        jenisKelasDb.save(jenisKelas);

        if (program.getKelas().size() == 0) {
            program.setKelas(new ArrayList<Kelas>());
        }
        program.getKelas().add(createdKelas);
        programDb.save(program);

        if (pengajar.getKelas().size() == 0) {
            pengajar.setKelas(new ArrayList<Kelas>());
        }
        pengajar.getKelas().add(createdKelas);
        pengajarDb.save(pengajar);

        return createdKelas;

    }

    private int generateKelasId() {
        List<Kelas> listKelas = kelasDb.findAllIncludeDeleted();
        if (listKelas.isEmpty()) {
            return 1000;
        }
        return 1000 + listKelas.size() + 1;
    }

    @Override
    public Kelas getById(int id) {
        Kelas kelas = kelasDb.findById(id).get();
        if (kelas == null) {
            throw new NoSuchElementException("Kelas with id " + id + " not found");
        }
        if (kelas.getIsDeleted()) {
            throw new NoSuchElementException("Kelas with id " + id + " is deleted");
        }
        return kelas;
    }

    @Override
    public void delete(int id) {
        Kelas kelas = getById(id);
        kelas.setIsDeleted(true);
        kelasDb.save(kelas);
    }

    @Override
    public Kelas update(UpdateKelas kelasRequest) {

        Kelas updatedKelas = kelasDb.findById(kelasRequest.getId()).get();
        Pengajar newpengajar = (Pengajar) userService.getUserById(kelasRequest.getPengajarId());
        List<MuridKelas> newlistMurid = muridKelasService.updateListMurid(kelasRequest.getListMurid(), updatedKelas);

        updatedKelas = KelasMapper.toEntity(kelasRequest, updatedKelas, newpengajar, newlistMurid);
        kelasDb.save(updatedKelas);

        sesiKelasService.updateListSesiKelas(
                updatedKelas,
                newpengajar, newlistMurid);
        return updatedKelas;
    }

    @Override
    public Kelas addPlaylist(int kelasId, String linkPlaylist) {
        Kelas kelas = getById(kelasId);
        kelas.setLinkPlaylist(linkPlaylist);
        return kelasDb.save(kelas);
    }

    @Override
    public void updateRating(int kelasId) {
        Kelas kelas = getById(kelasId);

        List<SesiKelas> listSesiKelas = kelas.getListsesiKelas();
        double totalRating = 0;
        int totalSesiSudahBerlangsung = 0;

        for (SesiKelas sesiKelas : listSesiKelas) {
            if (sesiKelas.getAverageRating() == 0) {
                continue;
            }
            totalRating += sesiKelas.getAverageRating();
            totalSesiSudahBerlangsung++;
        }
        kelas.setAverageRating(totalRating / totalSesiSudahBerlangsung);
        kelasDb.save(kelas);
    }
}

package com.nakahama.simpenbackend.Kelas.service;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.nakahama.simpenbackend.Feedback.service.FeedbackService;
import com.nakahama.simpenbackend.Kelas.dto.SesiKelas.UpdateAbsensiMurid;
import com.nakahama.simpenbackend.Kelas.model.*;
import com.nakahama.simpenbackend.Kelas.repository.SesiKelasDb;
import com.nakahama.simpenbackend.PerubahanKelas.model.PengajarMenggantikan;
import com.nakahama.simpenbackend.PerubahanKelas.model.Reschedule;
import com.nakahama.simpenbackend.Platform.service.PlatformService;
import com.nakahama.simpenbackend.User.model.Pengajar;
import com.nakahama.simpenbackend.User.model.UserModel; // need to change after Pengajar model is created
import com.nakahama.simpenbackend.User.repository.PengajarDb;
import com.nakahama.simpenbackend.User.service.UserService;

@Service
public class SesiKelasServiceImpl implements SesiKelasService {

    @Autowired
    SesiKelasDb sesiKelasDb;

    @Autowired
    PengajarDb pengajarDb;

    @Autowired
    @Lazy
    KelasService kelasService;

    @Autowired
    UserService userService;

    @Autowired
    @Lazy
    MuridSesiService muridSesiService;

    @Autowired
    @Lazy
    PlatformService platformService;

    @Autowired
    FeedbackService feedbackService;

    @Override
    public List<SesiKelas> getAll() {
        return sesiKelasDb.findAll();
    }

    @Override
    public SesiKelas save(SesiKelas sesiKelas) {
        return sesiKelasDb.save(sesiKelas);
    }

    @Override
    public SesiKelas getById(UUID id) {
        SesiKelas sesiKelas = sesiKelasDb.findById(id).get();
        if (sesiKelas == null)
            throw new NoSuchElementException("Sesi Kelas with id " + id + " not found");
        return sesiKelas;
    }

    @Override
    public void delete(UUID id) {
        sesiKelasDb.deleteById(id);
    }

    @Override
    public List<SesiKelas> getByKelasId(int idKelas) {
        Kelas kelas = kelasService.getById(idKelas);
        List<SesiKelas> listSesiKelas = sesiKelasDb.findAllByKelas(kelas);
        if (listSesiKelas == null)
            throw new NoSuchElementException("Sesi Kelas with kelas " + kelas + " not found");
        listSesiKelas.sort(Comparator.comparing(SesiKelas::getNomorPertemuan));
        for (SesiKelas sesiKelas : listSesiKelas) {
            sesiKelas.getListReschedule().sort(Comparator.comparing(Reschedule::getWaktuPermintaan));
            sesiKelas.getListPengajarMenggantikan()
                    .sort(Comparator.comparing(PengajarMenggantikan::getWaktuPermintaan));
        }
        return listSesiKelas;
    }

    @Override
    public List<SesiKelas> getByPengajarId(UUID idPengajar) {
        UserModel pengajar = userService.getUserById(idPengajar);
        List<SesiKelas> sesiKelas = sesiKelasDb.findAllByPengajar(pengajar);
        if (sesiKelas == null)
            throw new NoSuchElementException("Sesi Kelas with pengajar " + pengajar + " not found");
        return sesiKelas;
    }

    @Override
    public SesiKelas getByPengajarPenggantiId(UUID idPengajarPengganti) {
        UserModel pengajar = userService.getUserById(idPengajarPengganti);
        SesiKelas sesiKelas = sesiKelasDb.findByPengajarPengganti(pengajar).get();
        if (sesiKelas == null)
            throw new NoSuchElementException("Sesi Kelas with pengajar pengganti " + pengajar + " not found");
        return sesiKelas;
    }

    @Override
    public SesiKelas getByWaktuPermintaanBetween(Date startDate, Date endDate) {
        SesiKelas sesiKelas = sesiKelasDb.findByWaktuPermintaanBetween(startDate, endDate).get();
        if (sesiKelas == null)
            throw new NoSuchElementException(
                    "Sesi Kelas with waktu permintaan between " + startDate + " and " + endDate + " not found");
        return sesiKelas;
    }

    @Override
    public SesiKelas getByStatus(String status) {
        SesiKelas sesiKelas = sesiKelasDb.findByStatus(status).get();
        if (sesiKelas == null)
            throw new NoSuchElementException(
                    "Sesi Kelas with status " + status + " not found");
        return sesiKelas;
    }

    @Override
    public SesiKelas getByPlatformId(String platform) {
        SesiKelas sesiKelas = sesiKelasDb.findByStatus(platform).get();
        if (sesiKelas == null)
            throw new NoSuchElementException(
                    "Sesi Kelas with platform " + platform + " not found");
        return sesiKelas;
    }

    @Override
    public List<SesiKelas> createListSesiKelas(List<LocalDateTime> jadwalKelas, Kelas createdKelas, Pengajar pengajar,
            List<MuridKelas> listMurid) {
        List<SesiKelas> listSesiKelas = new ArrayList<>();
        int nomorPertemuan = 1;
        for (LocalDateTime e : jadwalKelas) {
            SesiKelas sesiKelas = new SesiKelas();

            sesiKelas.setKelas(createdKelas);
            sesiKelas.setPengajar(pengajar);
            sesiKelas.setWaktuPelaksanaan(e);
            sesiKelas.setStatus("Scheduled");
            sesiKelas.setNomorPertemuan(nomorPertemuan);
            save(sesiKelas);
            listSesiKelas.add(sesiKelas);

            sesiKelas.setListMuridSesi(muridSesiService.createListMuridSesi(listMurid, sesiKelas));
            save(sesiKelas);

            if (pengajar.getSesiKelas().size() == 0) {
                pengajar.setSesiKelas(new ArrayList<SesiKelas>());
            }
            pengajar.getSesiKelas().add(sesiKelas);
            pengajarDb.save(pengajar);

            nomorPertemuan++;
        }

        if (createdKelas.getJenisKelas().getModaPertemuan().equals("ONLINE")) {
            platformService.assignZoom(listSesiKelas);
        } else {
            platformService.assignRuangan(listSesiKelas);
        }

        return listSesiKelas;
    }

    @Override
    public void uppdateAbsenSesi(UUID idSesi, List<UpdateAbsensiMurid> updateAbsensiMurid) {
        SesiKelas sesiKelas = getById(idSesi);
        List<MuridSesi> muridSesi = sesiKelas.getListMuridSesi();
        if (muridSesi.size() != updateAbsensiMurid.size())
            throw new NoSuchElementException("MuridSesi and UpdateAbsensiMurid size not match");

        double totalRating = 0;
        double attendance = 0;

        for (int i = 0; i < muridSesi.size(); i++) {
            MuridSesi muridSesiToUpdate = muridSesi.get(i);
            muridSesiToUpdate.setIsPresent(updateAbsensiMurid.get(i).getIsPresent() == null ? false
                    : updateAbsensiMurid.get(i).getIsPresent());
            muridSesiToUpdate.setRating(updateAbsensiMurid.get(i).getRating() == null ? 0
                    : updateAbsensiMurid.get(i).getRating() > 5 ? 5 : updateAbsensiMurid.get(i).getRating());
            muridSesiToUpdate.setKomentar(updateAbsensiMurid.get(i).getKomentar());
            muridSesiService.save(muridSesiToUpdate);

            if (muridSesiToUpdate.getIsPresent() != null && muridSesiToUpdate.getIsPresent()) {
                attendance++;
                totalRating += muridSesiToUpdate.getRating();
            }
        }

        if (attendance != 0) {
            sesiKelas.setAverageRating(totalRating / attendance);
            sesiKelas.setPersentaseKehadiran((attendance / sesiKelas.getListMuridSesi().size()) * 100);
        } else {
            sesiKelas.setAverageRating(0);
            sesiKelas.setPersentaseKehadiran(0);
        }
        save(sesiKelas);
        kelasService.updateRating(sesiKelas.getKelas().getKelasId());
    }

    @Override
    public void updateStatus(SesiKelas sesiKelas) {
        // TODO: adapt lagi
        sesiKelas.setStatus("Finished");
        if(sesiKelas.getKelas().getListsesiKelas().stream().allMatch(s -> s.getStatus().equals("Finished"))){
            sesiKelas.getKelas().setStatus("Finished");
        }else{
            sesiKelas.getKelas().setStatus("Ongoing");
        }
        save(sesiKelas);

        feedbackService.generateFeedback(sesiKelas.getKelas());

    }

    @Override
    public SesiKelas updateJadwal(SesiKelas sesiKelas) {
        SesiKelas sesiKelasToUpdate = getById(sesiKelas.getId());
        sesiKelasToUpdate.setJadwalZoom(sesiKelas.getJadwalZoom());
        sesiKelasToUpdate.setJadwalRuangan(sesiKelas.getJadwalRuangan());
        return save(sesiKelasToUpdate);
    }

    @Override
    public List<SesiKelas> updateListSesiKelas(Kelas updatedKelas, Pengajar newpengajar,
            List<MuridKelas> newlistMurid) {
        List<SesiKelas> listSesiKelas = getByKelasId(updatedKelas.getKelasId());
        for (SesiKelas sesiKelas : listSesiKelas) {
            if (sesiKelas.getStatus() != "Scheduled")
                continue;

            sesiKelas.setPengajar(newpengajar);
            sesiKelas.setListMuridSesi(muridSesiService.createListMuridSesi(newlistMurid, sesiKelas));
            save(sesiKelas);
        }
        return listSesiKelas;
    }

}

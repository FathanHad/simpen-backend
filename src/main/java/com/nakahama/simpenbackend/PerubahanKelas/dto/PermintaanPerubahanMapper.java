package com.nakahama.simpenbackend.PerubahanKelas.dto;

import java.util.ArrayList;
import java.util.List;

import com.nakahama.simpenbackend.PerubahanKelas.model.PengajarMenggantikan;
import com.nakahama.simpenbackend.PerubahanKelas.model.Reschedule;

public class PermintaanPerubahanMapper {
    public static List<ReadPermintaanPerubahan> toListReadPermintaanPerubahan(List<Reschedule> listReschedule,
            List<PengajarMenggantikan> listPengajarMenggantikan) {
        List<ReadPermintaanPerubahan> response = new ArrayList<>();
        for (Reschedule reschedule : listReschedule) {
            response.add(toReadDetailReschedule(reschedule));
        }

        for (PengajarMenggantikan pengajarMenggantikan : listPengajarMenggantikan) {
            response.add(toReadDetailGantiPengajar(pengajarMenggantikan));
        }
        return response;
    }

    private static ReadPermintaanPerubahan toReadDetailGantiPengajar(PengajarMenggantikan pengajarMenggantikan) {
        ReadPermintaanPerubahan response = new ReadPermintaanPerubahan();
        response.setPermintaanId(pengajarMenggantikan.getId());
        response.setKelasId(pengajarMenggantikan.getSesiKelas().getKelas().getKelasId());
        response.setNamaPengajar(pengajarMenggantikan.getSesiKelas().getPengajar().getNama());
        response.setPengajarId(pengajarMenggantikan.getSesiKelas().getPengajar().getId());
        response.setStatus(pengajarMenggantikan.getStatus());
        response.setWaktuPermintaan(pengajarMenggantikan.getWaktuPermintaan());
        response.setTipePermintaan("Ganti Pengajar");

        return response;
    }

    private static ReadPermintaanPerubahan toReadDetailReschedule(Reschedule reschedule) {
        ReadPermintaanPerubahan response = new ReadPermintaanPerubahan();
        response.setPermintaanId(reschedule.getId());
        response.setKelasId(reschedule.getSesiKelas().getKelas().getKelasId());
        response.setNamaPengajar(reschedule.getSesiKelas().getPengajar().getNama());
        response.setPengajarId(reschedule.getSesiKelas().getPengajar().getId());
        response.setStatus(reschedule.getStatus());
        response.setWaktuPermintaan(reschedule.getWaktuPermintaan());
        response.setTipePermintaan("Reschedule");

        return response;

    }
}

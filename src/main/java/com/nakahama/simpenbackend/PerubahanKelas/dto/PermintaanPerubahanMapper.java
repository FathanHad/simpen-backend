package com.nakahama.simpenbackend.PerubahanKelas.dto;

import java.util.ArrayList;
import java.util.List;

import com.nakahama.simpenbackend.PerubahanKelas.model.Reschedule;

public class PermintaanPerubahanMapper {
    public static List<ReadPermintaanPerubahan> toListReadPermintaanPerubahan(List<Reschedule> listReschedule) {
        List<ReadPermintaanPerubahan> response = new ArrayList<>();
        for (Reschedule reschedule : listReschedule) {
            response.add(toReadDetailReschedule(reschedule));
        }
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

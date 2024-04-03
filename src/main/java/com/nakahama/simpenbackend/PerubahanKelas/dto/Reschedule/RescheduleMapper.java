package com.nakahama.simpenbackend.PerubahanKelas.dto.Reschedule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.nakahama.simpenbackend.Kelas.dto.SesiKelas.SesiKelasMapper;
import com.nakahama.simpenbackend.Kelas.model.SesiKelas;
import com.nakahama.simpenbackend.PerubahanKelas.model.Reschedule;

public class RescheduleMapper {
    public static Reschedule toEntity(CreateReschedule request, SesiKelas sesiKelas) {
        Reschedule response = new Reschedule();
        response.setWaktuBaru(request.getWaktuBaru());
        response.setAlasan(request.getAlasan());
        response.setStatus(request.getStatus());
        response.setSesiKelas(sesiKelas);
        response.setKelas(sesiKelas.getKelas());
        response.setWaktuAwal(sesiKelas.getWaktuPelaksanaan());
        response.setWaktuPermintaan(LocalDateTime.now());

        return response;
    }

    public static ReadRescheduleSesi toReadRescheduleSesi(SesiKelas request) {
        ReadRescheduleSesi response = new ReadRescheduleSesi();
        response.setSesiKelas(SesiKelasMapper.toDto(request));
        response.setListReschedule(new ArrayList<>());

        for (Reschedule reschedule : request.getListReschedule()) {
            ReadReschedule readReschedule = toReadReschedule(reschedule);
            response.getListReschedule().add(readReschedule);
        }

        if (response.getListReschedule().size() > 0) {
            ReadReschedule latest = response.getListReschedule().get(response.getListReschedule().size() - 1);
            if (latest.getStatus().equals("Requested")) {
                response.setActiveReschedule(response.getListReschedule().get(response.getListReschedule().size() - 1));
                response.setActiveRescheduleDate(
                        response.getActiveReschedule().getWaktuBaru().toLocalDate().toString());
                response.setActiveRescheduleTime(
                        response.getActiveReschedule().getWaktuBaru().toLocalTime().toString());
            }
        }
        return response;
    }

    private static ReadReschedule toReadReschedule(Reschedule request) {
        ReadReschedule response = new ReadReschedule();
        response.setId(request.getId());
        response.setWaktuAwal(request.getWaktuAwal());
        response.setWaktuBaru(request.getWaktuBaru());
        response.setAlasan(request.getAlasan());
        response.setStatus(request.getStatus());
        response.setWaktuPermintaan(request.getWaktuPermintaan());
        return response;
    }

    public static ReadDetailReschedule toReadDetailReschedule(List<SesiKelas> request) {
        ReadDetailReschedule response = new ReadDetailReschedule();
        response.setListSesiReschedule(new ArrayList<>());
        for (SesiKelas sesiKelas : request) {
            ReadRescheduleSesi readRescheduleSesi = toReadRescheduleSesi(sesiKelas);
            response.getListSesiReschedule().add(readRescheduleSesi);
        }
        return response;
    }
}

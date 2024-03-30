package com.nakahama.simpenbackend.PerubahanKelas.dto.Reschedule;

import java.time.LocalDateTime;
import java.util.List;

import com.nakahama.simpenbackend.Kelas.dto.Kelas.KelasMapper;
import com.nakahama.simpenbackend.Kelas.model.SesiKelas;
import com.nakahama.simpenbackend.PerubahanKelas.model.Reschedule;

public class RescheduleMapper {
    public static Reschedule toEntity(CreateReschedule request, SesiKelas sesiKelas) {
        Reschedule response = new Reschedule();
        response.setWaktuAwal(request.getWaktuAwal());
        response.setWaktuBaru(request.getWaktuBaru());
        response.setAlasan(request.getAlasan());
        response.setStatus(request.getStatus());
        response.setSesiKelas(sesiKelas);
        response.setWaktuPermintaan(LocalDateTime.now());

        return response;
    }

    public static ReadRescheduleSesi toReadRescheduleSesi(Reschedule request) {
        ReadRescheduleSesi response = new ReadRescheduleSesi();
        response.setId(request.getId());
        response.setSesiKelas(request.getSesiKelas());
        response.setWaktuAwal(request.getWaktuAwal());
        response.setWaktuBaru(request.getWaktuBaru());
        response.setAlasan(request.getAlasan());
        response.setStatus(request.getStatus());

        return response;
    }

    public static ReadDetailReschedule toReadDetailReschedule(List<Reschedule> request) {
        ReadDetailReschedule response = new ReadDetailReschedule();
        response.setKelas(KelasMapper.toReadDto(request.get(0).getKelas()));
        for (Reschedule reschedule : request) {
            ReadRescheduleSesi readRescheduleSesi = toReadRescheduleSesi(reschedule);
            response.getListRescheduleSesi().add(readRescheduleSesi);
        }
        return response;
    }
}

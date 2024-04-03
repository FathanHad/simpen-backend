package com.nakahama.simpenbackend.PerubahanKelas.dto.GantiPengajar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.nakahama.simpenbackend.Kelas.dto.SesiKelas.SesiKelasMapper;
import com.nakahama.simpenbackend.Kelas.model.SesiKelas;
import com.nakahama.simpenbackend.PerubahanKelas.model.PengajarMenggantikan;

public class GantiPengajarMapper {
    public static PengajarMenggantikan toEntity(CreateGantiPengajar request, SesiKelas sesiKelas) {
        PengajarMenggantikan response = new PengajarMenggantikan();
        response.setKelas(sesiKelas.getKelas());
        response.setSesiKelas(sesiKelas);
        response.setAlasan(request.getAlasan());
        response.setWaktuPermintaan(LocalDateTime.now());
        return response;
    }

    public static ReadGantiPengajarSesi toReadGantiPengajarSesi(SesiKelas request) {
        ReadGantiPengajarSesi response = new ReadGantiPengajarSesi();
        response.setSesiKelas(SesiKelasMapper.toDto(request));
        response.setListGantiPengajar(new ArrayList<>());

        for (PengajarMenggantikan pengajarMenggantikan : request.getListPengajarMenggantikan()) {
            ReadGantiPengajar readGantiPengajar = toReadGantiPengajar(pengajarMenggantikan);
            response.getListGantiPengajar().add(readGantiPengajar);
        }

        if (response.getListGantiPengajar().size() > 0) {
            ReadGantiPengajar latest = response.getListGantiPengajar().get(response.getListGantiPengajar().size() - 1);
            if (latest.getStatus().equals("Requested")) {
                response.setActiveGantiPengajar(
                        response.getListGantiPengajar().get(response.getListGantiPengajar().size() - 1));
                response.setActiveGantiPengajarNamaPengajar(
                        response.getActiveGantiPengajar().getIdPengajar().toString());
                response.setActiveGantiPengajarNamaPengajar(response.getActiveGantiPengajar().getNamaPengajar());
            }
        }
        return response;
    }

    private static ReadGantiPengajar toReadGantiPengajar(PengajarMenggantikan request) {
        ReadGantiPengajar response = new ReadGantiPengajar();
        response.setId(request.getId());
        response.setIdPengajar(request.getPengajarPenganti().getId());
        response.setNamaPengajar(request.getPengajarPenganti().getNama());
        response.setAlasan(request.getAlasan());
        response.setStatus(request.getStatus());
        return response;
    }

    public static ReadDetailGantiPengajar toReadDetailGantiPengajar(List<SesiKelas> request) {
        ReadDetailGantiPengajar response = new ReadDetailGantiPengajar();
        response.setListSesiGantiPengajar(new ArrayList<>());
        for (SesiKelas sesiKelas : request) {
            ReadGantiPengajarSesi readGantiPengajarSesi = toReadGantiPengajarSesi(sesiKelas);
            response.getListSesiGantiPengajar().add(readGantiPengajarSesi);
        }
        return response;
    }
}

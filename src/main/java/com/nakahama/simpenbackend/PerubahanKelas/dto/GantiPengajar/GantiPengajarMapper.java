package com.nakahama.simpenbackend.PerubahanKelas.dto.GantiPengajar;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.nakahama.simpenbackend.Kelas.dto.SesiKelas.SesiKelasMapper;
import com.nakahama.simpenbackend.Kelas.model.SesiKelas;
import com.nakahama.simpenbackend.PerubahanKelas.model.PengajarMenggantikan;
import com.nakahama.simpenbackend.User.model.UserModel;

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
                ReadGantiPengajar activeGantiPengajar = response.getActiveGantiPengajar();
                if (activeGantiPengajar.getIdPengajarPengganti() != null) {
                    response.setActiveGantiPengajarNamaPengajar(
                            response.getActiveGantiPengajar().getIdPengajarPengganti().toString());
                    response.setActiveGantiPengajarNamaPengajar(
                            response.getActiveGantiPengajar().getNamaPengajarPenggati());
                }
            }
        }
        return response;
    }

    private static ReadGantiPengajar toReadGantiPengajar(PengajarMenggantikan request) {
        ReadGantiPengajar response = new ReadGantiPengajar();
        response.setId(request.getId());
        UserModel pengajarPengganti = request.getPengajarPenganti();
        if (pengajarPengganti != null) {
            response.setIdPengajarPengganti(request.getPengajarPenganti().getId());
            response.setNamaPengajarPenggati(request.getPengajarPenganti().getNama());
        }
        response.setAlasan(request.getAlasan());
        response.setStatus(request.getStatus());
        response.setWaktuPermintaan(request.getWaktuPermintaan());
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

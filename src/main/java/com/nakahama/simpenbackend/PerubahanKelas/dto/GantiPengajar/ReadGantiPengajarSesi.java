package com.nakahama.simpenbackend.PerubahanKelas.dto.GantiPengajar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

import com.nakahama.simpenbackend.Kelas.dto.SesiKelas.SesiKelasDTO;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ReadGantiPengajarSesi {

    private SesiKelasDTO sesiKelas;

    private ReadGantiPengajar activeGantiPengajar;

    private String activeGantiPengajarNamaPengajar = "";

    private String activeGantiPengajarIdPengajar = "";

    private List<ReadGantiPengajar> listGantiPengajar;
}

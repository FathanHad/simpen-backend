package com.nakahama.simpenbackend.PerubahanKelas.dto.Reschedule;

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
public class ReadRescheduleSesi {

    private SesiKelasDTO sesiKelas;

    private ReadReschedule activeReschedule;

    private String activeRescheduleDate = "";

    private String activeRescheduleTime = "";

    private List<ReadReschedule> listReschedule;
}

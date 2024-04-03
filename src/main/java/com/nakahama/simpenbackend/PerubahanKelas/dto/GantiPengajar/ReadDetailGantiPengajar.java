package com.nakahama.simpenbackend.PerubahanKelas.dto.GantiPengajar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ReadDetailGantiPengajar {

    private List<ReadGantiPengajarSesi> listSesiGantiPengajar;
}

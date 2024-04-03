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
public class ReadGantiPengajar {

    private UUID id;

    private String namaPengajar;

    private UUID idPengajar;

    private String alasan;

    private String status;
}

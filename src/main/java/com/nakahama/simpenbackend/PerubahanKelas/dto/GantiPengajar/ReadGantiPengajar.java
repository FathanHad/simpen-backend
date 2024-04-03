package com.nakahama.simpenbackend.PerubahanKelas.dto.GantiPengajar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ReadGantiPengajar {

    private UUID id;

    private String namaPengajarPenggati = "";

    private UUID idPengajarPengganti;

    private String alasan;

    private String status;

    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime waktuPermintaan;
}

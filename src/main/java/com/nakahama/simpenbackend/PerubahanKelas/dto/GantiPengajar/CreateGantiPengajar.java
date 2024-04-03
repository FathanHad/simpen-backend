package com.nakahama.simpenbackend.PerubahanKelas.dto.GantiPengajar;

import jakarta.validation.constraints.NotNull;
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
public class CreateGantiPengajar {

    @NotNull(message = "Id Sesi is mandatory")
    private UUID sesiKelasId;

    @NotNull(message = "Alasan is mandatory")
    private String alasan;

    private String status = "Requested";
}

package com.nakahama.simpenbackend.PerubahanKelas.dto.Reschedule;

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
public class ReadDetailReschedule {

    private List<ReadRescheduleSesi> listSesiReschedule;
}

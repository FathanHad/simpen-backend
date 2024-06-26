package com.nakahama.simpenbackend.Feedback.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ListRatingResponseDTO {

    @NotBlank(message = "Pengajar required")
    UUID idPengajar;

    @NotBlank(message = "Pengajar required")
    String namaPengajar;

    List<RatingMuridResponseDTO> listRatingMurid = new ArrayList<RatingMuridResponseDTO>();
}

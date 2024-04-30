package com.nakahama.simpenbackend.Feedback.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FillFeedbackRequestDTO {

    // @NotBlank(message = "Id required")
    UUID id;

    @NotBlank(message = "Isi required")
    String isi;

    // @NotBlank(message = "Rating required")
    Double rating;
}

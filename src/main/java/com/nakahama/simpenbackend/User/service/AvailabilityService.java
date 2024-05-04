package com.nakahama.simpenbackend.User.service;

import java.time.LocalDateTime;
import java.util.List;

import com.nakahama.simpenbackend.User.dto.availability.ReadAvailability;
import com.nakahama.simpenbackend.User.dto.availability.UpdateAvailabilityRequest;
import com.nakahama.simpenbackend.User.model.Pengajar;

public interface AvailabilityService {
    void updateAvailability(List<UpdateAvailabilityRequest> listAvailabilityDTO, Pengajar pengajar);

    ReadAvailability getAvailability(Pengajar pengajar);

    void deleteAvailability(Pengajar pengajar, LocalDateTime waktu);
}

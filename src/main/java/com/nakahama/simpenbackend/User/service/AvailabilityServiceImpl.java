package com.nakahama.simpenbackend.User.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nakahama.simpenbackend.User.dto.availability.ReadAvailability;
import com.nakahama.simpenbackend.User.dto.availability.UpdateAvailabilityRequest;
import com.nakahama.simpenbackend.User.model.Availability;
import com.nakahama.simpenbackend.User.model.Pengajar;
import com.nakahama.simpenbackend.User.repository.AvailabilityDb;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    @Autowired
    AvailabilityDb availabilityDb;

    @Autowired
    UserService userService;

    @Override
    public void updateAvailability(List<UpdateAvailabilityRequest> listAvailabilityDTO, Pengajar pengajar) {
        availabilityDb.deleteByPengajar(pengajar);
        for (UpdateAvailabilityRequest availabilityDTO : listAvailabilityDTO) {
            Availability availability = new Availability();
            availability.setPengajar(pengajar);
            availability.setWaktu(availabilityDTO.getWaktu());
            userService.setLastUpdateAvailability(pengajar.getId());
            availabilityDb.save(availability);
        }

    }

    @Override
    public ReadAvailability getAvailability(Pengajar pengajar) {
        List<Availability> listAvailability = availabilityDb.findByPengajar(pengajar);

        ReadAvailability readAvailability = new ReadAvailability();

        readAvailability.setAvailability(new ArrayList<LocalDateTime>());
        readAvailability.setIdPengajar(pengajar.getId());
        readAvailability.setNamaPengajar(pengajar.getNama());
        readAvailability.setLastUpdate(pengajar.getLastUpdateAvailability());

        for (Availability availability : listAvailability) {
            readAvailability.getAvailability().add(availability.getWaktu());
        }
        return readAvailability;
    }

    @Override
    public void deleteAvailability(Pengajar pengajar, LocalDateTime waktu) {
        waktu = LocalDateTime.of(2024, 1, waktu.getDayOfWeek().getValue(), waktu.getHour(), waktu.getMinute());
        availabilityDb.deleteByPengajarAndWaktu(pengajar, waktu.minusMinutes(15), waktu.plusMinutes(15));
        userService.setLastUpdateAvailability(pengajar.getId());
    }

}

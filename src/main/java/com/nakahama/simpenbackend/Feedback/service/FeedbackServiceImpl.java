package com.nakahama.simpenbackend.Feedback.service;

import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nakahama.simpenbackend.Feedback.dto.FillFeedbackRequestDTO;
import com.nakahama.simpenbackend.Feedback.model.Feedback;
import com.nakahama.simpenbackend.Feedback.repository.FeedbackDb;
import com.nakahama.simpenbackend.Kelas.model.Kelas;
import com.nakahama.simpenbackend.Notification.dto.GenerateNotifDTO;
import com.nakahama.simpenbackend.Notification.service.NotificationService;
import com.nakahama.simpenbackend.exception.BadRequestException;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    FeedbackDb feedbackDb;

    @Autowired
    NotificationService notificationService;

    @Override
    public List<Feedback> retrieveAllFeedback() {
        return feedbackDb.findAll();
    }

    @Override
    public List<Feedback> feedbackByUserId(UUID id) {
        List<Feedback> listFeedback = retrieveAllFeedback();
        List<Feedback> feedbackById = new ArrayList<>();

        if (listFeedback != null) {
            for (Feedback feedback : listFeedback) {
                if (feedback.getPengajar().getId().equals(id)) {
                    feedbackById.add(feedback);
                }
            }
        }

        return feedbackById;
    }

    @Override
    public Feedback getFeedbackById(UUID id) {
        for (Feedback feedback : retrieveAllFeedback()) {
            if (feedback.getId().equals(id)) {
                return feedback;
            }
        }
        throw new BadRequestException("Feedback with id " + id + " not found");

    }

    @Override
    public Feedback fillFeedback(FillFeedbackRequestDTO fillFeedbackRequestDTO) {
        Feedback feedback = getFeedbackById(fillFeedbackRequestDTO.getId());

        feedback.setIsi(fillFeedbackRequestDTO.getIsi());
        feedback.setRating(fillFeedbackRequestDTO.getRating());
        feedback.setFinished(true);

        feedbackDb.save(feedback);

        return feedback;
    }

    @Override
    public void deleteFeedback(UUID id) {
        Feedback feedback = getFeedbackById(id);

        if (feedback != null) {
            feedback.setDeleted(true);
            feedbackDb.save(feedback);

        }
    }

    @Override
    public void generateFeedback(Kelas kelas) {
        List<Feedback> listFeedback = retrieveAllFeedback();
        boolean kelasIsCreated = false;

        if (listFeedback != null) {
            for (Feedback feedback : listFeedback) {
                if (feedback.getKelas().getKelasId() == (kelas.getKelasId())) {
                    kelasIsCreated = true;
                }
            }
        }

        if (!kelasIsCreated) {
            Feedback feedback = new Feedback();
            feedback.setPengajar(kelas.getPengajar());
            feedback.setKelas(kelas);
            feedback.setTanggalPembuatan(LocalDateTime.now());

            feedbackDb.save(feedback);

            GenerateNotifDTO notification = new GenerateNotifDTO();

            notification.setAkunPenerima(kelas.getPengajar().getId());
            notification.setTipe(2);
            notification.setJudul("Anda mendapatkan feedback");
            notification.getIsi().put("feedbackId", String.valueOf(feedback.getId()));
            notificationService.generateNotification(notification);

        }
    }

}

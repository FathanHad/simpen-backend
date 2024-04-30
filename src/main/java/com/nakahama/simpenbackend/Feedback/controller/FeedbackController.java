package com.nakahama.simpenbackend.Feedback.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nakahama.simpenbackend.Feedback.dto.FillFeedbackRequestDTO;
import com.nakahama.simpenbackend.Feedback.model.Feedback;
import com.nakahama.simpenbackend.Feedback.service.FeedbackService;
import com.nakahama.simpenbackend.util.ResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @GetMapping("")
    public ResponseEntity<Object> getAllFeedback() {
        List<Feedback> listFeedback = feedbackService.retrieveAllFeedback();
        return ResponseUtil.okResponse(listFeedback, "Success");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFeedbackByUser(@PathVariable("id") String id) {
        List<Feedback> listFeedback = feedbackService.feedbackByUserId(UUID.fromString(id));
        return ResponseUtil.okResponse(listFeedback, "Success");
    }

    @PutMapping("")
    public ResponseEntity<Object> fillFeedbackById(@Valid @RequestBody FillFeedbackRequestDTO fillFeedbackRequestDTO) {
        Feedback feedback = feedbackService.fillFeedback(fillFeedbackRequestDTO);
        return ResponseUtil.okResponse(feedback, "Success");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeFeedback(@PathVariable("id") String id) {
        feedbackService.deleteFeedback(UUID.fromString(id));
        return ResponseUtil.okResponse("Deleted", "Success");

    }
}

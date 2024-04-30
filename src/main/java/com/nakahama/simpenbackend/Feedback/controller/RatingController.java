package com.nakahama.simpenbackend.Feedback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nakahama.simpenbackend.Feedback.dto.ListRatingResponseDTO;
import com.nakahama.simpenbackend.Feedback.service.RatingService;
import com.nakahama.simpenbackend.Kelas.model.Kelas;
import com.nakahama.simpenbackend.User.model.Pengajar;
import com.nakahama.simpenbackend.User.service.UserService;
import com.nakahama.simpenbackend.util.ResponseUtil;

import java.util.*;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    UserService userService;

    @Autowired
    RatingService ratingService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRatingsById(@PathVariable("id") String id) {
        Pengajar pengajar = (Pengajar) userService.getUserById(UUID.fromString(id));

        ListRatingResponseDTO ratingsMurid = ratingService.getRatingForPengajar(pengajar);

        return ResponseUtil.okResponse(ratingsMurid, "Success");

    }
}

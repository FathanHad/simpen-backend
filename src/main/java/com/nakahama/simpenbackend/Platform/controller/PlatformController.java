package com.nakahama.simpenbackend.Platform.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nakahama.simpenbackend.Platform.dto.Ruangan.CreateRuanganRequest;
import com.nakahama.simpenbackend.Platform.dto.Ruangan.UpdateRuanganRequest;
import com.nakahama.simpenbackend.Platform.dto.Zoom.CreateZoomRequest;
import com.nakahama.simpenbackend.Platform.dto.Zoom.UpdateZoomRequest;
import com.nakahama.simpenbackend.Platform.model.Platform;
import com.nakahama.simpenbackend.Platform.model.Ruangan;
import com.nakahama.simpenbackend.Platform.model.Zoom;
import com.nakahama.simpenbackend.Platform.service.PlatformService;
import com.nakahama.simpenbackend.util.ResponseUtil;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/platform")
public class PlatformController {

    @Autowired
    PlatformService platformService;

    @PostMapping(value = "", params = "zoom")
    public ResponseEntity<Object> CreateZoom(@RequestParam("zoom") String tipe,
            @Valid @RequestBody CreateZoomRequest createZoomRequest) {

        Platform platform = platformService.save(createZoomRequest);
        return ResponseUtil.okResponse(platform, "Success");
    }

    @PostMapping(value = "", params = "ruangan")
    public ResponseEntity<Object> CreateRuangan(@RequestParam("ruangan") String tipe,
            @Valid @RequestBody CreateRuanganRequest createRuanganRequest) {

        Platform platform = platformService.save(createRuanganRequest);
        return ResponseUtil.okResponse(platform, "Success");
    }

    @GetMapping(value = "", params = "zoom")
    public ResponseEntity<Object> getAllZoom(@RequestParam("zoom") String tipe) {
        List<Zoom> listZoom = platformService.getAllZoom();
        return ResponseUtil.okResponse(listZoom, "Success");
    }

    @GetMapping(value = "", params = "ruangan")
    public ResponseEntity<Object> getAllRuangan(@RequestParam("ruangan") String tipe) {
        List<Ruangan> listRuangan = platformService.getAllRuangan();
        return ResponseUtil.okResponse(listRuangan, "Success");
    }

    @PutMapping(value = "", params = "zoom")
    public ResponseEntity<Object> UpdateZoom(@RequestParam("zoom") String id,
            @Valid @RequestBody UpdateZoomRequest updateZoomRequest) {

        updateZoomRequest.setId(UUID.fromString(id));
        Platform platform = platformService.update(updateZoomRequest);
        return ResponseUtil.okResponse(platform, "Success");
    }

    @PutMapping(value = "", params = "ruangan")
    public ResponseEntity<Object> UpdateRuangan(@RequestParam("ruangan") String id,
            @Valid @RequestBody UpdateRuanganRequest updateRuanganRequest) {

        updateRuanganRequest.setId(UUID.fromString(id));
        Platform platform = platformService.update(updateRuanganRequest);
        return ResponseUtil.okResponse(platform, "Success");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> DeletePlatform(@PathVariable("id") String id) {
        platformService.delete(UUID.fromString(id));
        return ResponseUtil.okResponse(null, "Success");
    }
}
package com.nakahama.simpenbackend.User.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nakahama.simpenbackend.Auth.service.AuthService;
import com.nakahama.simpenbackend.User.dto.User.AddUserRequestDTO;
import com.nakahama.simpenbackend.User.dto.User.EditDataUserRequestDTO;
import com.nakahama.simpenbackend.User.dto.User.EditUserRequestDTO;
import com.nakahama.simpenbackend.User.dto.User.UserWithTagsResponseDTO;
import com.nakahama.simpenbackend.User.model.Akademik;
import com.nakahama.simpenbackend.User.model.Operasional;
import com.nakahama.simpenbackend.User.model.Pengajar;
import com.nakahama.simpenbackend.User.model.UserModel;
import com.nakahama.simpenbackend.User.repository.UserDb;
import com.nakahama.simpenbackend.User.service.UserService;
import com.nakahama.simpenbackend.util.ResponseUtil;
import com.nakahama.simpenbackend.Notification.dto.GenerateNotifDTO;
import com.nakahama.simpenbackend.Notification.service.NotificationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserDb userDb;

    @Autowired
    AuthService authService;

    @Autowired
    NotificationService notificationService;

    @PostMapping("")
    public ResponseEntity<Object> AddUser(@Valid @RequestBody AddUserRequestDTO addUserRequestDTO) {
        UserModel newUser = userService.addUser(addUserRequestDTO.getEmail(), addUserRequestDTO.getRole(),
                addUserRequestDTO.getNama());
        return ResponseUtil.okResponse(newUser, "Success");
    }

    @GetMapping("")
    public ResponseEntity<Object> getAllUser() {

        List<UserModel> listUser = userService.retrieveAllUser();
        List<UserModel> listAkademik = new ArrayList<>();

        for (UserModel userModel : listUser) {
            if (userModel.getRole().equals("akademik")) {
                listAkademik.add(userModel);
            }
        }

        for (UserModel userModel : listUser) {
            LocalDateTime currTime = LocalDateTime.now();
            LocalDateTime lastLogin = userModel.getLastLogin();
            long monthDiff = 0;

            if (lastLogin != null) {
                monthDiff = ChronoUnit.MONTHS.between(lastLogin, currTime);
            }

            if ((monthDiff >= 3 && !userModel.isInactive() &&
                    userModel.getRole().equals("pengajar"))) {
                userModel.setInactive(true);

                for (UserModel userModel2 : listAkademik) {
                    // Generate Notification
                    GenerateNotifDTO notification = new GenerateNotifDTO();
                    notification.setAkunPenerima(userModel2.getId());
                    notification.setTipe(8);

                    // Content of Notification
                    notification.setJudul("tidak aktif selama 3 bulan");
                    notification.getIsi().put("idPengajar", String.valueOf(userModel.getId()));
                    notification.getIsi().put("namaPengajar", String.valueOf(userModel.getNama()));

                    notificationService.generateNotification(notification);
                }
            }
        }

        Map<String, List<UserModel>> listUser2 = userService.getAllUsersGroupedByCategory();

        List<Map<String, Object>> contentList = new ArrayList<>();
        for (Map.Entry<String, List<UserModel>> entry : listUser2.entrySet()) {
            Map<String, Object> roleUserMap = new LinkedHashMap<>();
            roleUserMap.put("role", entry.getKey());
            roleUserMap.put("user", entry.getValue());
            contentList.add(roleUserMap);
        }
        return ResponseUtil.okResponse(contentList, "Success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(UUID.fromString(id));
        return ResponseUtil.okResponse(null, "User dengan id " + id + " berhasil dihapus");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editUser(@PathVariable("id") String id,
            @RequestBody EditUserRequestDTO editUserRequestDTO) {
        editUserRequestDTO.setId(UUID.fromString(id));
        UserModel user = userService.updateUser(editUserRequestDTO);
        if (user != null) {
            return ResponseUtil.okResponse((user),
                    "User berhasil diupdate");
        } else {
            return ResponseUtil.badRequest(id, null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable("id") String id) {
        UserWithTagsResponseDTO response = userService.getUserAndTag(UUID.fromString(id));
        return ResponseUtil.okResponse(response, "Success");
    }

    @PutMapping("")
    public ResponseEntity<Object> editDataUser(@RequestBody EditDataUserRequestDTO userRequestDTO,
            HttpServletRequest request) {
        authService.checkOwnership(request, userRequestDTO.getId());
        if (userRequestDTO.getRole().equals("pengajar")) {
            Pengajar pengajar = userService.editDataPengajar(userRequestDTO);
            return ResponseUtil.okResponse(pengajar,
                    "User berhasil diupdate");
        } else if (userRequestDTO.getRole().equals("operasional")) {
            Operasional operasional = userService.editDataOperasional(userRequestDTO);
            return ResponseUtil.okResponse(operasional,
                    "User berhasil diupdate");
        } else {
            Akademik akademik = userService.editDataAkademik(userRequestDTO);
            return ResponseUtil.okResponse(akademik,
                    "User berhasil diupdate");
        }
    }

    @GetMapping("/pengajar")
    public ResponseEntity<Object> getAllPengajar() {
        List<UserModel> response = userService.getAllPengajar();
        return ResponseUtil.okResponse(response, "Success");
    }

    @GetMapping("/pengajar/{id}")
    public ResponseEntity<Object> getPengajar(@PathVariable("id") String id) {
        UserModel response = userService.getPengajar(UUID.fromString(id));
        return ResponseUtil.okResponse(response, "Success");
    }

    @GetMapping(value = "/pengajar/availability", params = { "hari", "waktuStart", "waktuEnd" })
    public ResponseEntity<Object> getAllPengajarByAvailability(
            @RequestParam(required = true, value = "hari") String hari,
            @RequestParam(required = true, value = "waktuStart") String waktuStart,
            @RequestParam(required = true, value = "waktuEnd") String waktuEnd) {
        List<UserModel> response = userService.getAllPengajarByAvailability(hari, waktuStart, waktuEnd);
        return ResponseUtil.okResponse(response, "Success");
    }
}

package com.nakahama.simpenbackend.User.service;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nakahama.simpenbackend.User.dto.Tag.ReadTagResponseDTO;
import com.nakahama.simpenbackend.User.dto.User.EditDataUserRequestDTO;
import com.nakahama.simpenbackend.User.dto.User.EditUserRequestDTO;
import com.nakahama.simpenbackend.User.dto.User.UserWithTagsResponseDTO;
import com.nakahama.simpenbackend.User.dto.User.mapper.AkademikMapper;
import com.nakahama.simpenbackend.User.dto.User.mapper.OperasionalMapper;
import com.nakahama.simpenbackend.User.dto.User.mapper.PengajarMapper;
import com.nakahama.simpenbackend.User.model.Akademik;
import com.nakahama.simpenbackend.User.model.Operasional;
import com.nakahama.simpenbackend.User.model.Pengajar;
import com.nakahama.simpenbackend.User.model.Tag;
import com.nakahama.simpenbackend.User.model.UserModel;
import com.nakahama.simpenbackend.User.repository.AkademikDb;
import com.nakahama.simpenbackend.User.repository.OperasionalDb;
import com.nakahama.simpenbackend.User.repository.PengajarDb;
import com.nakahama.simpenbackend.User.repository.UserDb;
import com.nakahama.simpenbackend.exception.BadRequestException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDb userDb;

    @Autowired
    AkademikDb akademikDb;

    @Autowired
    OperasionalDb operasionalDb;

    @Autowired
    PengajarDb pengajarDb;

    @Autowired
    PengajarMapper pengajarMapper;

    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    // <-- Helper method -->

    @Override
    public String generatePassword(int length) {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }
        return password.toString();
    }

    @Override
    public void addDummySuperadmin() {
        UserModel user = userDb.findByRole("superadmin");

        if (user == null) {
            UserModel superadmin = new UserModel();
            superadmin.setEmail("superadmin@kalananti.id");
            superadmin.setPassword(bCryptPasswordEncoder.encode("12345"));
            superadmin.setRole("superadmin");
            userDb.save(superadmin);
        }
    }

    // <-- Helper method -->

    @Override
    public boolean isDeactivate(String email) {
        for (UserModel user : userDb.findAllDeleted()) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean isExistByEmail(String email) {
        UserModel user = userDb.findByEmail(email);
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserModel addUser(String email, String role, String nama) {
        if (!isExistByEmail(email)) {
            UserModel user = new UserModel();

            if (role.equals("pengajar")) {
                Pengajar pengajar = new Pengajar();
                user = pengajar;
            }
            if (role.equals("operasional")) {
                Operasional operasional = new Operasional();
                user = operasional;
            }
            if (role.equals("akademik")) {
                Akademik akademik = new Akademik();
                user = akademik;
            }

            if (!role.equals("superadmin") && !role.equals("pengajar") && !role.equals("operasional")
                    && !role.equals("akademik")) {
                throw new BadRequestException("Role " + role + " not found");
            }

            user.setNama(nama);
            user.setEmail(email);
            user.setRole(role);
            user.setPassword(bCryptPasswordEncoder.encode("12345"));
            userDb.save(user);

            return user;
        } else {
            throw new BadRequestException("email " + email + " already exist");
        }

    }

    @Override
    public UserModel getUserById(UUID id) {
        for (UserModel user : retrieveAllUser()) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        throw new BadRequestException("User with id " + id + " not found");
    }

    @Override
    public List<UserModel> retrieveAllUser() {

        return userDb.findAll();
    }

    @Override
    public Map<String, List<UserModel>> getAllUsersGroupedByCategory() {
        List<UserModel> allUsersExceptSuperadmin = userDb.findAll()
                .stream()
                .filter(user -> !"superadmin".equalsIgnoreCase(user.getRole()))
                .collect(Collectors.toList());

        Map<String, List<UserModel>> groupedUsers = allUsersExceptSuperadmin.stream()
                .collect(Collectors.groupingBy(UserModel::getRole));

        groupedUsers.forEach((role, userList) -> userList.sort(
                Comparator.comparing(user -> user.getNama() != null ? user.getNama() : "")));

        return groupedUsers;
    }

    @Override
    public void deleteUser(UUID id) {
        UserModel user = getUserById(id);
        if (user != null) {
            user.setDeleted(true);
            userDb.save(user);
        }
    }

    @Override
    public UserModel updateUser(EditUserRequestDTO editUserRequestDTO) {
        UserModel user = getUserById(editUserRequestDTO.getId());

        if (editUserRequestDTO.getNama() != null) {
            user.setNama(editUserRequestDTO.getNama());
        }
        if (editUserRequestDTO.getEmail() != null && !user.getEmail().equals(editUserRequestDTO.getEmail())) {
            if (!isExistByEmail(editUserRequestDTO.getEmail())) {
                user.setEmail(editUserRequestDTO.getEmail());
            } else {
                return null;
            }
        } else {
            editUserRequestDTO.getEmail();
        }

        userDb.save(user);
        return user;
    }

    @Override
    public Pengajar editDataPengajar(EditDataUserRequestDTO pengajarRequestDTO) {
        Pengajar pengajar = pengajarDb.findById(pengajarRequestDTO.getId()).orElseThrow(
                () -> new BadRequestException("Pengajar with id " + pengajarRequestDTO.getId() + " not found"));
        pengajar = PengajarMapper.toEntity(pengajarRequestDTO, pengajar);
        pengajarDb.save(pengajar);
        return pengajar;
    }

    @Override
    public Operasional editDataOperasional(EditDataUserRequestDTO operasionalRequestDTO) {
        Operasional operasional = operasionalDb.findById(operasionalRequestDTO.getId()).orElseThrow(
                () -> new BadRequestException("Operasional with id " + operasionalRequestDTO.getId() + " not found"));
        operasional = OperasionalMapper.toEntity(operasionalRequestDTO, operasional);
        operasionalDb.save(operasional);
        return operasional;
    }

    @Override
    public Akademik editDataAkademik(EditDataUserRequestDTO akademikRequestDTO) {
        Akademik akademik = akademikDb.findById(akademikRequestDTO.getId()).orElseThrow(
                () -> new BadRequestException("Akademik with id " + akademikRequestDTO.getId() + " not found"));
        akademik = AkademikMapper.toEntity(akademikRequestDTO, akademik);
        akademikDb.save(akademik);
        return akademik;
    }

    @Override
    public UserWithTagsResponseDTO getUserAndTag(UUID id) {
        UserModel user = getUserById(id);
        UserWithTagsResponseDTO response = new UserWithTagsResponseDTO();

        if (user instanceof Pengajar) {
            Pengajar pengajar = (Pengajar) user;
            List<Tag> tags = pengajar.getListTag();

            List<ReadTagResponseDTO> tagsDTOs = new ArrayList<>();
            for (Tag tag : tags) {
                tagsDTOs.add(new ReadTagResponseDTO(tag.getId(), tag.getNama()));
            }

            response.setTags(tagsDTOs);
        }

        response.setId(id);
        response.setNama(user.getNama());
        response.setEmail(user.getEmail());
        response.setEmailPribadi(user.getEmailPribadi());
        response.setJenisKelamin(user.getJenisKelamin());
        response.setRole(user.getRole());
        response.setNoTelp(user.getNoTelp());

        return response;
    }

    @Override
    public List<UserModel> getAllOperasional() {
        List<UserModel> listUser = retrieveAllUser();
        List<UserModel> listOps = new ArrayList<>();

        for (UserModel userModel : listUser) {
            if (userModel.getRole().equals("operasional")) {
                listOps.add(userModel);
            }
        }

        return listOps;

    }

    @Override
    public Pengajar setLastUpdateAvailability(UUID id) {
        Pengajar pengajar = pengajarDb.findById(id).orElseThrow(
                () -> new BadRequestException("Pengajar with id " + id + " not found"));
        pengajar.setLastUpdateAvailability(LocalDateTime.now());

        return pengajarDb.save(pengajar);
    }

    @Override
    public List<UserModel> getAllPengajar() {
        List<UserModel> listPengajar = userDb.findAll().stream()
                .filter(user -> user.getRole().equals("pengajar"))
                .collect(Collectors.toList());
        return listPengajar;

    }

    private LocalDateTime fromHariAndWaktu(String hari, String waktu) {
        Map<String, Integer> hariMap = new LinkedHashMap<>();
        hariMap.put("senin", 1);
        hariMap.put("selasa", 2);
        hariMap.put("rabu", 3);
        hariMap.put("kamis", 4);
        hariMap.put("jumat", 5);
        hariMap.put("sabtu", 6);
        hariMap.put("minggu", 7);

        int dayOfMonth = hariMap.get(hari.toLowerCase());
        int hour = Integer.parseInt(waktu.substring(0, 2));
        int minute = Integer.parseInt(waktu.substring(4, 5));
        LocalDateTime waktuStart = LocalDateTime.of(2024, 1, dayOfMonth, hour, minute, 0);
        return waktuStart;
    }

    @Override
    public List<UserModel> getAllPengajarByAvailability(String hari, String waktuStart, String waktuEnd) {
        LocalDateTime start = fromHariAndWaktu(hari, waktuStart);
        LocalDateTime end = fromHariAndWaktu(hari, waktuEnd);

        List<UserModel> listPengajar = userDb.findPengajarByAvailability(start, end);
        return listPengajar;
    }

    @Override
    public UserModel getPengajar(UUID id) {
        UserModel pengajar = getUserById(id);
        if (!pengajar.getRole().equals("pengajar"))
            throw new BadRequestException("User with id " + id + " is not a pengajar");

        return pengajar;
    }
}

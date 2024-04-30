package com.nakahama.simpenbackend.User.service;

import com.nakahama.simpenbackend.User.dto.User.EditDataUserRequestDTO;
import com.nakahama.simpenbackend.User.dto.User.EditUserRequestDTO;
import com.nakahama.simpenbackend.User.dto.User.UserWithTagsResponseDTO;
import com.nakahama.simpenbackend.User.model.Akademik;
import com.nakahama.simpenbackend.User.model.Operasional;
import com.nakahama.simpenbackend.User.model.Pengajar;
import com.nakahama.simpenbackend.User.model.UserModel;
import java.util.List;
import java.util.UUID;
import java.util.Map;

public interface UserService {

    public List<UserModel> retrieveAllUser();

    public List<UserModel> getAllOperasional();

    public String generatePassword(int length);

    public boolean isDeactivate(String email);

    public boolean isExistByEmail(String email);

    public UserModel addUser(String email, String role, String nama);

    public UserModel getUserById(UUID id);

    public void addDummySuperadmin();

    public Map<String, List<UserModel>> getAllUsersGroupedByCategory();

    public void deleteUser(UUID id);

    public UserModel updateUser(EditUserRequestDTO editUserRequestDTO);

    public Pengajar editDataPengajar(EditDataUserRequestDTO pengajarRequestDTO);

    public Operasional editDataOperasional(EditDataUserRequestDTO operasionalRequestDTO);

    public Akademik editDataAkademik(EditDataUserRequestDTO akademikRequestDTO);

    public UserWithTagsResponseDTO getUserAndTag(UUID id);

    public Pengajar setLastUpdateAvailability(UUID id);

    public List<UserModel> getAllPengajar();

    public List<UserModel> getAllPengajarByAvailability(String hari, String waktuStart, String waktuEnd);

    public UserModel getPengajar(UUID fromString);
}

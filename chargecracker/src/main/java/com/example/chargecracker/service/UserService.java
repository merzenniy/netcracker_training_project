package com.example.chargecracker.service;

import com.example.chargecracker.dto.UserProfileDto;
import com.example.chargecracker.exception.ModelAlreadyExistsException;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUser(Long id);

    UserProfileDto getUserProfile(Long id);

    void createUser(User user) throws ModelAttributeException, ModelAlreadyExistsException, ModelNotFoundException;

    void createUserByAdmin(User user) throws ModelAlreadyExistsException, ModelNotFoundException, ModelAttributeException;

    void deleteUser(Long id) throws ModelNotFoundException;

    void updateUser(Long id, User user) throws ModelNotFoundException, ModelAlreadyExistsException, ModelAttributeException;

    void updateUserByAdmin(User user) throws ModelNotFoundException, ModelAttributeException, ModelAlreadyExistsException;

    void updateUserAuto(Long userId, Long autoId);

    void updateUserPassword(Long id, String oldPassword, String newPassword) throws ModelAttributeException, ModelNotFoundException;
}

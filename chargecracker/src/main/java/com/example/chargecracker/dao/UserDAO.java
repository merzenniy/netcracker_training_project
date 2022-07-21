package com.example.chargecracker.dao;

import com.example.chargecracker.model.User;

import java.util.List;

public interface UserDAO {
    List<User> getAll();

    User getById(Long id);

    User getByMail(String mail);

    void create(User user);

    void createFullUser(User user);

    void delete(Long id);

    void update(Long id, User user);

    void updateFullUser(Long id, User user);

    void updateAuto(Long userId, Long autoId);

    void updatePassword(Long id, String password);
}

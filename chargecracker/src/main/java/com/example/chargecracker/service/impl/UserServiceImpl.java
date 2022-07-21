package com.example.chargecracker.service.impl;

import com.example.chargecracker.dao.UserDAO;
import com.example.chargecracker.dto.UserProfileDto;
import com.example.chargecracker.exception.ModelAlreadyExistsException;
import com.example.chargecracker.exception.ModelAttributeException;
import com.example.chargecracker.exception.ModelNotFoundException;
import com.example.chargecracker.model.*;
import com.example.chargecracker.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AutoService autoService;
    @Autowired
    private ConnectorService connectorService;
    @Autowired
    private BrandService brandService;

    @Override
    public List<User> getUsers() {
        return userDAO.getAll();
    }

    @Override
    public User getUser(Long id) {
        return userDAO.getById(id);
    }

    @Override
    public UserProfileDto getUserProfile(Long id) {
        User foundUser = getUser(id);
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUserId(foundUser.getId());
        userProfileDto.setUserFirstName(foundUser.getFirstName());
        userProfileDto.setUserLastName(foundUser.getLastName());
        userProfileDto.setUserMail(foundUser.getMail());
        userProfileDto.setUserMidName(foundUser.getMidName());
        Auto auto = autoService.getAuto(foundUser.getAutoId());
        if (auto != null) {
            userProfileDto.setAutoId(auto.getId());
            userProfileDto.setAutoModel(auto.getModel());
            userProfileDto.setAutoMaxCharge(auto.getMaxCharge());

            Brand brand = brandService.getBrand(auto.getBrandId());
            if (brand != null) {
                userProfileDto.setBrandName(brand.getName());
            }

            Connector connector = connectorService.getConnector(auto.getConnectorId());
            if (connector != null) {
                userProfileDto.setConnectorType(connector.getType());
                userProfileDto.setConnectorAmperage(connector.getAmperage());
                userProfileDto.setConnectorPower(connector.getPower());
                userProfileDto.setConnectorVoltage(connector.getVoltage());
            }

        }
        return userProfileDto;
    }

    @Override
    public void createUser(User user) throws ModelAttributeException, ModelAlreadyExistsException, ModelNotFoundException {
        userCreation(user);
        validationUser(user);
        Role role = roleService.getRole("USER");
        if (role == null) {
            throw new ModelNotFoundException("Role USER not found");
        }
        user.setRoleId(role.getId());
        userDAO.create(user);
    }

    @Override
    public void createUserByAdmin(User user) throws ModelAlreadyExistsException, ModelAttributeException {
        userCreation(user);
        userDAO.createFullUser(user);
    }

    private void userCreation(User user) throws ModelAlreadyExistsException, ModelAttributeException {
        if (userDAO.getByMail(user.getMail()) != null) {
            throw new ModelAlreadyExistsException("Пользователь с почтой " + user.getMail() + " уже существует");
        }
        validationUser(user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    }

    @Override
    public void deleteUser(Long id) throws ModelNotFoundException {
        if (userDAO.getById(id) == null) {
            throw new ModelNotFoundException("User with id " + id + " not found");
        }
        userDAO.delete(id);
    }

    @Override
    public void updateUser(Long id, User user) throws ModelNotFoundException, ModelAttributeException, ModelAlreadyExistsException {
        userUpdate(id, user);
        User existUser = getUser(id);
        User foundUser = userDAO.getByMail(user.getMail());
        if (existUser == null) {
            throw new ModelNotFoundException("User not found");
        } else if (!bCryptPasswordEncoder.matches(user.getPassword(), existUser.getPassword())) {
            throw new ModelAttributeException("Введенный пароль не совпадает с паролем пользователя");
        } else if (foundUser != null && !existUser.getMail().equals(foundUser.getMail())) {
            throw new ModelAlreadyExistsException("User with such mail exists");
        }
        user.setPassword(existUser.getPassword());
        userDAO.update(id, user);
    }

    @Override
    public void updateUserByAdmin(User user) throws ModelAttributeException, ModelAlreadyExistsException, ModelNotFoundException {
        validationUser(user);
        User updatedUser = userDAO.getById(user.getId());
        if (updatedUser == null) {
            throw new ModelNotFoundException("User with id " + user.getId() + " not found");
        }
        User foundUserByEmail = userDAO.getByMail(user.getMail());
        if (foundUserByEmail != null) {
            if (!foundUserByEmail.getMail().equals(updatedUser.getMail())) {
                throw new ModelAlreadyExistsException("Пользователь с почтой " + user.getMail() + " уже существует");
            }
        } else {
            if (user.getPassword() != null) {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }
            userDAO.updateFullUser(user.getId(), user);
        }
    }

    @Override
    public void updateUserAuto(Long userId, Long autoId) {
        userDAO.updateAuto(userId, autoId);
    }

    @Override
    public void updateUserPassword(Long id, String oldPassword, String newPassword) throws ModelAttributeException {
        User user = userDAO.getById(id);
        if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ModelAttributeException("Введенный пароль не совпадает с паролем пользователя");
        }
        userDAO.updatePassword(id, bCryptPasswordEncoder.encode(newPassword));
    }

    private void userUpdate(Long id, User user) throws ModelNotFoundException, ModelAttributeException {
        if (userDAO.getById(id) == null) {
            throw new ModelNotFoundException("User with id " + id + " not found");
        }
        user.setId(id);
        validationUser(user);
    }

    private void validationUser(User user) throws ModelAttributeException {
        if (user.getFirstName() == null || user.getLastName() == null || user.getMidName() == null
                || user.getMail() == null || user.getPassword() == null) {
            throw new ModelAttributeException("Attributes can't be empty");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        User user = userDAO.getByMail(mail);
        if (mail == null) {
            throw new UsernameNotFoundException("User with mail " + mail + " not found");
        }
        Role role = roleService.getRole(user.getRoleId());
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        user.setAuthorities(authorities);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        return user;
    }
}

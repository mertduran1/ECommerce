package com.eticaret.admin.user;


import com.eticaret.common.entity.Role;
import com.eticaret.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listAll() {
        return (List<User>) userRepo.findAll();
    }

    public List<Role> listRoles() {
        return (List<Role>) roleRepo.findAll();
    }

    public void save(User user) {
        boolean isUpdatingUser = (user.getId() != null); //it is in a updating mode

        if (isUpdatingUser) {
            User existingUser = userRepo.findById(user.getId()).get();
            if(user.getPassword().isEmpty()) {
                user.setPassword(existingUser.getPassword());
            }else {
                encodePassword(user);
            }
        }else {
            encodePassword(user);
        }

        userRepo.save(user);
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = userRepo.getUserByEmail(email);

        if (userByEmail == null) return true;

        boolean isCreatingNew = (id == null); //That means user is being edited

        if (isCreatingNew) {
            if(userByEmail != null) return false; //there is already user with this email
        }else {
            if(userByEmail.getId() != id) {
                return false;
            }
        }

        return true;   // if this returns that means the user email is unique
    }

    public User get(Integer id) throws UserNotFoundException {
        try {
            return userRepo.findById(id).get();
        } catch (NoSuchElementException exception) {
            throw new UserNotFoundException("Could not find any user with id " + id);
        }

    }
}

package com.eticaret.admin.user;

import com.eticaret.common.entity.Role;
import com.eticaret.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)

public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;
    @Test
    public void testCreateUser() {
        Role roleAdmin = entityManager.find(Role.class, 1);
        User userMertD = new User("mert@codejava.net", "mert2020", "Mert" , "Duran");
        userMertD.addRole(roleAdmin);

        User savedUser = repo.save(userMertD);
        assertThat(savedUser.getId()).isGreaterThan(0);

    }

    @Test
    public void testCreateNewUserWithTwoRoles() {
        User userUgur = new User("ugur@gmail.com", "ugur1234", "Ugur", "Duran");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);
        userUgur.addRole(roleEditor);
        userUgur.addRole(roleAssistant);

        User savedUser = repo.save(userUgur);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> listUsers = repo.findAll();
        listUsers.forEach(user -> System.out.println(user));

    }

    @Test
    public void testGetUserById() {
        User userMert = repo.findById(1).get();
        System.out.println(userMert);
        assertThat(userMert).isNotNull();
    }

    @Test
    public void testUpdateUserDetails() {
        User userMert = repo.findById(1).get();
        userMert.setEnabled(true);
        userMert.setEmail("mertjavaprogramliyor@java.com");

        repo.save(userMert);
    }

    @Test
    public void testUpdateUserRoles() {
        User userUgur = repo.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesperson = new Role(2);
        userUgur.getRoles().remove(roleEditor);
        userUgur.addRole(roleSalesperson);

        repo.save(userUgur);
    }

    @Test
    public void testDeleteUser() {
        Integer userId = 2;
        repo.deleteById(2);

    }
}

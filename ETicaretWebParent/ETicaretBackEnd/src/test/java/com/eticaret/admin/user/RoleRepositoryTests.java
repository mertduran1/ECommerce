package com.eticaret.admin.user;

import com.eticaret.common.entity.Role;
import org.junit.jupiter.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false)
public class RoleRepositoryTests {

    private RoleRepository repo;

    @Autowired
    public RoleRepositoryTests(RoleRepository repo) {
        this.repo = repo;
    }

    @Test
    public void testCreateFirstRole() {
        Role roleAdmin = new Role("Admin", "Manage Everything");
        Role savedRole = repo.save(roleAdmin);
        assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateRestRoles() {
        Role roleSalesPerson = new Role("SalesPerson", "Manage product price, customers, shipping, " + "customers, shipping, orders and sales report");
        Role roleEditor = new Role("Editor", "manage categories, brands, " + "products, articles, menus");
        Role roleShipper = new Role("Shipper", "view products, view orders and update order status");
        Role roleAssistant = new Role("Assistant", "manage questions, review products");

        repo.saveAll(List.of(roleSalesPerson, roleEditor, roleShipper, roleAssistant));
    }
}

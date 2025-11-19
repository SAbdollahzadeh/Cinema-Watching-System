package ir.maktabsharif.cinemawatchingsystem.config;

import ir.maktabsharif.cinemawatchingsystem.enums.AdminLevel;
import ir.maktabsharif.cinemawatchingsystem.model.Admin;
import ir.maktabsharif.cinemawatchingsystem.repository.impl.UserRepositoryImpl;
import ir.maktabsharif.cinemawatchingsystem.service.UserService;
import ir.maktabsharif.cinemawatchingsystem.service.impl.UserServiceImpl;
import ir.maktabsharif.cinemawatchingsystem.util.PasswordUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserService userService = new UserServiceImpl(new UserRepositoryImpl());
        if (!userService.existsByUsername("admin")) {
            Admin admin = Admin.builder()
                    .username("admin")
                    .password(PasswordUtil.hashPassword("a"))
                    .email("admin@example.com")
                    .adminLevel(AdminLevel.ADMIN_LEVEL_1)
                    .build();
            userService.save(admin);
        }
    }
}

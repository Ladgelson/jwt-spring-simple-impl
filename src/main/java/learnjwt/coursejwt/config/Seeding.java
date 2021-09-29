package learnjwt.coursejwt.config;

import learnjwt.coursejwt.model.Permission;
import learnjwt.coursejwt.model.User;
import learnjwt.coursejwt.repository.PermissionRepository;
import learnjwt.coursejwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public class Seeding implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public void run(String... args) throws Exception {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
		String result = bCryptPasswordEncoder.encode("admin123");
		System.out.println("My hash " + result);
        User user = User.builder()
                .id(null)
                .userName("micaias")
                .fullName("Micaias Silva")
                .password(result)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();

        Permission permission = Permission.builder()
                .id(null)
                .description("ADMIN")
                .build();

        permission = permissionRepository.save(permission);

        user.setPermissions(List.of(permission));
        userRepository.save(user);
    }
}

package learnjwt.coursejwt.repository;

import learnjwt.coursejwt.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}

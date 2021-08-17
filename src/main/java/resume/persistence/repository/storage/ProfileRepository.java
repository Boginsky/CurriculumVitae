package resume.persistence.repository.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import resume.persistence.entity.Profile;


public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findByUid(String uid);

    Profile findByEmail(String email);

    Profile findByPhone(String phone);

    int countByUid(String uid);

}

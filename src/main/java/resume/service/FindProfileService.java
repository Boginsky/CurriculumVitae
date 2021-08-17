package resume.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import resume.persistence.entity.Profile;

public interface FindProfileService {

    Profile findByUid(String uid);

    Page<Profile> findAll(Pageable pageable);

}

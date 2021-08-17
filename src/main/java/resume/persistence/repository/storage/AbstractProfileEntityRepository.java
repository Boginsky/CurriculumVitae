package resume.persistence.repository.storage;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface AbstractProfileEntityRepository<T> extends Repository<T, Long> {

    void deleteByProfileId(Long idProfile);

    List<T> findByProfileIdOrderByIdAsc(Long idProfile);

    <S extends T> S saveAndFlush(S entity);

    void flush();
}

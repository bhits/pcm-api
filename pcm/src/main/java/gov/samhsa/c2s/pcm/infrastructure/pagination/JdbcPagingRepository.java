package gov.samhsa.c2s.pcm.infrastructure.pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JdbcPagingRepository {

    public abstract <T> Page<T> findAll(Pageable pageable);

    public abstract <T> Page<T> findAllByArgs(Pageable pageable, Object... args);
}

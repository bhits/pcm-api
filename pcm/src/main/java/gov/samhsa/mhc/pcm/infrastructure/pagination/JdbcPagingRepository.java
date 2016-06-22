package gov.samhsa.mhc.pcm.infrastructure.pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Jiahao.Li on 6/19/2016.
 */
public interface JdbcPagingRepository {

    public abstract <T> Page<T> findAll(Pageable pageable);

    public abstract <T> Page<T> findAllByArgs(Pageable pageable, Object... args);
}

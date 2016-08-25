package gov.samhsa.c2s.pcm.domain.consent;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsentTermsVersionsRepository extends JpaSpecificationExecutor<ConsentTermsVersions>,
        JpaRepository<ConsentTermsVersions, Long>{

    /**
     * This query finds all versions that have the disabled
     * property set to whatever value is passed in as a parameter
     * sorted by added_date_time in descending order.
     *
     * @param disabled This is boolean true/false value to search for.
     * @return List<ConsentRevocationTermsVersions>
     */
    List<ConsentTermsVersions> findAllByVersionDisabledOrderByAddedDateTimeDesc(Boolean disabled);
}

package gov.samhsa.mhc.pcm.domain.consent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ConsentRevocationTermsVersionsRepository extends JpaSpecificationExecutor<ConsentRevocationTermsVersions>,
        JpaRepository<ConsentRevocationTermsVersions, Long>{

}

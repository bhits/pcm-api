package gov.samhsa.mhc.pcm.domain.consent;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsentTermsVersionsRepository extends JpaSpecificationExecutor<ConsentTermsVersions>,
        JpaRepository<ConsentTermsVersions, Long>{

    ConsentTermsVersions findOneByVersionDisabled(Boolean disabled);
}

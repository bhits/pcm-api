/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the <organization> nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.mhc.pcm.service.audit;

import gov.samhsa.mhc.pcm.domain.audit.ModifiedEntityTypeEntity;
import gov.samhsa.mhc.pcm.domain.audit.ModifiedEntityTypeEntityRepository;
import gov.samhsa.mhc.pcm.domain.audit.RevisionInfoEntity;
import gov.samhsa.mhc.pcm.domain.audit.RevisionInfoEntityRepository;
import gov.samhsa.mhc.pcm.domain.consent.Consent;
import gov.samhsa.mhc.pcm.domain.patient.*;
import gov.samhsa.mhc.pcm.domain.staff.StaffRepository;
import gov.samhsa.mhc.pcm.infrastructure.pagination.JdbcPagingException;
import gov.samhsa.mhc.pcm.infrastructure.pagination.JdbcPagingRepository;
import gov.samhsa.mhc.pcm.service.audit.domain.ActivityHistory;
import gov.samhsa.mhc.pcm.service.dto.ActivityHistoryListDto;
import gov.samhsa.mhc.pcm.service.dto.HistoryDto;
import gov.samhsa.mhc.pcm.service.exception.PatientNotFoundException;
import gov.samhsa.mhc.pcm.service.exception.ViewActivitiesException;
import gov.samhsa.mhc.pcm.service.patient.PatientService;
import gov.samhsa.mhc.pcm.service.reference.RevisionTypeCodeService;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The Class AuditServiceImpl.
 */
@Transactional
@Service
public class AuditServiceImpl implements AuditService {

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The entity manager factory.
     */
    @Autowired
    protected EntityManagerFactory entityManagerFactory;

    /**
     * The patient repository.
     */
    @Autowired
    protected PatientRepository patientRepository;

    /**
     * The patient revision entity repository.
     */
    @Autowired
    protected RevisionInfoEntityRepository patientRevisionEntityRepository;

    /**
     * The modified entity type entity repository.
     */
    @Autowired
    protected ModifiedEntityTypeEntityRepository modifiedEntityTypeEntityRepository;

    /**
     * The patient legal representative association repository.
     */
    @Autowired
    protected PatientLegalRepresentativeAssociationRepository patientLegalRepresentativeAssociationRepository;

    /**
     * The staff repository.
     */
    @Autowired
    protected StaffRepository staffRepository;

    @Autowired
    RevisionTypeCodeService revisionTypeCodeService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private JdbcPagingRepository jdbcPagingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${mhc.pcm.config.pagination.itemsPerPage}")
    private int itemsPerPage;

    public AuditServiceImpl() {
    }

    /**
     * Instantiates a new audit service impl.
     *
     * @param entityManagerFactory                            the entity manager factory
     * @param patientRepository                               the patient repository
     * @param patientRevisionEntityRepository                 the patient revision entity repository
     * @param modifiedEntityTypeEntityRepository              the modified entity type entity repository
     * @param patientLegalRepresentativeAssociationRepository the patient legal representative association repository
     * @param staffRepository                                 the staff repository
     */
    public AuditServiceImpl(
            EntityManagerFactory entityManagerFactory,
            PatientRepository patientRepository,
            RevisionInfoEntityRepository patientRevisionEntityRepository,
            ModifiedEntityTypeEntityRepository modifiedEntityTypeEntityRepository,
            PatientLegalRepresentativeAssociationRepository patientLegalRepresentativeAssociationRepository,
            StaffRepository staffRepository) {
        super();
        this.entityManagerFactory = entityManagerFactory;
        this.patientRepository = patientRepository;
        this.patientRevisionEntityRepository = patientRevisionEntityRepository;
        this.modifiedEntityTypeEntityRepository = modifiedEntityTypeEntityRepository;
        this.patientLegalRepresentativeAssociationRepository = patientLegalRepresentativeAssociationRepository;
        this.staffRepository = staffRepository;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.audit.AuditService#findAllHistory(java
     * .lang.String)
     */
    @Override
    public List<HistoryDto> findAllHistory(String username) {
        // Assert patient should not be null
        assertPatientNotNull(username);

        Patient patientA = patientRepository.findByUsername(username);
        Long patientId = patientA.getId();

        EntityManager em = entityManagerFactory.createEntityManager();
        AuditReader reader = AuditReaderFactory.get(em);
        List<Number> revisions = reader.getRevisions(Patient.class, patientId);
        List<HistoryDto> historyList = findHistoryDetails(revisions);
        List<HistoryDto> legalHistoryList = findLegalHistoryByPatient(patientId);
        historyList.addAll(legalHistoryList);
        Collections.sort(historyList);

        List<HistoryDto> historyListReverse = getReversed(historyList);
        em.close();
        return historyListReverse;
    }

    @Override
    public ActivityHistoryListDto findAllActivityHistoryPageable(String username, int pageNumber) {
        try {
            List<HistoryDto> activityHistoryDtoList = new ArrayList<>();
            PageRequest pageable = new PageRequest(pageNumber, itemsPerPage, Sort.Direction.DESC, "activity_id");
            //TODO: Ensure args must belong to T
            Page<ActivityHistory> pages = jdbcPagingRepository.findAllByArgs(pageable, username);

            if (pages != null) {
                activityHistoryDtoList = activityHistoryToHistoryDtoList(pages.getContent());
                activityHistoryDtoList
                        .stream()
                        .forEach(
                                a -> {
                                    a.setChangedBy(getFullName(a.getChangedBy()));
                                    a.setType(convertRevClassNameToType(a.getType()));
                                });

            } else {
                logger.error("No pages found for current page: " + pageNumber);
            }

            ActivityHistoryListDto activityHistoryListDto = new ActivityHistoryListDto();
            activityHistoryListDto.setHistoryDtoList(activityHistoryDtoList);
            activityHistoryListDto.setCurrentPage(pages.getNumber());
            activityHistoryListDto.setTotalItems(pages.getTotalElements());
            activityHistoryListDto.setTotalPages(pages.getTotalPages());
            activityHistoryListDto.setItemsPerPage(pages.getSize());
            return activityHistoryListDto;
        } catch (JdbcPagingException pageException) {
            logger.error(pageException.getMessage());
            throw pageException;
        } catch (Exception e) {
            logger.error("View activity history failed: " + e.getMessage());
            throw new ViewActivitiesException();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.audit.AuditService#getReversed(java.
     * util.List)
     */
    @Override
    public List<HistoryDto> getReversed(List<HistoryDto> original) {
        List<HistoryDto> copy = new ArrayList<HistoryDto>(original);
        Collections.reverse(copy);
        return copy;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.audit.AuditService#findHistoryDetails
     * (java.util.List)
     */
    @Override
    public List<HistoryDto> findHistoryDetails(List<Number> revisions) {
        List<HistoryDto> historyDtos = makeHistoryDtos();
        for (Number n : revisions) {
            HistoryDto hd = findHistoryDetail(n);
            historyDtos.add(hd);

        }
        return historyDtos;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.audit.AuditService#makeHistoryDtos()
     */
    @Override
    public List<HistoryDto> makeHistoryDtos() {
        return new ArrayList<HistoryDto>();
    }

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.consent2share.service.audit.AuditService#makeHistoryDto()
     */
    @Override
    public HistoryDto makeHistoryDto() {
        return new HistoryDto();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.audit.AuditService#findHistoryDetail
     * (java.lang.Number)
     */
    @Override
    public HistoryDto findHistoryDetail(Number n) {
        HistoryDto hd = makeHistoryDto();
        hd.setRevisionid(n.longValue());

        RevisionInfoEntity patientRevisionEntity = patientRevisionEntityRepository
                .findOneById(n);
        List<ModifiedEntityTypeEntity> modifiedEntityTypeEntitys = modifiedEntityTypeEntityRepository
                .findAllByRevision(patientRevisionEntity);

        if (patientRevisionEntity.getUsername() != null) {
            String username1 = patientRevisionEntity.getUsername();
            if (patientRepository.findByUsername(username1) != null) {
                Patient patientA1 = patientRepository.findByUsername(username1);
                String changedBy = patientA1.getLastName() + ", "
                        + patientA1.getFirstName();
                hd.setChangedBy(changedBy);
            }

            if (staffRepository.findByUsername(username1) != null) {

                hd.setChangedBy("provider staff");
            }
        }

        Long timestamp = patientRevisionEntity.getTimestamp();
        String revdate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(timestamp);

        hd.setChangedDateTime(revdate);

        String revtp = findRevType(modifiedEntityTypeEntitys);
        String entityClassname = findRevClassName(modifiedEntityTypeEntitys);

        hd.setRecType(revtp);
        hd.setType(entityClassname);

        return hd;
    }

    /**
     * Find consent revokation history by patient.
     *
     * @param reader    the reader
     * @param patientId the patient id
     * @return the list
     */
    public List<HistoryDto> findConsentRevokationHistoryByPatient(
            AuditReader reader, long patientId) {

        @SuppressWarnings("unchecked")
        List<Object[]> historys = reader
                .createQuery()
                .forRevisionsOfEntity(Consent.class, false, true)
                // .add(AuditEntity.id().eq(Long.valueOf(5)))
                .add(AuditEntity.property("revocationDate").hasChanged())
                .add(AuditEntity.relatedId("patient").eq(patientId))
                .getResultList();

        // Fix issue #492 start
        // Add the name to 'Changed By'field.
        Patient patient = patientRepository.findOne(patientId);
        String changedBy = patient.getLastName() + ",   "
                + patient.getFirstName();
        List<HistoryDto> historyDtos = new ArrayList<HistoryDto>();

        for (Object[] history : historys) {

            RevisionInfoEntity rif = (RevisionInfoEntity) history[1];

            HistoryDto historyDto = new HistoryDto();
            historyDto.setRevisionid(rif.getId());
            historyDto.setChangedDateTime(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
                    .format(rif.getTimestamp()));
            historyDto.setRecType("Changed entry");
            historyDto.setType("Signed PDF Consent Revokation");
            historyDto.setChangedBy(changedBy);

            historyDtos.add(historyDto);
        }

        return historyDtos;

    }

    /**
     * Find consent sign pdf history by patient.
     *
     * @param reader    the reader
     * @param patientId the patient id
     * @return the list
     */
    public List<HistoryDto> findConsentSignPDFHistoryByPatient(
            AuditReader reader, long patientId) {

        @SuppressWarnings("unchecked")
        List<Object[]> historys = reader
                .createQuery()
                .forRevisionsOfEntity(Consent.class, false, true)
                // .add(AuditEntity.id().eq(Long.valueOf(5)))
                .add(AuditEntity.property("signedDate").hasChanged())
                .add(AuditEntity.relatedId("patient").eq(patientId))
                .getResultList();
        Patient patient = patientRepository.findOne(patientId);
        String changedBy = patient.getLastName() + ",   "
                + patient.getFirstName();
        List<HistoryDto> historyDtos = new ArrayList<HistoryDto>();

        for (Object[] history : historys) {

            RevisionInfoEntity rif = (RevisionInfoEntity) history[1];

            HistoryDto historyDto = new HistoryDto();
            historyDto.setRevisionid(rif.getId());
            historyDto.setChangedDateTime(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
                    .format(rif.getTimestamp()));
            historyDto.setRecType("Changed entry");
            historyDto.setType("Signed PDF Consent");
            historyDto.setChangedBy(changedBy);
            // fix issue #492 end
            historyDtos.add(historyDto);
        }

        return historyDtos;

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.audit.AuditService#findRevType(java.
     * util.List)
     */
    @Override
    public String findRevType(
            List<ModifiedEntityTypeEntity> modifiedEntityTypeEntitys) {
        Byte btype = 1;
        if (modifiedEntityTypeEntitys.size() == 1)
            btype = modifiedEntityTypeEntitys.get(0).getRevisionType();

        if (modifiedEntityTypeEntitys.size() == 2) {
            if (modifiedEntityTypeEntitys.get(0).getRevisionType() != 1)
                btype = modifiedEntityTypeEntitys.get(0).getRevisionType();
            else
                btype = modifiedEntityTypeEntitys.get(1).getRevisionType();

        }
        if (modifiedEntityTypeEntitys.size() == 3) {

            if (modifiedEntityTypeEntitys.get(0).getRevisionType() == 2
                    || modifiedEntityTypeEntitys.get(1).getRevisionType() == 2
                    || modifiedEntityTypeEntitys.get(2).getRevisionType() == 2)
                btype = 2;

            else if (modifiedEntityTypeEntitys.get(0).getRevisionType() == 0
                    || modifiedEntityTypeEntitys.get(1).getRevisionType() == 0
                    || modifiedEntityTypeEntitys.get(2).getRevisionType() == 0)
                btype = 0;

        }
        return revisionTypeCodeService.findRevisionTypeCodeByCode(btype).getDisplayName();
    }


    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.audit.AuditService#findRevClassName(
     * java.util.List)
     */
    @Override
    public String findRevClassName(
            List<ModifiedEntityTypeEntity> modifiedEntityTypeEntitys) {
        StringBuffer entityClassname = new StringBuffer();

        if (modifiedEntityTypeEntitys.size() == 1) {
            String entityname = new String(modifiedEntityTypeEntitys.get(0)
                    .getEntityClassName());
            entityClassname.append(entityname
                    .substring(entityname.lastIndexOf('.') + 1).trim()
                    .replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2"));
        }

        if (modifiedEntityTypeEntitys.size() == 2) {
            if (modifiedEntityTypeEntitys.get(0).getRevisionType() != 1) {
                String entityname = new String(modifiedEntityTypeEntitys.get(0)
                        .getEntityClassName());
                entityClassname.append(entityname
                        .substring(entityname.lastIndexOf('.') + 1).trim()
                        .replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2"));
            } else {
                String entityname = new String(modifiedEntityTypeEntitys.get(1)
                        .getEntityClassName());
                entityClassname.append(entityname
                        .substring(entityname.lastIndexOf('.') + 1).trim()
                        .replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2"));
            }
        }
        if (modifiedEntityTypeEntitys.size() == 3) {

            entityClassname.append("Add provider");

        }
        return entityClassname.toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.audit.AuditService#findLegalHistoryByPatient
     * (long)
     */
    @Override
    public List<HistoryDto> findLegalHistoryByPatient(long patientId) {

        EntityManager em2 = entityManagerFactory.createEntityManager();
        AuditReader reader2 = AuditReaderFactory.get(em2);

        Set<Number> legalList = new HashSet<Number>();

        List<ModifiedEntityTypeEntity> metes = modifiedEntityTypeEntityRepository
                .findAll();
        for (ModifiedEntityTypeEntity m : metes) {
            String entityname = m.getEntityClassName()
                    .substring(m.getEntityClassName().lastIndexOf('.') + 1)
                    .trim();
            if (entityname.equals("PatientLegalRepresentativeAssociation")) {
                long revisionId = m.getRevision().getId();
                Byte bt = m.getRevisionType();

                if (bt != 2) {
                    AuditQuery query = reader2
                            .createQuery()
                            .forEntitiesAtRevision(
                                    PatientLegalRepresentativeAssociation.class,
                                    revisionId);

                    List<PatientLegalRepresentativeAssociation> psList = query
                            .getResultList();
                    for (PatientLegalRepresentativeAssociation ps : psList) {
                        PatientLegalRepresentativeAssociationPk pks = ps
                                .getPatientLegalRepresentativeAssociationPk();
                        Patient pl = pks.getPatient();
                        if (pl != null) {
                            if (patientId == pl.getId()) {

                                Long LegalId = ps.getId();
                                List<Number> revisionListLegal = reader2
                                        .getRevisions(
                                                PatientLegalRepresentativeAssociation.class,
                                                LegalId);
                                legalList.addAll(revisionListLegal);

                                Long LegalRepId = ps
                                        .getPatientLegalRepresentativeAssociationPk()
                                        .getLegalRepresentative().getId();
                                List<Number> revisionListLegal2 = reader2
                                        .getRevisions(Patient.class, LegalRepId);

                                legalList.addAll(revisionListLegal2);

                            }
                        }
                    }

                }

            }

        }
        Set<Number> removeLegalList = new HashSet<Number>();
        for (Number a : legalList) {
            RevisionInfoEntity patientRevisionEntity = patientRevisionEntityRepository
                    .findOneById(a);
            List<ModifiedEntityTypeEntity> meteLegalLists = modifiedEntityTypeEntityRepository
                    .findAllByRevision(patientRevisionEntity);
            for (ModifiedEntityTypeEntity meteLegal : meteLegalLists) {
                if (meteLegal.getRevisionType() == 0) {
                    String en = new String(meteLegal.getEntityClassName());
                    if (en.substring(en.lastIndexOf('.') + 1).trim()
                            .equals("Patient")) {
                        removeLegalList.add(a);
                    }
                }

            }
        }
        legalList.removeAll(removeLegalList);
        em2.close();
        List<Number> legalList2 = new ArrayList<Number>(legalList);
        List<HistoryDto> historyLegals = findLegalHistoryDetails(legalList2);
        return historyLegals;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.audit.AuditService#findLegalHistoryDetails
     * (java.util.List)
     */
    @Override
    public List<HistoryDto> findLegalHistoryDetails(List<Number> revisions) {
        List<HistoryDto> historyDtos = makeHistoryDtos();
        for (Number n : revisions) {
            HistoryDto hd = findLegalHistoryDetail(n);
            historyDtos.add(hd);

        }
        return historyDtos;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.consent2share.service.audit.AuditService#findLegalHistoryDetail
     * (java.lang.Number)
     */
    @Override
    public HistoryDto findLegalHistoryDetail(Number n) {
        HistoryDto hd = makeHistoryDto();
        hd.setRevisionid(n.longValue());

        RevisionInfoEntity patientRevisionEntity = patientRevisionEntityRepository
                .findOneById(n);
        List<ModifiedEntityTypeEntity> modifiedEntityTypeEntitys = modifiedEntityTypeEntityRepository
                .findAllByRevision(patientRevisionEntity);

        if (patientRevisionEntity.getUsername() != null) {
            String username1 = patientRevisionEntity.getUsername();
            Patient patientA1 = patientRepository.findByUsername(username1);
            String changedBy = patientA1.getLastName() + ",   "
                    + patientA1.getFirstName();
            hd.setChangedBy(changedBy);
        }

        Long timestamp = patientRevisionEntity.getTimestamp();
        String revdate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
                .format(timestamp);

        hd.setChangedDateTime(revdate);

        Byte bt = modifiedEntityTypeEntitys.get(0).getRevisionType();

        String revtp = revisionTypeCodeService.findRevisionTypeCodeByCode(bt).getDisplayName();

        hd.setRecType(revtp);
        hd.setType("Legal Representative Association");

        return hd;
    }

    private void assertPatientNotNull(String name) {
        if (patientService.findIdByUsername(name) == null) {
            throw new PatientNotFoundException("No patient record found for patient name: " + name);
        }
    }

    private List<HistoryDto> activityHistoryToHistoryDtoList(List<ActivityHistory> listOfActivityHistory) {
        List<HistoryDto> historyDtoList = new ArrayList<>();
        for (ActivityHistory activityHistory : listOfActivityHistory) {
            HistoryDto historyDto = modelMapper.map(activityHistory, HistoryDto.class);
            historyDtoList.add(historyDto);
        }
        return historyDtoList;
    }

    private String getFullName(String userName) {
        Patient patient = patientRepository.findByUsername(userName);
        return patient.getLastName().concat(", ").concat(patient.getFirstName());
    }

    private String convertRevClassNameToType(String revClassName) {
        String type = revClassName
                .substring(revClassName.lastIndexOf('.') + 1).trim()
                .replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2");

        Set<String> providerType = new HashSet<>();
        providerType.add("Individual Provider");
        providerType.add("Organizational Provider");

        if (providerType.contains(type)) {
            return "Add provider";
        } else {
            return type;
        }
    }
}

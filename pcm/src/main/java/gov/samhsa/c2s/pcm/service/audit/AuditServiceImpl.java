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
package gov.samhsa.c2s.pcm.service.audit;

import gov.samhsa.c2s.pcm.config.PcmProperties;
import gov.samhsa.c2s.pcm.domain.patient.Patient;
import gov.samhsa.c2s.pcm.domain.patient.PatientRepository;
import gov.samhsa.c2s.pcm.infrastructure.pagination.JdbcPagingException;
import gov.samhsa.c2s.pcm.infrastructure.pagination.JdbcPagingRepository;
import gov.samhsa.c2s.pcm.service.audit.domain.ActivityHistory;
import gov.samhsa.c2s.pcm.service.dto.ActivityHistoryListDto;
import gov.samhsa.c2s.pcm.service.dto.HistoryDto;
import gov.samhsa.c2s.pcm.service.exception.ViewActivitiesException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The Class AuditServiceImpl.
 */
@Transactional
@Service
public class AuditServiceImpl implements AuditService {

    private static final String ID_COLUMN = "activity_id";

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The patient repository.
     */
    @Autowired
    protected PatientRepository patientRepository;

    @Autowired
    private JdbcPagingRepository jdbcPagingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PcmProperties pcmProperties;

    @Autowired
    private MessageSource messageSource;

    public AuditServiceImpl() {
    }

    /**
     * Instantiates a new audit service impl.
     *
     * @param patientRepository the patient repository
     */
    public AuditServiceImpl(PatientRepository patientRepository) {
        super();
        this.patientRepository = patientRepository;
    }

    @Override
    public ActivityHistoryListDto findAllActivityHistoryPageable(String username, int pageNumber) {
        try {
            List<HistoryDto> activityHistoryDtoList = new ArrayList<>();
            PageRequest pageable = new PageRequest(pageNumber, pcmProperties.getPagination().getItemsPerPage(), Sort.Direction.DESC, ID_COLUMN);
            Page<ActivityHistory> pages = jdbcPagingRepository.findAllByArgs(pageable, username);

            if (pages != null) {
                activityHistoryDtoList = activityHistoryToHistoryDtoList(pages.getContent());
                activityHistoryDtoList
                        .stream()
                        .forEach(
                                a -> {
                                    a.setChangedBy(getFullName(a.getChangedBy()));
                                    a.setRecType(convertAttestedConsentRevType(a.getRecType(), convertRevClassNameToType(a.getType())));
                                    a.setType(convertRevClassNameToTypeWithLang(a.getType()));
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

    private List<HistoryDto> activityHistoryToHistoryDtoList(List<ActivityHistory> listOfActivityHistory) {
        List<HistoryDto> historyDtoList = new ArrayList<>();
        for (ActivityHistory activityHistory : listOfActivityHistory) {
            HistoryDto historyDto = modelMapper.map(activityHistory, HistoryDto.class);
            historyDtoList.add(historyDto);
        }
        return historyDtoList;
    }

    private String getFullName(String username) {
        Patient patient = patientRepository.findByUsername(username);
        return patient.getLastName().concat(", ").concat(patient.getFirstName());
    }

    private String convertRevClassNameToType(String revClassName) {
        String type = revClassName
                .substring(revClassName.lastIndexOf('.') + 1).trim()
                .replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2");

        switch (type) {
            case "Individual Provider":
            case "Organizational Provider":
                type = "Patient Provider";
                break;
            default:
                return type;
        }
        return type;
    }

    //convert type name with desired language
    private String convertRevClassNameToTypeWithLang(String revType) {
        String type = convertRevClassNameToType (revType);
        if (LocaleContextHolder.getLocale().getLanguage().equalsIgnoreCase("en")) {
            return type;
        } else {
            return replaceWithDesignedLang(type);
        }
    }

    private String convertAttestedConsentRevType(String revType, String type) {
        boolean returnRevType = false;
        switch (type) {
            case "Attested Consent":
                type = "Sign entry";
                break;
            case "Attested Consent Revocation":
                type = "Revoke entry";
                break;
            default:
                returnRevType = true;
        }
        if (LocaleContextHolder.getLocale().getLanguage().equalsIgnoreCase("en")) {
            return returnRevType ? revType : type;
        } else {
            return returnRevType ? replaceWithDesignedLang(revType) : replaceWithDesignedLang(type);
        }
        //return type;
    }

    /**
     * get type name in designed language
     *
     * */
    private String replaceWithDesignedLang(String str) {
        Locale locale = LocaleContextHolder.getLocale();
        switch (str) {
            case "Patient Provider":
                return messageSource.getMessage("REV.TYPE.PATIENT.PROVIDER", null, locale);
            case "Patient":
                return messageSource.getMessage("REV.TYPE.PATIENT", null, locale);
            case "Consent":
                return messageSource.getMessage("REV.TYPE.CONSENT", null, locale);
            case "Attested Consent":
                return messageSource.getMessage("REV.TYPE.ATTESTED.CONSENT", null, locale);
            case "Attested Consent Revocation":
                return messageSource.getMessage("REV.TYPE.ATTESTED.CONSENT.REVOCATION", null, locale);
            case "Clinical Document":
                return messageSource.getMessage("REV.TYPE.CLINICAL.DOC", null, locale);
            case "Consent Terms Versions":
                return messageSource.getMessage("REV.TYPE.CONSENT.TERM", null, locale);
            case "Sign entry":
                return messageSource.getMessage("TYPE.SIGN.ENTRY", null, locale);
            case "Revoke entry":
                return messageSource.getMessage("TYPE.REVOKE.ENTRY", null, locale);
            case "Create new entry":
                return messageSource.getMessage("TYPE.CREATE.NEW.ENTRY", null, locale);
            case "Delete entry":
                return messageSource.getMessage("TYPE.DELETE.ENTRY", null, locale);
            case "Changed entry":
                return messageSource.getMessage("TYPE.CHANGED.ENTRY", null, locale);
            default:
                return str;
        }
    }
}

/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * <p/>
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
 * <p/>
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
package gov.samhsa.mhc.pcm.web;

import gov.samhsa.mhc.common.consentgen.ConsentGenException;
import gov.samhsa.mhc.pcm.infrastructure.eventlistener.EventService;
import gov.samhsa.mhc.pcm.infrastructure.securityevent.FileDownloadedEvent;
import gov.samhsa.mhc.pcm.service.audit.AuditService;
import gov.samhsa.mhc.pcm.service.consent.ConsentHelper;
import gov.samhsa.mhc.pcm.service.consent.ConsentService;
import gov.samhsa.mhc.pcm.service.consent.ConsentStatus;
import gov.samhsa.mhc.pcm.service.dto.*;
import gov.samhsa.mhc.pcm.service.exception.*;
import gov.samhsa.mhc.pcm.service.notification.NotificationService;
import gov.samhsa.mhc.pcm.service.patient.PatientService;
import gov.samhsa.mhc.pcm.service.reference.PurposeOfUseCodeService;
import gov.samhsa.mhc.vss.service.ValueSetCategoryService;
import gov.samhsa.mhc.vss.service.dto.AddConsentFieldsDto;
import gov.samhsa.mhc.vss.service.dto.ValueSetCategoryFieldsDto;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

/**
 * The Class ConsentRestController.
 */
@RestController
@RequestMapping("/patients")
public class AuditController {

    /** The patient audit service. */
    @Autowired
    AuditService auditService;

    @Autowired
    private PatientService patientService;

    @RequestMapping(value = "/activity", method = RequestMethod.GET)
    public List<HistoryDto> activityHistory(Principal principal) throws ClassNotFoundException {
        if (patientService.findIdByUsername(principal.getName()) != null) {
            return auditService.findAllHistory(principal.getName());
        } else {
            throw new ConsentNotBelongingToPatientException("Error: cannot get audit information. Invalid patient.");
        }
    }


}

package gov.samhsa.c2s.pcm.web;

import gov.samhsa.c2s.pcm.service.consent.ConsentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class ObligationRestController {

    @Autowired
    private ConsentService consentService;

    @RequestMapping("/patients/consents/{consentId}/obligations")
    public List<String> getConsentObligations(Principal principal, @PathVariable("consentId") Long consentId) {
        return consentService.findObligationsConsentById(principal.getName(), consentId);
    }
}

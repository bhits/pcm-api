package gov.samhsa.mhc.pcm.service.userinfo;

import gov.samhsa.mhc.pcm.service.dto.OpenIDConnectUserInfoDto;

import java.security.Principal;

public interface OpenIDConnectUserInfoService {
    OpenIDConnectUserInfoDto getUserInfo(Principal principal);
}

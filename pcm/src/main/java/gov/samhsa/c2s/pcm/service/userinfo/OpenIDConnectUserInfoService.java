package gov.samhsa.c2s.pcm.service.userinfo;

import gov.samhsa.c2s.pcm.service.dto.OpenIDConnectUserInfoDto;

import java.security.Principal;

public interface OpenIDConnectUserInfoService {
    OpenIDConnectUserInfoDto getUserInfo(Principal principal);
}

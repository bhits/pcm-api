package gov.samhsa.c2s.pcm.service.userinfo;

import gov.samhsa.c2s.pcm.service.dto.OpenIDConnectUserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.Arrays;
import java.util.Optional;

@Service
public class OpenIDConnectUserInfoServiceImpl implements OpenIDConnectUserInfoService {

    @Autowired
    private ResourceServerProperties resourceServerProperties;

    @Override
    public OpenIDConnectUserInfoDto getUserInfo(Principal principal) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        Optional<OAuth2AuthenticationDetails> oAuth2AuthenticationDetails = Optional.of(principal)
                .filter(OAuth2Authentication.class::isInstance)
                .map(OAuth2Authentication.class::cast)
                .map(OAuth2Authentication::getDetails)
                .filter(OAuth2AuthenticationDetails.class::isInstance)
                .map(OAuth2AuthenticationDetails.class::cast);
        final String tokenValue = oAuth2AuthenticationDetails.map(OAuth2AuthenticationDetails::getTokenValue).get();
        final String tokenType = oAuth2AuthenticationDetails.map(OAuth2AuthenticationDetails::getTokenType).get();
        final String userInfoUri = resourceServerProperties.getUserInfoUri();

        headers.add("Authorization", tokenType + " " + tokenValue);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        OpenIDConnectUserInfoDto userInfo = restTemplate
                .exchange(userInfoUri, HttpMethod.GET, httpEntity, OpenIDConnectUserInfoDto.class).getBody();
        return userInfo;
    }
}

package com.clientapp.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

public class TokenUtil {

    public static String getAccessToken()
    {
        OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        String token = oAuth2AuthenticationDetails.getTokenType().concat(" ").concat(oAuth2AuthenticationDetails.getTokenValue());

        return token;
    }
}

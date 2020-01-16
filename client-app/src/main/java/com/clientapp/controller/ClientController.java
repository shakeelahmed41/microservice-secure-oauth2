package com.clientapp.controller;

import com.clientapp.model.CatalogItem;
import com.clientapp.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.metadata.MethodType;
import java.util.Arrays;
import java.util.List;

@Controller
@EnableOAuth2Sso
public class ClientController extends WebSecurityConfigurerAdapter {

    @Autowired
    RestTemplate restTemplate;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll()
                .anyRequest().authenticated();
    }

    @RequestMapping(value = "/")
    public String getHome()
    {
        return "home";
    }

    @RequestMapping(value = "/{userId}")
    public String getMovie(@PathVariable("userId") String userId)
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", TokenUtil.getAccessToken());
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        List<CatalogItem> catalogItems = Arrays.asList(restTemplate.exchange("http://localhost:8081/catalog/" + userId, HttpMethod.GET, httpEntity, CatalogItem[].class).getBody());

        return catalogItems.toString();

    }

}

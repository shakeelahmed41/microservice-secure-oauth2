package io.jaziu.moviecatalogservice.resources;

import io.jaziu.moviecatalogservice.models.CatalogItem;
import io.jaziu.moviecatalogservice.models.Movie;
import io.jaziu.moviecatalogservice.models.Rating;
import io.jaziu.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient buildedWebClient;


    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId, HttpServletRequest request) {


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization",request.getHeader("Authorization"));
        HttpEntity<UserRating> httpEntity = new HttpEntity<>(httpHeaders);

        //UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);
        UserRating ratings = restTemplate.exchange("http://ratings-data-service/ratingsdata/users/" + userId, HttpMethod.GET, httpEntity, UserRating.class).getBody();

        return ratings.getUserRating().stream()
                //for each movie call movie info service and get details
                .map(rating -> {
                    //Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
                    Movie movie = restTemplate.exchange("http://movie-info-service/movies/" + rating.getMovieId(), HttpMethod.GET, httpEntity, Movie.class).getBody();

                    //put them all tohether
                    return new CatalogItem(movie.getName(), "Desc", rating.getRating());
                })
                .collect(Collectors.toList());
    }




}

 /*Movie movie = buildedWebClient
                            .get()
                            .uri("http://localhost:8083/movies/" + rating.getMovieId())
                            .retrieve()
                            .bodyToMono(Movie.class)
                            .block();
                   */



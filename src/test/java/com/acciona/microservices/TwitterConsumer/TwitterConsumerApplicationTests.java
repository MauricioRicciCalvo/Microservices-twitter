package com.acciona.microservices.TwitterConsumer;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.acciona.microservices.TwitterConsumer.model.TweetModel;
import com.acciona.microservices.TwitterConsumer.pojos.Hashtag;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TwitterConsumerApplicationTests {

	@LocalServerPort
	private int randomServerPort;
	
	private static final long IDTEST = 2L;
	private static final String TWEETS = "/tweets";
	private static final String TRENDING_HASH_TAGS = "/trendingHashtags";
	private static RestTemplate restTemplate;

	@BeforeClass
	public static void init() {
		restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory()); //Permitir test con Metodos como PATCH
	}
	
	@Before
	public void initializingDataTime() throws InterruptedException {
		Thread.sleep(5 * 1000);
	}

	@Test
	public void getTweetsWithRestrictions() throws URISyntaxException {

		final String baseUrl = "http://localhost:" + randomServerPort + TWEETS;
		URI uri = new URI(baseUrl);

		ResponseEntity<List<TweetModel>> result = restTemplate.exchange(uri, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<TweetModel>>() {});
		// Verificar que es un status HTTP OK 200
		assertThat(result.getStatusCode(), is(HttpStatus.OK));
		// Verificar que contiene un cuerpo
		assertThat(result.getBody(), is(not(nullValue())));

		int randomLowNumber = (int) (Math.floor(Math.random() * 6)); // Numero aleatorio para coger alguno de los primeros tweets
		// Verificar restricciones
		assertThat(result.getBody().get(randomLowNumber).getFollowers(), is((greaterThan(1500))));
		assertThat(result.getBody().get(randomLowNumber).getLang(), anyOf(equalTo("es"), equalTo("en"), equalTo("fr")));
	}
	
	@Test
	public void getTweetsValidated() throws URISyntaxException {
		
		final String baseUrlValidate = "http://localhost:" + randomServerPort + TWEETS + "/" + IDTEST;
		URI uri = new URI(baseUrlValidate);
		
		ResponseEntity<TweetModel> resultValidate = restTemplate.exchange(uri, HttpMethod.PATCH, null,
				new ParameterizedTypeReference<TweetModel>() {});
		
		// Verificar que es un status HTTP OK 200
		assertThat(resultValidate.getStatusCode(), is(HttpStatus.OK));
		// Verificar que contiene un cuerpo con el id de la prueba idTest
		assertThat(resultValidate.getBody(), is(not(nullValue())));
		assertThat(resultValidate.getBody().getId(), is(IDTEST));

	}
	
	@Test
	public void getTweetsValidatedAndChecked() throws URISyntaxException {
		
		getTweetsValidated();
		
		final String baseUrlAreValidated = "http://localhost:" + randomServerPort + TWEETS + "?areValidated=true";
		URI uri = new URI(baseUrlAreValidated);
		
		ResponseEntity<List<TweetModel>> resultAreValidated = restTemplate.exchange(uri, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<TweetModel>>() {});
		// Verificar que es un status HTTP OK 200
		assertThat(resultAreValidated.getStatusCode(), is(HttpStatus.OK));
		// Verificar que contiene un cuerpo
		assertThat(resultAreValidated.getBody(), is(not(nullValue())));
		assertThat(resultAreValidated.getBody().get(0).getId(), is(IDTEST)); //Recogemos uno ya que es el unico validado en pruebas
		
	}
	
	@Test
	public void getTrendingHashtags() throws URISyntaxException {
		
		final String baseUrlTrendingHashtags = "http://localhost:" + randomServerPort + TRENDING_HASH_TAGS;
		URI uri = new URI(baseUrlTrendingHashtags);
		
		ResponseEntity<List<Hashtag>> resultTrendingHashtag = restTemplate.exchange(uri, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Hashtag>>() {});
		// Verificar que es un status HTTP OK 200
		assertThat(resultTrendingHashtag.getStatusCode(), is(HttpStatus.OK));
		// Verificar que contiene un cuerpo
		assertThat(resultTrendingHashtag.getBody(), is(not(nullValue())));
		assertThat(resultTrendingHashtag.getBody().size(), is(10)); //Devolver 10 ocurrencias de los trending hashtags
		//Verificacion  de que el cuarto y quinto hashtag van por orden de volumen
		assertThat(resultTrendingHashtag.getBody().get(3).getVolume(), is(greaterThan(resultTrendingHashtag.getBody().get(4).getVolume())));
	}

}

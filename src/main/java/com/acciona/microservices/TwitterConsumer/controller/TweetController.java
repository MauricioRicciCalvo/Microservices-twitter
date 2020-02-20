package com.acciona.microservices.TwitterConsumer.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acciona.microservices.TwitterConsumer.model.TweetModel;
import com.acciona.microservices.TwitterConsumer.pojos.Hashtag;
import com.acciona.microservices.TwitterConsumer.service.ITweetService;
import com.acciona.microservices.TwitterConsumer.service.ITwitterService;

import twitter4j.TwitterException;

@RestController
public class TweetController {

	@Autowired
	private ITweetService tweetService;

	@Autowired
	private ITwitterService twitterService;

	// ?areValidated=true
	@GetMapping("/tweets")
	public List<TweetModel> getTweets(@RequestParam(required = false) String areValidated) {
		Boolean validatedTweetsParam =  (areValidated != null) ? new Boolean(areValidated) : null; //Transformamos RequestParam String a Boolean para aplicar o no al filtrado
		return tweetService.getTweets(validatedTweetsParam);
	}

	@GetMapping("/trendingHashtags")
	public List<Hashtag> getTrendingHashtags() throws TwitterException {
		return twitterService.searchTopHashtags();
	}
	
	// Seria mas correcto insertar un RequestBody para poder alterar el recurso en cualquiera de sus campos
	// Se expondria el API en otro modulo para poder subirlo a un repositorio Maven
	@PatchMapping("/tweets/{id}")
	public TweetModel validateTweet(@PathVariable Long id) {
		return tweetService.patchTweetValidar(id, true);
	}

	@PostConstruct
	private void twitterStreamingInit() {
		twitterService.searchStreamingTweets();
	}

}

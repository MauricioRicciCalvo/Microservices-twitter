package com.acciona.microservices.TwitterConsumer.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acciona.microservices.TwitterConsumer.exception.TweetNotFoundException;
import com.acciona.microservices.TwitterConsumer.model.TweetModel;
import com.acciona.microservices.TwitterConsumer.repository.TweetRepository;
import com.acciona.microservices.TwitterConsumer.service.ITweetService;

import twitter4j.Status;

@Service
public class TweetServiceImpl implements ITweetService {

	private static final int MIN_NUMBER_FOLLOWERS = 1500;

	@Autowired
	private TweetRepository tweetRepository;

	// Se puede insertar nuevos filter si fuese necesario nuevos campos
	public List<TweetModel> getTweets(Boolean filtrarValidate) {
		
		return tweetRepository.findAll().stream()
				.filter(tweet -> filtrarValidate == null || filtrarValidate == tweet.isValidate())
				.collect(Collectors.toList());
	}

	// Se busca insertar los patchTweet (x,x) necesarios para la funcionalidad buscada
	// en este exclusivamente validar el Tweet o al contrario
	public TweetModel patchTweetValidar(Long id, boolean validate) {
		return tweetRepository.findById(id).map(tweet -> {
			tweet.setValidate(validate);
			return tweetRepository.save(tweet);
		}).orElseThrow(() -> new TweetNotFoundException(id));
	}

	public void persistTweets(Status status) {
		//Si aplica restricciones y vale lo persistimos
		if (applyTweetsRestrictions(status)) { 
			TweetModel tweetModel = new TweetModel();
			tweetModel.setLat(status.getGeoLocation() != null ?status.getGeoLocation().getLatitude() : null);
			tweetModel.setLon(status.getGeoLocation() != null ?status.getGeoLocation().getLongitude() : null);
			tweetModel.setPlace(status.getPlace() != null ? status.getPlace().getName() : null);
			tweetModel.setUser(status.getUser() != null ?status.getUser().getName() : null);
			tweetModel.setFollowers(status.getUser() != null ?status.getUser().getFollowersCount() : null);
			tweetModel.setLang(status.getLang());
			tweetModel.setText(status.getText());
			tweetModel.setValidate(false);

			tweetRepository.save(tweetModel);
		}
	}
	
	private static boolean applyTweetsRestrictions(Status status) {
		if (status.getUser() == null || status.getUser().getFollowersCount() <= MIN_NUMBER_FOLLOWERS) {
			return false;
		}
		if(!"es".equalsIgnoreCase(status.getLang()) && !"it".equalsIgnoreCase(status.getLang()) && !"fr".equalsIgnoreCase(status.getLang()) ) {
			return false;
		}	
		return true;
	}
}

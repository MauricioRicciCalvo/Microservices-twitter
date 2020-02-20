package com.acciona.microservices.TwitterConsumer.service;

import java.util.List;

import com.acciona.microservices.TwitterConsumer.model.TweetModel;

import twitter4j.Status;

public interface ITweetService {

	public List<TweetModel> getTweets(Boolean isValidated);

	public TweetModel patchTweetValidar(Long id, boolean validate);

	public void persistTweets(Status status);

}

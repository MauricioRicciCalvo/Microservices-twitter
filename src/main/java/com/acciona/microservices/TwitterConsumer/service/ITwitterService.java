package com.acciona.microservices.TwitterConsumer.service;

import java.util.List;

import com.acciona.microservices.TwitterConsumer.pojos.Hashtag;

import twitter4j.TwitterException;

public interface ITwitterService {

	public void searchStreamingTweets();
	
	public List<Hashtag> searchTopHashtags() throws TwitterException;

}

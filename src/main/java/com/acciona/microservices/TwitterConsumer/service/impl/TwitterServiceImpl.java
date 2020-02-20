package com.acciona.microservices.TwitterConsumer.service.impl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acciona.microservices.TwitterConsumer.pojos.Hashtag;
import com.acciona.microservices.TwitterConsumer.service.ITweetService;
import com.acciona.microservices.TwitterConsumer.service.ITwitterService;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

@Service
public class TwitterServiceImpl implements ITwitterService {

	@Autowired

	private ITweetService tweetService;

	public void searchStreamingTweets() {

		TwitterStream twitterStream = TwitterStreamFactory.getSingleton();
		//No aplico ningun FilterQuery ya que necesitamos filtrar por varios campos sin keywords y queremos una AND logica
		//Se realizan las restricciones sobre el stream de datos
		twitterStream.addListener(new StatusListener() {

			@Override
			public void onStatus(Status status) {
				// Aplicar restriccion usuario
				tweetService.persistTweets(status);
			}

			@Override
			public void onException(Exception ex) {
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			}

			@Override
			public void onStallWarning(StallWarning warning) {
			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
			}
		});
		twitterStream.sample();
	}
	
	public List<Hashtag> searchTopHashtags() throws TwitterException {

		Twitter twitter = TwitterFactory.getSingleton();
		Trends worldTrends = twitter.getPlaceTrends(1);
		List<Trend> trendsList = Arrays.asList(worldTrends.getTrends());
		
		return trendsList.stream()
				.filter(trend -> trend.getName().startsWith("#"))
				.sorted(Comparator.comparingInt(Trend::getTweetVolume).reversed())
				.limit(10)
				.map(trend -> new Hashtag(trend.getName(), trend.getQuery(), trend.getTweetVolume(), trend.getURL()))
				.collect(Collectors.toList());
	}


}

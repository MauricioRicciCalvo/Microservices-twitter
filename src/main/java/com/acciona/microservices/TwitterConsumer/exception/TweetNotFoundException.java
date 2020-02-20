package com.acciona.microservices.TwitterConsumer.exception;

public class TweetNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TweetNotFoundException(Long id) {
		super("Could not find tweet " + id);
	}
}

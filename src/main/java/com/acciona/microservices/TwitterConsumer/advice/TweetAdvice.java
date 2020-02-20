package com.acciona.microservices.TwitterConsumer.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.acciona.microservices.TwitterConsumer.exception.ErrorResponse;
import com.acciona.microservices.TwitterConsumer.exception.TweetNotFoundException;

import twitter4j.TwitterException;

@ControllerAdvice
public class TweetAdvice {

	@ResponseBody
	@ExceptionHandler(TweetNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse tweetNotFoundHandler(TweetNotFoundException exception) {
		return new ErrorResponse(exception.getMessage());
	}
	
	@ResponseBody
	@ExceptionHandler(TwitterException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse twitterException(TwitterException exception) {
		return new ErrorResponse(exception.getMessage());
	}
	
	@ResponseBody
	@ExceptionHandler(NumberFormatException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse numberFormatException(NumberFormatException exception) {
		return new ErrorResponse("Formato de un numero incorrecto: " + exception.getMessage());
	}
	
}

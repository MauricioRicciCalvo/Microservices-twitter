package com.acciona.microservices.TwitterConsumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acciona.microservices.TwitterConsumer.model.TweetModel;

@Repository
public interface TweetRepository extends JpaRepository<TweetModel, Long> {

}

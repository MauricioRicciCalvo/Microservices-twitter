package com.acciona.microservices.TwitterConsumer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor //Lombok generacion constructor con parametros
@NoArgsConstructor //Lombok generacion constructor sin parametros
public class TweetModel {

	@Id
	@GeneratedValue
	private Long id;
	
	private String user;
	
	@Column(name="text", columnDefinition="LONGTEXT")
	private String text;
	
	private int followers;
	private String lang;
	private Double lat;
	private Double lon;
	private String place;
	
	private boolean isValidate;
	
}

package com.acciona.microservices.TwitterConsumer.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hashtag {
	private String name;
	private String query;
	private int volume;
	private String url;
}

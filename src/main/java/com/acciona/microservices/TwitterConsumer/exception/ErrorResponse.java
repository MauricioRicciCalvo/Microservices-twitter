package com.acciona.microservices.TwitterConsumer.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

	private String codigo;
	private String mensaje;

	public ErrorResponse(String mensaje) {
		this.mensaje = mensaje;
	}

}

package com.bh.realtrack.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {

	@JsonProperty("description")
	private String message;

	@JsonProperty("error")
	private String error;
	
}

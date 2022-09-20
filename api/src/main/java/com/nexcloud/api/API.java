package com.nexcloud.api;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nexcloud.api.domain.ResponseData;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Configuration
@EnableScheduling
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
@RequestMapping(value = "/v1")
public class API extends SpringBootServletInitializer implements WebApplicationInitializer {
	static final Logger logger = LoggerFactory.getLogger(API.class);
	
	/**
	 * Heart Bit
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value="Heart Bit", httpMethod="GET", notes="Heart Bit")
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS")
	})
	@RequestMapping(value="/check", method=RequestMethod.GET)
    public ResponseEntity<ResponseData> checkBit(HttpServletRequest request) throws Exception {
		ResponseEntity<ResponseData> response = new ResponseEntity<ResponseData>( HttpStatus.OK);
		
		return response;
    }
	
    public static void main(String[] args) throws Exception {
    	SpringApplication.run(API.class, args);
    }
}

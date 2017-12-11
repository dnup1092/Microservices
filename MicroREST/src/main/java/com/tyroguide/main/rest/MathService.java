package com.tyroguide.main.rest;

import java.net.URI;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class MathService {

	@Autowired
	DiscoveryClient client;
	
	@Autowired
	LoadBalancerClient lbClient;
	
	@GetMapping("/ops")
	public ResponseEntity<Integer> performOps(@PathParam("ops") String ops, @PathParam("num1") int num1, @PathParam("num2") int num2) throws Exception {
		URI opsUri = null;
		int result = 0;
		
		//using ribbon to choose
		ServiceInstance instance = lbClient.choose(ops.toUpperCase());
		if(instance != null) {
			opsUri = instance.getUri();
		}
		
		/*When using only client without Ribbon
		 * List<ServiceInstance> instances = client.getInstances(ops.toUpperCase());
		for(ServiceInstance instance : instances) {
			if(instance != null) {
				opsUri = instance.getUri();
				break;
			}
		}*/
		
		if(opsUri == null) {
			throw new Exception("No Operation available");
		}
			
		
		RestTemplate request = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(opsUri.toString() + "/" +ops.toLowerCase())
		        .queryParam("num1", num1)
		        .queryParam("num2", num2);
				/*.queryParam("num1", (int) (Math.random() * 5))
		        .queryParam("num2", (int) (Math.random() * 5));*/
		opsUri = builder.build().toUri();
		/*Map<String,String> params = new HashMap<String,String>();
		params.put("num1", String.valueOf(Math.random() * 5));
		params.put("num2", String.valueOf(Math.random() * 5));*/
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		HttpEntity requestEntity = new HttpEntity(headers);
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> response = request.exchange(opsUri, HttpMethod.GET, requestEntity, Map.class);
		result = (Integer) response.getBody().get("result");
		HttpHeaders respHeader = new HttpHeaders();
		respHeader.add("Access-Control-Allow-Origin", "http://localhost:4200");
		return new ResponseEntity<Integer>(result,respHeader,HttpStatus.CREATED);
		
	}
		
	
	
}

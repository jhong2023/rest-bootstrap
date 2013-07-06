package com.thu.api.test;

import java.io.IOException;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import junit.framework.Assert;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.ClientFilter;

public class PersonResourceTest extends BaseTestClient{
	
	@Test
	public void shouldPostAndGetPerson() {
		WebResource resource = resource();
		
		String json = "{\"address\":{\"city\":\"Beijing\",\"street\":\"No. 1 Tiananmen\"},\"name\":\"Test person\"}";
		
		ClientResponse clientResponse = resource.path("/person").type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
		
		String userId = clientResponse.getEntity(String.class);
		
		System.out.println("Person Id: " + userId);
		Assert.assertNotNull(userId);
		
		String result = resource.path("/person/" + userId).get(String.class);
		Assert.assertTrue(result.contains("Test person"));
		System.out.println("Get Person: " + result);
	}
	
	@Test
	public void shouldPostAndGetPersonList() throws JSONException{
		WebResource resource = resource();
		String json = "{\"address\":{\"city\":\"Beijing\",\"street\":\"No. 1 Tiananmen\"},\"name\":\"foo\"}";
		resource.path("/person").type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
		
		json = "{\"address\":{\"city\":\"Beijing\",\"street\":\"No. 1 Tiananmen\"},\"name\":\"bar\"}";
		resource.path("/person").type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);
		
		
		String result = resource.path("/person/list").get(String.class);
		System.out.println("Get Person List: " + result);
		
		JSONObject oj = new JSONObject(result);
		Assert.assertEquals(2, oj.getJSONArray("person").length());
	}
	

	protected String getResourcePath() {
		return "/person/";
	}
	
	/*
	 * In order to see the app running, initialize the container with this app
	 * (you may use mvn jetty:run), and, next, run this method. 
	 * 
	 * Then, visit the following URL's (on browser - you will need to use 
	 * username and password. Use admin - admin):
	 * 
	 * http://localhost:8080/restv2/person/1
	 * http://localhost:8080/restv2/person/1/portrait
	 */
	
	public static void main(String[] args) throws IOException {
		new PersonResourceTest() {
			@Override
			public WebResource resource() {
				Client client = Client.create();
				client.addFilter(new ClientFilter() {
					
					@Override
					public ClientResponse handle(ClientRequest cr)
							throws ClientHandlerException {
						
						cr.getHeaders().add(HttpHeaders.AUTHORIZATION, "Basic admin:admin");
						return getNext().handle(cr);
					}
				});
				return client.resource("http://localhost:8080" + CONTEXT_PATH + "/person");
			}
		}.shouldPostAndGetPerson();
	}
	
	
	
}

package com.na.ecs.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletConfigAware;


import rx.Observable;
import rx.util.functions.Action1;

@RequestMapping("/**")
@Controller
public class EchoEndPointController implements ServletConfigAware  {

	@Override
	public void setServletConfig(ServletConfig servletConfig) {
		// TODO Auto-generated method stub
		
	}
	
	@RequestMapping(value = "/healthcheck", method = RequestMethod.GET)
	public void healthcheck(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			
			// Prepare headers in about page
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/plain;charset=UTF-8");
			
			PrintWriter writer = response.getWriter();
			writer.write("hello");
			writer.write("\r\n");
			writer.flush();
			writer.close();
			
		} catch (Exception e) {
			//TODO Log exception
			e.printStackTrace();
		}
	}		
	
	@RequestMapping(value = "/echo2", method = RequestMethod.POST)
	public void echo2(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			
			final String payload = readRequestPayload(request);
			final String responseBody = getResponse(payload);
			
			// Prepare headers in about page
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/plain;charset=UTF-8");
			
			PrintWriter writer = response.getWriter();
			writer.write(responseBody);
			writer.write("\r\n");
			writer.flush();
			
		} catch (Exception e) {
			//TODO Log exception
			e.printStackTrace();
		}
	}	
	
	@RequestMapping(value = "/echo", method = RequestMethod.POST)
	public void about(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			Session session = new Session();
			session.url = "healthcheck";
			session.request = request;
			session.response = response;
			
			echo(session);
		} catch (Exception e) {
			//TODO Log exception
			e.printStackTrace();
		}
	}
	
	class Session {
		public String url;
		public HttpServletRequest request;
		public HttpServletResponse response;
	}
	
	protected void echo(Session... sessions) {
		
		Observable<Session> sessionStream = Observable.from(sessions);
		
		sessionStream.subscribe(new Action1<Session>() {

	          @Override
	          public void call(Session session) {
	        	  try {
	        		  
	      				final String payload = readRequestPayload(session.request);
	      				final String responseBody = getResponse(payload);	        		
	        		  
	        		  PrintWriter writer;
	        		  
	        		  writer = session.response.getWriter();
	        		  writer.write(responseBody);
	        		  writer.write("\r\n");
	        		  writer.flush();
	        		  
	        		  session.response.setStatus(HttpServletResponse.SC_OK);
	        		  session.response.setContentType("text/plain;charset=UTF-8");
	        		  
	        	  } catch (IOException e) {
	        		  // TODO Auto-generated catch block
	        		  e.printStackTrace();
	        	  }
	          }

	      });
		
		/*
		sessionStream.subscribe(new Action1<Session>() {

	          @Override
	          public void call(Session s) {
	              System.out.println(s.url);
	          }

	      });	
	      */	
	}
	
	
	protected String readRequestPayload(HttpServletRequest request) {
		String res = "";
		
		StringBuffer body = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				body.append(line);
			}
			
			res = body.toString();
		} 
		catch (Exception e) { /* report an error */
			e.printStackTrace();
			res = "";
		}
		
		return res;
	}	
	
	private final String getResponse(final String payload) {
		String res = "";
		Map<String, String> requestObject = null;
		
		try {
			requestObject = parseRequest(payload);
			
			// TODO Get name
			final String name = requestObject.get("target");
			// TODO Get say
			final String message = requestObject.get("message");
			
			Map<String,String> response = new HashMap<String,String> ();
			response.put("status", "ack");
			response.put("message", name + " says " + message);
			
			ObjectMapper m = new ObjectMapper();
			return m.writeValueAsString(response);
			
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}
	
	private Map<String, String> parseRequest(final String payload) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper m = new ObjectMapper();
		Map<String,String> request = m.readValue(payload, Map.class);
		return request;
	}
}

package com.myCode;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myFacade.ServiceFacade;

/**
 * @author namit
 * 
 * */
@Path("/employees")
public class EmployeeService {
	
	/* Landing page configured = http://localhost:XXXX/RestPathAnnotationExample/ 
	 * Can be changed in web.xml (welcome.jsp) */

	/**
	 * 
	 * This is a default message providing method
	 * Can be checked using - http://localhost:XXXX/RestPathAnnotationExample/rest/employees
	 * 
	 * */
	@GET
	@Produces("text/html")
	public Response testMethod() {
		String output = "this is employee service test...";
		return Response.status(200).entity(output).build();
	}

	/**
	 * 
	 * This is a method to fetch the list of employees available
	 * Can be checked using - http://localhost:XXXX/RestPathAnnotationExample/rest/employees/list
	 * 
	 * */
	@GET
	@Path("list")
	@Produces("application/json")
	public Response getEmployees() {
		ServiceFacade serviceFacade = new ServiceFacade();
		String output = null;

		HashMap<String, String> errorMap = new HashMap<String, String>();
		errorMap.put("Error", "No Record Found!");
		JSONObject jsonError = new JSONObject(errorMap);

		try {
			output = serviceFacade.getEmployees();
			if (null == output || output.isEmpty()) {
				output = jsonError.toJSONString();
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("JSON list = " + output);
		return Response.status(200).entity(output).build();
	}
	
	/**
	 * 
	 * This is a method to persist new employee's details
	 * Can be checked using - http://localhost:XXXX/RestPathAnnotationExample/rest/employees/save
	 * 
	 * */
	@POST
	@Path("/save")
	@Consumes("application/json")
	public Response saveEmployee(String str) {

		String[] inputArray = str.split("&");
		String fname = inputArray[0].split("=")[1];
		String lname = inputArray[1].split("=")[1];

		String result = "Record created : " + str;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("firstname", fname);
		map.put("lastname", lname);

		ServiceFacade serviceFacade = new ServiceFacade();
		serviceFacade.saveEmployees(map);

		return Response.status(201).entity(result).build();

	}

	/**
	 * 
	 * This is a method to update an existing employee, identified using employee id
	 * Can be checked using - http://localhost:XXXX/RestPathAnnotationExample/rest/employees/update
	 * 
	 * */
	@PUT
	@Path("/update")
	@Consumes("application/json")
	public Response updateEmployee(String str) {

		String[] inputArray = str.split("&");
		String id = inputArray[0].split("=")[1];
		String fname = inputArray[1].split("=")[1];
		String lname = inputArray[2].split("=")[1];

		String result = "Record updated : " + str;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("firstname", fname);
		map.put("lastname", lname);

		ServiceFacade serviceFacade = new ServiceFacade();
		serviceFacade.updateEmployees(map);

		return Response.status(201).entity(result).build();

	}
	
	/**
	 * 
	 * This is a method to delete an employee, identified by employee id
	 * Can be checked using - http://localhost:XXXX/RestPathAnnotationExample/rest/employees/delete
	 * 
	 * */
	@DELETE
	@Path("/delete")
	@Consumes("application/json")
	public Response deleteEmployee(String str) {

		String result = "Record deleted : " + str;
		int i = Integer.valueOf(str.split("=")[1]);

		ServiceFacade serviceFacade = new ServiceFacade();
		serviceFacade.deleteEmployee(i);
		return Response.status(200).entity(result).build();

	}
}

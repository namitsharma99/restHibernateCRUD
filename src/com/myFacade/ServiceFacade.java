package com.myFacade;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myBeans.Employee;
import com.myDB.ServiceDAO;

/**
 * @author namit
 * 
 * */
public class ServiceFacade {

	public String getEmployees() throws JsonProcessingException {
		ServiceDAO serviceDAO = new ServiceDAO();
		List<Employee> ls = serviceDAO.getEmployees();

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonStr = objectMapper.writeValueAsString(ls);

		return jsonStr;
	}

	public void saveEmployees(Map<String, Object> map) {
		ServiceDAO serviceDAO = new ServiceDAO();
		serviceDAO.saveEmployees(map);

	}

	public void updateEmployees(Map<String, Object> map) {
		ServiceDAO serviceDAO = new ServiceDAO();
		serviceDAO.updateEmployees(map);
	}

	public void deleteEmployee(int i) {
		ServiceDAO serviceDAO = new ServiceDAO();
		serviceDAO.deleteEmployee(i);
	}
}

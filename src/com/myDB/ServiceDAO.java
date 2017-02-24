package com.myDB;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.connectors.HibernateConnector;
import com.myBeans.Employee;

/**
 * @author namit
 * 
 * */
@SuppressWarnings("deprecation")
public class ServiceDAO {

	@SuppressWarnings({ "unchecked" })
	public List<Employee> getEmployees() {

		Session session = null;

		session = HibernateConnector.getInstance().getSession();
		Query<Employee> q = session.createQuery("from Employee emp");

		List<Employee> qList = q.list();

		if (qList != null && qList.isEmpty()) {
			return null;
		} else {
			System.out.println("list " + qList);
			return (List<Employee>) qList;
		}

	}

	public void saveEmployees(Map<String, Object> map) {

		Session session = HibernateConnector.getInstance().getSession();
		session.beginTransaction();
		String fname = (String) map.get("firstname");
		String lname = (String) map.get("lastname");
		Employee employee = new Employee();
		employee.setFirstName(fname);
		employee.setLastName(lname);

		session.save(employee);

		session.getTransaction().commit();

		session.close();
	}

	public void updateEmployees(Map<String, Object> map) {

		Session session = HibernateConnector.getInstance().getSession();
		session.beginTransaction();
		int id = Integer.valueOf((String) map.get("id"));
		String fname = (String) map.get("firstname");
		String lname = (String) map.get("lastname");
		Employee employee = new Employee();
		employee.setId(id);
		employee.setFirstName(fname);
		employee.setLastName(lname);

		session.update(employee);

		session.getTransaction().commit();

		session.close();
	}

	public void deleteEmployee(int i) {

		Session session = HibernateConnector.getInstance().getSession();
		session.beginTransaction();

		Object o = session.load(Employee.class, new Integer(i));
		Employee e = (Employee) o;

		session.delete(e);

		session.getTransaction().commit();

		session.close();
	}

}

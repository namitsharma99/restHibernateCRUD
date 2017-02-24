package com.connectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConnector {

	private static HibernateConnector hibConn;
    private Configuration cfg;
    private SessionFactory sessionFactory;
    
    private HibernateConnector() throws HibernateException {

        cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        cfg.addResource("employee.hbm.xml"); 
        sessionFactory = cfg.buildSessionFactory();
    }
    
    public static synchronized HibernateConnector getInstance() throws HibernateException {
        if (hibConn == null) {
            hibConn = new HibernateConnector();
        }
        return hibConn;
    }
 
    public Session getSession() throws HibernateException {
        Session session = sessionFactory.openSession();
        if (!session.isConnected()) {
            this.reconnect();
        }
        return session;
    }
 
    private void reconnect() throws HibernateException {
        this.sessionFactory = cfg.buildSessionFactory();
    }
    
}

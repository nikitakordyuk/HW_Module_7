package com.app.serviceDAO.DAO;

import com.app.serviceDAO.entity.Customer;
import com.app.hibernate.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CustomerDAO implements DAO<Customer> {
    private final HibernateUtil util = HibernateUtil.getInstance();

    @Override
    public void insert(Customer customer) {
        Session session = util.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();

        session.persist(customer);

        transaction.commit();

        session.close();
    }

    @Override
    public Customer getById(long id) {
        Session session = util.getSessionFactory().openSession();

        Customer customer = session.get(Customer.class, id);

        session.close();

        return customer;
    }

    @Override
    public List<Customer> getList() {
        Session session = util.getSessionFactory().openSession();

        List<Customer> customers = session.createQuery("from Customer", Customer.class).list();

        session.close();

        return customers;
    }

    @Override
    public void update(long id, Customer customer) {
        Session session = util.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();

        String name = customer.getName();
        String address = customer.getAddress();

        Customer existing = session.get(Customer.class, id);

        existing.setName(name);
        existing.setAddress(address);

        session.merge(existing);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(long id) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Customer customerToDelete = getById(id);

        if (customerToDelete != null) {
            session.remove(customerToDelete);
        } else System.out.println("ID not found");

        transaction.commit();
        session.close();
    }
}

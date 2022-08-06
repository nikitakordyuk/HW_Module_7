package com.app.serviceDAO.DAO;

import com.app.serviceDAO.entity.Company;
import com.app.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CompaniesDAO implements DAO<Company> {
    private final HibernateUtil util = HibernateUtil.getInstance();

    @Override
    public void insert(Company company) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(company);
        transaction.commit();
        session.close();
    }

    @Override
    public Company getById(long id) {
        Session session = util.getSessionFactory().openSession();

        Company company = session.get(Company.class, id);

        session.close();

        return company;
    }

    @Override
    public List<Company> getList() {
        Session session = util.getSessionFactory().openSession();

        List<Company> companies = session.createQuery("from Company", Company.class).list();

        session.close();

        return companies;
    }

    @Override
    public void update(long id, Company company) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String nameOfCompany = company.getNameOfCompany();
        String address = company.getAddress();

        Company existing = session.get(Company.class, id);
        existing.setNameOfCompany(nameOfCompany);
        existing.setAddress(address);

        session.merge(existing);
        transaction.commit();
        session.close();

    }

    @Override
    public void delete(long id) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Company companyToDelete = getById(id);

        if (companyToDelete != null) {
            session.remove(companyToDelete);
        } else System.out.println("ID not found");

        transaction.commit();
        session.close();
    }
}

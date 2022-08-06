package com.app.serviceDAO.DAO;

import com.app.serviceDAO.entity.Developer;
import com.app.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DeveloperDAO implements DAO<Developer> {
    private final HibernateUtil util = HibernateUtil.getInstance();

    @Override
    public void insert(Developer developer) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(developer);
        transaction.commit();
        session.close();
    }

    @Override
    public Developer getById(long id) {
        Session session = util.getSessionFactory().openSession();

        Developer developer = session.get(Developer.class, id);

        session.close();

        return developer;
    }

    @Override
    public List<Developer> getList() {
        Session session = util.getSessionFactory().openSession();

        List<Developer> developers = session.createQuery("from Developer", Developer.class).list();

        session.close();

        return developers;
    }

    @Override
    public void update(long id, Developer developer) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        int age = developer.getAge();
        String firstName = developer.getFirstName();
        String secondName = developer.getSecondName();
        int salary = developer.getSalary();

        Developer.Sex sex = developer.getSex();

        Developer existingDeveloper = session.get(Developer.class, id);

        if (existingDeveloper != null) {
            existingDeveloper.setFirstName(firstName);
            existingDeveloper.setSecondName(secondName);
            existingDeveloper.setAge(age);
            existingDeveloper.setSex(sex);
            existingDeveloper.setSalary(salary);

            session.merge(existingDeveloper);
        } else System.out.println("ID incorrect");

        transaction.commit();
        session.close();
    }

    @Override
    public void delete(long id) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Developer developerToDelete = getById(id);

        if (developerToDelete != null) {
            session.remove(developerToDelete);
        } else System.out.println("ID not found");

        transaction.commit();
        session.close();
    }
}

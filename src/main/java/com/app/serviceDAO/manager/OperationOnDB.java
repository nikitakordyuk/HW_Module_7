package com.app.serviceDAO.manager;

import com.app.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.app.serviceDAO.entity.Developer;
import com.app.serviceDAO.entity.Project;
import com.app.serviceDAO.entity.Skills;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OperationOnDB {
    private final HibernateUtil util = HibernateUtil.getInstance();

    public int getSumOfSalary(long id) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query<Project> query =
                // from table Project as alias p join "field" developers (set) where project's id is ?
                session.createQuery("from Project as p inner join fetch p.developers as d where p.id = : id",
                                Project.class)
                        .setParameter("id", id);

        List<Project> list = query.list();
        int sum = list.stream()
                .flatMap(o -> o.getDevelopers().stream())
                .mapToInt(Developer::getSalary)
                .sum();

        transaction.commit();
        session.close();

        return sum;
    }

    public List<Developer> getListOfDevelopers(int id) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query<Project> query =
                session.createQuery("from Project as p inner join fetch p.developers as d where p.id = : id",
                                Project.class)
                        .setParameter("id", id);

        List<Project> list = query.list();
        List<Developer> result = list.stream()
                .flatMap(o -> o.getDevelopers().stream())
                .collect(Collectors.toList());

        transaction.commit();
        session.close();

        return result;
    }

    public List<Developer> getListOfJavaDev() {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query<Skills> query = session.createQuery("from Skills as s inner join fetch s.developer",
                Skills.class);

        List<Skills> list = query.list();
        List<Developer> result = list.stream()
                .filter(it -> it.getTechnology() == Skills.Technology.Java)
                .map(Skills::getDeveloper)
                .collect(Collectors.toList());

        transaction.commit();
        session.close();

        return result;
    }

    public List<Developer> getListMidDev() {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query<Skills> query = session.createQuery("from Skills as s inner join fetch s.developer",
                Skills.class);

        List<Skills> list = query.list();
        List<Developer> result = list.stream()
                .filter(it -> it.getLevelOfPosition().equals("Middle"))
                .map(Skills::getDeveloper)
                .collect(Collectors.toList());

        transaction.commit();
        session.close();

        return result;
    }

    public List<String> getListOfProject2() {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<String> result = new ArrayList<>();
        Query<Project> query = session.createQuery("select p from Project as p inner join fetch p.developers as d",
                Project.class);

        List<Project> list1 = query.list();

        for (Project l : list1) {
            StringBuilder sb = new StringBuilder();

            sb.append("id project: ").append(l.getId()).append(" start date: ").append(l.getDate()).
                    append(", name of project: ").append(l.getNameOfProject()).append(", description")
                    .append(l.getDescription()).append(", count(dev): ").append((long) l.getDevelopers().size());
            sb.append("\n");

            String s = sb.toString();
            result.add(s);
        }

        transaction.commit();
        session.close();

        return result;
    }
}

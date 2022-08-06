package com.app.serviceDAO.DAO;

import com.app.serviceDAO.entity.Project;
import com.app.hibernate.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;


public class ProjectDAO implements DAO<Project> {
    private final HibernateUtil util = HibernateUtil.getInstance();

    @Override
    public void insert(Project project) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(project);
        transaction.commit();
        session.close();
    }

    @Override
    public Project getById(long id) {
        Session session = util.getSessionFactory().openSession();

        Project project = session.get(Project.class, id);

        session.close();

        return project;
    }

    @Override
    public List<Project> getList() {
        Session session = util.getSessionFactory().openSession();

        List<Project> projects = session.createQuery("from Project", Project.class).list();

        session.close();

        return projects;
    }

    @Override
    public void update(long id, Project project) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String name = project.getNameOfProject();
        LocalDate date = project.getDate();
        String description = project.getDescription();

        Project existingProject = getById(id);

        if (existingProject != null) {
            existingProject.setNameOfProject(name);
            existingProject.setDescription(description);
            existingProject.setDate(date);

            session.merge(project);
        } else System.out.println("ID incorrect");

        transaction.commit();
        session.close();
    }

    @Override
    public void delete(long id) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Project projectToDelete = getById(id);
        if (projectToDelete != null) {
            session.remove(projectToDelete);
        } else {
            System.out.println("ID not found");
        }
        transaction.commit();
        session.close();
    }
}

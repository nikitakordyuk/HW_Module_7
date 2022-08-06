package com.app.serviceDAO.DAO;

import com.app.serviceDAO.entity.Developer;
import com.app.serviceDAO.entity.Skills;
import com.app.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class SkillsDAO implements DAO<Skills> {
    private final HibernateUtil util = HibernateUtil.getInstance();

    @Override
    public void insert(Skills skills) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(skills);
        transaction.commit();
        session.close();
    }

    public void insertIntoCertainPosition(long developerId, Skills skills) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        DeveloperDAO developerDAO = new DeveloperDAO();
        Developer developer = developerDAO.getById(developerId);

        List<Skills> collect = developer.getSkills().stream().filter(it -> it.equals(skills)).collect(Collectors.toList());

        if (developer != null) {
            developer.addSkills(skills);
            session.merge(developer);
        } else System.out.println("ID incorrect");

        transaction.commit();
        session.close();
    }

    @Override
    public Skills getById(long id) {
        Session session = util.getSessionFactory().openSession();

        Skills skills = session.get(Skills.class, id);

        session.close();

        return skills;
    }

    @Override
    public List<Skills> getList() {
        Session session = util.getSessionFactory().openSession();

        List<Skills> skills = session.createQuery("from Skills as s inner join fetch s.developer", Skills.class).list();

        session.close();

        return skills;
    }

    @Override
    public void update(long skillsId, Skills skills) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Skills.Technology technology = skills.getTechnology();
        String levelOfPosition = skills.getLevelOfPosition();

        Skills existingSkills = getById(skillsId);

        if (existingSkills != null) {
            existingSkills.setTechnology(technology);
            existingSkills.setLevelOfPosition(levelOfPosition);

            session.merge(existingSkills);
        } else System.out.println("ID incorrect");

        transaction.commit();
        session.close();
    }

    @Override
    public void delete(long skillsId) {
        Session session = util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Skills skillsToDelete = session.find(Skills.class, skillsId);

        if (skillsToDelete != null) {
            session.remove(skillsToDelete);
        } else System.out.println("ID not found");

        transaction.commit();
        session.close();
    }
}
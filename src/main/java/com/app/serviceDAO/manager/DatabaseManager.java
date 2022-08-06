package com.app.serviceDAO.manager;

import lombok.Getter;
import com.app.serviceDAO.DAO.*;

public class DatabaseManager {

    @Getter
    private final RelationManager relationManager;
    @Getter
    private final OperationOnDB operationOnDB;
    @Getter
    private final ProjectDAO projectDAO;
    @Getter
    private final DeveloperDAO developerDAO;
    @Getter
    private final CustomerDAO customerDAO;
    @Getter
    private final CompaniesDAO companiesDAO;
    @Getter
    private final SkillsDAO skillsDAO;

    public DatabaseManager() {
        relationManager = new RelationManager();
        operationOnDB = new OperationOnDB();
        projectDAO = new ProjectDAO();
        developerDAO = new DeveloperDAO();
        customerDAO = new CustomerDAO();
        companiesDAO = new CompaniesDAO();
        skillsDAO = new SkillsDAO();
    }
}

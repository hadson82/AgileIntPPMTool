package io.agileintelligence.ppmtool.services;

import io.agileintelligence.ppmtool.domain.Project;
import io.agileintelligence.ppmtool.exceptions.ProjectIdException;
import io.agileintelligence.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        try {
            project.setProjectIndentifier(project.getProjectIndentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '"+
                    project.getProjectIndentifier().toUpperCase()
                    +"' already exists");
        }
    }

    public Project findProjectByIndentifier(String projectId) {
        Project project = projectRepository.findByProjectIndentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project ID '"
                    + projectId + "' does not exist");
        }

        return project;
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectid) {
        Project project = projectRepository.findByProjectIndentifier(projectid.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Cannot find Project with ID '" + project +
                    "'. This project does not exist");
        }

        projectRepository.delete(project);
    }
}

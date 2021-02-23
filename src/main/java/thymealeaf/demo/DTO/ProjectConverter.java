package thymealeaf.demo.DTO;

import org.springframework.stereotype.Component;
import thymealeaf.demo.model.Project;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectConverter {
    public ProjectDto entityToDto(Project project){
        ProjectDto dto = new ProjectDto();
        dto.setId(project.getProject_id());
        dto.setStart(project.getDeadEnd());
        dto.setTitle(project.getName() + " " + "Project");

        return dto;
    }
    public List<ProjectDto> entityToDto(List<Project> projects){
        return projects.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
    }
}

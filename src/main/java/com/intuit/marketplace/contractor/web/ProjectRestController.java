 /**
  * @author swethakasireddy
  * Restcontroller class to implement rest APIs related to persisting or retrieving data from Project table
  */
package com.intuit.marketplace.contractor.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.intuit.marketplace.contractor.domain.Project;
import com.intuit.marketplace.contractor.domain.ProjectRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Project", description = "the marketplace API")
public class ProjectRestController {

	
	private final Logger log = LoggerFactory.getLogger(ProjectRestController.class);
	
    private final ProjectRepository repository;

 
    ProjectRestController(ProjectRepository repository) {
    		this.repository = repository;
    }
    
    @Operation(summary = "Add a new Project", description = "endpoint for creating an entity", tags = {"Project"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Project already exists")})
    @PostMapping("/project")
    @ResponseStatus(HttpStatus.CREATED)
    public Project saveProject(
            @Parameter(description = "Project", required = true) @NotNull @RequestBody Project project) {
        log.info("saveProject() - start: project = {}", project);
        Project savedProject = repository.save(project);
        log.info("saveProject() - end: savedProject = {}", savedProject.getId());
        return savedProject;
    }

    @Operation(summary = "Find all Projects", description = " ", tags = {"Project"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Project.class))))})
    @GetMapping("/projects")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Project> getAllProjects() {
        log.info("getAllProjects() - start");
        Collection<Project> collection = repository.findAll();
        log.info("getAllProjects() - end");
        return collection;
    }

    @Operation(summary = "Find Project by ID", description = "Returns a single project", tags = {"Project"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "404", description = "Project not found")})
    @GetMapping("/projects/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Project getProjectById(
            @Parameter(description = "Id of the Project to be obtained. Cannot be empty.", required = true)
            @PathVariable Long id) {
        log.info("getProjectById() - start: id = {}", id);
        Project receivedProject = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project with id = Not found"));
        log.info("getProjectById() - end: Project = {}", receivedProject.getId());
        return receivedProject;
    }
    
    /**
     * @param none
     * Method to schedule the Contractor project winning process
     * invoked for every 1000 secs
     * @returns void 
     */
    @Scheduled(initialDelay = 1000, fixedRate = 100000000)
    public void scheduleProjectWinningJob() {
    	log.info("Current time is :: " + Calendar.getInstance().getTime());
    	
    	 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
    	   LocalDateTime now = LocalDateTime.now();  
    	   log.info("Current time is :: " + dtf.format(now));      	
    	Collection<Long> listProjectIds = repository.getCurrentActiveProjectsIds(dtf.format(now));
    	for(Long projId:listProjectIds) {
    		Collection<BigDecimal> minBidAmount =repository.getMinBids(projId);
    		List<Long> bidIds = repository.getLowestBidId(projId,minBidAmount.iterator().next());
    		log.info("Active projId is :: " + bidIds.get(0));
    		
    			Project updateProject = repository.findById(projId)
    	                .orElseThrow(() -> new EntityNotFoundException("Project with id = Not found"));
    			if(null!=bidIds.get(0) && null==updateProject.getWinBidId()) {
    			updateProject.setWinBidId(bidIds.get(0));
    		repository.save(updateProject);
    		}
    	}
    }

    
    
    
    @Operation(summary = "Find Lowest bid", description = "Returns a single project", tags = {"Project"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "404", description = "Project not found")})
    @GetMapping("/{project_id}/minBid")
    @ResponseStatus(HttpStatus.OK)
    public Collection<BigDecimal> getLowestBid(
            @Parameter(description = "Id of the Project to be obtained. Cannot be empty.", required = true)
            @PathVariable Long project_id) {
        log.info("getProjectById() - start: id = {}", project_id);
        Collection<BigDecimal> receivedProject = repository.getMinBids(project_id);
        log.info("getProjectById() - end: Project = {}");
        return receivedProject;
    }
    

    @Operation(summary = "Deletes a Project", description = "need to fill", tags = {"Project"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Project not found")})
    @DeleteMapping("/projects/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProjectById(
            @Parameter(description = "Id of the Project to be delete. Cannot be empty.", required = true)
            @PathVariable Long id) {
        log.info("removeProjectById() - start: id = {}", id);
        repository.deleteById(id);
        log.info("removeProjectById() - end: id = {}", id);
    }
}


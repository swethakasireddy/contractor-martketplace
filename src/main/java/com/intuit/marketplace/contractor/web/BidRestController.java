/**
 * @author swethakasireddy
 * RestController class which has methods to implement the rest APIs to persist or retrieve data from bid table
 */
package com.intuit.marketplace.contractor.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.intuit.marketplace.contractor.domain.Bid;
import com.intuit.marketplace.contractor.domain.BidRepository;
import com.intuit.marketplace.contractor.domain.Project;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;



@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Bid", description = "Bidding API's")
public class BidRestController {
private final Logger log = LoggerFactory.getLogger(ProjectRestController.class);
	
    private final BidRepository repository;

 
    BidRestController(BidRepository repository) {
    		this.repository = repository;
    }
    
    @Operation(summary = "Add a new Bid", description = "endpoint for creating an entity", tags = {"Bid"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Project already exists")})
    @PostMapping("/bid")
    @ResponseStatus(HttpStatus.CREATED)
    public Bid saveBid(
            @Parameter(description = "Bid", required = true) @NotNull @RequestBody Bid bid) {
        log.info("saveBid() - start: bid = {}", bid);
        BigDecimal maxBudget= repository.getMaxBudget(bid.getProjectID().longValue());
        BigDecimal minBudget= repository.getMinBudget(bid.getProjectID().longValue());
        if(maxBudget.compareTo(bid.getBidAmount())==-1 || minBudget.compareTo(bid.getBidAmount())==1 ){
        	throw new EntityNotFoundException("BidAmount not in the range of Project Budget");
        }
        // invoking Auto bid process for any bid that is saved to DB
        invokeAutoBid(bid.getBidAmount(),bid.getProjectID());
        Bid savedBid = repository.save(bid);
        log.info("saveBid() - end: savedBid = {}", savedBid.getId());
        return savedBid;
    }
    
    @Operation(summary = "Find Bids by project ID", description = "Returns Bids", tags = {"Bid"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "404", description = "Project not found")})
    @GetMapping("/bids/{projectID}")
    @ResponseStatus(HttpStatus.OK)
    public Bid getBidById(
            @Parameter(description = "Id of the Project to be obtained. Cannot be empty.", required = true)
            @PathVariable Long bidId) {
        log.info("getBidByProjectId() - start: id = {}", bidId);
        Bid receivedBid = repository.findById(bidId)
                .orElseThrow(() -> new EntityNotFoundException("Project with id = Not found"));
        log.info("getBidByProjectId() - end: Project = {}", receivedBid.getId());
        return receivedBid;
    }

    /**
     * @param BigDecimal bidAmount
     * @param BigDecimal bidAmount
     * Method to implement Auto 
     * @returns void 
     */
    
    public void invokeAutoBid(BigDecimal bidAmount,Long projectId) {
    	log.info("Invoke Auto Bid :: " + Calendar.getInstance().getTime());
    	  	
    	List<Long> listAutoBids = repository.getlistAutoBidIds(bidAmount,projectId);
    	for(Long bidId:listAutoBids) {
    		 Bid autoBid = repository.findById(bidId)
 	                .orElseThrow(() -> new EntityNotFoundException("bidwith id = Not found"));
    		 autoBid.setBidAmount(autoBid.getMinAmount());
    		repository.save(autoBid);
    		log.info("Auto Bid saved:: " + bidId);
    		}
    }
}

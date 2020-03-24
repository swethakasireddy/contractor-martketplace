/**
 * @author swethakasireddy
 * This class is for Accessing PROJECT table data
 */
package com.intuit.marketplace.contractor.domain;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.*;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Schema(name = "Project", description = "Data object for an project", oneOf = Project.class)
public class Project {

    @Schema(description = "Unique identifier of the project.", example = "1", required = false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Schema(description = "Name of the Project.", example = "House Renovation", required = true)
    @Size(max = 50)
    private String name;

    @Schema(description = "Description of the Project.", example = "Lwan replacement.", required = true)
    private String description;
    
    @Schema(description = "Min budget of the Project.", example = "1.", required = true)
    private BigDecimal minBudget;
    
    @Schema(description = "Max budget of the Project.", example = "100.00", required = true)
    private BigDecimal maxBudget;
    
    @Schema(description = "Winning bidId for a project.", example = "1", required = false)
	private Long winBidId;
    
    @Schema(description = "Project submission end date.", example = "03/29/2020.", required = true)
    @Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy")
    private Date endDate;

	public Long getId() {
        return id;
    }
	
	public Long getWinBidId() {
        return winBidId;
    }
	
	public void setWinBidId(Long winBidId) {
		this.winBidId=winBidId;
    }
	
	public BigDecimal getMaxBudget() {
		return maxBudget;
	}

	public void setMaxBudget(BigDecimal maxBudget) {
		this.maxBudget = maxBudget;
	}
	
	public BigDecimal getMinBudget() {
		return minBudget;
	}

	public void setMinBudget(BigDecimal minBudget) {
		this.minBudget = minBudget;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}   
}

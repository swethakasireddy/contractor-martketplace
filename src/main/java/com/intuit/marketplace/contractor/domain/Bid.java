/**
 * @author swethakasireddy
 * This class for Data access to the Bid table
 */
package com.intuit.marketplace.contractor.domain;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Schema(name = "Bid", description = "Data object for an Bidding", oneOf = Bid.class)
public class Bid {

	@Schema(description = "Unique identifier of the Bid.", example = "1", required = false)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
	
	@Schema(description = "ProjectID fk for bid.", example = "1", required = true)
	private Long projectID;
	
	@Schema(description = "Bidder Name.", example = "Test Bidder", required = true)
	private String bidderName;
	
	@Schema(description = "Bidder Phone number.", example = "100-000-0009", required = true)
	private String phoneNumber;
	
	@Schema(description = "bid amount.", example = "50", required = true)
	private BigDecimal bidAmount;

	@Schema(description = "Min amount for a bidder.", example = "50", required = true)
	private BigDecimal minAmount;
	
	@Schema(description = "Bidding submission date.", example = "03/29/2020.", required = false, hidden = true)
    @Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM/dd/yyyy")
	private Date date;
    
    
	public Long getProjectID() {
		return projectID;
	}
	
	public void setProjectID(Long projectID) {
		this.projectID = projectID;
	}
	
	public String getBidderName() {
		return bidderName;
	}
	
	public void setBidderName(String bidderName) {
		this.bidderName = bidderName;
	}
	
	public String getPhoneNumbber() {
		return phoneNumber;
	}
	
	public void setphoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public BigDecimal getBidAmount() {
		return bidAmount;
	}
	
	public void setBidAmount(BigDecimal bidAmount) {
		this.bidAmount = bidAmount;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
		Date DbDate = new Date(this.getDate().getTime());
		this.setDate(DbDate);
	}

	public Long getId() {
        return id;
    }
	
	public BigDecimal getMinAmount() {
		return minAmount;
	}
	
	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}
}




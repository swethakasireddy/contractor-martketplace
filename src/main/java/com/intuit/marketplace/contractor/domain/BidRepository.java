/**
 * @author swethakasireddy
 * Interface class which extends JPARepository to implement JPAs to access DB
 */
package com.intuit.marketplace.contractor.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.math.BigDecimal;
import java.util.List;


@RepositoryRestResource
public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByprojectID(Long id);
    
    @RestResource
    @Query(value = "SELECT max_budget FROM project WHERE id = :project_id", nativeQuery = true)
    BigDecimal getMaxBudget(@Param("project_id") long project_id);
    
    @RestResource
    @Query(value = "SELECT min_budget FROM project WHERE id = :project_id", nativeQuery = true)
    BigDecimal getMinBudget(@Param("project_id") long project_id);
    
    @RestResource
    @Query(value = "SELECT id FROM bid WHERE projectid = :project_id and min_amount < :bid_amount and bid_amount > :bid_amount", nativeQuery = true)
    List<Long> getlistAutoBidIds(BigDecimal bid_amount,long project_id);
    
    @RestResource
    @Query(value = "SELECT min_amount FROM bid WHERE id =:bidId", nativeQuery = true)
    BigDecimal getMinAmountById(long bidId);
}
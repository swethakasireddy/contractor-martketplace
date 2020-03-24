/**
 * @author swethakasireddy
 * Interface class which extends JPARepository to implement JPAs to access DB
 */
package com.intuit.marketplace.contractor.domain;

import java.math.BigDecimal;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.intuit.marketplace.contractor.domain.Project;

import java.util.List;

@RepositoryRestResource
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByName(String name);
    
    @RestResource
    @Query(value = "SELECT MIN(bid_amount) FROM bid WHERE projectid = :project_id", nativeQuery = true)
    Collection<BigDecimal> getMinBids(@Param("project_id") long project_id);
    
    @Query(value = "SELECT id FROM project WHERE end_date <= :current_date", nativeQuery = true)
    Collection<Long> getCurrentActiveProjectsIds(String current_date);
    
    @RestResource
    @Query(value = "SELECT id FROM bid WHERE projectid = :project_id and bid_amount = :bid_amount", nativeQuery = true)
    List<Long> getLowestBidId(long project_id , BigDecimal bid_amount);

}
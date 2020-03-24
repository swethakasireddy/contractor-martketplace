package com.intuit.marketplace.contractor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.intuit.marketplace.contractor.domain.Bid;
import com.intuit.marketplace.contractor.domain.BidRepository;
import com.intuit.marketplace.contractor.domain.Project;
import com.intuit.marketplace.contractor.domain.ProjectRepository;
import com.intuit.marketplace.contractor.web.BidRestController;
import com.intuit.marketplace.contractor.web.ProjectRestController;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

public class ContractorApplicationTests {
     
    @InjectMocks
    BidRestController bidRestController;
     
    @Mock
    BidRepository dao;
 
    @InjectMocks
    ProjectRestController projectRestController;
     
    @Mock
    ProjectRepository projectdao;
    
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
     
    //@Test
//    public void getAllEmployeesTest()
//    {
//        List<EmployeeVO> list = new ArrayList<EmployeeVO>();
//        Bid empOne = new EmployeeVO(1, "John", "John", "howtodoinjava@gmail.com");
//        EmployeeVO empTwo = new EmployeeVO(2, "Alex", "kolenchiski", "alexk@yahoo.com");
//        EmployeeVO empThree = new EmployeeVO(3, "Steve", "Waugh", "swaugh@gmail.com");
//         
//        list.add(empOne);
//        list.add(empTwo);
//        list.add(empThree);
//         
//        when(dao.getEmployeeList()).thenReturn(list);
//         
//        //test
//        List<EmployeeVO> empList = manager.getEmployeeList();
//         
//        assertEquals(3, empList.size());
//        verify(dao, times(1)).getEmployeeList();
//    }
     
  
    @Test
    public void createBidTest()
    {
        Bid bid = new Bid();
        bid.setProjectID((long)1);
        bid.setBidderName("assertTestBid");
        bid.setphoneNumber("891-189-1001");
        bid.setBidAmount(BigDecimal.valueOf(180));
        bidRestController.saveBid(bid);	
        verify(dao, times(1)).save(bid);
    }
    @Test
    public void createProjectTest()
    {
    Project proj = new Project();
   proj.setMaxBudget(BigDecimal.valueOf(200));
    proj.setMinBudget(BigDecimal.valueOf(160));
   proj.setName("testProject");
    proj.setName("testProject description");
    Date date = new Date();
    date.setYear(2020);
    date.setMonth(03);
    date.setDate(1);
   proj.setEndDate(date);
   projectdao.save(proj);
   verify(projectRestController, times(1)).saveProject(proj);
    }
}

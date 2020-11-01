package org.upgrad.upstac.testrequests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.engine.TestExecutionResult;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.config.security.UserLoggedInService;
import org.upgrad.upstac.exception.AppException;
import org.upgrad.upstac.testrequests.lab.CreateLabResult;
import org.upgrad.upstac.testrequests.lab.LabRequestController;
import org.upgrad.upstac.testrequests.lab.LabResult;
import org.upgrad.upstac.testrequests.lab.TestStatus;
import org.upgrad.upstac.users.User;
import org.upgrad.upstac.users.models.Gender;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
@ExtendWith(MockitoExtension.class)
class LabRequestControllerTest {


    @InjectMocks
    LabRequestController labRequestController;



    @Mock
    TestRequestQueryService testRequestQueryService;

    @Mock
    TestRequestUpdateService testRequestUpdateService;

    @Mock
    UserLoggedInService userLoggedInService;

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setUserName("someuser");
        return user;
    }

    @Test
    @WithUserDetails(value = "tester")
    public void calling_assignForLabTest_should_return_getLoggedInUser_and_testRequestUpdateService_assignForLabTest(){

        //Implement this method

        //Create another object of the TestRequest method and explicitly assign this object for Lab Test using assignForLabTest() method
        // from labRequestController class. Pass the request id of testRequest object.

        //Use assertThat() methods to perform the following two comparisons
        //  1. the request ids of both the objects created should be same
        //  2. the status of the second object should be equal to 'INITIATED'
        // make use of assertNotNull() method to make sure that the lab result of second object is not null
        // use getLabResult() method to get the lab result

        //Arrange
        User user=createUser ();
        TestRequest mockedtestRequest=createTestRequest();
        Mockito.when(userLoggedInService.getLoggedInUser()).thenReturn(user);
        Mockito.when(testRequestUpdateService.assignForLabTest ( 21L, user )).thenReturn(mockedtestRequest);

        //Act
        TestRequest testRequest= labRequestController.assignForLabTest (21L);

        //Assert
        assertNotNull ( testRequest );
        assertEquals (testRequest, mockedtestRequest);

    }



    @Test
    @WithUserDetails(value = "tester")
    public void calling_assignForLabTest_with_invalid_test_request_id_should_throw_exception(){

        //Arrange
        Long InvalidRequestId= -34L;
        User user= createUser();
        TestRequest mockedtestRequest = createTestRequest();

        Mockito.when(userLoggedInService.getLoggedInUser()).thenReturn(user);
        Mockito.when(labRequestController.assignForLabTest (-34L)).thenThrow(new AppException("Invalid data"));

        //Act
        ResponseStatusException result = assertThrows(ResponseStatusException.class,()->{

            labRequestController.assignForLabTest ( InvalidRequestId );
        });


        //Assert
        assertNotNull(result);
        assertEquals( HttpStatus.BAD_REQUEST, result.getStatus());
        assertEquals("Invalid data",result.getReason());

    }






    public TestRequest createTestRequest(){
        TestRequest testRequest=new TestRequest ();

        testRequest.setAddress ( "Some Address" );
        testRequest.setAge(39);
        testRequest.setEmail("someone" + "123456789" + "@somedomain.com");
        testRequest.setGender(Gender.MALE);
        testRequest.setName("someuser");
        testRequest.setPhoneNumber("123456789");
        testRequest.setPinCode(716768);
        return testRequest;

        }







    }
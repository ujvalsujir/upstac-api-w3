package org.upgrad.upstac.testrequests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import org.upgrad.upstac.testrequests.TestRequest;
import org.upgrad.upstac.testrequests.consultation.ConsultationController;
import org.upgrad.upstac.testrequests.consultation.CreateConsultationRequest;
import org.upgrad.upstac.testrequests.consultation.DoctorSuggestion;
import org.upgrad.upstac.testrequests.lab.CreateLabResult;
import org.upgrad.upstac.testrequests.lab.LabResult;
import org.upgrad.upstac.testrequests.lab.TestStatus;
import org.upgrad.upstac.testrequests.RequestStatus;
import org.upgrad.upstac.testrequests.TestRequestQueryService;
import org.upgrad.upstac.users.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j

@ExtendWith(MockitoExtension.class)
class ConsultationControllerTest {


    @InjectMocks
    ConsultationController consultationController;


    @Mock
    TestRequestQueryService testRequestQueryService;

    @Mock
    UserLoggedInService userLoggedInService;

    @Mock
    TestRequestUpdateService testRequestUpdateService;


    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setUserName("someuser");
        return user;
    }


    @Test
    @WithUserDetails(value = "doctor")
    public void calling_assignForConsultation_with_valid_test_request_id_should_return_getLoggedInUser_and_assignForConsultation(){



        //Implement this method

        //Create another object of the TestRequest method and explicitly assign this object for Consultation using assignForConsultation() method
        // from consultationController class. Pass the request id of testRequest object.

        //Use assertThat() methods to perform the following two comparisons
        //  1. the request ids of both the objects created should be same
        //  2. the status of the second object should be equal to 'DIAGNOSIS_IN_PROCESS'
        // make use of assertNotNull() method to make sure that the consultation value of second object is not null
        // use getConsultation() method to get the lab result

        //Arrange
        User user=createUser ();
        TestRequest mockedtestRequest=createConsultationRequest ();
        Mockito.when(userLoggedInService.getLoggedInUser()).thenReturn(user);
        Mockito.when(consultationController.assignForConsultation ( 21L)).thenReturn(mockedtestRequest);

        //Act
        TestRequest testRequest= consultationController.assignForConsultation (21L);

        //Assert
        assertNotNull ( testRequest );
        assertEquals ( testRequest, mockedtestRequest );
    }



    @Test

    public void calling_assignForConsultation_with_valid_test_request_id_should_throw_exception(){

        //Arrange
        Long InvalidRequestId= -34L;
        User user= createUser();
        TestRequest mockedtestRequest = createConsultationRequest ();

        Mockito.when(userLoggedInService.getLoggedInUser()).thenReturn(user);
        Mockito.when(consultationController.assignForConsultation (-34L)).thenThrow(new AppException ("Invalid data"));

        //Act
        ResponseStatusException result = assertThrows(ResponseStatusException.class,()->{

            consultationController.assignForConsultation ( InvalidRequestId );
        });


        //Assert
        assertNotNull(result);
        assertEquals( HttpStatus.BAD_REQUEST, result.getStatus());
        assertEquals("Invalid data",result.getReason());

    }




    public TestRequest createConsultationRequest() {

        //Create an object of CreateLabResult and set all the values
        // if the lab result test status is Positive, set the doctor suggestion as "HOME_QUARANTINE" and comments accordingly
        // else if the lab result status is Negative, set the doctor suggestion as "NO_ISSUES" and comments as "Ok"
        // Return the object

        TestRequest testRequest=new TestRequest ();
        testRequest.setAddress ( "Some Address" );
        testRequest.setName ( "Someone" );
        testRequest.setAge ( 39 );
        testRequest.setPhoneNumber ( "9967098765" );
        testRequest.setRequestId ( 21L );

        return testRequest;

    }

}
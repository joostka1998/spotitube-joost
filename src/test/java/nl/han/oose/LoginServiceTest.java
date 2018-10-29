package nl.han.oose;

import nl.han.oose.DAO.UserDAO;
import nl.han.oose.entities.LoginRequest;
import nl.han.oose.services.LoginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @InjectMocks
    private LoginService loginService;

    @Mock
    private UserDAO userDAOMock;

    @Test
    public void testLoginWithCorrectCredentials() {
        Mockito.when(userDAOMock.login(Mockito.any(), Mockito.any())).thenReturn(true);
        LoginRequest sut = new LoginRequest("test", "testpass");
        assertTrue(loginService.validateAccount(sut));
    }

}

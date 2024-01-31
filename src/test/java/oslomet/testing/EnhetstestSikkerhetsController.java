package oslomet.testing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpSession;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestSikkerhetsController {

    @InjectMocks
    private Sikkerhet sikkerhetsController;

    @Mock
    private BankRepository repository;

    @Mock
    private MockHttpSession session;

    @Before
    public void initSession(){
        Map<String, Object> attributes = new HashMap<>();

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                return attributes.get(key);
            }
        }).when(session).getAttribute(anyString());

        doAnswer(new Answer<Object>(){
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(session).setAttribute(anyString(), any());
    }

    //Test for feil i personnummer sjekkLoggInn:

    @Test
    public void test_sjekkLoggInn_OK(){
        //arange
        when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("OK");

        //act
        String resultat = sikkerhetsController.sjekkLoggInn("12345678910","passord");

        //assert
        assertEquals("OK",resultat);
    }

/*    @Test
    public void test_sjekkLoggInn_feilPN(){
        //arange
        when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("Feil i personnummer");

        //act
        String resultat = sikkerhetsController.sjekkLoggInn("12345678910","passord");

        //assert
        assertEquals("OK",resultat);
    }*/

    @Test
    public void test_LoggetInn_Innlogget(){
        //arrange
        session.setAttribute("Innlogget","12345678910");

        //act
        String resultat = sikkerhetsController.loggetInn();

        //assert
        assertEquals("12345678910",resultat);
    }

    @Test
    public void test_LoggetInn_IkkeInnlogget(){
        //arrange
        //act
        String resultat = sikkerhetsController.loggetInn();

        //assert
        assertNull(resultat);
    }
}

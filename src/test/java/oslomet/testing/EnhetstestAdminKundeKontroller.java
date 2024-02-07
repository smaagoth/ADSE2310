package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeKontroller {

    @InjectMocks
    // denne skal testes
    private AdminKundeController adminKundeController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void test_hentAlle(){
        //arrange
        List<Kunde> kundeliste = new ArrayList<>();
        Kunde kunde1 = new Kunde("1","Test","Testesen","Testveien 1",
                "1234","Testeby","123456","test1234");
        Kunde kunde2 = new Kunde("2","Test2","Testesen2","Testveien 2",
                "4567","Testeby","654321","123Test");
        kundeliste.add(kunde1);
        kundeliste.add(kunde2);

        when(sjekk.loggetInn()).thenReturn("1");
        when(repository.hentAlleKunder()).thenReturn(kundeliste);

        //act
        List<Kunde> resultat = adminKundeController.hentAlle();

        //assert
        assertEquals(kundeliste, resultat);
    }

    @Test
    public void test_hentAlle_feil(){

        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Kunde> resultat = adminKundeController.hentAlle();

        //assert
        assertNull(resultat);
    }

    @Test
    public void test_lagreKunde(){
        //arrange
        Kunde kunde1 = new Kunde("1","Test","Testesen","Testveien 1",
                "1234","Testeby","123456","test1234");


        when(sjekk.loggetInn()).thenReturn("1");
        when(repository.registrerKunde((any(Kunde.class)))).thenReturn("OK");

        //act
        String resultat = adminKundeController.lagreKunde(kunde1);

        //assert
        assertEquals("OK",resultat);
    }

    @Test
    public void test_lagreKunde_feil(){
        //arrange
        Kunde kunde1 = new Kunde("1","Test","Testesen","Testveien 1",
                "1234","Testeby","123456","test1234");

        when(sjekk.loggetInn()).thenReturn("1");
        when(repository.registrerKunde((any(Kunde.class)))).thenReturn("Feil");

        //act
        String resultat = adminKundeController.lagreKunde(kunde1);

        //assert
        assertEquals("Feil",resultat);

    }

    @Test
    public void test_endreKunde(){
        //arrange
        Kunde kunde1 = new Kunde("1","Test","Testesen","Testveien 1",
                "1234","Testeby","123456","test1234");

        when(sjekk.loggetInn()).thenReturn("1");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        //act
        String resultat = adminKundeController.endre(kunde1);

        //assert
        assertEquals("OK",resultat);
    }

    @Test
    public void test_endreKunde_feil(){
        //arrange
        Kunde kunde1 = new Kunde("1","Test","Testesen","Testveien 1",
                "1234","Testeby","123456","test1234");

        when(sjekk.loggetInn()).thenReturn("1");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("Feil");

        //act
        String resultat = adminKundeController.endre(kunde1);

        //assert
        assertEquals("Feil",resultat);
    }

    @Test
    public void test_slettKunde(){

        //arrange
        when(sjekk.loggetInn()).thenReturn("1");
        when(repository.slettKunde(anyString())).thenReturn("OK");

        //act
        String resultat = adminKundeController.slett("1");

        //assert
        assertEquals("OK",resultat);
    }

    @Test
    public void test_slett_IkkeInnlogget(){

        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminKundeController.slett("1");

        //assert
        assertEquals("Ikke logget inn", resultat);
    }
}

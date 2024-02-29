package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }
    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }
    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }
    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }
    @Test
    public void hentTransasksjoner_LoggetInn(){
        //arrange

        List<Transaksjon> transaksjons = new ArrayList<>();
        Transaksjon trans1 = new Transaksjon(1,"123456789",150.00,"01.01.2024",
                "Test","Test","987654321");
        Transaksjon trans2 = new Transaksjon(2,"112233445",500.00,"01.02.2024",
                "Test","Test","987654321");
        transaksjons.add(trans1);
        transaksjons.add(trans2);

        when(sjekk.loggetInn()).thenReturn("1");
        when(repository.hentBetalinger(any())).thenReturn(transaksjons);

        //act
        List<Transaksjon> res = bankController.hentBetalinger();

        //assert
        assertEquals(transaksjons, res);

    }
    @Test
    public void hentTransaksjoner_IkkeLoggetInn(){
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Konto resultat = bankController.hentTransaksjoner(null,null,null);

        //assert
        assertNull(resultat);
    }
    @Test
    public void hentSaldi_LoggetInn(){
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("12345678912", "1122334455",
                1000, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("33445566772", "9876451231",
                5000, "Brukskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("12345678912");

        when(repository.hentSaldi(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertEquals(konti, resultat);

    }
    @Test
    public void hentSaldi_IkkeLoggetInn(){
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Konto> resultat = bankController.hentSaldi();

        //assert
        assertNull(resultat);
    }
    @Test
    public void registrerBetaling_LoggetInn(){
        //arrange
        Transaksjon trans = new Transaksjon(1,"123456789",150.00,"01.01.2024",
                "Test","Test","987654321");
        when(sjekk.loggetInn()).thenReturn("1");
        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("OK");

        //act
        String resultat = bankController.registrerBetaling(trans);

        //assert
        assertEquals("OK", resultat);
    }
    @Test
    public void registrerBetaling_IkkeLoggetInn(){
        //arrange
        Transaksjon trans = new Transaksjon(1,"123456789",150.00,"01.01.2024",
                "Test","Test","987654321");

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = bankController.registrerBetaling(trans);

        //assert
        assertEquals(null, resultat);

    }

}


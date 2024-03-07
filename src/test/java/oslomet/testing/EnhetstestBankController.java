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
import static org.mockito.ArgumentMatchers.*;
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
    public void hentBetalinger_LoggetInn(){
        List<Transaksjon> betalinger = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(10,"105010123456",-10.0,"2024-02-27",
                "Takk for bolle","","22334412345");
        Transaksjon transaksjon2 = new Transaksjon(11,"105010123456",-10.0,"2024-02-27",
                "Ditt","","22334412345");
        betalinger.add(transaksjon1);
        betalinger.add(transaksjon2);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentBetalinger(anyString())).thenReturn(betalinger);
        List<Transaksjon> resultat = bankController.hentBetalinger();
        assertEquals(betalinger,resultat);
    }

    @Test
    public void hentBetalingerIkkeLoggetInn(){
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        //assert
        assertNull(resultat);
    }
    @Test
    public void utforBetaling_LoggetInn(){
        List<Transaksjon> betalinger = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(10,"105010123456",-10.0,"2024-02-27",
                "Takk for bolle","","22334412345");
        betalinger.add(transaksjon1);

        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.utforBetaling(anyInt())).thenReturn("OK");
        when(repository.hentBetalinger("01010110523")).thenReturn(betalinger);
        List<Transaksjon> resultat = bankController.utforBetaling(10);
        assertEquals(betalinger,resultat);
    }
    @Test
    public void utforBetalingIkkeLoggetInn(){
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Transaksjon> resultat = bankController.utforBetaling(10);

        //assert
        assertNull(resultat);
    }
    @Test
    public void endreKundeInfo(){
        //arrange
        Kunde kunde = new Kunde("01010110523","","","Adresseveien 1","0001",
                "Oslo","99887766","passord");
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        //act
        String resultat = bankController.endre(kunde);

        //assert
        assertEquals("OK",resultat);
    }

    @Test
    public void endreKundeInfoIkkeLoggetInn(){
        //arrange
        Kunde kunde = new Kunde("01010110523","","","Adresseveien 1","0001",
                "Oslo","99887766","passord");
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = bankController.endre(kunde);

        //assert
        assertNull(resultat);
    }
    
        @Test
    public void hentTransaksjoner_LoggetInn(){
        //arrange
        List<Transaksjon> transaksjons = new ArrayList<>();
        Transaksjon trans1 = new Transaksjon(1,"123456789",150.00,"01.01.2024",
                "Test","Test","987654321");
        Transaksjon trans2 = new Transaksjon(2,"112233445",500.00,"01.02.2024",
                "Test","Test","987654321");
        transaksjons.add(trans1);
        transaksjons.add(trans2);

        Konto konto = new Konto("10101012345","123456789",380.00,"brukskonto","NOK",transaksjons);

        when(sjekk.loggetInn()).thenReturn("1");
        when(repository.hentTransaksjoner(any(),any(),any())).thenReturn(konto);

        //act
        Konto res = bankController.hentTransaksjoner("123456789","09.09.01","20.12.01");

        //assert
        assertEquals(konto, res);
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
        assertNull(resultat);
    }
}


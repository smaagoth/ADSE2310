package oslomet.testing;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

//Følger samme oppsett som eksemplene fra forelesningsvideoene.
@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {

    @InjectMocks
    // denne skal testes
    private AdminKontoController adminKontoController;

    @Mock
    private AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;

    @Test
    public void test_hentAlleKonti_LoggetInn(){
        //arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("09090928374", "94837261738",
                13453, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("09090928374", "93847264758",
                54938, "Sparekonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("Admin");
        when(repository.hentAlleKonti()).thenReturn(konti);

        //act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        //assert
        assertEquals(konti, resultat);
    }

    @Test
    public void test_hentAlleKonti_IkkeLoggetInn(){
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        //assert
        assertNull(resultat);
    }

    @Test
    public void test_registrerKonto_LoggetInn(){
        //arrange
        Konto konto = new Konto("09090928374", "93847264758",
                54938, "Sparekonto", "NOK", null);
        when(sjekk.loggetInn()).thenReturn("Admin");
        when(repository.registrerKonto(any(Konto.class))).thenReturn("OK");

        //act
        String resultat = adminKontoController.registrerKonto(konto);

        //assert
        assertEquals("OK",resultat);
    }

    @Test
    public void test_registrerKonto_IkkeLoggetInn(){
        //arrange
        Konto konto = new Konto("09090928374", "93847264758",
                54938, "Sparekonto", "NOK", null);
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminKontoController.registrerKonto(konto);

        //assert
        assertEquals("Ikke innlogget",resultat);
    }

    //Test for endreKonto() x 2 (logget inn eller ikke)
    @Test
    public void test_endreKonto_LoggetInn(){
        //arrange
        Konto konto = new Konto("09090928374", "93847264758",
                54938, "Sparekonto", "NOK", null);
        when(sjekk.loggetInn()).thenReturn("Admin");
        when(repository.endreKonto(any(Konto.class))).thenReturn("OK");

        //act
        String resultat = adminKontoController.endreKonto(konto);

        //assert
        assertEquals("OK",resultat);
    }

    @Test
    public void test_endreKonto_IkkeLoggetInn(){
        //arrange
        Konto konto = new Konto("09090928374", "93847264758",
                54938, "Sparekonto", "NOK", null);
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminKontoController.endreKonto(konto);

        //assert
        assertEquals("Ikke innlogget",resultat);
    }

    @Test
    public void test_slettKonto_LoggetInn(){
        //arrange
        String fjernet = "OK";
        when(sjekk.loggetInn()).thenReturn("Admin");
        when(repository.slettKonto(anyString())).thenReturn("OK");

        //act
        String resultat = adminKontoController.slettKonto("01010112345");

        //assert
        assertEquals(fjernet, resultat);
    }

    @Test
    public void test_slettKonto_IkkeLoggetInn(){
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminKontoController.slettKonto("01010112345");

        //assert
        assertEquals("Ikke innlogget",resultat);
    }
}

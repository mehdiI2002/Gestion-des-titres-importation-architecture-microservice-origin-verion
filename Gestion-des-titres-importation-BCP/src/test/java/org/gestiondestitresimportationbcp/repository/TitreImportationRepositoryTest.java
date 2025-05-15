package org.gestiondestitresimportationbcp.repository;

import org.gestiondestitresimportationbcp.components.DirectoriesInitializer;
import org.gestiondestitresimportationbcp.entities.*;
import org.gestiondestitresimportationbcp.models.BanqueId;
import org.gestiondestitresimportationbcp.models.OperatorId;
import org.gestiondestitresimportationbcp.models.TitreImportationId;
import org.gestiondestitresimportationbcp.repositories.*;
import org.gestiondestitresimportationbcp.service.WatchFolderServicesDefault;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class TitreImportationRepositoryTest {
    @MockBean
    private WatchFolderServicesDefault watchFolderServicesDefault;
    @MockBean
    private DirectoriesInitializer directoriesInitializer;
    @Autowired
    private TitreImportationRepository titreImportationRepository;
    @Autowired
    private BanqueRepository banqueRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    OperateurRepository operateurRepository;
    @Autowired
    MarchandiseRepository marchandiseRepository;
    @Autowired
    private PaysProvenanceInfoRepository paysProvenanceInfoRepository;
    @BeforeEach
    public void setUp(){
        OperatorId idOperator = new OperatorId(23L,112334445555L

        );
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.MAY, 6, 14, 7, 0);
        Date dateSpecifique = calendar.getTime();
        Message message = new Message(1999L, "PORTNET", "BCP", dateSpecifique, "DTE", "DOMICILIATION_IMPORTATION");
        messageRepository.save(message);
BanqueId idBanque = new BanqueId(010L,112334445555L);
Banque banque = new Banque(idBanque,190L,7808,780);
banqueRepository.save(banque);
        MarchandiseInfo marchandiseInfo = new MarchandiseInfo();
        marchandiseInfo.setNomenclature(8703231900L);
        marchandiseInfo.setPaysOrigine("ALLEMAGNE");
        marchandiseInfo.setDesignation("BLÉ TENDRE");
        marchandiseInfo.setQuantite(2500.0);
        marchandiseInfo.setUniteComplementaire("KG");
        marchandiseInfo.setPoidsUnit(2500.0);
        marchandiseRepository.save(marchandiseInfo);
        PaysProvenanceInfo paysProvenanceInfo = new PaysProvenanceInfo("FRANCE");
paysProvenanceInfoRepository.save(paysProvenanceInfo);
        Operator operator = new Operator(idOperator,40226585,"ALF KESSAB AL MAGHREB",81,"RC",223975,88665,"190780212115909011000206");
        operateurRepository.save(operator);
        TitreImportationId id = new TitreImportationId(112334445555L,1999L);
        TitreImportation titreImportation = new TitreImportation(id,1,112334445555L,1,"non traité","ALF KESSAB AL MAGHREB","BUNGE"
                ,010,309,166500.000,126000.000,40500.000,".000","USD",2,"CFR",new Date(),new Date(),operator,message,banque,marchandiseInfo,paysProvenanceInfo);
        titreImportationRepository.save(titreImportation);

    }
    @Test
    public void findByIdNumEnregistrementAndIdNumeroMessageTest(){
        Optional<TitreImportation> titles = titreImportationRepository.findByIdNumEnregistrementAndIdNumeroMessage(112334445555L,1999L);
     assertEquals(112334445555L,titles.get().getId().getNumEnregistrement());
        assertEquals(1999L,titles.get().getId().getNumeroMessage());
    }
    @Test
    public void findByNumeroMessage(){
        TitreImportation titreImportation = titreImportationRepository.findByIdNumeroMessage(1999L);
        assertEquals(1999L,titreImportation.getId().getNumeroMessage());
    }
    @Test
    public void findNonTraitesTest(){
        List<TitreImportation> titles = titreImportationRepository.findNonTraites();
        assertEquals(titles.get(0).getId().getNumEnregistrement(),112334445555L);
        assertEquals(titles.get(0).getId().getNumeroMessage(),1999L);
    }
    @Test
    public void findfindAllById_NumEnregistrementContainingAndEtatTest(){
        String numEnregistrement = "112334445555";
        List<TitreImportation> titles = titreImportationRepository.findAllById_NumEnregistrementContainingAndEtat(numEnregistrement);
        assertEquals(titles.get(0).getId().getNumEnregistrement(),112334445555L);
        assertEquals(titles.get(0).getId().getNumeroMessage(),1999L);
        assertEquals(titles.get(0).getEtat(),"non traité");
    }
    @Test
    public void findAllTitlesByRibTest(){
        String ribBancaire = "190780212115909011000206";
        List<TitreImportation> titles = titreImportationRepository.findAllTitlesByRib(ribBancaire);
        assertEquals(titles.get(0).getId().getNumEnregistrement(),112334445555L);
        assertEquals(titles.get(0).getId().getNumeroMessage(),1999L);
        assertEquals(titles.get(0).getEtat(),"non traité");
        assertEquals(titles.get(0).getOperator().getRibBancaire(),"190780212115909011000206");
    }






}

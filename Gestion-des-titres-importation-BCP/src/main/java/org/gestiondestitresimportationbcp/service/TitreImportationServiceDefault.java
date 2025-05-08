package org.gestiondestitresimportationbcp.service;
import org.gestiondestitresimportationbcp.dto.TitreImportationDTO;
import org.gestiondestitresimportationbcp.dto.TitreImportationDetailsDTO;
import org.gestiondestitresimportationbcp.dto.TitreImportationHistoriqueDTO;
import org.gestiondestitresimportationbcp.entities.*;

import org.gestiondestitresimportationbcp.mappers.TitreImportationDTOMapper;
import org.gestiondestitresimportationbcp.mappers.TitreImportationDetailsDTOMapper;
import org.gestiondestitresimportationbcp.mappers.TitreImportationHistoriqueDTOMapper;
import org.gestiondestitresimportationbcp.models.*;
import org.gestiondestitresimportationbcp.repositories.PaysProvenanceInfoRepository;
import org.gestiondestitresimportationbcp.repositories.ResponseTablerepository;
import org.gestiondestitresimportationbcp.repositories.TitreImportationRepository;
import org.gestiondestitresimportationbcp.web.SseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TitreImportationServiceDefault implements TitreImportationServices {
    @Autowired
  private TitreImportationRepository titreImportationRepository;
    @Autowired
    private TitreImportationDTOMapper titreImportationDTOMapper;
    @Autowired
    private TitreImportationDetailsDTOMapper titreImportationDetailsDTOMapper;
    @Autowired
    PaysProvenanceInfoRepository paysProvenanceInfoRepository;
    @Autowired
    PdfFileServices pdfFileServices;
    @Autowired
    TransformObjectsToResponeFile transformObjectsToResponeFile;
    private  static int cpt = 0 ;
    private BanqueResponseMessage message ;
    @Autowired
    private ResponseTablerepository responseTablerepository;
    @Autowired
    private SseController sseController;
   @Autowired
   private TitreImportationHistoriqueDTOMapper titreImportationHistoriqueDTOMapper;

     @Override
    public void insertTitle(DemandeDomiciliationMessage demandeDomiciliationMessage, Date  DateReception){
        TitreImportation titreImportation = demandeDomiciliationMessage.getTitre();
        Operator operateur = demandeDomiciliationMessage.getOperateur();
        titreImportation.setOperator(operateur);
        titreImportation.setMessage(demandeDomiciliationMessage.getHeaderMessage());
        titreImportation.setBanque(demandeDomiciliationMessage.getBanque());
        titreImportation.setMarchandiseInfo(demandeDomiciliationMessage.getMarchandise().getMarchandiseInfo());
        PaysProvenanceInfo paysProvenanceInfo = demandeDomiciliationMessage.getTitre().getPaysProvenanceInfo();
        titreImportation.setPaysProvenanceInfo(paysProvenanceInfo);
        titreImportation.setDateReception(DateReception);

        Long  messageTitle = titreImportation.getMessage().getNumeroMessage();
         Long numEnregistrement = titreImportation.getNumEnregistrement();
        TitreImportationId id  = new TitreImportationId(numEnregistrement,messageTitle);
        titreImportation.setId(id);
        // Vérifier si le titre existe déjà
        if (!titreImportationRepository.existsById(id)) {
            titreImportationRepository.save(titreImportation);
            // Notifier le front via SSE
            sseController.notifyFileCreated("PortNet"+titreImportation.getMessage().getTypeMessage()+"_"+titreImportation.getMessage().getNumeroMessage());
        }
    }
    @Override
    public List<TitreImportationDTO> afficherTitreImportation() {
        return titreImportationRepository.findNonTraites()
                .stream()
                .map(titreImportationDTOMapper)
                .collect(Collectors.toList());
    }
    @Override
    public TitreImportationDetailsDTO afficherDetailTitreImportation(TitreImportationId id) {
        TitreImportationDetailsDTO detailsDTO = titreImportationRepository.findByIdNumEnregistrementAndIdNumeroMessage(
                        id.getNumEnregistrement(),
                        id.getNumeroMessage())
                .map(titreImportationDetailsDTOMapper::apply)
                .orElse(null);

        if (detailsDTO != null) {
            List<String> pdfFilePaths = pdfFileServices.selectPdfsFortitle(id.getNumEnregistrement());
            if (pdfFilePaths != null) {
                return new TitreImportationDetailsDTO(
                        detailsDTO.numeroMessage(),
                        detailsDTO.emetteur(),
                        detailsDTO.destinataire(),
                        detailsDTO.dateMessage(),
                        detailsDTO.typeMessage(),
                        detailsDTO.fonction(),
                        detailsDTO.idFicalUnique(),
                        detailsDTO.nom(),
                        detailsDTO.centre(),
                        detailsDTO.typeIdentification(),
                        detailsDTO.numIdentification(),
                        detailsDTO.identifiantDouane(),
                        detailsDTO.ribBancaire(),
                        detailsDTO.codeBanque(),
                        detailsDTO.guichet(),
                        detailsDTO.localite(),
                        detailsDTO.numEnregistrement(),
                        detailsDTO.Categorie(),
                        detailsDTO.typeDedmande(),
                        detailsDTO.importateur(),
                        detailsDTO.expediteur(),
                        detailsDTO.regimeDouanier(),
                        detailsDTO.bureauDouanier(),
                        detailsDTO.montantTotale(),
                        detailsDTO.montantFOB(),
                        detailsDTO.motantFret(),
                        detailsDTO.montantAssuranceAcessoires(),
                        detailsDTO.devise(),
                        detailsDTO.ConditionsLivraison(),
                        detailsDTO.incotermString(),
                        detailsDTO.nomenclature(),
                        detailsDTO.paysOrigine(),
                        detailsDTO.designation(),
                        detailsDTO.quantite(),
                        detailsDTO.uniteComplementaire(),
                        detailsDTO.poidsUnit(),
                        detailsDTO.paysProvenance(),
                        pdfFilePaths
                );
            }
        }
        return detailsDTO;
    }
    @Override
    public void accepterTitre(Long numeroMessage) {
        TitreImportation titre = titreImportationRepository.findByIdNumeroMessage(numeroMessage);
        Long messageId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
            MessageReponse messageReponse = new MessageReponse(messageId, titre.getMessage().getEmetteur(), titre.getMessage().getDestinataire(), titre.getMessage().getDateMessage(),
                    titre.getMessage().getTypeMessage(), titre.getMessage().getFonction(),
                    titre.getMessage().getNumeroMessage());
            BanqueRep banqueRep = new BanqueRep(titre.getBanque().getIdBanque().getCodeBank(), titre.getBanque().getGuichet(), titre.getBanque().getLocalite(), titre.getOperator().getRibBancaire());
        TitreReponse titreReponse = new TitreReponse(titre.getId().getNumEnregistrement());

        if(titre.getMessage().getTypeMessage().contains("DDD")) {
            Long numDomiciliation = titre.getBanque().getGuichet()  + cpt++;
            Date dateActuelle = new Date(); // Date actuelle
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateActuelle);
            calendar.add(Calendar.MONTH, 3); // Ajoute 3 mois
            Date datePlus3Mois = calendar.getTime();
            ReponseAcceptation reponse = new ReponseAcceptation(1,numDomiciliation,dateActuelle,datePlus3Mois);
            message = new BanqueResponseMessage(messageReponse, banqueRep, titreReponse,reponse);
        }
        else if(titre.getMessage().getTypeMessage().contains("DPD")){
   ReponseAcceptation reponseDPD = new ReponseAcceptation(1,new Date());
   message= new BanqueResponseMessage(messageReponse,banqueRep,titreReponse,reponseDPD);
        }
        titre.setEtat("Acceptée");
        titre.setDateTraitement(new Date());

        titreImportationRepository.save(titre);
        transformObjectsToResponeFile.tranformReponseToXml(message);
        ReponseTable reponseTable = new ReponseTable(messageId,titre.getMessage().getEmetteur(), titre.getMessage().getDestinataire(), titre.getMessage().getDateMessage(),
                titre.getMessage().getTypeMessage(),titre.getMessage().getFonction(),titre.getMessage().getNumeroMessage()
                ,titre.getBanque().getIdBanque().getCodeBank(), titre.getBanque().getGuichet(), titre.getBanque().getLocalite(), titre.getOperator().getRibBancaire(),
                titre.getId().getNumEnregistrement());
        responseTablerepository.save(reponseTable);
    }

    @Override
    public void refuserTitre(Long numeroMessage,String motif) {
        long messageId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        BanqueResponseMessage message;
        TitreImportation titre = titreImportationRepository.findByIdNumeroMessage(numeroMessage);
        MessageReponse messageReponse = new MessageReponse(messageId, titre.getMessage().getEmetteur(), titre.getMessage().getDestinataire(), titre.getMessage().getDateMessage(),
                titre.getMessage().getTypeMessage(), titre.getMessage().getFonction(),
                titre.getMessage().getNumeroMessage());
        BanqueRep banqueRep = new BanqueRep(titre.getBanque().getIdBanque().getCodeBank(), titre.getBanque().getGuichet(), titre.getBanque().getLocalite(), titre.getOperator().getRibBancaire());
        TitreReponse titreReponse = new TitreReponse(titre.getId().getNumEnregistrement());
       ReponseAcceptation reponse = new ReponseAcceptation(2,motif);
        message= new BanqueResponseMessage(messageReponse,banqueRep,titreReponse,reponse);
        titre.setEtat("Rejetée");
        titre.setDateTraitement(new Date());
        titreImportationRepository.save(titre);
        transformObjectsToResponeFile.tranformReponseToXml(message);
        ReponseTable reponseTable = new ReponseTable(messageId,titre.getMessage().getEmetteur(), titre.getMessage().getDestinataire(), titre.getMessage().getDateMessage(),
                titre.getMessage().getTypeMessage(),titre.getMessage().getFonction(),titre.getMessage().getNumeroMessage()
        ,titre.getBanque().getIdBanque().getCodeBank(), titre.getBanque().getGuichet(), titre.getBanque().getLocalite(), titre.getOperator().getRibBancaire(),
                titre.getId().getNumEnregistrement(),motif  );
        responseTablerepository.save(reponseTable);
    }

    @Override
    public List<TitreImportationDTO> researchTitleByNumEnregistrement(String numeroEnregistrement) {
      return titreImportationRepository.findAllById_NumEnregistrementContainingAndEtat(numeroEnregistrement)
                .stream()
                .map(titreImportationDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public List<TitreImportationDTO> researchTitleByRibBancaire(String ribBancaire) {
        return titreImportationRepository.findAllTitlesByRib(ribBancaire)
                .stream()
                .map(titreImportationDTOMapper)
                .collect(Collectors.toList());
    }
    @Override
    public List<TitreImportationHistoriqueDTO> findAllTitlesHistorique() {
        List<TitreImportation> titres = titreImportationRepository.findAll();
        List<TitreImportationHistoriqueDTO> resultat = new ArrayList<>();

        for (TitreImportation titre : titres) {
            TitreImportationHistoriqueDTO dto = titreImportationHistoriqueDTOMapper.apply(titre);
            resultat.add(dto);
        }
        return resultat;
    }
    @Override
    public List<Map<String, Object>> getTitlesCountByMonth() {
        List<Object[]> results = titreImportationRepository.countTitlesByYearAndMonth();
        String[] monthNames = new DateFormatSymbols(Locale.FRENCH).getMonths();
        return results.stream().map(result -> {
            int year = ((Number) result[0]).intValue();
            int monthIndex = ((Number) result[1]).intValue() - 1; // Les mois commencent à 1 dans SQL
            String monthName = monthNames[monthIndex];
            long fileCount = ((Number) result[2]).longValue();

            Map<String, Object> map = new HashMap<>();
            map.put("year", year);
            map.put("month", monthName);
            map.put("fileCount", fileCount);
            return map;
        }).collect(Collectors.toList());
    }

}

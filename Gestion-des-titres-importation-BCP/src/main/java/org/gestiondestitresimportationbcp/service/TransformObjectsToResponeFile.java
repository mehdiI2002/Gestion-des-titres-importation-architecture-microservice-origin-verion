package org.gestiondestitresimportationbcp.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.gestiondestitresimportationbcp.config.PathsProperties;
import org.gestiondestitresimportationbcp.models.BanqueResponse;
import org.gestiondestitresimportationbcp.models.BanqueResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TransformObjectsToResponeFile {
    @Autowired
    private PathsProperties pathsProperties;
    public void tranformReponseToXml(BanqueResponseMessage message) {
        try {
            BanqueResponse response = new BanqueResponse(message);
            JAXBContext context = JAXBContext.newInstance(BanqueResponse.class);
            // Créer le marshaller pour convertir l'objet en XML
            Marshaller marshaller = context.createMarshaller();

            // Formater le XML pour être lisible
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            String directoryPath = pathsProperties.getResponse();
            Date dateMessage = message.getHeaderMessageReponse().getDateMessage();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String formattedDate = dateFormat.format(dateMessage);
            String fileName = message.getBanqueRep().getCodeBanque()+ "_"
                    + message.getHeaderMessageReponse().getTypeMessage() + "_"
                    + formattedDate
                    + message.getHeaderMessageReponse().getNumeroMessage() + ".xml";
            // Écrire l'objet dans un fichier XML
            File file = new File(directoryPath +"\\"+ fileName);
            marshaller.marshal(response, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}

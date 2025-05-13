package org.gestiondestitresimportationbcp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.paths")
public class PathsProperties {
    private String logs;
    private String files;
    private String archives;
    private String docs;
private String response;
private String accuse;
private String ficshema;
private String DDDAndDPD;
    public String getLogs() {
        return logs;
    }

    public String getFiles() {
        return files;
    }

    public String getArchives() {
        return archives;
    }

    public String getDocs() {
        return docs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public void setArchives(String archives) {
        this.archives = archives;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getAccuse() {
        return accuse;
    }

    public void setAccuse(String accuse) {
        this.accuse = accuse;
    }

    public void setFicshema(String ficshema) {
        this.ficshema = ficshema;
    }

    public String getFicshema() {
        return ficshema;
    }

    public String getDDDAndDPD() {
        return DDDAndDPD;
    }

    public void setDDDAndDPD(String DDDAndDPD) {
        this.DDDAndDPD = DDDAndDPD;
    }
}

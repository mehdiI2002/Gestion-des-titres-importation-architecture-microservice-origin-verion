package org.gestiondestitresimportationbcp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "global.params")
public record ConfigParams (String x , String y){

}

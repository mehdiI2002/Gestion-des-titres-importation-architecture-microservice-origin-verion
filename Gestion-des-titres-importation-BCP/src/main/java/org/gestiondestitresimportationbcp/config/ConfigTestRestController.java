package org.gestiondestitresimportationbcp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RefreshScope
public class ConfigTestRestController {
    @Autowired
    private ConfigParams configParams;
    @Value("${global.params.p1}")
    private String a ;
    @Value("${global.params.p2}")
    private String b ;
    @GetMapping("/configTest1")
    public Map<String, String> configTest1(){
        return Map.of("p1", a, "p2", b );
    }
    @GetMapping("/configTest2")
    public ConfigParams configTest2(){
        return configParams;
    }
}

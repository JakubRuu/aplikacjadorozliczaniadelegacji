package com.appRachunek.AplikacjaDoRozliczaniaDelegacji;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.CompositeHealthContributor;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.NamedContributor;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
@Component("FetchUsersAPI")
public class FetchReservationApiHealthCheck implements CompositeHealthContributor {
    private Map<String,HealthContributor> contributors= new LinkedHashMap<>();
    private NbpApiHealthCheck nbpApiHealthCheck;
    @Autowired
    private DbHealthCheck dbHealthCheck;

    public FetchReservationApiHealthCheck(DbHealthCheck dbHealthCheck,NbpApiHealthCheck nbpApiHealthCheck) {
        this.dbHealthCheck = dbHealthCheck;
        this.nbpApiHealthCheck=nbpApiHealthCheck;
        contributors.put("Database",dbHealthCheck);
    }

    @Override
    public HealthContributor getContributor(String name) {
       return getContributor(name);
    }

    @Override
    public Iterator<NamedContributor<HealthContributor>> iterator() {
        return contributors.entrySet().stream().map((eentry)-> NamedContributor.of(eentry.getKey(),eentry.getValue())).iterator();
    }
}
//http://localhost:8080/actuator/health
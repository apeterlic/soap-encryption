package dev.beenary.config;

import dev.beenary.service.UsersClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class WebServiceConfiguration {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("generated");
        return marshaller;
    }

    @Bean
    public UsersClient userClient(Jaxb2Marshaller marshaller) {
        UsersClient client = new UsersClient();
        client.setDefaultUri("http://localhost:7000/ws");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

}


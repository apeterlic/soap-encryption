package dev.beenary.config;

import dev.beenary.service.UsersClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.soap.server.endpoint.interceptor.SoapEnvelopeLoggingInterceptor;

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

    @Bean
    public SoapEnvelopeLoggingInterceptor soapEnvelopeLoggingInterceptor() {
        return new SoapEnvelopeLoggingInterceptor();
    }

    @Bean
    public PayloadValidatingInterceptor payloadValidatingInterceptor() {
        PayloadValidatingInterceptor payloadValidatingInterceptor = new PayloadValidatingInterceptor();
        payloadValidatingInterceptor.setSchema(new ClassPathResource("users.xsd"));
        payloadValidatingInterceptor.setValidateRequest(true);
        payloadValidatingInterceptor.setValidateResponse(true);
        return payloadValidatingInterceptor;
    }
}


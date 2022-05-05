package pl.edu.pwr.java.lab8.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import pl.edu.pwr.java.lab8.endpoints.EventsEndpoint;

@EnableWs
@Configuration
public class WebServiceConfig {
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/soap/*");
    }

    @Bean(name = "soap")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema soapSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("SoapPort");
        wsdl11Definition.setLocationUri("/soap");
        wsdl11Definition.setTargetNamespace(EventsEndpoint.NAMESPACE_URI);
        wsdl11Definition.setSchema(soapSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema soapSchema() {
        return new SimpleXsdSchema(new ClassPathResource("soap.xsd"));
    }
}

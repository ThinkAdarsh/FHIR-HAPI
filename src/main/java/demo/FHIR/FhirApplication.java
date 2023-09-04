package demo.FHIR;

import demo.FHIR.config.SimpleRestFulServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FhirApplication {

	public static void main(String[] args) {
		SpringApplication.run(FhirApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean ServletRegistrationBean(){
		ServletRegistrationBean registration=new ServletRegistrationBean(new SimpleRestFulServer(),"/*");
		registration.setName("FhirServlet");
		return registration;
	}


}

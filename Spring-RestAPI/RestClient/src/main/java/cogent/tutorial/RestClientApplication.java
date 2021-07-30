package cogent.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cogent.tutorial.client.Requester;
import cogent.tutorial.model.Employee;

@SpringBootApplication
public class RestClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestClientApplication.class, args);
		Requester requester = new Requester();
		//System.out.print(requester.getJson());
		System.out.println(requester.testAddEmployee());
		for (Object employee: requester.getEmployeeList()) {
			System.out.println(employee);
		}
		
		// System.out.println(requester.getHeader());
	}

}

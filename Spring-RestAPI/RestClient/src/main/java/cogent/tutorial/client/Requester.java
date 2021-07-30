package cogent.tutorial.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import cogent.tutorial.model.Employee;

@RestController
public class Requester {
	
	// @Autowired
	// RestTemplate template;
	
	public String getJson() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/employee/getList";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		HttpStatus status = response.getStatusCode();
		if (!status.is2xxSuccessful()) {
			return status.getReasonPhrase();
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode root = mapper.readTree(response.getBody());
			JsonNode name = root.path("name");
			if (name == null) {
				return "Name is null + \n" + root.toPrettyString();
			} else {
				return root.toPrettyString();
			}
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
		
	public List<Object> getEmployeeList() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/employee/getList";
		List<Object> result = restTemplate.getForObject(url, List.class);
		return result;
	}
	
	public String getHeader() {
		String url = "http://localhost:8080/employee/getPage?pageNumber=1&pageSize=3&sortBy=name";
		HttpHeaders headers = new RestTemplate().headForHeaders(url);
		return headers.toString();
	}
	
	public String testAddEmployee() {
		String url="http://localhost:8080/employee/add";
		// Department dept = new Department(57, "DeptName", "DeptOffice", "DeptTime");
		Employee employee = new Employee(2245, "Sally", 60000, 1);
		return addEmployee(employee);
	}
	
	@RequestMapping(value = "/client/employee/add", method = RequestMethod.POST)
	public String addEmployee(@RequestBody Employee employee) {
		String url="http://localhost:8080/employee/add";
		HttpHeaders header = new HttpHeaders();
		header.setAccept(Arrays.asList(MediaType.ALL));
		HttpEntity<Employee> request = new HttpEntity<Employee>(employee, header);
		return new RestTemplate().exchange(url, HttpMethod.POST,
				request, String.class).getBody();
	}
}

/* 
 * https://stackoverflow.com/questions/47857894/restcontroller-http-status-406-not-acceptable
 * https://www.tutorialspoint.com/spring_boot/spring_boot_rest_template.htm
 * */

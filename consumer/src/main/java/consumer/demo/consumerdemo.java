package consumer.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class consumerdemo {
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("consumer/demo")
	public String consumerdemo() {
		return this.restTemplate.getForObject("http://localhost:8080/cycle/provider/demo",String.class);
		
	}

}

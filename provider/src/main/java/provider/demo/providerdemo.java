package provider.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class providerdemo {

	@RequestMapping("/demo")
	public String demo(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        String memberID = request.getParameter("MemberID");
        System.out.println("memberID="+memberID);
		return "hello world!";
	}
}

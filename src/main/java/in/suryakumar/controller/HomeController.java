package in.suryakumar.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class HomeController {

    // This reads the secret key you saved in Render's Environment Variables
    @Value("${WEB3FORMS_ACCESS_KEY}")
    private String web3formsAccessKey;

    @RequestMapping({"/", "", "/home"})
    public String showHomePage() {
        return "home";
    }

    // This new method handles the form submission
    @PostMapping("/send-message")
    public String handleFormSubmission(@RequestParam("name") String name,
                                       @RequestParam("email") String email,
                                       @RequestParam("message") String message) {

        RestTemplate restTemplate = new RestTemplate();
        String web3FormsUrl = "https://api.web3forms.com/submit";

        // Create the data payload to send to Web3Forms
        Map<String, Object> formData = new LinkedHashMap<>();
        formData.put("access_key", web3formsAccessKey); // Your secret key is added here
        formData.put("name", name);
        formData.put("email", email);
        formData.put("message", message);
        formData.put("from_name", "Portfolio Contact Form"); // Optional

        // Send the data
        try {
            restTemplate.postForObject(web3FormsUrl, formData, String.class);
        } catch (Exception e) {
            // You can add logging here to see any errors in your Render logs
            System.out.println("Error sending email: " + e.getMessage());
        }

        // Redirect back to the contact section after submission
        return "redirect:/#contact";
    }
}
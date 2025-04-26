package MikaDevelops.ticketingSystem.ticketingPage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ticketingPageController {
    @GetMapping("/")
    public String index(){
        return "index.html";
    }
}

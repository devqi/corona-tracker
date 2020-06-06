package devq.coronatracker.controllers;

import devq.coronatracker.services.CoronaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    CoronaDataService coronaDataService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("locationStats", coronaDataService.getLocationStatsList());
        model.addAttribute("totalReportCaseCount", coronaDataService.getTotalReportCaseCount());
        return "home";
    }
}

package be.buschop.ap2022b.party.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



@Controller
public class HomeController {
    private final int mySpecialNumber = 35;

    @GetMapping(value = {"/", "/home", "/home/"})
    public String home(Model model) {
        model.addAttribute("mySpecialNumber", mySpecialNumber);
        return "home";
    }

    @GetMapping("/pay")
    public String pay(Model model) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);

        Boolean weekend = false;
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            weekend = true;
        }


        c.add(Calendar.DATE, 5);
        Date paydate = c.getTime();


        model.addAttribute("paydate", format.format(paydate));
        model.addAttribute("weekend", weekend);
        return "pay";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }



}

package be.buschop.ap2022b.party.controllers;


import be.thomasmore.party.model.Venue;
import org.hibernate.query.criteria.internal.predicate.BooleanExpressionPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



@Controller
public class HomeController {
    private final int mySpecialNumber = 35;
    private final String [] venuenames = {"Carré", "Zillion", "Cherrymoon", "Boccaccio", "Carat"};

    private final Venue [] venues = {
            new Venue("Carré", "Website Carré", 500, true, true, false,false, "Willebroek", 5),
            new Venue("Zillion", "Website Zillion", 500, true, true, false,false, "Antwerpen", 0),
            new Venue("Cherrymoon", "Website Cherrymoon", 500, true, true, false,false, "Knokke", 2),
            new Venue("Boccaccio", "Website Boccaccio", 500, true, true, false,false, "Ergens", 2),
            new Venue("Carat", "Website Carat", 500, true, true, false,false, "Ergensnogverder", 2),
};

    @GetMapping(value = {"/", "/home", "/home/"})
    public String home (Model model){
        model.addAttribute("mySpecialNumber",mySpecialNumber);
        return "home";
    }

    @GetMapping("/pay")
    public String pay(Model model){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);

        Boolean weekend = false;
        if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY )
        {
            weekend = true;
        }


        c.add(Calendar.DATE,5);
        Date paydate = c.getTime();



        model.addAttribute("paydate", format.format(paydate));
        model.addAttribute("weekend",weekend);
        return "pay";
    }

    @GetMapping("/about")
    public String about (){
        return "about";
    }

    @GetMapping("/venuelist")
    public String venuelist (Model model){
        model.addAttribute("venues", venues );
        return "venuelist";
    }

    @GetMapping({"/venuedetails","/venuedetails/","/venuedetails/{venuename}"})
    public String venuedetails(Model model, @PathVariable(required = false) String venuename){
        model.addAttribute("venuename",venuename);
        return "venuedetails";
    }

    @GetMapping({"/venuedetailsbyindex","/venuedetailsbyindex/","/venuedetailsbyindex/{venueindex}"})
    public String venuedetailsbyindex(Model model, @PathVariable(required = false) String venueindex){
        Venue venue = null;
        if(venueindex !=null && Integer.parseInt(venueindex)%1 == 0 && Integer.parseInt(venueindex)>= 0 && Integer.parseInt(venueindex)< venues.length )
        {
            //get venue data here
            venue = venues[Integer.parseInt(venueindex)];
        }

        int prevIndex = Integer.parseInt(venueindex)-1;

        if(prevIndex<0){
            prevIndex = venuenames.length -1;
        }

        int nextIndex = Integer.parseInt(venueindex)+1;

        if(nextIndex > venues.length -1)
        {
            nextIndex = 0;
        }

        model.addAttribute("venue",venue);
        model.addAttribute("prevIndex", prevIndex);
        model.addAttribute("nextIndex", nextIndex);
        return "venuedetailsbyindex";
    }



}

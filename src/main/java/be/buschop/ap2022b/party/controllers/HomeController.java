package be.buschop.ap2022b.party.controllers;


import be.buschop.ap2022b.party.model.Artist;
import be.buschop.ap2022b.party.repositories.ArtistRepository;
import be.buschop.ap2022b.party.repositories.VenueRepository;
import be.buschop.ap2022b.party.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


@Controller
public class HomeController {
    private final int mySpecialNumber = 35;
    private final String[] venuenames = {"Carré", "Zillion", "Cherrymoon", "Boccaccio", "Carat"};

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private ArtistRepository artistRepository;

    private final Venue[] venues = {
            new Venue("Carré", "Website Carré", 500, true, true, false, false, "Willebroek", 5),
            new Venue("Zillion", "Website Zillion", 500, true, true, false, false, "Antwerpen", 0),
            new Venue("Cherrymoon", "Website Cherrymoon", 500, true, true, false, false, "Knokke", 2),
            new Venue("Boccaccio", "Website Boccaccio", 500, true, true, false, false, "Ergens", 2),
            new Venue("Carat", "Website Carat", 500, true, true, false, false, "Ergensnogverder", 2),
    };

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

    @GetMapping("/venuelist")
    public String venuelist(Model model) {
        Iterable<Venue> venues = venueRepository.findAll();
        model.addAttribute("venues", venues);
        return "venuelist";
    }

    @GetMapping("/artistlist")
    public String artistList(Model model) {
        Iterable<Artist> artists = artistRepository.findAll();
        model.addAttribute("artists", artists);
        return "artistList";
    }

    @GetMapping("venuelist/outdoor/{outdoor}")
    public String venuelistOutdoor(Model model, @PathVariable(required = false) String outdoor) {
        Iterable<Venue> venues = venueRepository.findAll();
        if (outdoor.equals("yes")) {
            venues = venueRepository.findByOutdoor(true);
        } else if (outdoor.equals("no")) {
            venues = venueRepository.findByOutdoor(false);
        }

        model.addAttribute("venues", venues);
        model.addAttribute("outdoor", outdoor);

        return "venuelist";
    }

    @GetMapping("venuelist/indoor/{indoor}")
    public String venuelistIndoor(Model model, @PathVariable(required = false) String indoor) {
        Iterable<Venue> venues = venueRepository.findAll();
        if (indoor.equals("yes")) {
            venues = venueRepository.findByIndoor(true);
        } else if (indoor.equals("no")) {
            venues = venueRepository.findByIndoor(false);
        }

        model.addAttribute("venues", venues);
        model.addAttribute("indoor", indoor);

        return "venuelist";
    }

    @GetMapping("venuelist/size/{size}")
    public String venuelistsize(Model model, @PathVariable(required = false) String size) {
        Iterable<Venue> venues = venueRepository.findAll();
        if (size.equals("S")) {
            venues = venueRepository.findByCapacityLessThan(200);
        } else if (size.equals("M")) {
            venues = venueRepository.findByCapacityBetween(199, 600);
        } else if (size.equals("L")) {
            venues = venueRepository.findByCapacityGreaterThanEqual(600);
        }

        model.addAttribute("venues", venues);
        model.addAttribute("size", size);
        return "venuelist";
    }

    @GetMapping({"/venuedetails", "/venuedetails/", "/venuedetails/{venuename}"})
    public String venuedetails(Model model, @PathVariable(required = false) String venuename) {
        model.addAttribute("venuename", venuename);
        return "venuedetails";
    }


    @GetMapping({"/venuedetailsbyid", "/venuedetailsbyid/", "/venuedetailsbyid/{venueid}"})
    public String venuedetailsbyid(Model model,
                                   @PathVariable(required = false) String venueid) {

        Optional oVenue = null;
        Venue venue = null;
        int venueCount = 0;
        boolean idNull = false;

        venueCount = (int) venueRepository.count();

        if (Integer.parseInt(venueid) <= 0 || Integer.parseInt(venueid) > venueCount) {
            idNull = true;
        }

        oVenue = venueRepository.findById(Integer.parseInt(venueid));
        if (oVenue.isPresent()) {
            venue = (Venue) oVenue.get();
        }

        int prevId = Integer.parseInt(venueid) - 1;
        if (prevId < 1) {
            prevId = venueCount;
        }

        int nextId = Integer.parseInt(venueid) + 1;
        if (nextId > venueCount) {
            nextId = 1;
        }

        model.addAttribute("venue", venue);
        model.addAttribute("prevIndex", prevId);
        model.addAttribute("nextIndex", nextId);
        model.addAttribute("idNull", idNull);
        return "venuedetailsbyid";
    }

    @GetMapping({"/artistdetailsbyid", "/artistdetailsbyid/", "/artistdetailsbyid/{artistid}"})
    public String artistdetailsbyid(Model model,
                                    @PathVariable(required = false) String artistid) {

        Optional oArtist = null;
        Artist artist = null;
        int artistCount = 0;
        boolean idNull = false;

        artistCount = (int) artistRepository.count();

        if (Integer.parseInt(artistid) <= 0 || Integer.parseInt(artistid) > artistCount) {
            idNull = true;
        }

        oArtist = artistRepository.findById(Integer.parseInt(artistid));
        if (oArtist.isPresent()) {
            artist = (Artist) oArtist.get();
        }

        int prevId = Integer.parseInt(artistid) - 1;
        if (prevId < 1) {
            prevId = artistCount;
        }

        int nextId = Integer.parseInt(artistid) + 1;
        if (nextId > artistCount) {
            nextId = 1;
        }

        model.addAttribute("artist", artist);
        model.addAttribute("prevIndex", prevId);
        model.addAttribute("nextIndex", nextId);
        model.addAttribute("idNull", idNull);
        return "artistdetailsbyid";
    }

    @GetMapping({"/venuedetailsbyindex", "/venuedetailsbyindex/", "/venuedetailsbyindex/{venueindex}"})
    public String venuedetailsbyindex(Model model, @PathVariable(required = false) String venueindex) {
        Venue venue = null;
        if (venueindex != null && Integer.parseInt(venueindex) % 1 == 0 && Integer.parseInt(venueindex) >= 0 && Integer.parseInt(venueindex) < venues.length) {
            //get venue data here
            venue = venues[Integer.parseInt(venueindex)];
        }

        int prevIndex = Integer.parseInt(venueindex) - 1;

        if (prevIndex < 0) {
            prevIndex = venuenames.length - 1;
        }

        int nextIndex = Integer.parseInt(venueindex) + 1;

        if (nextIndex > venues.length - 1) {
            nextIndex = 0;
        }

        model.addAttribute("venue", venue);
        model.addAttribute("prevIndex", prevIndex);
        model.addAttribute("nextIndex", nextIndex);
        return "venuedetailsbyindex";
    }

}

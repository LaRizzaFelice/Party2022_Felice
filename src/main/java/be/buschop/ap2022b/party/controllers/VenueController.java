package be.buschop.ap2022b.party.controllers;

import be.buschop.ap2022b.party.repositories.VenueRepository;
import be.buschop.ap2022b.party.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;


@Controller
public class VenueController {
    @Autowired
    private VenueRepository venueRepository;

    @GetMapping({"/venuelist/{venuelist}", "/venuelist"})
    public String venuelist(Model model) {
        Iterable<Venue> venues = venueRepository.findAll();
        model.addAttribute("venues", venues);
        return "venuelist";
    }

    @GetMapping({"/venuedetails", "/venuedetails/", "/venuedetails/{venueid}"})
    public String venuedetails(Model model,
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
        return "venuedetails";
    }
    @GetMapping("venuelist/outdoor/{outdoor}")
    public String venuelistOutdoor (Model model, @PathVariable(required = false) String outdoor)
    {Iterable<Venue> venues = venueRepository.findAll();
        if(outdoor.equals("yes")) {
            venues = venueRepository.findByOutdoor(true);
        }
        else if(outdoor.equals("no")) {
            venues = venueRepository.findByOutdoor(false);
        }

        model.addAttribute("venues", venues);
        model.addAttribute("outdoor", outdoor);
        return "venuelist";
    }
    @GetMapping("venuelist/indoor/{indoor}")
    public String venuelistindoor (Model model, @PathVariable(required = false) String indoor)
    {Iterable<Venue> venues = venueRepository.findAll();
        if(indoor.equals("yes")) {
            venues = venueRepository.findByIndoor(true);
        }
        else if(indoor.equals("no")) {
            venues = venueRepository.findByIndoor(false);
        }

        model.addAttribute("venues", venues);
        model.addAttribute("indoor", indoor);
        return "venuelist";
    }
    @GetMapping("venuelist/size/{size}")
    public String venuelistsize (Model model, @PathVariable(required = false) String size) {
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
}

package be.buschop.ap2022b.party.controllers;

import be.buschop.ap2022b.party.model.Party;
import be.buschop.ap2022b.party.repositories.PartyRepository;
import be.buschop.ap2022b.party.repositories.VenueRepository;
import be.buschop.ap2022b.party.model.Venue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Controller
public class VenueController {
    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private PartyRepository partyRepository;


    private boolean showFilters;
    private Logger logger = LoggerFactory.getLogger(VenueController.class);

    @GetMapping("/venuelist")
    public String venuelist(Model model) {
        boolean showFilters = false;
        Iterable<Venue> venues = venueRepository.findAll();

        model.addAttribute("venues", venues);
        model.addAttribute("showFilters", showFilters);
        model.addAttribute("showFilters", showFilters);
        model.addAttribute("aantal", venueRepository.count());
        return "venuelist";
    }

    @GetMapping("/venuelist/filter")
    public String filter(Model model,
                         @RequestParam(required = false) Integer minCapacity, Integer maxCapacity, Integer maxDistance, Boolean foodProvided, Boolean indoor, Boolean outdoor ){
        if (minCapacity == null){
            minCapacity = 0;
        }
        if (maxCapacity == null){
            maxCapacity = 0;
        }

        if (maxDistance == null){
            maxDistance = 0;
        }
        logger.info(String.format("filter -- min=%d", minCapacity));
        logger.info(String.format("filter -- min=%d", maxCapacity));
        boolean showFilters = true;
        Iterable<Venue> venues = venueRepository.findByFilter(minCapacity,maxCapacity,maxDistance, foodProvided, indoor, outdoor);
        model.addAttribute("venues", venues);
        model.addAttribute("showFilters", showFilters);
        model.addAttribute("aantal", venueRepository.count());
        return "venuelist";
    }

    @GetMapping({"/venuedetails", "/venuedetails/", "/venuedetails/{venueid}"})
    public String venuedetails(Model model, @PathVariable(required = false) String venueid) {

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

        Iterable<Party> parties = partyRepository.findByVenue((Venue) oVenue.get());

        model.addAttribute("parties", parties);
        model.addAttribute("venue", venue);
        model.addAttribute("prevIndex", prevId);
        model.addAttribute("nextIndex", nextId);
        model.addAttribute("idNull", idNull);
        return "venuedetails";
    }
}

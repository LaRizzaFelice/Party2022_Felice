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
    private Logger logger = LoggerFactory.getLogger(VenueController.class);

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private PartyRepository partyRepository;

    @GetMapping({"/venuedetails", "/venuedetails/{id}"})
    public String venueDetails(Model model,
                               @PathVariable(required = false) Integer id) {
        if (id == null) return "venuedetails";

        Optional<Venue> optionalVenue = venueRepository.findById(id);
        if (optionalVenue.isPresent()) {
            long nrOfVenues = venueRepository.count();
            Iterable<Party> parties = partyRepository.findByVenue(optionalVenue.get());
            model.addAttribute("venue", optionalVenue.get());
            model.addAttribute("prevId", id > 1 ? id - 1 : nrOfVenues);
            model.addAttribute("nextId", id < nrOfVenues ? id + 1 : 1);
            model.addAttribute("parties", parties);
        }
        return "venuedetails";
    }

    @GetMapping({"/venuelist"})
    public String venueList(Model model) {
        logger.info("venueList");
        Iterable<Venue> venues = venueRepository.findAll();
        long nrOfVenues = venueRepository.count();
        model.addAttribute("venues", venues);
        model.addAttribute("nrOfVenues", nrOfVenues);
        model.addAttribute("showFilters", false);
        return "venuelist";
    }

    @GetMapping({"/venuelist/filter"})
    public String venueListWithFilter(Model model,
                                      @RequestParam(required = false) Integer minCapacity,
                                      @RequestParam(required = false) Integer maxCapacity,
                                      @RequestParam(required = false) Integer maxDistance,
                                      @RequestParam(required = false) String filterFood,
                                      @RequestParam(required = false) String filterIndoor,
                                      @RequestParam(required = false) String filterOutdoor) {
        logger.info(String.format("venueListWithFilter -- min=%d, max=%d, distance=%d, filterFood=%s, filterIndoor=%s, , filterOutdoor=%s",
                minCapacity, maxCapacity, maxDistance, filterFood, filterIndoor, filterIndoor));

        List<Venue> venues = venueRepository.findByFilter(minCapacity, maxCapacity, maxDistance,
                filterStringToBoolean(filterFood), filterStringToBoolean(filterIndoor), filterStringToBoolean(filterOutdoor));

        model.addAttribute("venues", venues);
        model.addAttribute("nrOfVenues", venues.size());
        model.addAttribute("showFilters", true);
        model.addAttribute("minCapacity", minCapacity);
        model.addAttribute("maxCapacity", maxCapacity);
        model.addAttribute("maxDistance", maxDistance);
        model.addAttribute("filterFood", filterFood);
        model.addAttribute("filterIndoor", filterIndoor);
        model.addAttribute("filterOutdoor", filterOutdoor);

        return "venuelist";
    }

    private Boolean filterStringToBoolean(String filterString) {
        return (filterString == null || filterString.equals("all")) ? null : filterString.equals("yes");
    }

}
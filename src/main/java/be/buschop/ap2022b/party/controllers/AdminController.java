package be.buschop.ap2022b.party.controllers;

import be.buschop.ap2022b.party.model.Party;
import be.buschop.ap2022b.party.model.Venue;
import be.buschop.ap2022b.party.repositories.PartyRepository;
import be.buschop.ap2022b.party.repositories.VenueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private VenueRepository venueRepository;

    @ModelAttribute("party")
    public Party findParty(@PathVariable(required = false) Integer id) {
        logger.info("findParty " + id);
        if (id == null) return new Party();

        Optional<Party> optionalParty = partyRepository.findById(id);
        if (optionalParty.isPresent())
            return optionalParty.get();
        return null;
    }

    @GetMapping("/partyedit/{id}")
    public String partyEdit(Model model,
                            @PathVariable int id) {
        logger.info("partyEdit " + id);
        model.addAttribute("venues", venueRepository.findAll());
        return "admin/partyedit";
    }

    @PostMapping("/partyedit/{id}")
    public String partyEditPost(Model model,
                                @PathVariable int id,
                                @Valid @ModelAttribute("party") Party party,
                                BindingResult bindingResult,
                                @RequestParam int venueId) {
        logger.info("partyEditPost " + id + " -- new name=" + party.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("venues", venueRepository.findAll());
            return "admin/partyedit";
        }
        partyRepository.save(party);
        return "redirect:/partydetails/" + id;
    }

    @GetMapping("/partynew")
    public String partyNew(Model model) {
        logger.info("partyNew ");
        model.addAttribute("party", new Party());
        model.addAttribute("venues", venueRepository.findAll());
        return "admin/partynew";
    }

    @PostMapping("/partynew")
    public String partyNewPost(Model model,
                               @ModelAttribute("party") Party party,
                               @RequestParam int venueId) {
        logger.info("partyNewPost -- new name=" + party.getName() + ", date=" + party.getDate());
        party.setVenue(new Venue(venueId));
        Party newParty = partyRepository.save(party);
        return "redirect:/partydetails/" + newParty.getId();
    }

}

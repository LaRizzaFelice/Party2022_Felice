package be.buschop.ap2022b.party.controllers;


import be.buschop.ap2022b.party.model.Party;
import be.buschop.ap2022b.party.repositories.PartyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class PartyController {
    private Logger logger = LoggerFactory.getLogger(PartyController.class);

    @Autowired
    private PartyRepository partyRepository;

    @GetMapping ("/partylist")
    public String partyList(Model model) {
        Iterable<Party> parties = partyRepository.findAll();
        model.addAttribute("parties", parties);
        return "partylist";
    }

    @GetMapping({"/partydetails", "/partydetails/{id}"})
    public String partyDetails(Model model,
                               @PathVariable(required = false) Integer id) {
        if (id == null) return "partydetails";

        Optional<Party> optionalParty = partyRepository.findById(id);
        if (optionalParty.isPresent()) {
            long nrOfParties = partyRepository.count();
            model.addAttribute("party", optionalParty.get());
            model.addAttribute("prevId", id > 1 ? id - 1 : nrOfParties);
            model.addAttribute("nextId", id < nrOfParties ? id + 1 : 1);
        }
        return "partydetails";
    }

}

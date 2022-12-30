package be.buschop.ap2022b.party.controllers;

import be.buschop.ap2022b.party.model.Party;
import be.buschop.ap2022b.party.repositories.PartyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private Logger logger = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private PartyRepository partyRepository;
    @GetMapping("/partyedit/{id}")
    public String partyEdit(Model model,
                            @PathVariable int id) {
        logger.info("partyEdit " + id);
        Optional<Party> optionalParty = partyRepository.findById(id);
        if (optionalParty.isPresent()) {
            model.addAttribute("party", optionalParty.get());
        }
        return "admin/partyedit";
    }
    @PostMapping("/partyedit/{id}")
    public String partyEditPost(Model model,
                                @PathVariable int id,
        @ModelAttribute("party") Party party) {
            logger.info("partyEditPost " + id + " -- new name=" + party.getName());
            Optional<Party> optionalParty = partyRepository.findById(id);
            if (optionalParty.isPresent()) {
                Party editedParty = optionalParty.get();
                editedParty.setName(party.getName());
                editedParty.setPricePresaleInEur(party.getPricePresaleInEur());
                editedParty.setPriceInEur(party.getPriceInEur());
                editedParty.setExtraInfo(party.getExtraInfo());
                partyRepository.save(editedParty);
            }
            return "redirect:/partydetails/"+id;
        }
    }

package be.buschop.ap2022b.party.controllers;

import be.buschop.ap2022b.party.model.Artist;
import be.buschop.ap2022b.party.repositories.ArtistRepository;
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
public class ArtistController {
    private Logger logger = LoggerFactory.getLogger(ArtistController.class);

    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping({"/artistdetails", "/artistdetails/{id}"})
    public String artistDetails(Model model,
                                @PathVariable(required = false) Integer id) {
        if (id == null) return "artistdetails";

        Optional<Artist> optionalArtist = artistRepository.findById(id);
        if (optionalArtist.isPresent()) {
            long nrOfArtists = artistRepository.count();
            model.addAttribute("artist", optionalArtist.get());
            model.addAttribute("prevId", id > 1 ? id - 1 : nrOfArtists);
            model.addAttribute("nextId", id < nrOfArtists ? id + 1 : 1);
        }
        return "artistdetails";
    }

    @GetMapping("/artistlist")
    public String artistList(Model model) {
        List<Artist> artists = artistRepository.findAllBy();
        model.addAttribute("artists", artists);
        model.addAttribute("nrOfArtists", artists.size());
        model.addAttribute("showFilters", false);
        return "artistlist";
    }

    @GetMapping("/artistlist/filter")
    public String artistListWithFilter(Model model,
                                       @RequestParam(required = false) String keyword) {
        logger.info(String.format("artistListWithFilter -- keyword=%s", keyword));
        List<Artist> artists = artistRepository.findByKeyword(keyword);

        model.addAttribute("artists", artists);
        model.addAttribute("nrOfArtists", artists.size());
        model.addAttribute("showFilters", true);
        model.addAttribute("keyword", keyword);
        return "artistlist";
    }
}
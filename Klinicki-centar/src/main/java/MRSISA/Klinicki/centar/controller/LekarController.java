package MRSISA.Klinicki.centar.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import MRSISA.Klinicki.centar.domain.Lekar;
import MRSISA.Klinicki.centar.service.LekarService;


@Controller

@RequestMapping("/lekari")
public class LekarController {

	@Autowired
	private LekarService lekarService;

	
	@GetMapping
	public ModelAndView getLekari() {
		Collection<Lekar> lekari = lekarService.findAll();
		return new ModelAndView("listaLekara", "lekari", lekari);
	}

	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String getNew(Model model) {
		model.addAttribute("lekar", new Lekar());
		return "createLekar";
	}

	
	@PostMapping(value = "/create")
	public ModelAndView createGreeting(@Valid Lekar lekar, BindingResult result) throws Exception {
		if (result.hasErrors()) {
			return new ModelAndView("createLekar", "formErrors", result.getAllErrors());
		}
		
		lekarService.create(lekar);
		return new ModelAndView("redirect:/lekari", "lekari", lekarService.findAll());
	}

	@RequestMapping("foo")
	public String foo() {
		throw new RuntimeException("Exception u kontroleru");
	}

}
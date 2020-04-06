package MRSISA.Klinicki.centar.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import MRSISA.Klinicki.centar.controller.CounterController;
import MRSISA.Klinicki.centar.domain.Counter;


@Service
public class CounterServiceImpl implements CounterService {

	private static Log log = LogFactory.getLog(CounterController.class);

	@Override
	public void incCounter(ModelMap model) {
		Counter counter = (Counter) model.get("counter");

		if (counter == null) {
			counter = new Counter();
		}

		model.addAttribute("counter", counter);

		counter.inc();
		log.info("Vrednost brojaca je " + counter.getCount());
	}

}

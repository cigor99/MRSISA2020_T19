package MRSISA.Klinicki.centar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.ConfirmationTokenPregled;
import MRSISA.Klinicki.centar.repository.ConfirmationTokenPregledRepository;

@Service
public class ConfirmationTokenPregledService {
	@Autowired
	private ConfirmationTokenPregledRepository confirTokenRepository;

	public ConfirmationTokenPregled save(ConfirmationTokenPregled token) {
		return confirTokenRepository.save(token);
	}

	public ConfirmationTokenPregled finByToken(String token) {
		return confirTokenRepository.findByConfirmationToken(token);
	}

	public void remove(Integer id) {
		confirTokenRepository.deleteById(id);
	}
}

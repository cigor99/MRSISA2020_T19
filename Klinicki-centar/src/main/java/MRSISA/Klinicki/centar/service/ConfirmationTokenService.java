package MRSISA.Klinicki.centar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MRSISA.Klinicki.centar.domain.ConfirmationToken;
import MRSISA.Klinicki.centar.repository.ConfirmationTokenRepository;

@Service
public class ConfirmationTokenService {
	@Autowired
	private ConfirmationTokenRepository confirTokenRepository;

	public ConfirmationToken save(ConfirmationToken token) {
		return confirTokenRepository.save(token);
	}

	public ConfirmationToken finByToken(String token) {
		return confirTokenRepository.findByConfirmationToken(token);
	}

	public void remove(Integer id) {
		confirTokenRepository.deleteById(id);
	}
}

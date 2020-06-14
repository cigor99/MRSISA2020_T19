package MRSISA.Klinicki.centar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import MRSISA.Klinicki.centar.domain.ConfirmationTokenPregled;

public interface ConfirmationTokenPregledRepository extends JpaRepository<ConfirmationTokenPregled, Integer>  {
	ConfirmationTokenPregled findByConfirmationToken(String confirmationToken);
}

package MRSISA.Klinicki.centar.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import MRSISA.Klinicki.centar.domain.Lekar;

@Repository
public class InMemoryLekarRepository implements LekarRepository {

	private static AtomicLong counter = new AtomicLong();

	private final ConcurrentMap<String, Lekar> lekari = new ConcurrentHashMap<String, Lekar>();

	@Override
	public Collection<Lekar> findAll() {
		return this.lekari.values();
	}

	@Override
	public Lekar create(Lekar lekar) {
		String email = lekar.getEmail();
		this.lekari.put(email, lekar);
		return lekar;
	}

	@Override
	public Lekar findOne(String email) {
		return this.lekari.get(email);
	}

}



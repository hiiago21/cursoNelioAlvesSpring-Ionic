package silva.Hiago.cursoms.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import silva.Hiago.cursoms.domain.Cliente;
import silva.Hiago.cursoms.repositories.ClienteRepository;
import silva.Hiago.cursoms.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteSevice {

	@Autowired
	private ClienteRepository repository;

	public Cliente buscar(@PathVariable Integer id) {

		Optional<Cliente> cliReturn = repository.findById(id);

		return cliReturn.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
}

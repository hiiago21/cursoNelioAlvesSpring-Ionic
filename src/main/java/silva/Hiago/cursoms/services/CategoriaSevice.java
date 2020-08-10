package silva.Hiago.cursoms.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import silva.Hiago.cursoms.domain.Categoria;
import silva.Hiago.cursoms.repositories.CategoriaRepository;
import silva.Hiago.cursoms.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaSevice {

	@Autowired
	private CategoriaRepository repository;

	public Categoria buscar(@PathVariable Integer id) {

		Optional<Categoria> catReturn = repository.findById(id);

		return catReturn.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
}

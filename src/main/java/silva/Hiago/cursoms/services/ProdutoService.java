package silva.Hiago.cursoms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import silva.Hiago.cursoms.domain.Categoria;
import silva.Hiago.cursoms.domain.Produto;
import silva.Hiago.cursoms.repositories.CategoriaRepository;
import silva.Hiago.cursoms.repositories.ProdutoRepository;
import silva.Hiago.cursoms.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private CategoriaRepository repositoryCategoria;

	public Produto buscar(@PathVariable Integer id) {

		Optional<Produto> pedReturn = repository.findById(id);

		return pedReturn.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> search (String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		List<Categoria> categorias = repositoryCategoria.findAllById(ids);
		
		return repository.search(nome, categorias, pageRequest);
				
	}
}

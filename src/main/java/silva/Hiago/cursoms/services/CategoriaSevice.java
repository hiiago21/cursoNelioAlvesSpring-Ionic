package silva.Hiago.cursoms.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import silva.Hiago.cursoms.domain.Categoria;
import silva.Hiago.cursoms.domain.Cliente;
import silva.Hiago.cursoms.dto.CategoriaDTO;
import silva.Hiago.cursoms.repositories.CategoriaRepository;
import silva.Hiago.cursoms.services.exceptions.DataIntegrityException;
import silva.Hiago.cursoms.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaSevice {

	@Autowired
	private CategoriaRepository repository;

	public Categoria find(Integer id) {

		Optional<Categoria> catReturn = repository.findById(id);

		return catReturn.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public List<Categoria> findAll() {

		List<Categoria> catReturn = repository.findAll();

		return catReturn;
	}
	
	public Categoria insert(Categoria obj) {
		return repository.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		
		Categoria newObj =find(obj.getId());
		updateData(newObj, obj);
		return repository.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que tenha pedidos!");
		}
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return repository.findAll(pageRequest);
	}
	
	public Categoria fromDto(CategoriaDTO catDto) {
		return new Categoria(catDto.getId(), catDto.getNome());
	}
	
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
}

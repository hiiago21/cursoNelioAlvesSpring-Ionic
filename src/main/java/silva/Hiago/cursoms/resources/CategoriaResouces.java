package silva.Hiago.cursoms.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import silva.Hiago.cursoms.domain.Categoria;
import silva.Hiago.cursoms.dto.CategoriaDTO;
import silva.Hiago.cursoms.services.CategoriaSevice;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResouces {
	
	@Autowired
	private CategoriaSevice service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id){
		
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping()
	public ResponseEntity<List<CategoriaDTO>> findAll(){
		
		List<Categoria> list = service.findAll();
		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		
		
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<CategoriaDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome")String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC")String direction){
		
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listDto = list.map(cat -> new CategoriaDTO(cat));
		
		 
		return ResponseEntity.ok().body(listDto);
	}
	
	
	@PostMapping
	public ResponseEntity<?> insert(@RequestBody @Valid CategoriaDTO objDTO){
		
		Categoria obj = service.fromDto(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> update(@RequestBody @Valid CategoriaDTO objDTO , @PathVariable Integer id ){
		Categoria obj = service.fromDto(objDTO);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}

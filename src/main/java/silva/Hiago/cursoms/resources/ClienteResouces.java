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

import silva.Hiago.cursoms.domain.Cliente;
import silva.Hiago.cursoms.dto.ClienteDTO;
import silva.Hiago.cursoms.dto.ClienteNewDto;
import silva.Hiago.cursoms.services.ClienteSevice;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResouces {
	
	@Autowired
	private ClienteSevice service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id){
		
		Cliente cli = service.find(id);
		return ResponseEntity.ok().body(cli);
	}
	
	@GetMapping()
	public ResponseEntity<List<ClienteDTO>> findAll(){
		
		List<Cliente> list = service.findAll();
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		
		
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome")String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC")String direction){
		
		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDto = list.map(cat -> new ClienteDTO(cat));
		
		 
		return ResponseEntity.ok().body(listDto);
	}
	
	
	@PostMapping
	public ResponseEntity<?> insert(@RequestBody @Valid ClienteNewDto objDTO){
		
		Cliente obj = service.fromDto(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
		objDto.setId(id);
		Cliente obj = service.fromDto(objDto);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id){
		
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}

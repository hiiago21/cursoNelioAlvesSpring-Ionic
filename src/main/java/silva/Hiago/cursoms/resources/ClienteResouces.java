package silva.Hiago.cursoms.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import silva.Hiago.cursoms.domain.Cliente;
import silva.Hiago.cursoms.services.ClienteSevice;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResouces {
	
	@Autowired
	private ClienteSevice service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id){
		
		Cliente cli = service.buscar(id);
		return ResponseEntity.ok().body(cli);
	}
}

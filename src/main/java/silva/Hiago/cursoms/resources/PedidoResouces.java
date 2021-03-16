package silva.Hiago.cursoms.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import silva.Hiago.cursoms.domain.Pedido;
import silva.Hiago.cursoms.services.PedidoSevice;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResouces {
	
	@Autowired
	private PedidoSevice service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> find(@PathVariable Integer id){
		
		Pedido ped = service.buscar(id);
		return ResponseEntity.ok().body(ped);
	}
	
	@PostMapping
	public ResponseEntity<?> insert(@RequestBody @Valid Pedido obj){
		
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}

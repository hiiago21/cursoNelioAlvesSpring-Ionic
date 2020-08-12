package silva.Hiago.cursoms.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import silva.Hiago.cursoms.domain.Pedido;
import silva.Hiago.cursoms.repositories.PedidoRepository;
import silva.Hiago.cursoms.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoSevice {

	@Autowired
	private PedidoRepository repository;

	public Pedido buscar(@PathVariable Integer id) {

		Optional<Pedido> pedReturn = repository.findById(id);

		return pedReturn.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}

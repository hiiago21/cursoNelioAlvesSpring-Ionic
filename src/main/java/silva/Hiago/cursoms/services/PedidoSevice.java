package silva.Hiago.cursoms.services;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import silva.Hiago.cursoms.domain.ItemPedido;
import silva.Hiago.cursoms.domain.PagamentoComBoleto;
import silva.Hiago.cursoms.domain.Pedido;
import silva.Hiago.cursoms.domain.enums.EstadoPagamento;
import silva.Hiago.cursoms.repositories.ItemPedidoRepository;
import silva.Hiago.cursoms.repositories.PagamentoRepository;
import silva.Hiago.cursoms.repositories.PedidoRepository;
import silva.Hiago.cursoms.repositories.ProdutoRepository;
import silva.Hiago.cursoms.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoSevice {

	@Autowired
	private PedidoRepository repository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public Pedido buscar(@PathVariable Integer id) {

		Optional<Pedido> pedReturn = repository.findById(id);

		return pedReturn.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	public  Pedido insert(@Valid Pedido obj) {
		
		obj.setInstante(new Date());
		obj.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto)obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = repository.save(obj);
		
		pagamentoRepository.save(obj.getPagamento());
		
		for(ItemPedido ip : obj.getItens()) {
			
			ip.setDesconto(0.0);
			ip.setPreco(produtoRepository.findById(ip.getProduto().getId()).get().getPreco());
			ip.setPedido(obj);
		}
		
		 itemPedidoRepository.saveAll(obj.getItens());
		 
		return obj;
	}
}

package silva.Hiago.cursoms;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import silva.Hiago.cursoms.domain.Categoria;
import silva.Hiago.cursoms.domain.Cidade;
import silva.Hiago.cursoms.domain.Cliente;
import silva.Hiago.cursoms.domain.Endereco;
import silva.Hiago.cursoms.domain.Estado;
import silva.Hiago.cursoms.domain.ItemPedido;
import silva.Hiago.cursoms.domain.Pagamento;
import silva.Hiago.cursoms.domain.PagamentoComBoleto;
import silva.Hiago.cursoms.domain.PagamentoComCartao;
import silva.Hiago.cursoms.domain.Pedido;
import silva.Hiago.cursoms.domain.Produto;
import silva.Hiago.cursoms.domain.enums.EstadoPagamento;
import silva.Hiago.cursoms.domain.enums.TipoCliente;
import silva.Hiago.cursoms.repositories.CategoriaRepository;
import silva.Hiago.cursoms.repositories.CidadeRepository;
import silva.Hiago.cursoms.repositories.ClienteRepository;
import silva.Hiago.cursoms.repositories.EnderecoRepository;
import silva.Hiago.cursoms.repositories.EstadoRepository;
import silva.Hiago.cursoms.repositories.ItemPedidoRepository;
import silva.Hiago.cursoms.repositories.PagamentoRepository;
import silva.Hiago.cursoms.repositories.PedidoRepository;
import silva.Hiago.cursoms.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository catRep;
	
	@Autowired
	private ProdutoRepository prodRep;
	
	@Autowired
	private EstadoRepository estRep;
	
	@Autowired
	private CidadeRepository cidRep;
	
	@Autowired
	private ClienteRepository cliRep;
	
	@Autowired
	private EnderecoRepository endRep;
	
	@Autowired
	private PagamentoRepository pagRep;
	
	@Autowired
	private PedidoRepository pedRep;
	
	@Autowired
	private ItemPedidoRepository itpRep;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		catRep.saveAll(Arrays.asList(cat1, cat2));
		prodRep.saveAll(Arrays.asList(p1, p2,p3));
		
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estRep.saveAll(Arrays.asList(est1, est2));
		cidRep.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "33366688822", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("2222224455", "98885557758"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apt 203", "Jardim", "88877555520", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38875540211", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		cliRep.saveAll(Arrays.asList(cli1));
		endRep.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido (null, sdf.parse("30/09/2017 10:32"), cli1, e1); 
		Pedido ped2 = new Pedido (null, sdf.parse("10/10/2017 19:35"), cli1, e2); 
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedRep.saveAll(Arrays.asList(ped1, ped2));
		pagRep.saveAll(Arrays.asList(pagto1, pagto2));
		
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.0, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itpRep.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}

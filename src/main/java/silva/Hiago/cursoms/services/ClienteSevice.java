package silva.Hiago.cursoms.services;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import silva.Hiago.cursoms.domain.Cidade;
import silva.Hiago.cursoms.domain.Cliente;
import silva.Hiago.cursoms.domain.Endereco;
import silva.Hiago.cursoms.domain.enums.TipoCliente;
import silva.Hiago.cursoms.dto.ClienteDTO;
import silva.Hiago.cursoms.dto.ClienteNewDto;
import silva.Hiago.cursoms.repositories.ClienteRepository;
import silva.Hiago.cursoms.repositories.EnderecoRepository;
import silva.Hiago.cursoms.services.exceptions.DataIntegrityException;
import silva.Hiago.cursoms.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteSevice {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private EnderecoRepository endRepository;

	public Cliente find(@PathVariable Integer id) {

		Optional<Cliente> cliReturn = repository.findById(id);

		return cliReturn.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public List<Cliente> findAll() {

		List<Cliente> cliReturn = repository.findAll();

		return cliReturn;
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj = repository.save(obj);
		endRepository.saveAll(obj.getEnderecos());
		
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repository.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que tenha produtos!");
		}
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		return repository.findAll(pageRequest);
	}

	
	public Cliente fromDto(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	public Cliente fromDto(ClienteNewDto objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipoCliente()));
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, new Cidade(objDto.getCidadeId(), null, null));
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}

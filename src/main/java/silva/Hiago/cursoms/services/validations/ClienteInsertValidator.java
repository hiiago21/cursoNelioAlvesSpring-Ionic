package silva.Hiago.cursoms.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import silva.Hiago.cursoms.domain.Cliente;
import silva.Hiago.cursoms.domain.enums.TipoCliente;
import silva.Hiago.cursoms.dto.ClienteNewDto;
import silva.Hiago.cursoms.repositories.ClienteRepository;
import silva.Hiago.cursoms.resources.exception.FieldMessage;
import silva.Hiago.cursoms.services.validations.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDto> {
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDto objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if(objDto.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCod()) && BR.isValidCPF(objDto.getCpfOuCnpj())) {
			
			list.add(new FieldMessage("CpfouCnpj", "CPF inválido"));
		}
		if(objDto.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCod()) && BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			
			list.add(new FieldMessage("CpfouCnpj", "CNPJ inválido"));
		}
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		
		if(aux != null) {
			list.add(new FieldMessage("email", "Email já existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}

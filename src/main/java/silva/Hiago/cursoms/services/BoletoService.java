package silva.Hiago.cursoms.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import silva.Hiago.cursoms.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagamentoComBoleto, Date instanteDoPedido) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagamentoComBoleto.setDataVencimento(cal.getTime());
	}
}

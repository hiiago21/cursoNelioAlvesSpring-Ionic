package silva.Hiago.cursoms.domain.enums;

public enum EstadoPagamento {

	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	private int cod;
	private String descicao;

	private EstadoPagamento(int cod, String descicao) {
		this.cod = cod;
		this.descicao = descicao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescicao() {
		return descicao;
	}
	
	public static EstadoPagamento toEnum(Integer cod) {
		
		if(cod.equals(null)) {
			return null;
		}
		
		for(EstadoPagamento tc: EstadoPagamento.values()) {
			if(cod.equals(tc.getCod())) {
				return tc;
			}
		}
	
		throw new IllegalArgumentException("Id inválido no sistema:" + cod);
	}
}

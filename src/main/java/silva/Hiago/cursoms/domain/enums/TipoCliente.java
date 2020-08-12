package silva.Hiago.cursoms.domain.enums;

public enum TipoCliente {

	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private int cod;
	private String descicao;

	private TipoCliente(int cod, String descicao) {
		this.cod = cod;
		this.descicao = descicao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescicao() {
		return descicao;
	}
	
	public static TipoCliente toEnum(Integer cod) {
		
		if(cod.equals(null)) {
			return null;
		}
		
		for(TipoCliente tc: TipoCliente.values()) {
			if(cod.equals(tc.getCod())) {
				return tc;
			}
		}
	
		throw new IllegalArgumentException("Id inválido no sistema:" + cod);
	}
}

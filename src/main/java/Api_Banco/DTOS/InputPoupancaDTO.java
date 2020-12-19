package Api_Banco.DTOS;

public class InputPoupancaDTO {
	
	private String id;
	private double deposito;
	public InputPoupancaDTO(String id, double deposito) {
		super();
		this.id = id;
		this.deposito = deposito;
	}
	
	public InputPoupancaDTO() {
		super();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getDeposito() {
		return deposito;
	}
	public void setDeposito(double deposito) {
		this.deposito = deposito;
	}
	
	
	

}

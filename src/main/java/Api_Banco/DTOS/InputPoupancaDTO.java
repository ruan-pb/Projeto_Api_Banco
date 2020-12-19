package Api_Banco.DTOS;

public class InputPoupancaDTO {
	
	private String Id; 
	private double deposito;
	
	
	public InputPoupancaDTO(String id, double deposito) {
		super();
		this.Id = id;
		this.deposito = deposito;
	}
	
	public InputPoupancaDTO() {
		super();
	}

	public String getId() {
		return Id;
	}
	public void setId(String id) {
		this.Id = id;
	}
	public double getDeposito() {
		return deposito;
	}
	public void setDeposito(double deposito) {
		this.deposito = deposito;
	}
	
	
	

}

package Api_Banco.DTOS;

public class InputEmprestimo {
	private String Id; 
	private double Valor; 
	private int parcelas;
	
	
	
	
	public InputEmprestimo() {
		
	}
	
	
	
	public InputEmprestimo(String id, double valor, int quantidadeDeParcelas) {
		super();
		Id = id;
		Valor = valor;
		parcelas = quantidadeDeParcelas;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public double getValor() {
		return Valor;
	}
	public void setValor(double valor) {
		Valor = valor;
	}
	public int getQuantidadeDeParcelas() {
		return parcelas;
	}
	public void setQuantidadeDeParcelas(int quantidadeDeParcelas) {
		parcelas = quantidadeDeParcelas;
	}
	
	
	

}

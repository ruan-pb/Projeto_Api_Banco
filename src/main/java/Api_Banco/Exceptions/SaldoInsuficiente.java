package Api_Banco.Exceptions;

public class SaldoInsuficiente extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	
	public SaldoInsuficiente(String msg) {
		super(msg);
	}
	public SaldoInsuficiente() {}
	

}

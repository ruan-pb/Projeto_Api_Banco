package Api_Banco.Exceptions;

public class ContaInvalida extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ContaInvalida() {}
	
	public ContaInvalida(String msg) {
		super(msg);
	}
	
	

}

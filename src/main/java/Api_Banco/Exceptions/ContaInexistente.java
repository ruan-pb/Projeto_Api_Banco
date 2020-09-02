package Api_Banco.Exceptions;

public class ContaInexistente extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ContaInexistente(String msg) {
		super(msg);
	}
	public ContaInexistente() {
	}

}

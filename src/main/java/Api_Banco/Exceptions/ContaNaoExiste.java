package Api_Banco.Exceptions;

public class ContaNaoExiste extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ContaNaoExiste(String msg) {
		super(msg);
	}
	public ContaNaoExiste() {
	}

}

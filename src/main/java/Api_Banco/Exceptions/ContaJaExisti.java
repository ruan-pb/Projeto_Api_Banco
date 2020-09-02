package Api_Banco.Exceptions;

public class ContaJaExisti extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ContaJaExisti(String msg) {
		super(msg);
	}
	public ContaJaExisti() {}
	

}

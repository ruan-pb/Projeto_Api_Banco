package Api_Banco.Exceptions;

public class LimiteDeEmprestimo extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LimiteDeEmprestimo(String msg) {
		super(msg);
		
	}
	public LimiteDeEmprestimo() {
		
	}
	
	

}

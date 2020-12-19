package Api_Banco.Exceptions;

public class LimiteInsuficiente extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public LimiteInsuficiente(String msg) {
		super(msg);
	}
	public LimiteInsuficiente() {
		super();
	}
	

}



public class ExcepcionFTP extends Exception {

	private static final long serialVersionUID = -3203972661982848736L;

	public ExcepcionFTP() {
	}

	public ExcepcionFTP(String tipoError, Exception e) {
		super("Error ["+tipoError+"]: "+e.getMessage());
		System.err.println("Error ["+tipoError+"]: "+e.getMessage());
		e.printStackTrace();
	}

}

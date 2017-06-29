/**
 * @author xe21783
 * donedonde
 */
public class DatosConexion {
	private String _usuario, _password, _servidor;
	private int _puerto;

	public DatosConexion(String usuario, String password, String servidor, int puerto) {
		super();
		this._usuario = usuario;
		this._password = password;
		this._servidor = servidor;
		this._puerto = puerto;
	}

	public String getUsuario() {
		return _usuario;
	}

	public String getPassword() {
		return _password;
	}

	public String getServidor() {
		return _servidor;
	}

	public int getPuerto() {
		return _puerto;
	}
}

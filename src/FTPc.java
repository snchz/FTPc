import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;//commons-net-3.6.jar
import org.apache.commons.net.ftp.FTPFile;


/**
 * @author xe21783
 *
 */
public class FTPc {
	FTPClient cliente;

	/**
	 * @throws ExcepcionFTP 
	 * 
	 */
	public FTPc(DatosConexion dc) throws ExcepcionFTP {
		cliente = new FTPClient();
		try {
			cliente.connect(dc.getServidor(),dc.getPuerto());
			boolean conectado=cliente.login(dc.getUsuario(),dc.getPassword());
			
			if (!conectado)
				throw new ExcepcionFTP("NoConectado", new Exception("Posiblemente credenciales incorrectas"));

			cliente.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
			cliente.setFileTransferMode(FTP.BINARY_FILE_TYPE);
			cliente.enterLocalPassiveMode();
		} catch (SocketException e) {
			throw new ExcepcionFTP("SocketException",e);
		} catch (IOException e) {
			throw new ExcepcionFTP("IOException",e);
		}
	}
	

	public boolean descargarFichero(String rutaNombreFicheroLocal, String rutaNombreFicheroFTP) throws ExcepcionFTP{
		try {
			OutputStream os=new FileOutputStream(rutaNombreFicheroLocal);
			cliente.retrieveFile(rutaNombreFicheroFTP, os);	// Guardando el archivo en el servidor
			os.close();
			return true;
		} catch (IOException e) {
			throw new ExcepcionFTP("IOException",e);
		}
	}
	
	public boolean subirFichero(String rutaNombreFicheroLocal, String rutaNombreFicheroFTP) throws ExcepcionFTP{
		try {
			InputStream is=new FileInputStream(rutaNombreFicheroLocal);
			cliente.storeFile(rutaNombreFicheroFTP, is);	// Guardando el archivo en el servidor
			is.close();
			return true;
		} catch (IOException e) {
			throw new ExcepcionFTP("IOException",e);
		}
	}
	
	public void desconectar() throws ExcepcionFTP{
		try {
			cliente.logout();		//Cerrar sesion
			cliente.disconnect();	//Desconectar del servidor
		} catch (IOException e) {
			throw new ExcepcionFTP("SocketException",e);
		}
	}
	
	public List<String> obtenerLista(String directorioRaizCompleto, int numeroNiveles, List<String> lista) throws ExcepcionFTP{
		if (numeroNiveles>0)
			try {
				for(FTPFile f:cliente.listFiles(directorioRaizCompleto)){
					if (f.isFile())
						lista.add("[F]"+directorioRaizCompleto+f.getName());
					else if (f.isDirectory()&& f.getName().equals("."))	//Es Directorio
						lista.add("[D]"+directorioRaizCompleto);
					else if (f.isDirectory()&& !f.getName().equals(".")&& !f.getName().equals("..")){	//Es Directorio
						String dir=directorioRaizCompleto+f.getName()+"/";
						lista=obtenerLista(dir,numeroNiveles-1,lista);
					}
				}
			} catch (IOException e) {
				throw new ExcepcionFTP("IOException",e);
			}
		return lista;
	}

	

}

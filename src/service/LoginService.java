package service;

import assemblers.Assembler;
import dao.DBManager;
import dto.DTOUsuario;
import gateways.ILoginGw;
import jdo.Usuario;

public class LoginService {
	
private static LoginService instance;
	
	private LoginService() { }
	
	public static LoginService getInstance() {
		if (instance == null) {
			instance = new LoginService();
		}
		
		return instance;
	}

	public DTOUsuario login(String email, String password, String authService) {
		DTOUsuario usuario = null;
		
		ILoginGw login = Factory.getInstance().createLoginGw(authService);
		if(login.autenticar(email, password)) {
			usuario = Assembler.getInstance().assemble(DBManager.getInstance().getUser(email));
		}
		return usuario;
	}

}

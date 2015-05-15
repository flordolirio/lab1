package business.control;

import infra.InfraException;
import infra.UserFile;

import java.util.List;
import java.util.logging.Level;

import business.model.User;
import business.util.LoginInvalidException;
import business.util.PasswordInvalidException;
import business.util.UserValidador;


public class UserManager {
	
	private List<User> users;
	UserFile userFile;
	
	public UserManager() {		
		
		userFile = new UserFile();
		try {
			users = userFile.loadUsers();
		} catch (InfraException e) {
			UserFile.logger.log(Level.FINE, e.getMessage());
		} 
		

	}
	
	public void addUser(String name, String pass) throws LoginInvalidException, PasswordInvalidException {
		
		UserValidador.validateName(name);
		UserValidador.validatePassword(pass);
		
		users.add(new User(name,pass));
		userFile.saveUsers(users);
		
	}
	
	public List<User> getAllClients() throws InfraException {
			try {
			List<User> mylist = userFile.loadUsers();
			return mylist;

			} catch (NullPointerException ex){
	            UserFile.logger.severe(ex.getMessage());
	            throw new InfraException("Erro de persistencia, contacte o admin ou tente mais tarde");
	           
	        }
	}
	

}

package infra;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import business.model.User;


public class UserFile {
	
	public static Logger logger = Logger.getLogger(UserFile.class.getName());

	public UserFile() {
		
		try {
            
            Handler hdConsole = new ConsoleHandler();
            Handler hdArquivo = new FileHandler("relatorioLog.txt");

            hdConsole.setLevel(Level.OFF);
            hdArquivo.setLevel(Level.OFF);

            UserFile.logger.addHandler(hdConsole);
            UserFile.logger.addHandler(hdArquivo);

            UserFile.logger.setUseParentHandlers(false);


        } catch (IOException ex) {
            logger.severe("ocorreu um erro no arquivo durante a execu��o do programa");
        }
	}
    public void saveUsers(List<User> users){
        File file = new File("user.bin");
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(users);
            out.close();
            
        } catch (FileNotFoundException ex) {
        	ex.printStackTrace();
        } catch (IOException ex){
        	ex.printStackTrace();

        }
    }
    
    public List<User> loadUsers() throws InfraException{
    	List<User> users = new ArrayList<User>();
    	File file = new File("user.bin");
        ObjectInputStream objInput = null;
        InputStream in = null;
        if(!file.exists()){
        	saveUsers(users);
        }
        try {
            in = new FileInputStream(file);
            //Recupera a lista
            objInput = new ObjectInputStream(in);
            users = (List<User>) objInput.readObject();
            return users;
        
        } catch (NullPointerException ex){
            logger.config(ex.getMessage());
            throw new InfraException("Erro de persistencia, contacte o admin ou tente mais tarde");
           
        } catch (IOException ex){
            logger.config(ex.getMessage());
            throw new InfraException("Erro de persistencia, contacte o admin ou tente mais tarde");
         } catch (ClassNotFoundException ex) {
            logger.config(ex.getMessage());
            throw new InfraException("Erro de persistencia, contacte o admin ou tente mais tarde");      
        }
        finally {
        	try {
				objInput.close();
				in.close();
			} catch (IOException e) {
				logger.severe(e.getMessage());
				
			}
  			
        }
    }
}

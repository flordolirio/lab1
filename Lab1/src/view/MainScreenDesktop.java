package view;

import infra.InfraException;

import java.util.Iterator;

import javax.swing.JOptionPane;

import business.control.UserManager;
import business.model.User;
import business.util.LoginInvalidException;
import business.util.PasswordInvalidException;

public class MainScreenDesktop {
	
	UserManager userManager = new UserManager();
	
	public static void main (String[] args) {
		showMenu();
	}
	
	public static void showMenu() {
		String option = JOptionPane.showInputDialog("Bem vindo!\n1-Cadastrar Usuario;\n2-Listar Usuarios;\n3-Excluir Usuario;\n4-Sair;"
				+ "\nInforme a opcão:");		
		MainScreenDesktop main = new MainScreenDesktop();

		main.readUserInput(option);
	}
	
	public void readUserInput(String option) {
		int choice = Integer.parseInt(option);
		boolean checkedLogin = false;
		boolean checkedPassword = false;
		switch (choice) {
		case 1:
			while (true) {
				String name = "";
				String pass = "";

				if (!checkedLogin) {
					name = JOptionPane.showInputDialog("Nome do usuario:");					
				}
				if (!checkedPassword) {
					pass = JOptionPane.showInputDialog("Senha do usuario:");					
				}

				try {
					this.userManager.addUser(name, pass);
					JOptionPane.showMessageDialog(null, "Usuario adicionado com sucesso!");
					break;
				} catch (LoginInvalidException e) {
					JOptionPane.showMessageDialog(null, e.getMessage() );
					checkedLogin = false;
					checkedPassword = true;
				} catch (PasswordInvalidException e) {
					JOptionPane.showMessageDialog(null, e.getMessage() );
					checkedLogin = true;
					checkedPassword = false;
				}
				
			}
			showMenu();
			break;
		
		case 2:
			String usuarios = "";
			Iterator<User> users;
			try {
				users = this.userManager.getAllClients().iterator();
				while (users.hasNext()) {
					User user = users.next();
					usuarios = usuarios + "[ Login: " + user.getLogin() + " || Senha: " + user.getSenha() + " ]" + "\n";
				}
				JOptionPane.showMessageDialog(null, usuarios );
			} catch (InfraException e) {
				JOptionPane.showMessageDialog(null, e.getMessage() );
			}
			
			
			showMenu();
			break;
		case 3:
			break;
	
		}
	}
}
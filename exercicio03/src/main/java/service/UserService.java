package service;

import java.util.Scanner;
import java.io.InputStream;
import java.util.List;
import dao.UsuarioDAO;
import model.Usuario;
import spark.Request;
import spark.Response;


public class UserService {

	private UsuarioDAO UsuarioDAO = new UsuarioDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_CODIGO = 1;
	private final int FORM_ORDERBY_LOGIN = 2;
	private final int FORM_ORDERBY_SEXO = 3;
	private final int FORM_ORDERBY_IDADE = 4;
	
	
	public UserService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Usuario(), FORM_ORDERBY_SEXO);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Usuario(), orderBy);
	}

	
	public void makeForm(int tipo, Usuario usuario, int orderBy) {
		form = "";
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("form.html")) {
    		if (inputStream != null) {
        		Scanner entrada = new Scanner(inputStream, "UTF-8");
        		while (entrada.hasNext()) {
            		form += (entrada.nextLine() + "\n");
        		}
        		entrada.close();
    		} else {
        		System.out.println("Arquivo form.html não encontrado nos recursos.");
    		}
		} catch (Exception e) {
    		e.printStackTrace();
		}
		
		String novoUsuario = "";
		if(tipo != FORM_INSERT) {
			novoUsuario += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			novoUsuario += "\t\t<tr>";
			novoUsuario += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/usuario/list/1\">Novo Usuario</a></b></font></td>";
			novoUsuario += "\t\t</tr>";
			novoUsuario += "\t</table>";
			novoUsuario += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/usuario/";
			String name, login, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Usuario";
				login = "Gabriel, João, ...";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + usuario.getCodigo();
				name = "Atualizar Usuario (getCodigo " + usuario.getCodigo() + ")";
				login = usuario.getLogin();
				buttonLabel = "Atualizar";
			}
			novoUsuario += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			novoUsuario += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			novoUsuario += "\t\t<tr>";
			novoUsuario += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			novoUsuario += "\t\t</tr>";
			novoUsuario += "\t\t<tr>";
			novoUsuario += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			novoUsuario += "\t\t</tr>";
			novoUsuario += "\t\t<tr>";
			novoUsuario += "\t\t\t<td>&nbsp;Login: <input class=\"input--register\" type=\"text\" name=\"login\" value=\""+ login +"\"></td>";
			novoUsuario += "\t\t\t<td>Senha: <input class=\"input--register\" type=\"text\" name=\"senha\" value=\""+ usuario.getSenha() +"\"></td>";
			novoUsuario += "\t\t\t<td>Sexo: <input class=\"input--register\" type=\"text\" name=\"sexo\" value=\""+ usuario.getSexo() +"\"></td>";
			novoUsuario += "\t\t\t<td>Idade: <input class=\"input--register\"type=\"text\" name=\"idade\" value=\"" + usuario.getIdade() + "\"></td>";
			novoUsuario += "\t\t</tr>";
			novoUsuario += "\t\t<tr>";
			novoUsuario += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			novoUsuario += "\t\t</tr>";
			novoUsuario += "\t</table>";
			novoUsuario += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			novoUsuario += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			novoUsuario += "\t\t<tr>";
			novoUsuario += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Usuario (Codigo " + usuario.getCodigo() + ")</b></font></td>";
			novoUsuario += "\t\t</tr>";
			novoUsuario += "\t\t<tr>";
			novoUsuario += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			novoUsuario += "\t\t</tr>";
			novoUsuario += "\t\t<tr>";
			novoUsuario += "\t\t\t<td>&nbsp;Login: "+ usuario.getLogin() +"</td>";
			novoUsuario += "\t\t\t<td>Senha: "+ usuario.getSenha() +"</td>";
			novoUsuario += "\t\t\t<td>Sexo: "+ usuario.getSexo() +"</td>";
			novoUsuario += "\t\t\t<td>&nbsp;Idade: "+ usuario.getIdade() + "</td>";
			novoUsuario += "\t\t</tr>";
			novoUsuario += "\t\t<tr>";
			novoUsuario += "\t\t\t<td>&nbsp;</td>";
			novoUsuario += "\t\t</tr>";
			novoUsuario += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<novo-Usuario>", novoUsuario);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Lista Usuarios</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
				"\t<td><a href=\"/usuario/list/" + FORM_ORDERBY_CODIGO + "\"><b>Codigo</b></a></td>\n" +
        		"\t<td><a href=\"/usuario/list/" + FORM_ORDERBY_LOGIN + "\"><b>Login</b></a></td>\n" +
				"\t<td><b>Senha</b></td>\n" +
        		"\t<td><a href=\"/usuario/list/" + FORM_ORDERBY_SEXO + "\"><b>Sexo</b></a></td>\n" +
				"\t<td><a href=\"/usuario/list/" + FORM_ORDERBY_IDADE + "\"><b>Idade</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Usuario> usuarios;
		if (orderBy == FORM_ORDERBY_CODIGO) {               usuarios = UsuarioDAO.getOrderByCodigo();
		} else if (orderBy == FORM_ORDERBY_LOGIN) {			usuarios = UsuarioDAO.getOrderByLogin();
		} else if (orderBy == FORM_ORDERBY_SEXO) {			usuarios = UsuarioDAO.getOrderBySexo();
		} else if (orderBy == FORM_ORDERBY_IDADE) {			usuarios = UsuarioDAO.getOrderByIdade();
		} else {											usuarios = UsuarioDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Usuario u : usuarios) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + u.getCodigo() + "</td>\n" +
					  "\t<td>" + u.getLogin() + "</td>\n" +
            		  "\t<td>" + u.getSenha() + "</td>\n" +
            		  "\t<td>" + u.getSexo() + "</td>\n" +
					  "\t<td>" + u.getIdade() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/usuario/" + u.getCodigo() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/usuario/update/" + u.getCodigo() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteUsuario('" + u.getCodigo() + "', '" + u.getLogin() + "', '" + u.getSenha() + "', '" + u.getSexo() + "','" + u.getIdade() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-USUARIO>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String login = request.queryParams("login");
		String senha = request.queryParams("senha");
		char sexo = request.queryParams("sexo").charAt(0);
		int idade = Integer.parseInt(request.queryParams("idade"));
		
		String resp = "";
		
		Usuario usuario = new Usuario(-1, login, senha, sexo, idade);
		
		if(UsuarioDAO.insert(usuario) == true) {
            resp = "Usuario (" + login + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Usuario (" + login + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int codigo = Integer.parseInt(request.params(":codigo"));		
		Usuario usuario = (Usuario) UsuarioDAO.get(codigo);
		
		if (usuario != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, usuario, FORM_ORDERBY_SEXO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Usuario " + codigo + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int codigo = Integer.parseInt(request.params(":codigo"));		
		Usuario usuario = (Usuario) UsuarioDAO.get(codigo);
		
		if (usuario != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, usuario, FORM_ORDERBY_SEXO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Usuario " + codigo + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int codigo = Integer.parseInt(request.params(":codigo"));
		Usuario usuario = UsuarioDAO.get(codigo);
        String resp = "";       

        if (usuario != null) {
        	usuario.setLogin(request.queryParams("login"));
        	usuario.setSenha(request.queryParams("senha"));
        	usuario.setSexo(request.queryParams("sexo").charAt(0));
        	usuario.setIdade(Integer.parseInt(request.queryParams("idade")));
        	UsuarioDAO.update(usuario);
        	response.status(200); // success
            resp = "Usuario (CODIGO " + usuario.getCodigo() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Usuario (CODIGO \" + usuario.getCodigo() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int codigo = Integer.parseInt(request.params(":codigo"));
        Usuario usuario = UsuarioDAO.get(codigo);
        String resp = "";       

        if (usuario != null) {
            UsuarioDAO.delete(codigo);
            response.status(200); // success
            resp = "Usuario (" + codigo + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Usuario (" + codigo + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}
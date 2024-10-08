package model;

public class Usuario {
	private int codigo;
	private String login;
	private String senha;
	private char sexo;
	private int idade;
	
	public Usuario() {
		codigo = -1;
		login = "";
		senha = "";
		sexo = '*';
		idade = 0;
	}
	
	public Usuario(int codigo, String login, String senha, char sexo, int idade) {
		setCodigo(codigo);
		setLogin(login);
		setSenha(senha);
		setSexo(sexo);
		setIdade(idade);
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	@Override
	public String toString() {
		return "Usuario [codigo=" + codigo + ", login=" + login + ", senha=" + senha + ", sexo=" + sexo + ", idade=" + idade + "]";
	}	
}

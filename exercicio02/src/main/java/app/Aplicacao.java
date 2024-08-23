package app;

import java.util.List;
import java.util.Scanner;

import dao.DAO;
import dao.UsuarioDAO;
import model.Usuario;

public class Aplicacao {

	public static void menu () {

		UsuarioDAO usuarioDAO = new UsuarioDAO();

		Scanner sc = new Scanner(System.in);

        int opcao = 0;
        int opcaoListar = 0;
		
        do {
            System.out.println("1 - Listar usuário(s)");
            System.out.println("2 - Inserir usuário");
            System.out.println("3 - Excluir usuário");
            System.out.println("4 - Sair");
            System.out.print("Digite a opção desejada: ");
            opcao = sc.nextInt();
			sc.nextLine();

            switch (opcao) {
                case 1: //listar
                    do {
                        System.out.println("1 - Listar todos os usuários");
                        System.out.println("2 - Listar usuários do sexo masculino");
                        System.out.println("3 - Listar usuários do sexo feminino");
						System.out.println("4 - Listar usuários maiores de idade");
						System.out.println("5 - Listar usuários menores de idade");
                        System.out.println("6 - Sair");
                        System.out.print("Digite a opção desejada: ");
                        opcaoListar = sc.nextInt();
						
                        switch (opcaoListar) {
                            case 1: //listar todos
                                System.out.println("Listar todos os usuários");
								List<Usuario> usuarios = usuarioDAO.getOrderByCodigo();
								for (Usuario u: usuarios) {
									System.out.println(u.toString());
								}
                                break;
                            case 2: //listar usuários do sexo masculino
                                System.out.println("Listar usuários do sexo masculino");
								usuarios = usuarioDAO.getSexoMasculino();
								for (Usuario u: usuarios) {
									System.out.println(u.toString());
								}
                                break;
                            case 3: //listar usuários do sexo feminino
                                System.out.println("Listar usuários do sexo feminino");
								usuarios = usuarioDAO.getSexoFeminino();
								for (Usuario u: usuarios) {
									System.out.println(u.toString());
								}
                                break;
							case 4: //listar usuários maiores de idade 
								System.out.println("Listar usuários maiores de idade");
								usuarios = usuarioDAO.getMaiores18();
								for (Usuario u: usuarios) {
									System.out.println(u.toString());
								}
								break;
							case 5: //listar usuários menores de idade
								System.out.println("Listar usuários menores de idade");
								usuarios = usuarioDAO.getMenores18();
								for (Usuario u: usuarios) {
									System.out.println(u.toString());
								}
								break;
                            case 6: //sair do listar
                                System.out.println("Sair");
                                opcaoListar = 6;
                                break;
                            default:
                                System.out.println("Opção inválida");
                                break;
                        }
                    } while (opcaoListar != 6);
                    break;
                case 2: //inserir
                    System.out.println("Inserir usuário");
					System.out.print("Digite o código do usuário: ");
                    int codigo = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Digite o login do usuário: ");
                    String login = sc.nextLine();

                    System.out.print("Digite a senha do usuário: ");
                    String senha = sc.nextLine();

                    System.out.print("Digite o sexo do usuário (M/F): ");
                    char sexo = sc.nextLine().charAt(0);

                    System.out.print("Digite a idade do usuário: ");
                    int idade = sc.nextInt();

                    Usuario usuario = new Usuario(codigo, login, senha, sexo, idade);
                    if(usuarioDAO.insert(usuario)) {
                        System.out.println("Inserção com sucesso -> " + usuario.toString());
                    } else {
                        System.out.println("Erro ao inserir usuário.");
                    }
                    break;
                case 3: //excluir
                    System.out.println("Excluir usuário");
					System.out.print("Digite o código do usuário: ");
					codigo = sc.nextInt();
					usuarioDAO.delete(codigo);
					System.out.println("Usuário excluído com sucesso.");
                    break;
                case 4: //sair
                    System.out.println("Sair");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        } while (opcao != 4);

		sc.close();
	}
	
	public static void main(String[] args) throws Exception {	
		menu();

	}
}
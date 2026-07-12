import emprestimos.Emprestimo;
import excecoes.LivroIndisponivelException;
import excecoes.LivroNaoEncontradoException;
import excecoes.PessoaNaoEncontradaException;
import java.util.ArrayList;
import java.util.Scanner;
import livros.Livro;
import pessoas.Pessoa;

// Sistema de Biblioteca
public class biblioteca {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ArrayList<Livro> livros = new ArrayList<>();
        ArrayList<Pessoa> pessoas = new ArrayList<>();
        ArrayList<Emprestimo> emprestimos = new ArrayList<>();

        servicos serv1 = new servicos(scanner);
        int id_livro = 1;
        int id_pessoa = 1;
        int id;
        boolean parar = false;


        // Opções do Sistema
        do {
            int opcao = serv1.opcao();

            switch (opcao) {
                // Se opção for 1, chama o método para adicionar livro
                case 1:
                    livros.add(serv1.adicionarlivro(id_livro));
                    id_livro++;
                    break;

// ------------------------------------------------------------------------------------------------------------------------------------------------------------------

                // Se opção for 2, chama o método para listar livros
                case 2:
                    serv1.listarLivro(livros);
                    break;

// ------------------------------------------------------------------------------------------------------------------------------------------------------------------
                    
                // Se opção for 3, chama o método para emprestar livro
                case 3:
                    try {
                        System.out.println("Digite o ID do livro que deseja emprestar: ");
                        int idLivro = scanner.nextInt();
                        System.out.println("Digite o ID da pessoa: ");
                        int idPessoa = scanner.nextInt();
                        serv1.emprestarLivro(livros, pessoas, emprestimos, idLivro, idPessoa);
                    } catch (LivroNaoEncontradoException | LivroIndisponivelException | PessoaNaoEncontradaException e) {
                        System.out.println();
                        System.out.println("Erro: " + e.getMessage());
                        System.out.println();
                    }
                    break;

// ------------------------------------------------------------------------------------------------------------------------------------------------------------------

                // Se opção for 4, chama o método para devolver livro
                case 4:
                    try {
                        System.out.println("Digite o ID do livro que deseja devolver: ");
                        id = scanner.nextInt();
                        serv1.devolverLivro(livros, emprestimos, id);
                    } catch (LivroNaoEncontradoException e) {
                        System.out.println();
                        System.out.println("Erro: " + e.getMessage());
                        System.out.println();
                    }
                    break;

// ------------------------------------------------------------------------------------------------------------------------------------------------------------------

                // Se opção for 5, chama o método para excluir livro
                case 5:
                    try {
                        System.out.println("Digite o ID do livro que deseja excluir: ");
                        id = scanner.nextInt();
                        serv1.excluirLivro(livros, id);
                    } catch (LivroNaoEncontradoException e) {
                        System.out.println();
                        System.out.println("Erro: " + e.getMessage());
                        System.out.println();
                    }
                    break;

// ------------------------------------------------------------------------------------------------------------------------------------------------------------------

                // Se opção for 6, chama o método para cadastrar aluno
                case 6:
                    pessoas.add(serv1.cadastrarAluno(id_pessoa));
                    id_pessoa++;
                    break;

// ------------------------------------------------------------------------------------------------------------------------------------------------------------------

                // Se opção for 7, chama o método para cadastrar professor
                case 7:
                    pessoas.add(serv1.cadastrarProfessor(id_pessoa));
                    id_pessoa++;
                    break;

// ------------------------------------------------------------------------------------------------------------------------------------------------------------------

                // Se opção for 8, chama o método para listar pessoas
                case 8:
                    serv1.listarPessoas(pessoas);
                    break;
// ------------------------------------------------------------------------------------------------------------------------------------------------------------------

                // Se opção for 9, encerra o programa
                case 9:
                    parar = true;
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (!parar);
    }
}
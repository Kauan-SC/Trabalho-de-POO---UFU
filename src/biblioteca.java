import java.util.ArrayList;
import java.util.Scanner;

public class biblioteca {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);


        ArrayList<Livro> livros = new ArrayList<>();

        servicos serv1 = new servicos();
        int id_livro = 1;
        int id;
        boolean parar = false;

        do {
            int opcao = serv1.opcao();

            switch (opcao) {
                case 1:
                    livros.add(serv1.adicionarlivro(id_livro));
                    id_livro++;
                    break;
                case 2:
                    serv1.listarLivro(livros);
                    break;
                case 3:
                    System.out.println("Digite o ID do livro que deseja emprestar: ");
                    id = scanner.nextInt();
                    serv1.emprestarLivro(livros, id);
                    break;
                case 4:
                    System.out.println("Digite o ID do livro que deseja devolver: ");
                    id = scanner.nextInt();
                    serv1.devolverLivro(livros, id);
                    break;
                case 5:
                    System.out.println("Digite o ID do livro que deseja excluir: ");
                    id = scanner.nextInt();
                    serv1.excluirLivro(livros, id);
                    break;
                case 6:
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
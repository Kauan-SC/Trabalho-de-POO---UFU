import java.util.ArrayList;
import java.util.Scanner;

public class servicos {

    Scanner scanner = new Scanner(System.in);

    public int opcao() {
        System.out.println("Digite a opção desejada: ");
        System.out.println("(1) Adicionar livro");
        System.out.println("(2) Listar livros");
        System.out.println("(3) Emprestimo de livro");
        System.out.println("(4) Devolver livro");
        System.out.println("(5) Excluir livro");
        System.out.println("(6) Encerrar programa");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }

    public Livro adicionarlivro(int id) {
        System.out.println("Digite o título do livro:  ");
        String titulo = scanner.nextLine();
        System.out.println("Digite o nome do autor: ");
        String autor = scanner.nextLine();
        System.out.println("Digite o ano de lançamento: ");
        int ano = scanner.nextInt();
        Livro livro = new Livro(id, titulo, autor, ano, true);
        System.out.println();
        System.out.println("Livro adicionado com sucesso!");
        System.out.println();
        return livro;
    }

    public void listarLivro(ArrayList<Livro> livros) {
        if (livros.isEmpty()) {
            System.out.println();
            System.out.println("Não há livros cadastrados.");
        } else {
            System.out.println();
            System.out.println("Lista de livros cadastrados:");
            for (int i = 0; i < livros.size(); i++) {
                System.out.println(livros.get(i).toString());
            }
            System.out.println();

        }
    }

    public void emprestarLivro(ArrayList<Livro> livros, int id){
        for (Livro livro : livros) {
            if (livro.getId() == id) {
                if (livro.isDisponibilidade()) {
                    livro.setDisponibilidade(false);
                    System.out.println();
                    System.out.println("Livro emprestado com sucesso!" + livro.getTitulo());
                    System.out.println();
                } else {
                    System.out.println();
                    System.out.println("Livro indisponível para empréstimo." + livro.getTitulo());
                    System.out.println();
                }
                return;
            }
        }
        System.out.println();
        System.out.println("Livro" + id + " não encontrado.");
        System.out.println();
    }

    public void devolverLivro(ArrayList<Livro> livros, int id){
        for (Livro livro : livros) {
            if (livro.getId() == id) {
                if (!livro.isDisponibilidade()) {
                    livro.setDisponibilidade(true);
                    System.out.println();
                    System.out.println("Livro devolvido com sucesso!" + livro.getTitulo());
                    System.out.println();
                } else {
                    System.out.println();
                    System.out.println("Livro não está emprestado." + livro.getTitulo());
                    System.out.println();
                }
                return;
            }
        }
        System.out.println();
        System.out.println("Livro" + id + " não encontrado.");
        System.out.println();
    }

    public void excluirLivro(ArrayList<Livro> livros, int id){
        for (int i = 0; i < livros.size(); i++) {
            if (livros.get(i).getId() == id) {
                System.out.println();
                System.out.println("Livro excluído com sucesso!");
                System.out.println();
                livros.remove(i);
                return;
            }
        }
        System.out.println();
        System.out.println("Livro" + id + " não encontrado.");
        System.out.println();
    }
}

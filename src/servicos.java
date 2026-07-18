import emprestimos.Emprestimo;
import excecoes.LivroIndisponivelException;
import excecoes.LivroNaoEncontradoException;
import excecoes.PessoaNaoEncontradaException;
import java.util.ArrayList;
import java.util.Scanner;
import livros.Livro;
import pessoas.Aluno;
import pessoas.Pessoa;
import pessoas.Professor;

public class servicos {

    private Scanner scanner;

    public servicos(Scanner scanner) {
        this.scanner = scanner;
    }

    // Opções do Sistema
    public int opcao() {
        System.out.println("Digite a opção desejada: ");
        System.out.println("(1) Adicionar livro");
        System.out.println("(2) Listar livros");
        System.out.println("(3) Emprestimo de livro");
        System.out.println("(4) Devolver livro");
        System.out.println("(5) Excluir livro");
        System.out.println("(6) Cadastrar aluno");
        System.out.println("(7) Cadastrar professor");
        System.out.println("(8) Listar pessoas");
        System.out.println("(9) Encerrar programa");
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }

// ------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Opção 1 (Adicionar livro)
    public Livro adicionarlivro(int id) {
        System.out.println("Digite o título do livro:  ");
        String titulo = scanner.nextLine();
        System.out.println("Digite o nome do autor: ");
        String autor = scanner.nextLine();
        System.out.println("Digite o ano de lançamento: ");
        int ano = scanner.nextInt();
        scanner.nextLine();
        Livro livro = new Livro(id, titulo, autor, ano);
        System.out.println();
        System.out.println("Livro adicionado com sucesso!");
        System.out.println();
        return livro;
    }

// ------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Opção 2 (Listar livros)
    public void listarLivro(ArrayList<Livro> livros) {
        if (livros.isEmpty()) {
            System.out.println();
            System.out.println("Não há livros cadastrados.");
        } else {
            System.out.println();
            System.out.println("Lista de livros cadastrados:");
            for (Livro livro : livros) {
                System.out.println(livro.toString());
            }
            System.out.println();
        }
    }

// ------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Opção 4 (Devolver livro)
    public void devolverLivro(ArrayList<Livro> livros, ArrayList<Emprestimo> emprestimos, int idLivro)
            throws LivroNaoEncontradoException {

        Livro livro = buscarLivro(livros, idLivro);    // buscar o livro pelo ID

        // Verificar se o livro possui um empréstimo ativo
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getLivro() == livro && emprestimo.isAtivo()) {
                emprestimo.registrarDevolucao();
                livro.setDisponibilidade(true);

                // Verificar se a pessoa que devolveu o livro é um aluno e remover o empréstimo da lista de empréstimos ativos do aluno
                if (emprestimo.getPessoa() instanceof Aluno) {
                    ((Aluno) emprestimo.getPessoa()).getEmprestimosAtivos().remove(emprestimo);
                }

                System.out.println();
                System.out.println("Livro devolvido com sucesso: " + livro.getTitulo());
                System.out.println();
                return;
            }
        }

        System.out.println();
        System.out.println("Este livro não possui empréstimo em aberto.");
        System.out.println();
    }

// ------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Opção 5 (Excluir livro)
    public void excluirLivro(ArrayList<Livro> livros, int id) throws LivroNaoEncontradoException {
        Livro livro = buscarLivro(livros, id);
        livros.remove(livro);
        System.out.println();
        System.out.println("Livro excluído com sucesso!");
        System.out.println();
    }

    private Livro buscarLivro(ArrayList<Livro> livros, int id) throws LivroNaoEncontradoException {
        for (Livro livro : livros) {
            if (livro.getId() == id) {
                return livro;
            }
        }
        throw new LivroNaoEncontradoException("Livro com ID " + id + " não encontrado.");
    }

    private Pessoa buscarPessoa(ArrayList<Pessoa> pessoas, int id) throws PessoaNaoEncontradaException {
        for (Pessoa pessoa : pessoas) {
            if (pessoa.getId() == id) {
                return pessoa;
            }
        }
        throw new PessoaNaoEncontradaException("Pessoa com ID " + id + " não encontrada.");
    }

// ------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Opção 6 (Cadastrar aluno)
    public Aluno cadastrarAluno(int id) {
        System.out.println("Digite o nome do aluno: ");
        String nome = scanner.nextLine();
        System.out.println("Digite o CPF: ");
        String cpf = scanner.nextLine();
        System.out.println("Digite a matrícula: ");
        String matricula = scanner.nextLine();
        System.out.println("Digite o curso: ");
        String curso = scanner.nextLine();
        Aluno aluno = new Aluno(id, nome, cpf, matricula, curso);
        System.out.println();
        System.out.println("Aluno cadastrado com sucesso!");
        System.out.println();
        return aluno;
    }

// ------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Opção 7 (Cadastrar professor)
    public Professor cadastrarProfessor(int id) {
        System.out.println("Digite o nome do professor: ");
        String nome = scanner.nextLine();
        System.out.println("Digite o CPF: ");
        String cpf = scanner.nextLine();
        System.out.println("Digite o departamento: ");
        String departamento = scanner.nextLine();
        System.out.println("Digite a disciplina: ");
        String disciplina = scanner.nextLine();
        Professor professor = new Professor(id, nome, cpf, departamento, disciplina);
        System.out.println();
        System.out.println("Professor cadastrado com sucesso!");
        System.out.println();
        return professor;
    }

// ------------------------------------------------------------------------------------------------------------------------------------------------------------------

    // Opção 8 (Listar pessoas)
    public void listarPessoas(ArrayList<Pessoa> pessoas) {
        if (pessoas.isEmpty()) {
            System.out.println();
            System.out.println("Não há pessoas cadastradas.");
        } else {
            System.out.println();
            System.out.println("Lista de pessoas cadastradas:");
            for (Pessoa pessoa : pessoas) {
                System.out.println(pessoa.toString());
            }
            System.out.println();
        }
    }

// ------------------------------------------------------------------------------------------------------------------------------------------------------------------
    
    // Opção 9 (Emprestar livro)
    public void emprestarLivro(ArrayList<Livro> livros, ArrayList<Pessoa> pessoas, ArrayList<Emprestimo> emprestimos,
                                int idLivro, int idPessoa)
            throws LivroNaoEncontradoException, LivroIndisponivelException, PessoaNaoEncontradaException {

        Livro livro = buscarLivro(livros, idLivro);    // Buscar o livro pelo ID
        Pessoa pessoa = buscarPessoa(pessoas, idPessoa);    // Buscar a pessoa pelo ID

        // Se o livro estiver indisponível, lançar exceção
        if (!livro.isDisponibilidade()) {
            throw new LivroIndisponivelException("O livro '" + livro.getTitulo() + "' não está disponível para empréstimo.");
        }

        // Se livro disponível, registrar o empréstimo
        livro.setDisponibilidade(false);
        Emprestimo emprestimo = new Emprestimo(livro, pessoa);
        emprestimos.add(emprestimo);

        // Se a pessoa for um aluno, adicionar o empréstimo à lista de empréstimos ativos do aluno
        if (pessoa instanceof Aluno) {
            ((Aluno) pessoa).getEmprestimosAtivos().add(emprestimo);
        }

        System.out.println();
        System.out.println("Livro emprestado com sucesso para " + pessoa.getNome() + "!");
        System.out.println();
    }
}
package gui;

import emprestimos.Emprestimo;
import excecoes.LivroIndisponivelException;
import excecoes.LivroNaoEncontradoException;
import excecoes.PessoaNaoEncontradaException;
import livros.Livro;
import pessoas.Aluno;
import pessoas.Pessoa;
import pessoas.Professor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

// Janela principal do sistema, organizada em 3 abas: Livros, Pessoas e Empréstimos
public class JanelaPrincipal extends JFrame {

    // listas que guardam os dados enquanto o programa está aberto
    private ArrayList<Livro> livros = new ArrayList<>();
    private ArrayList<Pessoa> pessoas = new ArrayList<>();
    private ArrayList<Emprestimo> emprestimos = new ArrayList<>();

    // modelos das tabelas, usados para adicionar e remover linhas
    private DefaultTableModel modeloLivros;
    private DefaultTableModel modeloPessoas;
    private DefaultTableModel modeloEmprestimos;

    // controla o ID dos próximos cadastros
    private int proximoIdLivro = 1;
    private int proximoIdPessoa = 1;

    public JanelaPrincipal() {
        setTitle("Sistema de Biblioteca");
        setSize(750, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centraliza a janela na tela

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Livros", criarAbaLivros());
        abas.addTab("Pessoas", criarAbaPessoas());
        abas.addTab("Empréstimos", criarAbaEmprestimos());

        add(abas);
    }

    // monta o painel da aba Livros com tabela e botões
    private JPanel criarAbaLivros() {
        JPanel painel = new JPanel(new BorderLayout());

        String[] colunas = {"ID", "Título", "Autor", "Ano", "Disponível"};
        modeloLivros = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int linha, int coluna) { return false; }
        };

        JTable tabela = new JTable(modeloLivros);
        painel.add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        JButton botaoNovoLivro = new JButton("Adicionar Livro");
        JButton botaoRemoverLivro = new JButton("Excluir Livro");
        botoes.add(botaoNovoLivro);
        botoes.add(botaoRemoverLivro);
        painel.add(botoes, BorderLayout.SOUTH);

        botaoNovoLivro.addActionListener(e -> adicionarLivro());
        botaoRemoverLivro.addActionListener(e -> excluirLivro(tabela));

        return painel;
    }

    // monta o painel da aba Pessoas com tabela e botões
    private JPanel criarAbaPessoas() {
        JPanel painel = new JPanel(new BorderLayout());

        String[] colunas = {"ID", "Nome", "CPF", "Tipo", "Info"};
        modeloPessoas = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int linha, int coluna) { return false; }
        };

        JTable tabela = new JTable(modeloPessoas);
        painel.add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        JButton botaoCadastrarAluno = new JButton("Cadastrar Aluno");
        JButton botaoCadastrarProfessor = new JButton("Cadastrar Professor");
        botoes.add(botaoCadastrarAluno);
        botoes.add(botaoCadastrarProfessor);
        painel.add(botoes, BorderLayout.SOUTH);

        botaoCadastrarAluno.addActionListener(e -> cadastrarAluno());
        botaoCadastrarProfessor.addActionListener(e -> cadastrarProfessor());

        return painel;
    }

    // monta o painel da aba Empréstimos com tabela e botões
    private JPanel criarAbaEmprestimos() {
        JPanel painel = new JPanel(new BorderLayout());

        String[] colunas = {"Livro", "Pessoa", "Data Empréstimo", "Status"};
        modeloEmprestimos = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int linha, int coluna) { return false; }
        };

        JTable tabela = new JTable(modeloEmprestimos);
        painel.add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        JButton botaoEmprestar = new JButton("Emprestar");
        JButton botaoDevolver = new JButton("Devolver");
        botoes.add(botaoEmprestar);
        botoes.add(botaoDevolver);
        painel.add(botoes, BorderLayout.SOUTH);

        botaoEmprestar.addActionListener(e -> emprestarLivro());
        botaoDevolver.addActionListener(e -> devolverLivro());

        return painel;
    }

    // coleta os dados do novo livro e adiciona na lista e na tabela
    private void adicionarLivro() {
        String titulo = JOptionPane.showInputDialog(this, "Título do livro:");
        if (titulo == null || titulo.isBlank()) return;

        String autor = JOptionPane.showInputDialog(this, "Autor:");
        if (autor == null || autor.isBlank()) return;

        String anoStr = JOptionPane.showInputDialog(this, "Ano de lançamento:");
        if (anoStr == null || anoStr.isBlank()) return;

        try {
            int ano = Integer.parseInt(anoStr);
            Livro livro = new Livro(proximoIdLivro++, titulo, autor, ano);
            livros.add(livro);
            modeloLivros.addRow(new Object[]{livro.getId(), titulo, autor, ano, "Sim"});
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ano inválido. Digite apenas números.");
        }
    }

    // remove o livro selecionado na tabela
    private void excluirLivro(JTable tabela) {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um livro na tabela.");
            return;
        }

        int id = (int) modeloLivros.getValueAt(linhaSelecionada, 0);
        livros.removeIf(l -> l.getId() == id);
        modeloLivros.removeRow(linhaSelecionada);
    }

    // coleta os dados do aluno e adiciona na lista e na tabela
    private void cadastrarAluno() {
        String nome = JOptionPane.showInputDialog(this, "Nome do aluno:");
        if (nome == null || nome.isBlank()) return;

        String cpf = JOptionPane.showInputDialog(this, "CPF:");
        if (cpf == null || cpf.isBlank()) return;

        String matricula = JOptionPane.showInputDialog(this, "Matrícula:");
        if (matricula == null || matricula.isBlank()) return;

        String curso = JOptionPane.showInputDialog(this, "Curso:");
        if (curso == null || curso.isBlank()) return;

        Aluno aluno = new Aluno(proximoIdPessoa++, nome, cpf, matricula, curso);
        pessoas.add(aluno);
        modeloPessoas.addRow(new Object[]{aluno.getId(), nome, cpf, "Aluno", matricula + " - " + curso});
    }

    // coleta os dados do professor e adiciona na lista e na tabela
    private void cadastrarProfessor() {
        String nome = JOptionPane.showInputDialog(this, "Nome do professor:");
        if (nome == null || nome.isBlank()) return;

        String cpf = JOptionPane.showInputDialog(this, "CPF:");
        if (cpf == null || cpf.isBlank()) return;

        String departamento = JOptionPane.showInputDialog(this, "Departamento:");
        if (departamento == null || departamento.isBlank()) return;

        String disciplina = JOptionPane.showInputDialog(this, "Disciplina:");
        if (disciplina == null || disciplina.isBlank()) return;

        Professor professor = new Professor(proximoIdPessoa++, nome, cpf, departamento, disciplina);
        pessoas.add(professor);
        modeloPessoas.addRow(new Object[]{professor.getId(), nome, cpf, "Professor", departamento + " - " + disciplina});
    }

    // registra o empréstimo de um livro para uma pessoa
    private void emprestarLivro() {
        if (livros.isEmpty() || pessoas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cadastre livros e pessoas antes de fazer um empréstimo.");
            return;
        }

        String idLivroStr = JOptionPane.showInputDialog(this, "ID do livro:");
        if (idLivroStr == null) return;

        String idPessoaStr = JOptionPane.showInputDialog(this, "ID da pessoa:");
        if (idPessoaStr == null) return;

        try {
            int idLivro = Integer.parseInt(idLivroStr);
            int idPessoa = Integer.parseInt(idPessoaStr);

            Livro livro = buscarLivro(idLivro);
            Pessoa pessoa = buscarPessoa(idPessoa);

            if (!livro.isDisponibilidade()) {
                throw new LivroIndisponivelException("O livro '" + livro.getTitulo() + "' não está disponível.");
            }

            livro.setDisponibilidade(false);
            Emprestimo emprestimo = new Emprestimo(livro, pessoa);
            emprestimos.add(emprestimo);

            // se for aluno, registra o empréstimo na lista pessoal dele também
            if (pessoa instanceof Aluno) {
                ((Aluno) pessoa).getEmprestimosAtivos().add(emprestimo);
            }

            atualizarTabelaLivros();
            modeloEmprestimos.addRow(new Object[]{
                livro.getTitulo(), pessoa.getNome(), emprestimo.getDataEmprestimo(), "Ativo"
            });

            JOptionPane.showMessageDialog(this, "Empréstimo registrado com sucesso!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido. Digite apenas números.");
        } catch (LivroNaoEncontradoException | PessoaNaoEncontradaException | LivroIndisponivelException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    // registra a devolução de um livro emprestado
    private void devolverLivro() {
        String idLivroStr = JOptionPane.showInputDialog(this, "ID do livro a devolver:");
        if (idLivroStr == null) return;

        try {
            int idLivro = Integer.parseInt(idLivroStr);
            Livro livro = buscarLivro(idLivro);

            for (Emprestimo emp : emprestimos) {
                if (emp.getLivro() == livro && emp.isAtivo()) {
                    emp.registrarDevolucao();
                    livro.setDisponibilidade(true);

                    if (emp.getPessoa() instanceof Aluno) {
                        ((Aluno) emp.getPessoa()).getEmprestimosAtivos().remove(emp);
                    }

                    atualizarTabelaLivros();
                    atualizarTabelaEmprestimos();
                    JOptionPane.showMessageDialog(this, "Livro devolvido com sucesso!");
                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "Este livro não possui empréstimo ativo.");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido. Digite apenas números.");
        } catch (LivroNaoEncontradoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    // percorre a lista procurando o livro pelo ID
    private Livro buscarLivro(int id) throws LivroNaoEncontradoException {
        for (Livro l : livros) {
            if (l.getId() == id) return l;
        }
        throw new LivroNaoEncontradoException("Livro com ID " + id + " não encontrado.");
    }

    // percorre a lista procurando a pessoa pelo ID
    private Pessoa buscarPessoa(int id) throws PessoaNaoEncontradaException {
        for (Pessoa p : pessoas) {
            if (p.getId() == id) return p;
        }
        throw new PessoaNaoEncontradaException("Pessoa com ID " + id + " não encontrada.");
    }

    // limpa a tabela e repopula com os dados atuais da lista
    private void atualizarTabelaLivros() {
        modeloLivros.setRowCount(0);
        for (Livro l : livros) {
            modeloLivros.addRow(new Object[]{
                l.getId(), l.getTitulo(), l.getAutor(), l.getAno(),
                l.isDisponibilidade() ? "Sim" : "Não"
            });
        }
    }

    private void atualizarTabelaEmprestimos() {
        modeloEmprestimos.setRowCount(0);
        for (Emprestimo emp : emprestimos) {
            modeloEmprestimos.addRow(new Object[]{
                emp.getLivro().getTitulo(),
                emp.getPessoa().getNome(),
                emp.getDataEmprestimo(),
                emp.isAtivo() ? "Ativo" : "Devolvido"
            });
        }
    }
}

package gui;

import emprestimos.Emprestimo;
import excecoes.LivroIndisponivelException;
import excecoes.LivroNaoEncontradoException;
import excecoes.PessoaNaoEncontradaException;
import livros.Livro;
import pessoas.Aluno;
import pessoas.Bibliotecario;
import pessoas.Pessoa;
import pessoas.Professor;
import persistencia.EmprestimoDAO;
import persistencia.LivroDAO;
import persistencia.PessoaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
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

    // acesso ao banco de dados
    private final LivroDAO livroDAO = new LivroDAO();
    private final PessoaDAO pessoaDAO = new PessoaDAO();
    private final EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

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

        carregarDadosDoBanco();
    }

    // busca tudo que já existe no banco e popula as tabelas ao abrir o programa
    private void carregarDadosDoBanco() {
        try {
            livros.clear();
            livros.addAll(livroDAO.listarTodos());
            atualizarTabelaLivros();

            pessoas.clear();
            pessoas.addAll(pessoaDAO.listarTodos());
            atualizarTabelaPessoas();

            emprestimos.clear();
            emprestimos.addAll(emprestimoDAO.listarTodos(livros, pessoas));
            atualizarTabelaEmprestimos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Não foi possível conectar ao banco de dados. O programa vai continuar, mas os dados não serão salvos.\n"
                            + ex.getMessage(),
                    "Erro de conexão", JOptionPane.WARNING_MESSAGE);
        }
    }

    // monta o painel da aba Livros com tabela e botões
    private JPanel criarAbaLivros() {
        JPanel painel = new JPanel(new BorderLayout());

        String[] colunas = { "ID", "Título", "Autor", "Ano", "Disponível" };
        modeloLivros = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
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

        String[] colunas = { "ID", "Nome", "CPF", "Tipo", "Info" };
        modeloPessoas = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        JTable tabela = new JTable(modeloPessoas);
        painel.add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        JButton botaoCadastrarAluno = new JButton("Cadastrar Aluno");
        JButton botaoCadastrarProfessor = new JButton("Cadastrar Professor");
        JButton botaoCadastrarBibliotecario = new JButton("Cadastrar Bibliotecário");
        botoes.add(botaoCadastrarAluno);
        botoes.add(botaoCadastrarProfessor);
        botoes.add(botaoCadastrarBibliotecario);
        painel.add(botoes, BorderLayout.SOUTH);

        botaoCadastrarAluno.addActionListener(e -> cadastrarAluno());
        botaoCadastrarProfessor.addActionListener(e -> cadastrarProfessor());
        botaoCadastrarBibliotecario.addActionListener(e -> cadastrarBibliotecario());

        return painel;
    }

    // monta o painel da aba Empréstimos com tabela e botões
    private JPanel criarAbaEmprestimos() {
        JPanel painel = new JPanel(new BorderLayout());

        String[] colunas = { "Livro", "Pessoa", "Data Empréstimo", "Status" };
        modeloEmprestimos = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
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

    // coleta os dados do novo livro, grava no banco e atualiza a tabela
    private void adicionarLivro() {
        String titulo = JOptionPane.showInputDialog(this, "Título do livro:");
        if (titulo == null || titulo.isBlank())
            return;

        String autor = JOptionPane.showInputDialog(this, "Autor:");
        if (autor == null || autor.isBlank())
            return;

        String anoStr = JOptionPane.showInputDialog(this, "Ano de lançamento:");
        if (anoStr == null || anoStr.isBlank())
            return;

        try {
            int ano = Integer.parseInt(anoStr);
            Livro livro = new Livro(0, titulo, autor, ano);
            livroDAO.inserir(livro); // grava no banco e preenche o id gerado no objeto
            livros.add(livro);
            atualizarTabelaLivros();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ano inválido. Digite apenas números.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar livro no banco: " + ex.getMessage());
        }
    }

    // remove o livro selecionado, tanto no banco quanto na tabela
    private void excluirLivro(JTable tabela) {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um livro na tabela.");
            return;
        }

        int id = (int) modeloLivros.getValueAt(linhaSelecionada, 0);
        try {
            livroDAO.excluir(id);
            livros.removeIf(l -> l.getId() == id);
            atualizarTabelaLivros();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir livro no banco: " + ex.getMessage());
        }
    }

    // coleta os dados do aluno, grava no banco e atualiza a tabela
    private void cadastrarAluno() {
        String nome = JOptionPane.showInputDialog(this, "Nome do aluno:");
        if (nome == null || nome.isBlank())
            return;

        String cpf = JOptionPane.showInputDialog(this, "CPF:");
        if (cpf == null || cpf.isBlank())
            return;

        String matricula = JOptionPane.showInputDialog(this, "Matrícula:");
        if (matricula == null || matricula.isBlank())
            return;

        String curso = JOptionPane.showInputDialog(this, "Curso:");
        if (curso == null || curso.isBlank())
            return;

        try {
            Aluno aluno = new Aluno(0, nome, cpf, matricula, curso);
            pessoaDAO.inserir(aluno);
            pessoas.add(aluno);
            atualizarTabelaPessoas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar aluno no banco: " + ex.getMessage());
        }
    }

    // coleta os dados do bibliotecário, grava no banco e atualiza a tabela
    private void cadastrarBibliotecario() {
        String nome = JOptionPane.showInputDialog(this, "Nome do bibliotecário:");
        if (nome == null || nome.isBlank())
            return;

        String cpf = JOptionPane.showInputDialog(this, "CPF:");
        if (cpf == null || cpf.isBlank())
            return;

        String matriculaFuncional = JOptionPane.showInputDialog(this, "Matrícula funcional:");
        if (matriculaFuncional == null || matriculaFuncional.isBlank())
            return;

        try {
            Bibliotecario bibliotecario = new Bibliotecario(0, nome, cpf, matriculaFuncional);
            pessoaDAO.inserir(bibliotecario);
            pessoas.add(bibliotecario);
            atualizarTabelaPessoas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar bibliotecário no banco: " + ex.getMessage());
        }
    }

    // coleta os dados do professor, grava no banco e atualiza a tabela
    private void cadastrarProfessor() {
        String nome = JOptionPane.showInputDialog(this, "Nome do professor:");
        if (nome == null || nome.isBlank())
            return;

        String cpf = JOptionPane.showInputDialog(this, "CPF:");
        if (cpf == null || cpf.isBlank())
            return;

        String departamento = JOptionPane.showInputDialog(this, "Departamento:");
        if (departamento == null || departamento.isBlank())
            return;

        String disciplina = JOptionPane.showInputDialog(this, "Disciplina:");
        if (disciplina == null || disciplina.isBlank())
            return;

        try {
            Professor professor = new Professor(0, nome, cpf, departamento, disciplina);
            pessoaDAO.inserir(professor);
            pessoas.add(professor);
            atualizarTabelaPessoas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar professor no banco: " + ex.getMessage());
        }
    }

    // registra o empréstimo de um livro para uma pessoa, tanto no banco quanto na
    // tabela
    private void emprestarLivro() {
        if (livros.isEmpty() || pessoas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cadastre livros e pessoas antes de fazer um empréstimo.");
            return;
        }

        String idLivroStr = JOptionPane.showInputDialog(this, "ID do livro:");
        if (idLivroStr == null)
            return;

        String idPessoaStr = JOptionPane.showInputDialog(this, "ID da pessoa:");
        if (idPessoaStr == null)
            return;

        try {
            int idLivro = Integer.parseInt(idLivroStr);
            int idPessoa = Integer.parseInt(idPessoaStr);

            Livro livro = buscarLivro(idLivro);
            Pessoa pessoa = buscarPessoa(idPessoa);

            if (!livro.isDisponibilidade()) {
                throw new LivroIndisponivelException("O livro '" + livro.getTitulo() + "' não está disponível.");
            }

            Emprestimo emprestimo = new Emprestimo(livro, pessoa);
            emprestimoDAO.inserir(emprestimo);
            livroDAO.atualizarDisponibilidade(livro.getId(), false);

            livro.setDisponibilidade(false);
            emprestimos.add(emprestimo);

            if (pessoa instanceof Aluno aluno) {
                aluno.getEmprestimosAtivos().add(emprestimo);
            }

            atualizarTabelaLivros();
            atualizarTabelaEmprestimos();

            JOptionPane.showMessageDialog(this, "Empréstimo registrado com sucesso!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido. Digite apenas números.");
        } catch (LivroNaoEncontradoException | PessoaNaoEncontradaException | LivroIndisponivelException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar empréstimo no banco: " + ex.getMessage());
        }
    }

    // registra a devolução de um livro emprestado, tanto no banco quanto na tabela
    private void devolverLivro() {
        String idLivroStr = JOptionPane.showInputDialog(this, "ID do livro a devolver:");
        if (idLivroStr == null)
            return;

        try {
            int idLivro = Integer.parseInt(idLivroStr);
            Livro livro = buscarLivro(idLivro);

            for (Emprestimo emp : emprestimos) {
                if (emp.getLivro() == livro && emp.isAtivo()) {
                    emprestimoDAO.registrarDevolucao(livro.getId(), emp.getPessoa().getId());
                    livroDAO.atualizarDisponibilidade(livro.getId(), true);

                    emp.registrarDevolucao();
                    livro.setDisponibilidade(true);

                    if (emp.getPessoa() instanceof Aluno aluno) {
                        aluno.getEmprestimosAtivos().remove(emp);
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
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar devolução no banco: " + ex.getMessage());
        }
    }

    // percorre a lista procurando o livro pelo ID
    private Livro buscarLivro(int id) throws LivroNaoEncontradoException {
        for (Livro l : livros) {
            if (l.getId() == id)
                return l;
        }
        throw new LivroNaoEncontradoException("Livro com ID " + id + " não encontrado.");
    }

    // percorre a lista procurando a pessoa pelo ID
    private Pessoa buscarPessoa(int id) throws PessoaNaoEncontradaException {
        for (Pessoa p : pessoas) {
            if (p.getId() == id)
                return p;
        }
        throw new PessoaNaoEncontradaException("Pessoa com ID " + id + " não encontrada.");
    }

    // limpa a tabela e repopula com os dados atuais da lista
    private void atualizarTabelaLivros() {
        modeloLivros.setRowCount(0);
        for (Livro l : livros) {
            modeloLivros.addRow(new Object[] {
                    l.getId(), l.getTitulo(), l.getAutor(), l.getAno(),
                    l.isDisponibilidade() ? "Sim" : "Não"
            });
        }
    }

    // limpa a tabela e repopula com os dados atuais da lista, cobrindo os 3 tipos
    // de pessoa
    private void atualizarTabelaPessoas() {
        modeloPessoas.setRowCount(0);
        for (Pessoa p : pessoas) {
            String info;
            if (p instanceof Aluno a) {
                info = a.getMatricula() + " - " + a.getCurso();
            } else if (p instanceof Professor pr) {
                info = pr.getDepartamento() + " - " + pr.getDisciplina();
            } else if (p instanceof Bibliotecario b) {
                info = b.getMatriculaFuncional();
            } else {
                info = "";
            }
            modeloPessoas.addRow(new Object[] { p.getId(), p.getNome(), p.getCpf(), p.getTipo(), info });
        }
    }

    private void atualizarTabelaEmprestimos() {
        modeloEmprestimos.setRowCount(0);
        for (Emprestimo emp : emprestimos) {
            modeloEmprestimos.addRow(new Object[] {
                    emp.getLivro().getTitulo(),
                    emp.getPessoa().getNome(),
                    emp.getDataEmprestimo(),
                    emp.isAtivo() ? "Ativo" : "Devolvido"
            });
        }
    }
}
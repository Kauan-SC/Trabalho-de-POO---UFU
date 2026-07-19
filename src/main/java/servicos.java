// import java.util.ArrayList;

// import emprestimos.Emprestimo;
// import excecoes.LivroIndisponivelException;
// import excecoes.LivroNaoEncontradoException;
// import excecoes.PessoaNaoEncontradaException;
// import livros.Livro;
// import pessoas.Aluno;
// import pessoas.Pessoa;

// // Classe responsável por fornecer os serviços essenciais do sistema de biblioteca
// public class servicos {

//     // Serviços de busca de livros e pessoas, empréstimos e devoluções
//     public Livro buscarLivro(ArrayList<Livro> livros, int id) throws LivroNaoEncontradoException {
//         for (Livro livro : livros) {
//             if (livro.getId() == id)
//                 return livro;
//         }
//         throw new LivroNaoEncontradoException("Livro com ID " + id + " não encontrado.");
//     }

// // ------------------------------------------------------------------------------------------------------------------------------------------------------------------

//     public Pessoa buscarPessoa(ArrayList<Pessoa> pessoas, int id) throws PessoaNaoEncontradaException {
//         for (Pessoa pessoa : pessoas) {
//             if (pessoa.getId() == id)
//                 return pessoa;
//         }
//         throw new PessoaNaoEncontradaException("Pessoa com ID " + id + " não encontrada.");
//     }

// // ------------------------------------------------------------------------------------------------------------------------------------------------------------------

//     public void emprestarLivro(ArrayList<Livro> livros, ArrayList<Pessoa> pessoas, ArrayList<Emprestimo> emprestimos,
//             int idLivro, int idPessoa)
//             throws LivroNaoEncontradoException, LivroIndisponivelException, PessoaNaoEncontradaException {

//         Livro livro = buscarLivro(livros, idLivro);
//         Pessoa pessoa = buscarPessoa(pessoas, idPessoa);

//         if (!livro.isDisponibilidade()) {
//             throw new LivroIndisponivelException("O livro '" + livro.getTitulo() + "' não está disponível.");
//         }

//         livro.setDisponibilidade(false);
//         Emprestimo emprestimo = new Emprestimo(livro, pessoa);
//         emprestimos.add(emprestimo);

//         if (pessoa instanceof Aluno aluno) {
//             aluno.getEmprestimosAtivos().add(emprestimo);
//         }
//     }
  
// // ------------------------------------------------------------------------------------------------------------------------------------------------------------------

//     public void devolverLivro(ArrayList<Livro> livros, ArrayList<Emprestimo> emprestimos, int idLivro)
//             throws LivroNaoEncontradoException {

//         Livro livro = buscarLivro(livros, idLivro);
//         for (Emprestimo emprestimo : emprestimos) {
//             if (emprestimo.getLivro() == livro && emprestimo.isAtivo()) {
//                 emprestimo.registrarDevolucao();
//                 livro.setDisponibilidade(true);
//                 if (emprestimo.getPessoa() instanceof Aluno aluno) {
//                     aluno.getEmprestimosAtivos().remove(emprestimo);
//                 }
//                 return;
//             }
//         }
//     }
// }
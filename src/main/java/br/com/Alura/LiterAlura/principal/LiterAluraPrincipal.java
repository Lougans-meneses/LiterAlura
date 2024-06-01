package br.com.Alura.LiterAlura.principal;

import br.com.Alura.LiterAlura.model.*;
import br.com.Alura.LiterAlura.repository.LivroRepository;
import br.com.Alura.LiterAlura.service.ConsumoApi;
import br.com.Alura.LiterAlura.service.ConverteDados;

import java.util.*;
public class LiterAluraPrincipal {
    private Scanner leitura = new Scanner(System.in);
    private final String URL = "http://gutendex.com/books/?search=";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private List<DadosLivro> dadosLivros = new ArrayList<>();
    private LivroRepository repositorio;
    public LiterAluraPrincipal(LivroRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;

        while (opcao != 0) {
            var menu = """
                        ================================================
                                     Bem-vindo ao LiterAlura
                               Escolha o número da opção no menu:                    
                        ================================================
                                            
                        1 - Buscar livro pelo título
                        2 - Listar livros registrados
                        3 - Listar autores registrados
                        4 - Listar autores vivos em um determinado ano
                        5 - Listar livro em um determinado idioma                                  
                        0 - Sair
                        """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivro();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLivreIdioma();
                    break;
                case 0:
                    System.out.println("Encerrando a LiterAlura!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }


    }

    private void buscarLivro() {
        var cadastrarNovo = "s";

        while (cadastrarNovo.equalsIgnoreCase("s")) {
            System.out.println("Informe o título do livro: ");
            var tituloBusca = leitura.nextLine();

            var tituloBuscaFormatado = tituloBusca.toLowerCase().replace(" ", "%20");
            var json = consumoApi.obterDados(URL + tituloBuscaFormatado);
            ResultadoApi resultadoApi = conversor.ObterDados(json, ResultadoApi.class);

            if (resultadoApi != null && resultadoApi.getResults() != null && !resultadoApi.getResults().isEmpty()) {
                System.out.println(" \n =============== Obra(s) Localizadas =============== \n");

                for (DadosLivro livro : resultadoApi.getResults()) {
                    System.out.println("Título: " + livro.getTitle());
                    System.out.println("Autore(s) da obra ");

                    for (DadosAutor autor : livro.getAuthors()) {
                        System.out.println("  Nome: " + autor.getName());
                        System.out.println("  Ano de Nascimento: " + autor.getBirthYear());
                        System.out.println("  Ano de Morte: " + autor.getDeathYear());
                    }
                    System.out.println("Idiomas: " + String.join(", ", livro.getLanguages()));
                    System.out.println("Downloads até agora: " +livro.getDownloadCount());
                }
                System.out.println("=======================================================\n" +
                        "   Deseja salvar o(s) título(s) no Literalura? (s/n)   \n" +
                        "=======================================================");
                var salvarBanco = leitura.nextLine();

                if(salvarBanco.equalsIgnoreCase("s")){
                    System.out.println("\n ***** dados salvos com sucesso ***** \n");
                    List<Livro> livros = new ArrayList<>();

                    Set<String> titulosUnicos = new HashSet<>();

                    for (DadosLivro dadosLivro:resultadoApi.getResults()){
                        if(titulosUnicos.add(dadosLivro.getTitle())) {
                            Livro livro = new Livro(dadosLivro);
                            for(String idiomaStr: dadosLivro.getLanguages()){
                                Idioma idiomaEnum = Idioma.fromCode(idiomaStr.toLowerCase());
                                if(idiomaEnum != null){
                                    livro.setIdiomas(idiomaEnum.getIdiomaTraduzido());
                                    break;
                                }
                            }
                            livros.add(livro);
                            repositorio.save(livro);
                        } else {
                            System.out.println("Títulos duplicados não registrados: " + dadosLivro.getTitle());
                        }
                    }

                    cadastrarNovo = "n";
                } else {
                    cadastrarNovo = "n";
                }
            } else {
                System.out.println("Nenhum título encontrado com este nome: " +tituloBusca);
                System.out.println("Deseja pesquisar outro título? (s/n)");
                cadastrarNovo = leitura.nextLine();
            }
        }
    }



    private void listarLivros() {
        List <Object[]> acervo = repositorio.findLivrosAndAutores();
        if(acervo.isEmpty()){
            System.out.println("Acervo ainda sem registros! \n");
            acervo.forEach(System.out::println);
        } else {
            System.out.println("================================================\n" +
                    "   Registros localizados no Acervo Literalura   \n" +
                    "================================================\n");
            acervo.stream()
                    .map(row -> "  Título: " + row[0] + "\n  Idioma: "
                            + row[1] + "\n  Autore(s): " + row[2]
                            + "\n ________________________________________________________________")
                    .forEach(System.out::println);
        }
    }

    private void listarAutores() {
        List<String> acervo = repositorio.findAutores();
        if(acervo.isEmpty()){
            System.out.println("Acervo ainda sem registros! \n");
        } else {
            System.out.println("================================================\n" +
                    "   Registros localizados no Acervo Literalura   \n" +
                    "================================================\n");
            acervo.forEach(System.out::println);
        }

    }
    private void listarAutoresVivos() {
        System.out.println("Digite o ano que deseja pesquisar: ");
        Integer ano = leitura.nextInt();
        List<Autor> acervo = repositorio.findAutoresEmDeterminadoAno(ano);
        if(acervo.isEmpty()){
            System.out.println("Acervo sem registros para o ano: " + ano + "!"+"\n");
            acervo.forEach(System.out::println);
        } else {
            System.out.println("================================================\n" +
                    "   Registros localizados no Acervo Literalura   \n" +
                    "================================================\n");
            acervo.forEach(System.out::println);
        }

    }
    private void listarLivreIdioma() {
        System.out.println("Digite o idioma que deseja consultar: ");
        String idioma = leitura.nextLine();
        char primeiroChar = Character.toUpperCase(idioma.charAt(0));
        String restante = idioma.substring(1).toLowerCase();
        String idiomaFormatado = primeiroChar + restante;
        List<Livro> acervo = repositorio.findByIdioma(idiomaFormatado);
        if(acervo.isEmpty()){
            System.out.println("Acervo sem registros para o idioma " + idioma +"!" +"\n");
            acervo.forEach(System.out::println);
        } else {
            acervo.stream()
                    .forEach(livro -> System.out.println(
                            "------------------------------------------\n"+
                                    "Obra: " + livro.getTitulo()  +
                                    "\n" +
                                    "Idioma: " + livro.getIdiomas()));
        }

    }
}

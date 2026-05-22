import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<Usuario> lista = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int opcao;

        do {

            // Exibição do menu de opções
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Editar");
            System.out.println("4 - Remover");
            System.out.println("5 - Sair");
            System.out.print("Escolha: ");

            opcao = lerInteiro("opção");

            switch (opcao) {

                case 1 -> cadastrar();  
                case 2 -> listar();     
                case 3 -> editar();     
                case 4 -> remover();    
                case 5 -> System.out.println("Saindo do sistema...");  
                default -> System.out.println("Opção inválida!");      
            }

        } while (opcao != 5);  
    }

    // ====================== VALIDAÇÕES ======================

    // Método para ler um número inteiro com validação 
    static int lerInteiro(String nomeCampo) {
        while (true) {
            try {
                int valor = sc.nextInt();
                sc.nextLine();  // Limpa o buffer do teclado
                return valor;
            } catch (Exception e) {
                sc.nextLine();  // Limpa entrada inválida
                System.out.println("Você digitou " + nomeCampo + " errado.");
            }
        }
    }

    // Método específico para ler o CRE com validação de (0 a 100)
    static double lerCre() {
        while (true) {
            try {
                double cre = sc.nextDouble();
                sc.nextLine();

                if (cre >= 0 && cre <= 100) {
                    return cre;
                } else {
                    System.out.println("CRE inválido! Digite um valor entre 0 e 100.");
                }

            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Digite um número válido.");
            }
        }
    }

    // Método para ler uma string e garantir que não seja vazia
    static String lerString(String nomeCampo) {
        while (true) {
            String texto = sc.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("Você não digitou " + nomeCampo + ".");
        }
    }

    // Validação do CPF: remove caracteres não numéricos e verifica se tem exatamente 11 dígitos
    static boolean cpfValido(String cpf) {
        String apenasNumeros = cpf.replaceAll("\\D", "");
        return apenasNumeros.length() == 11;
    }

    // Validação da data de nascimento: formato DD/MM/AAAA, ano >= 1961, mês entre 1 e 12, dia entre 1 e 31
    static boolean dataValida(String data) {

        if (!data.matches("\\d{2}/\\d{2}/\\d{4}")) {
            return false;
        }

        int dia = Integer.parseInt(data.substring(0, 2));
        int mes = Integer.parseInt(data.substring(3, 5));
        int ano = Integer.parseInt(data.substring(6));

        if (ano < 1961) return false;
        if (mes < 1 || mes > 12) return false;
        if (dia < 1 || dia > 31) return false;

        return true;
    }

    // Busca um usuário na lista pelo CPF
    static Usuario buscar(String cpf) {
        for (Usuario u : lista) {
            if (u.getCpf().equals(cpf)) {
                return u;
            }
        }
        return null;
    }

    // Verifica se uma matrícula já existe entre os alunos cadastrados 
    static boolean matriculaExiste(String matricula) {
        for (Usuario u : lista) {
            if (u instanceof Aluno a) {
                if (a.getMatricula().equals(matricula)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Verifica se um SIAPE já existe entre os professores cadastrados
    static boolean siapeExiste(String siape, Usuario usuarioIgnorar) {
        for (Usuario u : lista) {
            if (u instanceof Professor p) {
                // Se for edição, ignora o próprio usuário
                if (usuarioIgnorar != null && u.equals(usuarioIgnorar)) {
                    continue;
                }
                if (p.getSiape().equals(siape)) {
                    return true;    
                }
            }
        }
        return false;
    }

    // Validação do SIAPE: verifica se tem até 7 dígitos
    static boolean siapeValido(String siape) {
        String apenasNumeros = siape.replaceAll("\\D", "");
        return apenasNumeros.length() <= 7 && apenasNumeros.length() > 0;
    }

    // ====================== CADASTRAR ======================

    // Método principal de cadastro: escolhe entre Professor ou Aluno, coleta dados comuns e específicos
    static void cadastrar() {

        int tipo;

        // Escolha do tipo de usuário (1 = Professor, 2 = Aluno)
        while (true) {

            System.out.println("1 - Professor | 2 - Aluno");
            tipo = lerInteiro("tipo");

            if (tipo == 1 || tipo == 2) {
                break;
            } else {
                System.out.println("Digite uma opção valida");
            }
        }

        // Dados comuns a ambos os tipos
        System.out.print("Nome: ");
        String nome = lerString("Nome");

        String cpf;

        // Leitura e validação do CPF, verificando se já existe
        while (true) {
            System.out.print("CPF (exatamente 11 dígitos): ");
            cpf = lerString("CPF");

            if (!cpfValido(cpf)) {
                System.out.println("CPF inválido! Digite exatamente 11 dígitos.");
                continue;
            }

            if (buscar(cpf) != null) {
                System.out.println("CPF já cadastrado.");
            } else {
                break;
            }
        }

        String data;

        // Leitura e validação da data de nascimento
        while (true) {
            System.out.print("Data Nascimento (DD/MM/AAAA): ");
            data = lerString("Data");

            if (!dataValida(data)) {
                System.out.println("Data inválida! (dia 1-31, mês 1-12, ano 1961+)");
            } else {
                break;
            }
        }

        // Cadastro específico para Professor
        if (tipo == 1) {

            System.out.print("Carga Horária: ");
            int ch = lerInteiro("Carga Horária");

            String siape;
            
            // Leitura e validação do SIAPE (máximo 7 dígitos, não pode ser igual)
            while (true) {
                System.out.print("SIAPE (máximo 7 dígitos): ");
                siape = lerString("SIAPE");
                
                if (!siapeValido(siape)) {
                    System.out.println("SIAPE inválido! Digite até 7 dígitos.");
                } else if (siapeExiste(siape, null)) {
                    System.out.println("SIAPE já cadastrado! Digite um SIAPE diferente.");
                } else {
                    break;
                }
            }

            lista.add(new Professor(nome, cpf, data, ch, siape));

            System.out.println("Professor cadastrado com sucesso!");
        }

        // Cadastro específico para Aluno
        else {

            String matricula;

            // Verifica se a matrícula já existe para evitar duplicidade
            while (true) {
                System.out.print("Matrícula: ");
                matricula = lerString("Matrícula");

                if (matriculaExiste(matricula)) {
                    System.out.println("Matrícula já cadastrada!");
                } else {
                    break;
                }
            }

            System.out.print("CRE (0 a 100): ");
            double cre = lerCre();

            lista.add(new Aluno(nome, cpf, data, matricula, cre));

            System.out.println("Aluno cadastrado com sucesso!");
        }
    }

    // ====================== LISTAR ======================

    // Método para exibir todos os usuários cadastrados (usa toString() polimórfico)
    static void listar() {
        if (lista.isEmpty()) {
            System.out.println("Nenhum usuario cadastrado.");
            return;
        }

        System.out.println("\n=== LISTA DE USUÁRIOS ===");

        for (Usuario u : lista) {
            System.out.println(u);
        }
    }

    // ====================== EDITAR ======================

    // Método para editar dados de um usuário existente, buscando pelo CPF
    static void editar() {

        if (lista.isEmpty()) {
            System.out.println("Nenhum usuario cadastrado.");
            return;
        }

        System.out.print("CPF do usuário a editar: ");
        String cpfBusca = lerString("CPF");

        Usuario u = buscar(cpfBusca);

        if (u == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        // Edita os dados comuns (nome e data de nascimento)
        System.out.print("Novo nome: ");
        u.setNome(lerString("Nome"));

        String novaData;

        while (true) {
            System.out.print("Nova data de nascimento (DD/MM/AAAA): ");
            novaData = lerString("Data");

            if (!dataValida(novaData)) {
                System.out.println("Data inválida!");
            } else {
                u.setDataNascimento(novaData);
                break;
            }
        }

        // Se for Professor, edita carga horária e SIAPE
        if (u instanceof Professor p) {

            System.out.print("Nova carga horária: ");
            p.setCargaHoraria(lerInteiro("Carga Horária"));

            String novoSiape;
            
            // Validação do novo SIAPE (máximo 7 dígitos e não pode ser igual a outro)
            while (true) {
                System.out.print("Novo SIAPE (máximo 7 dígitos): ");
                novoSiape = lerString("SIAPE");
                
                if (!siapeValido(novoSiape)) {
                    System.out.println("SIAPE inválido! Digite até 7 dígitos.");
                } else if (siapeExiste(novoSiape, u)) {
                    System.out.println("SIAPE já cadastrado por outro professor! Digite um SIAPE diferente.");
                } else {
                    break;
                }
            }
            
            p.setSiape(novoSiape);

            System.out.println("Professor editado com sucesso!");
        }

        // Se for Aluno, edita matrícula (com validação de duplicidade) e CRE
        else if (u instanceof Aluno a) {

            System.out.print("Nova matrícula: ");
            String novaMatricula = lerString("Matrícula");

            // Se a nova matrícula já existir, mantém a antiga
            if (!matriculaExiste(novaMatricula)) {
                a.setMatricula(novaMatricula);
            } else {
                System.out.println("Matrícula já existe! Mantendo a antiga.");
            }

            System.out.print("Novo CRE (0 a 100): ");
            a.setCre(lerCre());

            System.out.println("Aluno editado com sucesso!");
        }
    }

    // ====================== REMOVER ======================

    // Método para remover um usuário da lista, buscando pelo CPF
    static void remover() {

        if (lista.isEmpty()) {
            System.out.println("Nenhum usuario cadastrado.");
            return;
        }

        System.out.print("CPF do usuário a remover: ");
        String cpfBusca = lerString("CPF");

        Usuario u = buscar(cpfBusca);

        if (u != null) {
            lista.remove(u); 
            System.out.println("Usuário removido com sucesso!");
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }
}
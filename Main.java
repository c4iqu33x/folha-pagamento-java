import java.util.ArrayList;
import java.util.Scanner;

abstract class Funcionario {
    protected String nome;
    protected String matricula;
    protected static final double SALARIO_BASE = 2000.0;

    public Funcionario(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public double getSalarioBase() {
        return SALARIO_BASE;
    }

    public abstract double getExtra();

    public double getSalarioFinal() {
        return SALARIO_BASE + getExtra();
    }

    public abstract String getDescricaoExtra();
}

class FuncionarioPadrao extends Funcionario {

    public FuncionarioPadrao(String nome, String matricula) {
        super(nome, matricula);
    }

    @Override
    public double getExtra() {
        return 0;
    }

    @Override
    public String getDescricaoExtra() {
        return "Extras";
    }
}

class FuncionarioComissionado extends Funcionario {
    private double valorVendas;
    private double percentualComissao;

    public FuncionarioComissionado(String nome, String matricula,
                                   double valorVendas,
                                   double percentualComissao) {
        super(nome, matricula);
        this.valorVendas = valorVendas;
        this.percentualComissao = percentualComissao;
    }

    @Override
    public double getExtra() {
        return valorVendas * (percentualComissao / 100.0);
    }

    @Override
    public String getDescricaoExtra() {
        return "Comissão";
    }
}

class FuncionarioProducao extends Funcionario {
    private int quantidadePecas;
    private double valorPeca;

    public FuncionarioProducao(String nome, String matricula,
                               int quantidadePecas,
                               double valorPeca) {
        super(nome, matricula);
        this.quantidadePecas = quantidadePecas;
        this.valorPeca = valorPeca;
    }

    @Override
    public double getExtra() {
        return quantidadePecas * valorPeca;
    }

    @Override
    public String getDescricaoExtra() {
        return "Produtividade";
    }
}

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Funcionario> funcionarios = new ArrayList<>();

    public static void main(String[] args) {

        int opcao;

        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarPadrao();
                    break;

                case 2:
                    cadastrarComissionado();
                    break;

                case 3:
                    cadastrarProducao();
                    break;

                case 4:
                    gerarFolhaPagamento();
                    break;

                case 0:
                    System.out.println("Programa encerrado.");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    private static void exibirMenu() {
        System.out.println("\n===== FOLHA DE PAGAMENTO =====");
        System.out.println("1 - Cadastrar Funcionário Padrão");
        System.out.println("2 - Cadastrar Funcionário Comissionado");
        System.out.println("3 - Cadastrar Funcionário Produção");
        System.out.println("4 - Gerar Folha de Pagamento");
        System.out.println("0 - Sair");
    }

    private static void cadastrarPadrao() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();

        funcionarios.add(new FuncionarioPadrao(nome, matricula));

        System.out.println("Funcionário cadastrado.");
    }

    private static void cadastrarComissionado() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();

        double vendas = lerDouble("Informe valor das vendas: ");
        double comissao = lerDouble("Informe comissão percentual: ");

        funcionarios.add(
                new FuncionarioComissionado(
                        nome,
                        matricula,
                        vendas,
                        comissao
                )
        );

        System.out.println("Funcionário cadastrado.");
    }

    private static void cadastrarProducao() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();

        int quantidade = lerInteiro("Informe qtde de peças: ");
        double valorPeca = lerDouble("Informe valor da peça: ");

        funcionarios.add(
                new FuncionarioProducao(
                        nome,
                        matricula,
                        quantidade,
                        valorPeca
                )
        );

        System.out.println("Funcionário cadastrado.");
    }

    private static void gerarFolhaPagamento() {

        System.out.println("\n===== FOLHA DE PAGAMENTO =====");
        System.out.println("Total de pessoas cadastradas: " + funcionarios.size());

        for (Funcionario funcionario : funcionarios) {

            System.out.println("\nNome: " + funcionario.getNome());
            System.out.println("Matrícula: " + funcionario.getMatricula());
            System.out.println("Salário Fixo: " + funcionario.getSalarioBase());
            System.out.println(
                    funcionario.getDescricaoExtra() + ": "
                            + funcionario.getExtra()
            );
            System.out.println(
                    "Salário Final: "
                            + funcionario.getSalarioFinal()
            );
        }
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                int valor = Integer.parseInt(scanner.nextLine());

                if (valor < 0) {
                    System.out.println("Digite um valor positivo.");
                    continue;
                }

                return valor;

            } catch (NumberFormatException e) {
                System.out.println("Valor inválido.");
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                double valor = Double.parseDouble(
                        scanner.nextLine().replace(",", ".")
                );

                if (valor < 0) {
                    System.out.println("Digite um valor positivo.");
                    continue;
                }

                return valor;

            } catch (NumberFormatException e) {
                System.out.println("Valor inválido.");
            }
        }
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe principal
public class SistemaBancario {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Banco banco = new Banco("Banco Peres");

        int opcao;
        do {
            System.out.println("\n=== " + banco.getNome() + " ===");
            System.out.println("1. Criar conta");
            System.out.println("2. Depositar");
            System.out.println("3. Sacar");
            System.out.println("4. Transferir");
            System.out.println("5. Ver saldo");
            System.out.println("6. Listar contas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Nome do cliente: ");
                    sc.nextLine();
                    String nome = sc.nextLine();
                    banco.criarConta(nome);
                    break;
                case 2:
                    System.out.print("Número da conta: ");
                    int numDep = sc.nextInt();
                    System.out.print("Valor: ");
                    double valDep = sc.nextDouble();
                    banco.depositar(numDep, valDep);
                    break;
                case 3:
                    System.out.print("Número da conta: ");
                    int numSaq = sc.nextInt();
                    System.out.print("Valor: ");
                    double valSaq = sc.nextDouble();
                    banco.sacar(numSaq, valSaq);
                    break;
                case 4:
                    System.out.print("Conta origem: ");
                    int origem = sc.nextInt();
                    System.out.print("Conta destino: ");
                    int destino = sc.nextInt();
                    System.out.print("Valor: ");
                    double valor = sc.nextDouble();
                    banco.transferir(origem, destino, valor);
                    break;
                case 5:
                    System.out.print("Número da conta: ");
                    int numSaldo = sc.nextInt();
                    banco.mostrarSaldo(numSaldo);
                    break;
                case 6:
                    banco.listarContas();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        sc.close();
    }
}

// criação de Classe Banco //
class Banco {
    private String nome;
    private List<Conta> contas;
    private int contadorContas = 1;

    public Banco(String nome) {
        this.nome = nome;
        this.contas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void criarConta(String titular) {
        Conta nova = new Conta(contadorContas++, titular);
        contas.add(nova);
        System.out.println("Conta criada com sucesso! Número: " + nova.getNumero());
    }

    public Conta buscarConta(int numero) {
        for (Conta c : contas) {
            if (c.getNumero() == numero) return c;
        }
        return null;
    }

    public void depositar(int numero, double valor) {
        Conta conta = buscarConta(numero);
        if (conta != null) conta.depositar(valor);
        else System.out.println("Conta não encontrada!");
    }

    public void sacar(int numero, double valor) {
        Conta conta = buscarConta(numero);
        if (conta != null) conta.sacar(valor);
        else System.out.println("Conta não encontrada!");
    }

    public void transferir(int origem, int destino, double valor) {
        Conta cOrigem = buscarConta(origem);
        Conta cDestino = buscarConta(destino);
        if (cOrigem == null || cDestino == null) {
            System.out.println("Conta inválida!");
            return;
        }
        if (cOrigem.transferir(cDestino, valor))
            System.out.println("Transferência realizada!");
        else
            System.out.println("Saldo insuficiente!");
    }

    public void mostrarSaldo(int numero) {
        Conta conta = buscarConta(numero);
        if (conta != null)
            System.out.println("Saldo atual: R$ " + conta.getSaldo());
        else
            System.out.println("Conta não encontrada!");
    }

    public void listarContas() {
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
            return;
        }
        for (Conta c : contas) {
            System.out.println(c);
        }
    }
}

// Classe Conta
class Conta {
    private int numero;
    private String titular;
    private double saldo;

    public Conta(int numero, String titular) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = 0.0;
    }

    public int getNumero() { return numero; }
    public String getTitular() { return titular; }
    public double getSaldo() { return saldo; }

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            System.out.println("Depósito de R$ " + valor + " realizado.");
        } else System.out.println("Valor inválido!");
    }

    public void sacar(double valor) {
        if (valor <= saldo && valor > 0) {
            saldo -= valor;
            System.out.println("Saque de R$ " + valor + " realizado.");
        } else System.out.println("Saldo insuficiente ou valor inválido!");
    }

    public boolean transferir(Conta destino, double valor) {
        if (valor <= saldo && valor > 0) {
            this.saldo -= valor;
            destino.depositar(valor);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Conta " + numero + " | Titular: " + titular + " | Saldo: R$ " + saldo;
    }
}

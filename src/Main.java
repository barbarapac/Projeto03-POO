import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int encerrar = 1;
        String opecaoCadastro = "", opcaoEntrada = "";

        Scanner sString = new Scanner(System.in);
        Scanner sInt = new Scanner(System.in);

        do {
            do {
                try {
                    menu();
                    opecaoCadastro = sString.nextLine();
                } catch (Exception e){
                    opecaoCadastro = "5";
                }
            } while (opecaoCadastro == "5");

            switch (opecaoCadastro) {
                case "0":
                    encerrar = 0;
                    break;
                case "1":
                    do {
                        Pessoa pessoa = new Pessoa();
                        pessoa.cadastarPessoa();

                        System.out.println("| -------------------------------------------------- |");
                        System.out.println("| Deseja inserir um nova Pessoa? (1-sim/0-não):      |");
                        System.out.println("| -------------------------------------------------- |");
                        System.out.printf("| Resposta: ");
                        opcaoEntrada = sString.nextLine();
                    } while (opcaoEntrada == "1");
                    break;
                case "2":
                    do {
                        Conta conta = new Conta();

                        conta.cadastarConta();

                        System.out.println("| ---------------------------------------------------- |");
                        System.out.println("|  Deseja inserir uma nova Conta? (1-sim/0-não):       |");
                        System.out.println("| ---------------------------------------------------- |");
                        System.out.printf("| Resposta: ");
                        opcaoEntrada = sString.nextLine();
                    } while (opcaoEntrada == "1");
                    break;
                case "3":
                    Conta conta = new Conta();

                    conta.imprimeExtrato();
                    break;
                case "4":
                    Pessoa pessoa = new Pessoa();

                    pessoa.imprimePessoa();
                    break;
                default:
                    System.out.println("Opeção inválida");
            }
        } while (encerrar == 1);
    }

    public static void menu(){
        System.out.println("| --------------------------------------------------------- OPÇÕES BANCO ---------------------------------------------------------------------- |");
        System.out.println("|      1 - Cadastrar Pessoa        |     2 - Cadastar Conta     |     3 - Extrato conta     |     4 - Pesquisar Pessoa     |     0 - SAIR       |");
        System.out.println("| --------------------------------------------------------------------------------------------------------------------------------------------- |");
        System.out.printf("| Resposta: ");

    }
}


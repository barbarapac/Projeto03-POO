import java.sql.ResultSet;
import java.util.Scanner;

public class Conta {
    private int idpessoa, agencia, numeroConta;
    private String banco;
    private double saldo;

    public Conta(){}

    @SuppressWarnings("all")
    public void cadastarConta(){
        Conexao conexao = new Conexao();

        Scanner sInt = new Scanner(System.in);
        Scanner sString = new Scanner(System.in);

        int pessoaCorreta = 0;

        System.out.println("| -------------------------------------------------------------------------------------------------- |");
        System.out.println("| ---------------------------------------- CADASTRO CONTA ------------------------------------------ |");
        System.out.println("| -------------------------------------------------------------------------------------------------- |");
        System.out.println("|                                                                                                    |");

        do {
            System.out.printf("| Digite o CFP/CNPJ da pessoa: ");
            String cnpjPessoa = sString.nextLine();
            String nomePessoa = buscarPessoa(cnpjPessoa);


            if(nomePessoa == "N"){
                System.out.println("");
                System.out.println("|---------------------------------|");
                System.out.println("| ATENÇÃO: Pessoa não encontrada! |");
                System.out.println("|---------------------------------|");
                System.out.println("");
            }else{
                System.out.println("| --------------------------------------------------------------------------- |");
                System.out.println("|  A conta  ser cadastrada é para a pessoa " + nomePessoa + " (1-sim/0-não):  |");
                System.out.println("| --------------------------------------------------------------------------- |");
                System.out.printf("| Resposta: ");
                pessoaCorreta = sInt.nextInt();
            }

        }while (pessoaCorreta == 0);


        System.out.printf("| Numero Conta: ");
            numeroConta = sInt.nextInt();
        System.out.printf("| Agência: ");
            agencia = sInt.nextInt();
        System.out.printf("| Banco: ");
            banco = sString.nextLine();
        System.out.printf("| Saldo: ");
            saldo = sInt.nextDouble();


        String sql = "INSERT INTO conta (idpessoa, agencia, numeroconta, saldo, banco) " +
                "VALUES (" + idpessoa + ", " + agencia +  ", " + numeroConta + ", " + saldo + ",'" + banco + "'"+ ");";

        int res = conexao.executeSQL(sql);
        if(res >0) {
            System.out.println("Conta cadastrada com sucesso!");
        }else {
            System.out.println("Erro ao cadastrar conta.");
        }

    }


    @SuppressWarnings("all")
    public void imprimeExtrato(){

        int novaPesquisaConta = 1;

        Scanner sInt = new Scanner(System.in);

        System.out.println("");

        System.out.println("| -------------------------------------------------------------------------------------------------- |");
        System.out.println("| ---------------------------------------- EXTRATO CONTA ------------------------------------------- |");
        System.out.println("| -------------------------------------------------------------------------------------------------- |");
        System.out.println("|                                                                                                    |");

        do{
            System.out.printf("| Digite o numero da conta: ");
            int numeroConta = sInt.nextInt();
            int numeroContaResult = buscarConta(numeroConta);

            if(numeroContaResult == 0){
                System.out.println("");
                System.out.println("|---------------------------------|");
                System.out.println("| ATENÇÃO: Conta não encontrada!  |");
                System.out.println("|---------------------------------|");
                System.out.println("");
            }else{
                Conexao conexao = new Conexao();

                String sql = "select c.agencia, c.banco, c.numeroconta, c.saldo, p.nome, p.idade, p.cnjpcpf from conta c join pessoa p on (c.idpessoa = p.idpessoa) where numeroconta = " + numeroContaResult;
                ResultSet rs = conexao.executarBusca(sql);
                try {
                    while(rs.next()) {
                        agencia = rs.getInt("agencia");
                        banco = rs.getString("banco");
                        numeroConta = rs.getInt("numeroconta");
                        String nomePessoa = rs.getString("nome").trim();
                        saldo = rs.getDouble("saldo");
                        int idade = rs.getInt("idade");
                        String cnpjcpf = rs.getString("cnjpcpf").trim();


                        System.out.println("|       Titular: " + nomePessoa + "      | Idade: " + idade + "      | CNPJ/CPF: " + cnpjcpf);
                        System.out.println("|");
                        System.out.println("|       Numero conta: " + numeroConta + "      | Agência: " + agencia + " |      Banco: " + banco);
                        System.out.println("|------------------------------------------------------------------------- SALDO: " + saldo + " |");
                        System.out.println("    ");

                        deletaConta(numeroContaResult);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            System.out.println("| --------------------------------------------------------------------------- |");
            System.out.println("|  Deseja pesquisar uma nova conta? (1-sim/0-não):  |");
            System.out.println("| --------------------------------------------------------------------------- |");
            System.out.printf("| Resposta: ");
            novaPesquisaConta = sInt.nextInt();
        } while(novaPesquisaConta == 1);
    }

    @SuppressWarnings("all")
    private String buscarPessoa(String parmCpfCnpj){
        Conexao conexao = new Conexao();

        String sql = "Select * from pessoa  where cnjpcpf = '" + parmCpfCnpj + "'";
        ResultSet rs = conexao.executarBusca(sql);
        try {
            while(rs.next()) {
                idpessoa = rs.getInt("idpessoa");

                return rs.getString("nome").trim();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "N";
    }

    @SuppressWarnings("all")
    private int buscarConta(int parmNumConta){
        Conexao conexao = new Conexao();

        String sql = "select * from conta where numeroconta = " + parmNumConta;
        ResultSet rs = conexao.executarBusca(sql);
        try {
            while(rs.next()) {
                return rs.getInt("numeroconta");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


    @SuppressWarnings("all")
    public void deletaConta(int parmNumConta){
        Scanner s = new Scanner(System.in);

        System.out.println("| ------------------------------------------------------------ |");
        System.out.println("|  Deseja deletar a conta acima? (1-sim/0-não):                |");
        System.out.println("| ------------------------------------------------------------ |");
        System.out.printf("| Reposta: ");
        int delete = s.nextInt();

        if(delete == 1){
            Conexao conexao = new Conexao();

            String sql = "delete from conta where numeroconta = " + parmNumConta;

            conexao.deletar(sql);
        }
    }
}

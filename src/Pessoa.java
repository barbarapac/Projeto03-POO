import java.sql.ResultSet;
import java.util.Scanner;

public class Pessoa {
    private int idpessoa, idade;
    private String nome, cnpjcpf;

    public Pessoa(){}

    @SuppressWarnings("all")
    public void cadastarPessoa(){

        Conexao conexao = new Conexao();

        Scanner sInt = new Scanner(System.in);
        Scanner sString = new Scanner(System.in);

        System.out.println("| -------------------------------------------------------------------------------------------------- |");
        System.out.println("| ---------------------------------------- CADASTRO PESSOA ----------------------------------------- |");
        System.out.println("| -------------------------------------------------------------------------------------------------- |");
        System.out.println("");
        System.out.printf("| Nome: ");
            nome = sString.nextLine();
        System.out.printf("| CNPJ/CPF: ");
            cnpjcpf = sString.nextLine();
        System.out.printf("| Idade: ");
            idade = sInt.nextInt();



		String sql = "INSERT INTO pessoa (nome, cnjpcpf, idade) VALUES (" +  "'" + nome + "', " + "'" + cnpjcpf + "', " + idade+ ");";
		int res = conexao.executeSQL(sql);
		if(res >0) {
			System.out.println("Pessoa cadastrada com sucesso!");
		}else {
			System.out.println("Erro ao cadastrar pessoa.");
		}
    }

    @SuppressWarnings("all")
    public void imprimePessoa(){
        int novaPesquisaPessoa = 1;

        Scanner sString = new Scanner(System.in);
        Scanner sInt = new Scanner(System.in);

        System.out.println("");

        System.out.println("| -------------------------------------------------------------------------------------------------- |");
        System.out.println("| --------------------------------------- PESQUISA PESSOA ------------------------------------------ |");
        System.out.println("| -------------------------------------------------------------------------------------------------- |");
        System.out.println("|                                                                                                    |");

        do{
            System.out.printf("| Digite o CNPJ/CPF da pessoa: ");
            String cnpjCpf = sString.nextLine();
            int codPessoaResult = buscaPessoa(cnpjCpf);

            if(codPessoaResult == 0){
                System.out.println("");
                System.out.println("|---------------------------------|");
                System.out.println("| ATENÇÃO: Pessoa não encontrada! |");
                System.out.println("|---------------------------------|");
                System.out.println("");
            }else{
                Conexao conexao = new Conexao();

                String sql = "select * from pessoa where idpessoa = " + codPessoaResult;
                ResultSet rs = conexao.executarBusca(sql);
                try {
                    while(rs.next()) {
                        nome = rs.getString("nome");
                        cnpjCpf = rs.getString("cnjpcpf");
                        idade = rs.getInt("idade");

                        System.out.println("| ---------------------------------------------------------------- |");
                        System.out.println("| -------------------------- DADOS PESSOA ------------------------ |");
                        System.out.println("| ---------------------------------------------------------------- |");
                        System.out.println("|                                                                  |");
                        System.out.println("| Nome: " + nome);
                        System.out.println("| Idade: " + idade);
                        System.out.println("| CNPJ/CPF: " + cnpjCpf);
                        System.out.println("| ---------------------------------------------------------------- |");


                        deletaPessoa(codPessoaResult);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            System.out.println("| --------------------------------------------------------------------------- |");
            System.out.println("|  Deseja pesquisar uma nova pessoa? (1-sim/0-não):                           |");
            System.out.println("| --------------------------------------------------------------------------- |");
            System.out.printf("| Resposta: ");
            novaPesquisaPessoa = sInt.nextInt();
        } while(novaPesquisaPessoa == 1);

    }

    @SuppressWarnings("all")
    private int buscaPessoa(String cnpjCpfBusca){
        Conexao conexao = new Conexao();

        String sql = ("Select * from pessoa where cnjpcpf = '" + cnpjCpfBusca + "'");
        ResultSet rs = conexao.executarBusca(sql);
        try {
            while(rs.next()) {
                return rs.getInt("idpessoa");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @SuppressWarnings("all")
    private void deletaPessoa(int parmNumPessoa){
        Scanner s = new Scanner(System.in);

        System.out.println("| ------------------------------------------------------------ |");
        System.out.println("|  Deseja deletar a pessoa acima? (1-sim/0-não):                |");
        System.out.println("| ------------------------------------------------------------ |");
        System.out.printf("| Reposta: ");
        int delete = s.nextInt();

        if(delete == 1){
            Conexao conexao = new Conexao();

            String sql = "delete from pessoa where idpessoa = " + parmNumPessoa;

            conexao.deletar(sql);
        }
    }
}

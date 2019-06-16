import java.sql.*;

public class Conexao {

    private String url;
    private String usuario;
    private String senha;
    private Connection con;


    @SuppressWarnings("all")
    Conexao(){
        url = "jdbc:postgresql://localhost:5432/projeto3";
        usuario = "postgres";
        senha = "postgres";

        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, usuario, senha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("all")
    public int executeSQL(String sql) {
        try {
            Statement stm = con.createStatement();
            int res = stm.executeUpdate(sql);
            con.close();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @SuppressWarnings("all")
    public ResultSet executarBusca(String sql){
        try{
            Statement stm = con.createStatement();
            ResultSet res = stm.executeQuery(sql);
            con.close();
            return res;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("all")
    public void deletar(String sql) {
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.execute();
            stmt.close();
        } catch (Exception e) {
            System.out.println("|------------------------------------------|");
            System.out.println("| ATENÇÃO: Deleção não pode ser realizada! |");
            System.out.println("|------------------------------------------|");
            System.out.println("");
        }
    }
}

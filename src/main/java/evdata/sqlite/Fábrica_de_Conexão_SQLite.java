package evdata.sqlite;

import evdata.filemanager.Gerenciador_do_Arquivo_SQLite;
import evdata.sqlite.dataadm._i.Pesquisa;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Fábrica_de_Conexão_SQLite {
    
    //private static String ThisDB = "Contas-DB.db";
    private static final String THIS_JDBC_CONNECTION_STRING_PREFIX = "jdbc:sqlite:";
    //private static final String THIS_DEFAULT_DB_JDBC_CONNECTION_STRING = "jdbc:sqlite:Contas-DB.db";
    
    //public static void setDatabaseFilePath(String DatabaseFilePath) throws java.sql.SQLException{ThisDB = DatabaseFilePath;}
    
    public static java.sql.Connection getConnection(String thisDB) throws java.sql.SQLException{
        System.out.println(THIS_JDBC_CONNECTION_STRING_PREFIX+thisDB+"\n");
        return java.sql.DriverManager.getConnection(THIS_JDBC_CONNECTION_STRING_PREFIX+thisDB);
    }

    public static java.sql.ResultSet get_selection_resultset(String thisDB, String script) throws java.sql.SQLException {
        return getConnection(thisDB).createStatement().executeQuery(script);
    }

    public static java.sql.ResultSet get_full_resultset_from_table(String thisDB, Pesquisa pesquisa) throws java.sql.SQLException {
        return getConnection(thisDB).createStatement().executeQuery("select * from " + pesquisa.nome_da_entidade());
    }
    
    public static java.sql.ResultSetMetaData get_resultset_metadata_from_table(String thisDB, Pesquisa pesquisa) throws java.sql.SQLException {
        return get_empty_resultset_from_table(thisDB, pesquisa).getMetaData();
    }
    
    //private static java.sql.Connection getDefaultConnection() throws java.sql.SQLException{return java.sql.DriverManager.getConnection(THIS_DEFAULT_DB_JDBC_CONNECTION_STRING);}

    private static java.sql.ResultSet get_empty_resultset_from_table(String thisDB, Pesquisa pesquisa) throws java.sql.SQLException {
        return getConnection(thisDB).createStatement().executeQuery("select * from " + pesquisa.nome_da_entidade() + " LIMIT 0");
    }
    
    public static int get_column_count_from_table(String thisDB, Pesquisa pesquisa) {
        try {
            return get_resultset_metadata_from_table(thisDB, pesquisa).getColumnCount();
        } catch (java.sql.SQLException ex) {
            java.util.logging.Logger.getLogger(Fábrica_de_Conexão_SQLite.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return -1;
        }
    }

    public static int consultar_último_id_de_linha_inserida(String thisDB) throws java.sql.SQLException {
        java.sql.ResultSet rs = getConnection(thisDB).createStatement()
            .executeQuery("select last_insert_rowid()");
        rs.next();
        return rs.getInt("id");
    }
    public static void executar_rotinas_sql(String thisDB, String[] scripts){
        java.sql.Connection connection = null;
        if (scripts.length==0 || scripts==null){
            return;
        }
        boolean vac = false;
        try {
            //SQLiteConnectionFactory.setDatabaseFilePath(caminho_completo_e_nome_do_arquivo_tmp);
            connection = Fábrica_de_Conexão_SQLite.getConnection(thisDB);
            vac  =connection.getAutoCommit();
            connection.setAutoCommit(false);

            java.sql.Statement createdStatement = connection.createStatement();
            for (String textStatement: scripts){ createdStatement.addBatch(textStatement); }
            createdStatement.executeBatch();

            connection.commit();
            connection.setAutoCommit(vac);
            connection.close();
        } catch (java.sql.SQLException e) { System.out.println(e.getMessage());
            if (connection != null){
                try {
                    connection.setAutoCommit(vac);
                    connection.close();
                } catch (java.sql.SQLException ex) {
                    Logger.getLogger(Gerenciador_do_Arquivo_SQLite.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
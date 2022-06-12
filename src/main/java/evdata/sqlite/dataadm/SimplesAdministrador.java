package evdata.sqlite.dataadm;

import evdata.sqlite.Fábrica_de_Conexão_SQLite;
import evdata.sqlite.dataadm._i.Pesquisa;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SimplesAdministrador implements evdata.sqlite.dataadm._i.Administrador {

    @Override
    public java.util.ArrayList<String[]> obter_dados(String thisDB, evdata.sqlite.dataadm._i.Pesquisa pesquisa){//, String args){
        
        java.sql.ResultSet rs;
                
        //if (args==null){]
        if (true){
            rs = obter_ResultSet(
                //com.mrcontas.model.tools.DataModelFactory.tabela_por_tipo_de_pesquisa(
                    thisDB, pesquisa
                //)
            );
        } else{
            rs = obter_ResultSet(
                //com.mrcontas.model.tools.DataModelFactory.tabela_por_tipo_de_pesquisa(
                    thisDB, pesquisa
                //)
                //+ args
            );
        }
        
        if (rs == null){
            return null;
        }
        
        java.util.ArrayList<String[]> lista_de_linhas = new java.util.ArrayList<>();
        
        try {
            while(rs.next()){
                java.util.ArrayList<String> uma_linha = new java.util.ArrayList<>();
                for(int index=0; index<rs.getMetaData().getColumnCount(); index++){
                    uma_linha.add( rs.getString(index+1) );
                }
                lista_de_linhas.add(uma_linha.toArray(new String[0]));
            }
        } catch (SQLException ex) {
            Logger.getLogger(evdata.sqlite.dataadm._i.Administrador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return lista_de_linhas;
        
    }
    
    public java.util.ArrayList<String> get_Nomes_Colunas(String thisDB, Pesquisa pesquisa){
        java.sql.ResultSetMetaData resultSetMetaData = getMetaDados(thisDB, pesquisa);
        if(resultSetMetaData==null){
            return null;
        } else{
            try {
                java.util.ArrayList<String> nomes_de_colunas = new java.util.ArrayList <>();
                for(int index = 0; index< resultSetMetaData.getColumnCount(); index++){
                    nomes_de_colunas.add(resultSetMetaData.getColumnName(index+1));
                }
                return nomes_de_colunas;
            } catch (SQLException ex) {
                Logger.getLogger(evdata.sqlite.dataadm._i.Administrador.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        }
    }
    
    
    
    private java.sql.ResultSet obter_ResultSet(String thisDB, Pesquisa pesquisa){
        try {
            return Fábrica_de_Conexão_SQLite.get_full_resultset_from_table(
                thisDB, pesquisa
            );
        } catch (SQLException ex) {
            Logger.getLogger(evdata.sqlite.dataadm._i.Administrador.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private java.sql.ResultSetMetaData getMetaDados(String thisDB, Pesquisa pesquisa){
        try {
            return Fábrica_de_Conexão_SQLite.get_resultset_metadata_from_table(
                //com.mrcontas.model.tools.DataModelFactory.tabela_por_tipo_de_pesquisa(
                    thisDB, pesquisa
                //)
            );
        } catch (SQLException ex) {
            Logger.getLogger(evdata.sqlite.dataadm._i.Administrador.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }   

    
}
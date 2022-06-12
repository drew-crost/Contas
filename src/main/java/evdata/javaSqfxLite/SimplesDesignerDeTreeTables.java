package evdata.javaSqfxLite;

import evdata.sqlite.dataadm.SimplesAdministrador;
import evdata.sqlite.dataadm._i.Pesquisa;
import javafx.scene.control.TreeTableView;
import evdata.sqlite.dataadm._i.Administrador;


public class SimplesDesignerDeTreeTables {

    public SimplesDesignerDeTreeTables() {
    }

    private java.util.List<javafx.scene.control.TreeTableColumn> get_Colunas_FX(Pesquisa pesquisa, Administrador pesquisador, String thisDB) {
                java.util.ArrayList<String> nomes_de_colunas = pesquisador.get_Nomes_Colunas(thisDB, pesquisa);
        if(nomes_de_colunas==null){
            return null ;
        }    
        java.util.List<javafx.scene.control.TreeTableColumn> colunas = new java.util.ArrayList <>();
        javafx.scene.control.TreeTableColumn<String[], String> coluna;
        
        for(int index = 0; index< nomes_de_colunas.size(); index++){
            final int index_buffer = index;
            coluna = new javafx.scene.control.TreeTableColumn<>(nomes_de_colunas.get(index));
            coluna.setCellValueFactory((javafx.scene.control.TreeTableColumn.CellDataFeatures<String[], String> p) 
                    -> {
                return new javafx.beans.property.SimpleStringProperty((p.getValue().getValue()[index_buffer]));
            });
            colunas.add(coluna);
        }
        return colunas;
    }

    
    
    void erguer_Colunas_FX(TreeTableView ttview, Pesquisa pesquisa, boolean primeira_vez, String thisDB) {
         Administrador pesquisador = new SimplesAdministrador();
        if(primeira_vez){
            ttview.getColumns().clear();
            ttview.getColumns().addAll(get_Colunas_FX( pesquisa, pesquisador , thisDB));
        };
        /*ttview.setItems(
            javafx.collections.FXCollections.observableArrayList(pesquisador.obter_dados(tipo_de_pesquisa, null).toArray(new String[0]) )
        );*/
    }

        
}

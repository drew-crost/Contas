package evdata.javaSqfxLite;
import evdata.sqlite.dataadm.SimplesAdministrador;
import evdata.sqlite.dataadm._i.Administrador;
import evdata.sqlite.dataadm._i.Pesquisa;

class SimplesDesignerDeTabelas<TV extends javafx.scene.control.TableView> implements evdata.javaSqfxLite._i.Designer_de_Tabelas <TV>{
        
    private java.util.List<javafx.scene.control.TableColumn> get_Colunas_FX(Pesquisa pesquisa, Administrador administrador, String thisDB){
        java.util.ArrayList<String> nomes_de_colunas = administrador.get_Nomes_Colunas(thisDB, pesquisa);
        if(nomes_de_colunas==null){
            return null ;
        }    
        java.util.List<javafx.scene.control.TableColumn> colunas = new java.util.ArrayList <>();
        javafx.scene.control.TableColumn<String[], String> coluna;
        
        for(int index = 0; index< nomes_de_colunas.size(); index++){
            final int index_buffer = index;
            coluna = new javafx.scene.control.TableColumn<>(nomes_de_colunas.get(index));
            coluna.setCellValueFactory(
                    (javafx.scene.control.TableColumn.CellDataFeatures<String[], String> p) 
                    -> new javafx.beans.property.SimpleStringProperty((p.getValue()[index_buffer])));
            colunas.add(coluna);
        }
        return colunas;
    }

    void erguer_Colunas_FX(TV tview, Pesquisa pesquisa, boolean primeira_vez, boolean checkbox_em_coluna_inicial, String thisDB){
        Administrador administrador = new SimplesAdministrador();
        if(primeira_vez){
            tview.getColumns().clear();
            tview.getColumns().addAll(get_Colunas_FX(pesquisa, administrador, thisDB));
        };
        tview.setItems(
            javafx.collections.FXCollections.observableArrayList(administrador.obter_dados(
                    thisDB, pesquisa
                    //, null
            //)
            )
        ));
    }
    
    @Override
    public void erguer_Colunas_FX(TV tview, Pesquisa pesquisa, boolean primeira_vez, String thisDB){
        erguer_Colunas_FX(tview, pesquisa, primeira_vez, false, thisDB);
    }
}

package evdata.javaSqfxLite._i;

import evdata.sqlite.dataadm._i.Pesquisa;

public interface Designer_de_Tabelas <TV extends javafx.scene.control.TableView> {

    void erguer_Colunas_FX(TV tview, Pesquisa pesquisa, boolean primeira_vez, String thisDB);

    
}

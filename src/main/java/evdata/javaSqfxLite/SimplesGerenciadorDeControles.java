package evdata.javaSqfxLite;

import evdata.sqlite.dataadm._i.Pesquisa;
import javafx.scene.control.TreeTableView;

/**
 *
 * @param <TV>
 */
public class SimplesGerenciadorDeControles
    < TV extends javafx.scene.control.TableView //, D extends evdata.javaSqfxLite.i.Designer<TV, TP> //, TP //, P extends siscon.dados.i.Administrador <TP>
    > 
        implements evdata.javaSqfxLite._i.Gerenciador_de_Controles <TV> {
    @Override
    public void montar_Tabela_FX
                                ( TV tview //, D designer
                                , Pesquisa pesquisa
                                , boolean primeira_vez
                                ,String thisDB
                                )
    {                                                               
        new SimplesDesignerDeTabelas().erguer_Colunas_FX(tview, pesquisa ,primeira_vez, thisDB);
    }    

    public void montar_TreeTable_FX(TreeTableView treetable_view, Pesquisa pesquisa, boolean primeira_vez, String thisDB) {
        new SimplesDesignerDeTreeTables().erguer_Colunas_FX(treetable_view, pesquisa ,primeira_vez, thisDB);
    }
    
}

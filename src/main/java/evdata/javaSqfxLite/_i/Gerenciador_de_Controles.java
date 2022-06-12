package evdata.javaSqfxLite._i;

import evdata.sqlite.dataadm._i.Pesquisa;

/**
 *
 * @param <TV>
 * @param <TP>
 */
//TV = TableView
public interface Gerenciador_de_Controles 
    < TV extends javafx.scene.control.TableView> 
{
    public void montar_Tabela_FX(TV tview, Pesquisa pesquisa, boolean primeira_vez, String thisDB);
    
}

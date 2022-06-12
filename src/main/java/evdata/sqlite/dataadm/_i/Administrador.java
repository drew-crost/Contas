package evdata.sqlite.dataadm._i;

public interface Administrador {
    
    public java.util.ArrayList<String[]> obter_dados(String thisDB, Pesquisa pesquisa);//, String args);
    
    public java.util.ArrayList<String> get_Nomes_Colunas(String thisDB, Pesquisa pesquisa);
    
}
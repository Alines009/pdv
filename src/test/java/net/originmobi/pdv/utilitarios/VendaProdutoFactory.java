package net.originmobi.pdv.utilitarios;

import net.originmobi.pdv.enumerado.Ativo;
import net.originmobi.pdv.enumerado.VendaSituacao;
import net.originmobi.pdv.enumerado.produto.ProdutoBalanca;
import net.originmobi.pdv.enumerado.produto.ProdutoControleEstoque;
import net.originmobi.pdv.enumerado.produto.ProdutoSubstTributaria;
import net.originmobi.pdv.enumerado.produto.ProdutoVendavel;
import net.originmobi.pdv.model.Produto;
import net.originmobi.pdv.model.ProdutoEstoque;
import net.originmobi.pdv.model.Venda;
import net.originmobi.pdv.model.VendaProduto;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class VendaProdutoFactory {
	
	
	public static Produto criarProdutoValido (){
        Produto produto = new Produto();
        produto.setCodigo(5L);
        produto.setDescricao("descricao do produto");
        produto.setValor_venda(60.00);
        produto.setValor_custo(30.00);
        produto.setValor_balanca(200.00);
        produto.setData_validade(new Date(System.currentTimeMillis()));
        produto.setData_cadastro(new Date(System.currentTimeMillis()));
        produto.setAtivo(Ativo.ATIVO);
        produto.setUnidade("Unidade");
        produto.setSubtributaria(ProdutoSubstTributaria.SIM);
        produto.setControla_estoque(ProdutoControleEstoque.SIM);
        produto.setEstoque(criarEstoque());
        produto.setBalanca(ProdutoBalanca.SIM);
        produto.setValor_balanca(200.00);
        produto.setVendavel(ProdutoVendavel.SIM);
        produto.setNcm("N C M");
        produto.setCest("C E S T");
        return  produto;
    }

    public static List<Produto> criarListaProdutoValido(){
        List<Produto> produtos = new ArrayList<>();
        produtos.add(criarProdutoValido());
        return produtos;
    }
    public static  ProdutoEstoque criarEstoque () {
        ProdutoEstoque produtoEstoque = new ProdutoEstoque();
        produtoEstoque.setCodigo(10L);
        produtoEstoque.setQtd(1000);
        return  produtoEstoque;
    }
    public static VendaProduto criarProdutosVendidosValidos(){
        VendaProduto vendaProduto = new VendaProduto();
        vendaProduto.setCodigo(10L);
        vendaProduto.setProduto(10L);
        vendaProduto.setVenda(10L);
        vendaProduto.setValor_balanca(12.00);
        return vendaProduto;
    }
    public  static List<Object> criarListaProdutosVendidos(){
        List<Object> vendaProdutos = new ArrayList<>();
        VendaProduto vendaProduto = criarProdutosVendidosValidos();
        vendaProdutos.add(vendaProduto);
        return vendaProdutos;
    }
    public static List<Object[]> criarListaQTDProdutosVendidos () {
        List<Object[]> resultado = new ArrayList<>();
        Object[] obj = {10L, 10};
        resultado.add(obj);
        return  resultado;
    }

}

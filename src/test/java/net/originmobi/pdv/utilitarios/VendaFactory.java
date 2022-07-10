package net.originmobi.pdv.utilitarios;

import net.originmobi.pdv.controller.TituloService;
import net.originmobi.pdv.enumerado.Ativo;
import net.originmobi.pdv.enumerado.VendaSituacao;
import net.originmobi.pdv.enumerado.produto.ProdutoBalanca;
import net.originmobi.pdv.enumerado.produto.ProdutoControleEstoque;
import net.originmobi.pdv.enumerado.produto.ProdutoSubstTributaria;
import net.originmobi.pdv.enumerado.produto.ProdutoVendavel;
import net.originmobi.pdv.utilitarios.VendaProdutoFactory;
import net.originmobi.pdv.model.PagamentoTipo;
import net.originmobi.pdv.model.Parcela;
import net.originmobi.pdv.model.Produto;
import net.originmobi.pdv.model.ProdutoEstoque;
import net.originmobi.pdv.model.Titulo;
import net.originmobi.pdv.model.TituloTipo;
import net.originmobi.pdv.model.Venda;
import net.originmobi.pdv.model.VendaProduto;
import net.originmobi.pdv.service.ParcelaService;
import net.originmobi.pdv.service.TituloTipoService;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class VendaFactory {	
	
	
	public static Venda criarVendaAberta() {
        Venda venda = new Venda();
        venda.setCodigo(20L);
        venda.setUsuario(UsuarioFactory.criarUsuarioValido());
        venda.setData_cadastro(new Timestamp(System.currentTimeMillis()));
        venda.setSituacao(VendaSituacao.ABERTA);
        venda.setObservacao("ABERTA");
        venda.setValor_produtos(20.00);
        venda.setData_finalizado(new Timestamp(System.currentTimeMillis()));
        venda.setValor_total(200.00);
        venda.setProduto(VendaProdutoFactory.criarListaProdutoValido());
        return venda;
} 
	
    public static Venda criarVendaFechada(){
        Venda venda = new Venda();
        venda.setCodigo(10L);
        venda.setUsuario(UsuarioFactory.criarUsuarioValido());
        venda.setData_cadastro(new Timestamp(System.currentTimeMillis()));
        venda.setSituacao(VendaSituacao.FECHADA);
        venda.setObservacao("CONCLUIDA");
        venda.setValor_produtos(100.00);
        venda.setData_finalizado(new Timestamp(System.currentTimeMillis()));
        venda.setValor_total(1000.00);
        venda.setProduto(VendaProdutoFactory.criarListaProdutoValido());
        return venda;
    }

    
    public static Long tituloParaLong(PagamentoTipo tipoPagamento) {
    	Long l = Long.valueOf((tipoPagamento.toString())).longValue();
    	return l;
    }
    public static Long vendaParaLong(Venda venda) {
    	Long l = Long.valueOf((venda.toString())).longValue();
    	return l;
    }

    public static Long posicaoProduto(Produto produto, Venda venda) {
    	List<Produto> lista = venda.getProduto();
    	Long posicao = 0L;
    	for(int i = 0; i< (lista.size()) ; i++){
    		if(((lista.get(i)).getCodigo()) == (produto.getCodigo())) {
    			posicao = produto.getCodigo();
    		}
    	}
    	
    	return posicao;
    	
    }

	public static void zerarprodutos(Venda vendaaberta) {
		vendaaberta.setValor_total(0D);

	}
	public static String[] tituloString(TituloTipoService titulos) {
		List<TituloTipo> lista = titulos.lista();
		String[] strings = lista.stream().toArray(String[]::new);
		return strings;
		
	}
	public static String[] parcelaString(ParcelaService parcelas) {
		List<Parcela> lista = parcelas.lista();
		String[] strings = lista.stream().toArray(String[]::new);
		return strings;
		
	}
	public static String[] pagamentos() {
		String[] pagamentos = {"00"};
		return pagamentos; 
	}

}

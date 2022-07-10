package net.originmobi.pdv.service;

	import net.originmobi.pdv.model.Venda;
	import net.originmobi.pdv.model.VendaProduto;
	import net.originmobi.pdv.repository.VendaProdutosRepository;
	import net.originmobi.pdv.utilitarios.VendaProdutoFactory;
	import net.originmobi.pdv.utilitarios.VendaFactory;
	import org.assertj.core.api.Assertions;
	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.DisplayName;
	import org.junit.jupiter.api.Test;
	import static org.junit.jupiter.api.Assertions.assertEquals;
	import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
	import org.mockito.ArgumentMatchers;
	import org.mockito.BDDMockito;
	import org.mockito.InjectMocks;
	import org.mockito.Mock;
	import org.springframework.test.context.junit.jupiter.SpringExtension;

	import java.util.List;

	@ExtendWith(SpringExtension.class)
	public class VendaProdutoServiceTest {

	    @InjectMocks
	    private VendaProdutoService vendaProdutoService;

	    @Mock
	    private VendaProdutosRepository vendaProdutosRepositoryMock;

	    @BeforeEach
	    void setUp(){
	    	
	        when(vendaProdutosRepositoryMock.save(ArgumentMatchers.any(VendaProduto.class))).thenReturn(VendaProdutoFactory.criarProdutosVendidosValidos());

	        when(vendaProdutosRepositoryMock.findByCodigoIn(ArgumentMatchers.anyLong())).thenReturn(VendaProdutoFactory.criarProdutosVendidosValidos());

	        when(vendaProdutosRepositoryMock.findByProdutosDaVenda(ArgumentMatchers.anyLong())).thenReturn(VendaProdutoFactory.criarListaProdutosVendidos());

	        when(vendaProdutosRepositoryMock.buscaQtdProduto(ArgumentMatchers.anyLong())).thenReturn(VendaProdutoFactory.criarListaQTDProdutosVendidos());

	        doNothing().when(vendaProdutosRepositoryMock).delete(ArgumentMatchers.any(VendaProduto.class));

	        doNothing().when(vendaProdutosRepositoryMock).removeProduto(ArgumentMatchers.anyLong());
	    }

	    @Test
	    @DisplayName("testar salvar um produto")
	    public void SalvaVendaProduto(){
	        Assertions.assertThatCode(() -> vendaProdutoService.salvar(VendaProdutoFactory.criarProdutosVendidosValidos())).doesNotThrowAnyException();
	    }

	    @Test
	    @DisplayName("Testar buscar um produto")
	    public void busca(){
	        Long expectedVendaProduto = VendaProdutoFactory.criarProdutosVendidosValidos().getCodigo();
	        VendaProduto vendaProduto = vendaProdutoService.busca(VendaProdutoFactory.criarProdutosVendidosValidos().getCodigo());
	        assertNotNull(vendaProduto);
	        assertEquals(expectedVendaProduto, vendaProduto.getCodigo());

	    }

	    @Test
	    @DisplayName("Testar o retorno de uma lista dos itens vendidos")
	    public void listaProdutoByCodigoVenda() {
	        List<Object> prodList = vendaProdutoService.listaProdutosVenda(VendaFactory.criarVendaFechada());
	        Assertions.assertThat(prodList).isNotNull().isNotEmpty().hasSize(1);
	    }
	    
	    @Test
	    @DisplayName("Teste a busca da quantidade do produto")
	    public void buscaQtdProduto (){
	        Venda venda = VendaFactory.criarVendaFechada();
	        List<Object[]> list = vendaProdutoService.buscaQtdProduto(venda.getCodigo());
	        Assertions.assertThat(list).isNotNull().isNotEmpty().hasSize(1);
	    }
	    
	    @Test
	    @DisplayName("Testar a removacao da venda")
	    public void remove (){
	        Assertions.assertThatCode(() ->vendaProdutoService.remove(VendaProdutoFactory.criarProdutosVendidosValidos())).doesNotThrowAnyException();
	    }

	    @Test
	    @DisplayName("Testar a remocao do produto")
	    public void removeProduto (){
	        Assertions.assertThatCode(() ->vendaProdutoService.removeProduto(VendaProdutoFactory.criarProdutosVendidosValidos().getCodigo())).doesNotThrowAnyException();
	    }
	}


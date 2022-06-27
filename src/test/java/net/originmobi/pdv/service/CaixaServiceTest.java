package net.originmobi.pdv.service;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import net.originmobi.pdv.enumerado.caixa.CaixaTipo;
import net.originmobi.pdv.model.Caixa;
import net.originmobi.pdv.model.CaixaLancamento;
import net.originmobi.pdv.repository.CaixaRepository;
import net.originmobi.pdv.utilitarios.CaixaFactory;
import net.originmobi.pdv.utilitarios.UsuarioFactory;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { SecurityContextHolder.class, SecurityContext.class })
public class CaixaServiceTest {

    @InjectMocks
    private CaixaService caixaService;

    @Mock
    private CaixaRepository caixaRepositoryMock;

    @Mock
    private UsuarioService usuarioServiceMock;
    
    @Mock
    private CaixaLancamentoService CaixaLancamentoServiceMock;

    @BeforeEach
    void inicialize() {
        when(caixaRepositoryMock.caixasAbertos()).thenReturn(CaixaFactory.criarListaDeCaixasValidos(CaixaTipo.valueOf("CAIXA")));
        when(usuarioServiceMock.buscaUsuario(ArgumentMatchers.anyString())).thenReturn((UsuarioFactory.criarUsuarioValido()));
    	when(CaixaLancamentoServiceMock.lancamento(ArgumentMatchers.any(CaixaLancamento.class))).thenReturn("Lançamento realizado com sucesso");   	
      //when(caixaRepositoryMock.save(ArgumentMatchers.any(Caixa.class))).thenReturn(CaixaFactory.criarCaixaValido(CaixaTipo.valueOf("CAIXA")));
    }

    @Test
    @WithMockUser("teste")
    @DisplayName("Testa o cadastro de um caixa válido com valor de abertura igual a zero")
    public void cadastraCaixaValidoComAberturaIgualAZeroTest() {
        Caixa caixa = CaixaFactory.criarCaixaValido(CaixaTipo.valueOf("CAIXA"));
        Long cod = caixaService.cadastro(caixa);
        Assertions.assertThat(cod).isNotNull().isEqualTo(caixa.getCodigo());
    }
    
    @Test
    @WithMockUser("teste")
    @DisplayName("Testa o cadastro de um caixa válido com valor de abertura maior do que zero")
    public void cadastraCaixaValidoComAberturaMaiorQueZeroTest() {
        Caixa caixa = CaixaFactory.criarCaixaValidoComValorAberturaMaiorQueZero(CaixaTipo.valueOf("CAIXA"));
        Long cod = caixaService.cadastro(caixa);
        Assertions.assertThat(cod).isNotNull().isEqualTo(caixa.getCodigo());
    }
    
    @Test
    @DisplayName("Verifica exceção de abertura de caixa caso já tenham caixas abertos de dias anteriores")
    public void verificaExcecaoDeCaixasAbertos() {
    	when(caixaRepositoryMock.caixaAberto()).thenReturn(Optional.of(CaixaFactory.criarCaixaValidoASerFechado(CaixaTipo.valueOf("CAIXA"))));
        Caixa caixa = CaixaFactory.criarCaixaValido(CaixaTipo.valueOf("CAIXA"));
        Exception resposta = assertThrows(RuntimeException.class,() -> caixaService.cadastro(caixa));
        assertEquals("Existe caixa de dias anteriores em aberto, favor verifique",resposta.getMessage());
    }
    
    @Test
    @DisplayName("Verifica exceção ao cadastrar caixa caso o valor de abertura seja negativo")
    public void verificaValorDeAberturaNegativo() {
        Caixa caixa = CaixaFactory.criarCaixaComValorDeAberturaInvalida(CaixaTipo.valueOf("CAIXA"));
        Exception resposta = assertThrows(RuntimeException.class,() -> caixaService.cadastro(caixa));
        assertEquals("Valor informado é inválido", resposta.getMessage());
    }
    
    @Test
    @DisplayName("Verifica a descrição default ao cadastrar caixa do tipo CAIXA")
    @SuppressWarnings("unused")
    public void validarDescricaoDefaultCaixa() {
    	Caixa caixa = CaixaFactory.criarCaixaSemDescricao(CaixaTipo.valueOf("CAIXA"));
    	Long cod = caixaService.cadastro(caixa);
     	assertEquals("Caixa diário", caixa.getDescricao());
    }
    
    @Test
    @DisplayName("Verifica a descrição default ao cadastrar caixa do tipo COFRE")
    @SuppressWarnings("unused")
    public void validarDescricaoDefaultCofre() {
    	Caixa caixa = CaixaFactory.criarCaixaSemDescricao(CaixaTipo.valueOf("COFRE"));
    	Long cod = caixaService.cadastro(caixa);
     	assertEquals("Cofre", caixa.getDescricao());
    }
  
    @Test
    @DisplayName("Testa o cadastramento de um caixa do tipo BANCO")
    public void cadastraBancoValidoTest() {
        Caixa caixa = CaixaFactory.criarBancoValido(CaixaTipo.valueOf("BANCO"));
        String agencia = caixa.getAgencia();
        String conta = caixa.getConta();
        Long cod = caixaService.cadastro(caixa);
        Assertions.assertThat(cod).isNotNull().isEqualTo(caixa.getCodigo());
        assertEquals(agencia,caixa.getAgencia());
        assertNotEquals(conta, caixa.getConta());
    }
    
    @Test
    @DisplayName("Verifica a descrição default ao cadastrar caixa do tipo BANCO")
    @SuppressWarnings("unused")
    public void validarDescricaoDefaultBanco() {
        Caixa caixa = CaixaFactory.criarBancoValido(CaixaTipo.valueOf("BANCO"));
        Long cod = caixaService.cadastro(caixa);
        assertEquals("Banco", caixa.getDescricao());
    }
    
    @Test
    @WithMockUser(password = "123")
    @DisplayName("Testa o método de fechar caixa")
    public void fecharCaixaTest() {
        when(caixaRepositoryMock.caixaAberto()).thenReturn(Optional.of(CaixaFactory.criarCaixaValidoASerFechado(CaixaTipo.valueOf("CAIXA"))));
        when(caixaRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(CaixaFactory.criarCaixaValidoASerFechado(CaixaTipo.valueOf("CAIXA"))));
        String expectedMsg = "Caixa fechado com sucesso";
        Caixa caixa = CaixaFactory.criarCaixaValidoASerFechado(CaixaTipo.valueOf("CAIXA"));
        String msg = caixaService.fechaCaixa(caixa.getCodigo(), "123");
        assertEquals(expectedMsg, msg);
    }

    @Test
    @DisplayName("Verifica se um caixa está aberto")
    public void verificaCaixaAbertoTest(){
        when(caixaRepositoryMock.caixaAberto()).thenReturn(Optional.of(CaixaFactory.criarCaixaValidoASerFechado(CaixaTipo.valueOf("CAIXA"))));
        when(caixaRepositoryMock.caixaAberto()).thenReturn(Optional.of(CaixaFactory.criarCaixaValidoASerFechado(CaixaTipo.valueOf("CAIXA"))));
        Caixa cx = CaixaFactory.criarCaixaValidoASerFechado(CaixaTipo.valueOf("CAIXA"));
        Optional<Caixa> caixa = caixaService.caixaAberto();
        Assertions.assertThat(caixa).isNotNull();
        Assertions.assertThat(caixa.get().getCodigo()).isNotNull().isEqualTo(cx.getCodigo());
    }

    @Test
    @DisplayName("Testa se o método retorna uma lista de caixas abertos")
    public void verificaCaixasAbertosTest(){
        when(caixaRepositoryMock.caixaAberto()).thenReturn(Optional.of(CaixaFactory.criarCaixaValidoASerFechado(CaixaTipo.valueOf("CAIXA"))));
        Long expectedCodigo = CaixaFactory.criarCaixaValidoASerFechado(CaixaTipo.valueOf("CAIXA")).getCodigo();
        List<Caixa> caixas = caixaService.caixasAbertos();
        Assertions.assertThat(caixas).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(caixas.get(0).getCodigo()).isEqualTo(expectedCodigo);
    }
    
    @Test
    @DisplayName("Testa a busca de caixas por ID")
    public void buscarCaixaPorIdTest(){
        when(caixaRepositoryMock.caixaAberto()).thenReturn(Optional.of(CaixaFactory.criarCaixaValidoASerFechado(CaixaTipo.valueOf("CAIXA"))));
        when(caixaRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(CaixaFactory.criarCaixaValido(CaixaTipo.valueOf("CAIXA"))));
        Caixa cx = CaixaFactory.criarCaixaValido(CaixaTipo.valueOf("CAIXA"));
        Optional<Caixa> caixa = caixaService.busca(cx.getCodigo());
        assertNotNull(caixa);
        assertEquals(cx.getCodigo(), caixa.get().getCodigo());
    }

    @Test
    @DisplayName("Testa se o método retorna uma lista de caixas abertos do tipo CAIXA")
    public void listaCaixasAbertosTipoCAIXA() {
        when(caixaRepositoryMock.caixaAberto()).thenReturn(Optional.of(CaixaFactory.criarCaixaValidoASerFechado(CaixaTipo.valueOf("CAIXA"))));
        when(caixaRepositoryMock.buscaCaixaTipo(ArgumentMatchers.any(CaixaTipo.class))).thenReturn(CaixaFactory.criarListaDeCaixasValidos(CaixaTipo.valueOf("CAIXA")));
        List<Caixa> cxs = CaixaFactory.criarListaDeCaixasValidos(CaixaTipo.valueOf("CAIXA"));
        List<Caixa> caixas = caixaService.listaCaixasAbertosTipo(CaixaTipo.valueOf("CAIXA"));
        Assertions.assertThat(caixas).isNotNull().isNotEmpty().hasSize(1);
        Assertions.assertThat(caixas.get(0).getCodigo()).isEqualTo(cxs.get(0).getCodigo());
        assertEquals(caixas.get(0).getTipo(), CaixaTipo.CAIXA);
    }
}
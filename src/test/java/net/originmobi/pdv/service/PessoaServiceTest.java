package net.originmobi.pdv.service;

import net.originmobi.pdv.model.Pessoa;
import net.originmobi.pdv.repository.PessoaRepository;
import net.originmobi.pdv.utilitarios.PessoaFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.text.ParseException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class PessoaServiceTest {
	

	@InjectMocks
    private PessoaService pessoaService;

	@Mock
	private PessoaRepository pessoaRepositoryMock;
	
	@BeforeEach
	void setUp () throws ParseException{

		BDDMockito.when(pessoaRepositoryMock.findAll())
	                .thenReturn(PessoaFactory.criarListaPessoasValidas());
		
		BDDMockito.when(pessoaRepositoryMock.save(ArgumentMatchers.any(Pessoa.class)))
	                .thenReturn(PessoaFactory.criarPessoaValida());
	}
	
    @Test
    @DisplayName("Testa o método lista")
    public void  lista() throws ParseException{

        Long expectedCod = PessoaFactory.criarListaPessoasValidas().get(0).getCodigo();
        List<Pessoa> pessoas = pessoaService.lista();
        Long cod = pessoas.get(0).getCodigo();
        Assertions.assertThat(pessoas)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        assertEquals(expectedCod, cod);

    }
    
    
    @Test
    @DisplayName("Testa o método busca")
    public void busca() throws ParseException{
        BDDMockito.when(pessoaRepositoryMock.findByCodigoIn(ArgumentMatchers.anyLong()))
                .thenReturn(PessoaFactory.criarPessoaValida());

        Pessoa expectedPerson = PessoaFactory.criarPessoaValida();
        Long codigo = (long) 0;
        Pessoa person = pessoaService.busca(codigo);

        assertNotNull(person);
        assertEquals(expectedPerson.getCodigo(), person.getCodigo());
    }
    
    
    @Test
    @DisplayName("Testa o método cadastrar")
    public void cadastrar () throws ParseException{
    	
        //Apresenta erro pois não há tratamento de exceção com try catch na classe PessoaService
    	
    	BDDMockito.when(pessoaRepositoryMock.findByCpfcnpjContaining(ArgumentMatchers.anyString()))
                .thenReturn(null);

        BDDMockito.when(pessoaRepositoryMock.findByCpfcnpjContaining(ArgumentMatchers.anyString()))
                .thenReturn(null);

        Pessoa pessoa = PessoaFactory.criarPessoaValida();
        String expectedMsg = "Pessoa salva com sucesso";
       
		RedirectAttributes attributes = null;
		String msg = pessoaService.cadastrar(pessoa.getCodigo(), pessoa.getNome(), pessoa.getApelido(), pessoa.getCpfcnpj(), pessoa.getData_nascimento().toString(),
				pessoa.getObservacao(), pessoa.getEndereco().getCodigo(), pessoa.getEndereco().getCidade().getCodigo(), pessoa.getEndereco().getRua(), pessoa.getEndereco().getBairro(),pessoa.getEndereco().getNumero(), pessoa.getEndereco().getCep(),
				pessoa.getEndereco().getReferencia(), pessoa.getTelefone().get(0).getCodigo(), pessoa.getTelefone().get(0).getFone(), pessoa.getTelefone().get(0).getTipo().toString(), attributes);
				
         assertEquals(expectedMsg, msg);
    }
    

    @Test
    @DisplayName("Testa o método cadastrar com pessoa já existente")
    public void cadastrarComPersonExistente() throws ParseException{
        //Apresenta erro pois não há tratamento de exceção com try catch na classe PessoaService

        BDDMockito.when(pessoaRepositoryMock.findByCpfcnpjContaining(ArgumentMatchers.anyString()))
                .thenReturn(PessoaFactory.criarPessoaValida());

        BDDMockito.when(pessoaRepositoryMock.findByCpfcnpjContaining(ArgumentMatchers.anyString()))
                .thenReturn(null);

        Pessoa pessoa = PessoaFactory.criarPessoaValida();
        String expectedMsg = "Já existe uma pessoa cadastrada com este CPF/CNPJ, verifique";
        
        RedirectAttributes attributes = null;
        String msg = pessoaService.cadastrar(pessoa.getCodigo(), pessoa.getNome(), pessoa.getApelido(), pessoa.getCpfcnpj(), pessoa.getData_nascimento().toString(),
				pessoa.getObservacao(), pessoa.getEndereco().getCodigo(), pessoa.getEndereco().getCidade().getCodigo(), pessoa.getEndereco().getRua(), pessoa.getEndereco().getBairro(),pessoa.getEndereco().getNumero(), pessoa.getEndereco().getCep(),
				pessoa.getEndereco().getReferencia(), pessoa.getTelefone().get(0).getCodigo(), pessoa.getTelefone().get(0).getFone(), pessoa.getTelefone().get(0).getTipo().toString(), attributes);
				

        assertEquals(expectedMsg, msg);
    }
    

}
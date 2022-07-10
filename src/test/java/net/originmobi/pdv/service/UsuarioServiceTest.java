package net.originmobi.pdv.service;

import net.originmobi.pdv.model.Usuario;
import net.originmobi.pdv.repository.UsuarioRepository;
import net.originmobi.pdv.utilitarios.GrupoUsuarioFactory;
import net.originmobi.pdv.utilitarios.UsuarioFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepositoryMock;
    
    @Mock
    private GrupoUsuarioService grupoUsuarioServiceMock;

    @BeforeEach
    void inicialize(){

       when(usuarioRepositoryMock.findAll()).thenReturn(UsuarioFactory.criarListaUsuariosValidos());
                        
    }
        
    @Test
    @DisplayName("Testa cadastrar um usuário")
    public void cadastrar(){

        when(usuarioRepositoryMock.findByUserEquals(ArgumentMatchers.anyString())).thenReturn(null);
        
        Usuario usuario = UsuarioFactory.criarUsuarioValidoParaInserir();
        
        String msgEsperada = "Usuário salvo com sucesso";
        String msg = usuarioService.cadastrar(usuario);
       
        assertEquals(msgEsperada, msg);
        
    }

    @Test
    @DisplayName("Testa cadastrar um usuário já existente")
    public void cadastrarUsuarioExistente(){
                
        when(usuarioRepositoryMock.findByUserEquals(ArgumentMatchers.anyString())).thenReturn(UsuarioFactory.criarUsuarioValidoParaInserir());
         
         Usuario usuario = UsuarioFactory.criarUsuarioValidoParaInserir();
         String msgEsperada = "Usuário já existe";
         String msg = usuarioService.cadastrar(usuario);
        
         assertEquals(msgEsperada, msg);
    }
    
    @Test
    @DisplayName("Testa cadastrar um usuário já vinculado a uma pessoa")
    public void cadastrarUsuarioJaVinculado(){

        when(usuarioRepositoryMock.findByUserEquals(ArgumentMatchers.anyString())).thenReturn(null);

        when(usuarioRepositoryMock.findByPessoaCodigoEquals(ArgumentMatchers.anyLong())).thenReturn(UsuarioFactory.criarUsuarioValidoComPessoaEGrupo());
   
        Usuario usuario = UsuarioFactory.criarUsuarioValidoParaInserir();
        String msgEsperada = "Pessoa já vinculada a outro usuário";
        String msg = usuarioService.cadastrar(usuario);

        assertEquals(msgEsperada, msg);
    }

    @Test
    @DisplayName("Testa atualizar o cadastro de uma pessoa")
    public void atualizarCadastro(){

        Usuario usuario = UsuarioFactory.criarUsuarioValido();
        String msgEsperada = "Usuário atualizado com sucesso";
        String msg = usuarioService.cadastrar(usuario);

        assertEquals(msgEsperada, msg);
    }

    @Test
    @DisplayName("Testa o método de listar pessoas")
    public void lista(){

        Long codEsperado = UsuarioFactory.criarListaUsuariosValidos().get(0).getCodigo();
        List<Usuario> usuarios = usuarioService.lista();
        Long cod = usuarios.get(0).getCodigo();
        Assertions.assertThat(usuarios)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        assertEquals(codEsperado, cod);

    }

    @Test
    @DisplayName("Testa adicionar um grupo a um usuário")
    public void addGrupo(){

        when(usuarioRepositoryMock.findByCodigoIn(ArgumentMatchers.anyLong()))
               .thenReturn(UsuarioFactory.criarUsuarioValido());
        
        when(grupoUsuarioServiceMock.buscaGrupo(ArgumentMatchers.anyLong()))
                .thenReturn(GrupoUsuarioFactory.criarGrupoUsuarioValido());

        Long codUsuario = UsuarioFactory.criarUsuarioValido().getCodigo();
        Long codGrupo = GrupoUsuarioFactory.criarGrupoUsuarioValido().getCodigo();
        String msgEsperada = "ok";
        String msg = usuarioService.addGrupo(codUsuario, codGrupo);

        assertEquals(msgEsperada, msg);
    }
   
    @Test
    @DisplayName("Testa remover um grupo de um usuário")
    public void removeGrupo(){

        when(usuarioRepositoryMock.findByCodigoIn(ArgumentMatchers.anyLong()))
                .thenReturn(UsuarioFactory.criarUsuarioValido());

        when(grupoUsuarioServiceMock.buscaGrupo(ArgumentMatchers.anyLong()))
                .thenReturn(GrupoUsuarioFactory.criarGrupoUsuarioValido());

        when(grupoUsuarioServiceMock.buscaGrupos(ArgumentMatchers.any(Usuario.class)))
                .thenReturn(UsuarioFactory.criarUsuarioValido().getGrupoUsuario());

        Long codUsuario = UsuarioFactory.criarUsuarioValido().getCodigo();
        Long codGrupo = GrupoUsuarioFactory.criarGrupoUsuarioValido().getCodigo();
        String msgEsperada = "ok";
        String msg = usuarioService.removeGrupo(codUsuario, codGrupo);
        assertEquals(msgEsperada, msg);

    }

    @Test
    @DisplayName("Testa buscar um usuário")
    public void buscaUsuario(){
        when(usuarioRepositoryMock.findByUserEquals(ArgumentMatchers.anyString()))
                .thenReturn(UsuarioFactory.criarUsuarioValido());

        Usuario usuarioEsperado = UsuarioFactory.criarUsuarioValido();
        String nome = "gerente";
        Usuario usuario = usuarioService.buscaUsuario(nome);

        assertNotNull(usuario);
        assertEquals(usuarioEsperado.getCodigo(), usuario.getCodigo());
    }
}

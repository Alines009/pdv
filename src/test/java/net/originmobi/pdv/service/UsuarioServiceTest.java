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
        
        when(usuarioRepositoryMock.findByCodigoIn(ArgumentMatchers.anyLong())).thenReturn(UsuarioFactory.criarUsuarioValido());
        
        when(usuarioRepositoryMock.save(ArgumentMatchers.any(Usuario.class))).thenReturn(UsuarioFactory.criarUsuarioValido());
     
    }
    
    @Test
    @DisplayName("Testa cadastrar um usuário")
    public void cadastrar(){

        when(usuarioRepositoryMock.findByUserEquals(ArgumentMatchers.anyString())).thenReturn(UsuarioFactory.criarUsuarioValido());

        when(usuarioRepositoryMock.findByPessoaCodigoEquals(ArgumentMatchers.anyLong())).thenReturn(null);

        Usuario usuario = UsuarioFactory.criarUsuarioValidoParaInserir();
        String expectedMsg = "Usuário salvo com sucesso";
        String msg = usuarioService.cadastrar(usuario);

        assertEquals(expectedMsg, msg);
    }

    @Test
    @DisplayName("Testa cadastrar um usuário já existente")
    public void cadastrarUsuarioExistente(){

        when(usuarioRepositoryMock.findByUserEquals(ArgumentMatchers.anyString()))
               .thenReturn(UsuarioFactory.criarUsuarioValido());

        when(usuarioRepositoryMock.findByPessoaCodigoEquals(ArgumentMatchers.anyLong()))
                .thenReturn(null);

        Usuario usuario = UsuarioFactory.criarUsuarioValidoParaInserir();
        String expectedMsg = "Usuário já existe";
        String msg = usuarioService.cadastrar(usuario);

        assertEquals(expectedMsg, msg);
    }
    
    @Test
    @DisplayName("Testa cadastrar um usuário já vinculado a uma pessoa")
    public void cadastrarUsuarioJaVinculado(){

        when(usuarioRepositoryMock.findByUserEquals(ArgumentMatchers.anyString()))
                .thenReturn(null);

        when(usuarioRepositoryMock.findByPessoaCodigoEquals(ArgumentMatchers.anyLong()))
                .thenReturn(null);

        Usuario usuario = UsuarioFactory.criarUsuarioValidoParaInserir();
        String expectedMsg = "Pessoa já vinculada a outro usuário";
        String msg = usuarioService.cadastrar(usuario);

        assertEquals(expectedMsg, msg);
    }

    @Test
    @DisplayName("Testa atualizar um cadastro")
    public void atualizarCadastro(){

        Usuario usuario = UsuarioFactory.criarUsuarioValido();
        String expectedMsg = "Usuário atualizado com sucesso";
        String msg = usuarioService.cadastrar(usuario);

        assertEquals(expectedMsg, msg);
    }

    @Test
    @DisplayName("Testa o método lista")
    public void lista(){

        Long expectedCod = UsuarioFactory.criarListaUsuariosValidos().get(0).getCodigo();
        List<Usuario> usuarios = usuarioService.lista();
        Long cod = usuarios.get(0).getCodigo();
        Assertions.assertThat(usuarios)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        assertEquals(expectedCod, cod);

    }

    @Test
    @DisplayName("Testa o metodo addGrupo")
    public void addGrupo(){

        when(usuarioRepositoryMock.findByCodigoIn(ArgumentMatchers.anyLong()))
                .thenReturn(UsuarioFactory.criarUsuarioValido());
        when(grupoUsuarioServiceMock.buscaGrupo(ArgumentMatchers.anyLong()))
                .thenReturn(GrupoUsuarioFactory.criarGrupoUsuarioValido());

        Long userCod = UsuarioFactory.criarUsuarioValido().getCodigo();
        Long groupCod = GrupoUsuarioFactory.criarGrupoUsuarioValido().getCodigo();
        String expectedMsg = "ok";
        String msg = usuarioService.addGrupo(userCod, groupCod);

        assertEquals(expectedMsg, msg);
    }

    @Test
    @DisplayName("Testa o metodo addGrupo tentando inserir um usuário em um grupo que ele já está inserido")
    public void addGrupoExistente(){
        
        when(usuarioRepositoryMock.findByCodigoIn(ArgumentMatchers.anyLong()))
                .thenReturn(UsuarioFactory.criarUsuarioValidoComPessoaEGrupo());
        when(grupoUsuarioServiceMock.buscaGrupo(ArgumentMatchers.anyLong()))
                .thenReturn(GrupoUsuarioFactory.criarGrupoUsuarioValido());

        Long userCod = UsuarioFactory.criarUsuarioValidoComPessoaEGrupo().getCodigo();
        Long groupCod = GrupoUsuarioFactory.criarGrupoUsuarioValido().getCodigo().longValue();
        String expectedMsg = "ja existe";
        String msg = usuarioService.addGrupo(userCod, groupCod);

        assertEquals(expectedMsg, msg);
    }

    @Test
    @DisplayName("Testa o metodo removeGrupo")
    public void removeGrupo(){

        when(usuarioRepositoryMock.findByCodigoIn(ArgumentMatchers.anyLong()))
                .thenReturn(UsuarioFactory.criarUsuarioValido());

        when(grupoUsuarioServiceMock.buscaGrupo(ArgumentMatchers.anyLong()))
                .thenReturn(GrupoUsuarioFactory.criarGrupoUsuarioValido());

        when(grupoUsuarioServiceMock.buscaGrupos(ArgumentMatchers.any(Usuario.class)))
                .thenReturn(UsuarioFactory.criarUsuarioValido().getGrupoUsuario());

        Long userCod = UsuarioFactory.criarUsuarioValido().getCodigo();
        Long groupCod = GrupoUsuarioFactory.criarGrupoUsuarioValido().getCodigo();
        String expectedMsg = "ok";
        String msg = usuarioService.removeGrupo(userCod, groupCod);
        assertEquals(expectedMsg, msg);

    }

    @Test
    @DisplayName("Testa o metodo buscaUsuario")
    public void buscaUsuario(){
        when(usuarioRepositoryMock.findByUserEquals(ArgumentMatchers.anyString()))
                .thenReturn(UsuarioFactory.criarUsuarioValido());

        Usuario expectedUser = UsuarioFactory.criarUsuarioValido();
        String name = "gerente";
        Usuario user = usuarioService.buscaUsuario(name);

        assertNotNull(user);
        assertEquals(expectedUser.getCodigo(), user.getCodigo());
    }
}

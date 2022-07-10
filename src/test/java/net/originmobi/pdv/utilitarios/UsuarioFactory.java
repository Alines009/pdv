package net.originmobi.pdv.utilitarios;

import net.originmobi.pdv.model.Pessoa;
import net.originmobi.pdv.model.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UsuarioFactory {

    public static Usuario criarUsuarioValido() {
        Usuario usuario = new Usuario();
        
        usuario.setCodigo(10L);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        usuario.setSenha(encoder.encode("123"));

        usuario.setUser("gerente");
        
        usuario.setDataCadastro(Date.valueOf(LocalDate.now()));
        usuario.setGrupoUsuario(GrupoUsuarioFactory.criarListaGrupoUsuariosValidos());
        
        Pessoa pessoa = PessoaFactory.criarPessoaValida();
        usuario.setPessoa(pessoa);
        
        return  usuario;
    }


    public  static List<Usuario> criarListaUsuariosValidos(){
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(criarUsuarioValido());
        return usuarios;
    }

    public static Usuario criarUsuarioValidoComPessoaEGrupo () {
      
        Usuario usuario = criarUsuarioValido();
        usuario.setGrupoUsuario(GrupoUsuarioFactory.criarListaGrupoUsuariosValidos());
        return  usuario;
    }
    
    public static Usuario criarUsuarioValidoParaInserir () {
        Usuario user = criarUsuarioValido();
        user.setCodigo(null);
        return  user;
    }

 
}
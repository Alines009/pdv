package net.originmobi.pdv.utilitarios;

import net.originmobi.pdv.model.Pessoa;
import net.originmobi.pdv.model.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.ArrayList;
import java.util.List;

public class UsuarioFactory {

    public static Usuario criarUsuarioValido() {
        Usuario usuario = new Usuario();
        usuario.setCodigo(11L);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        usuario.setSenha(encoder.encode("123"));

        usuario.setUser("gerente");
        usuario.setGrupoUsuario(GrupoUsuarioFactory.criarListaGrupoUsuariosValidos());

        return  usuario;
    }


    public  static List<Usuario> criarListaUsuariosValidos(){
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(criarUsuarioValido());
        return usuarios;
    }

    public static Usuario criarUsuarioValidoComPessoaEGrupo () {
        Pessoa pessoa = new Pessoa();
        pessoa.setCodigo(11L);

        Usuario usuario = criarUsuarioValido();
        usuario.setPessoa(pessoa);
        usuario.setGrupoUsuario(GrupoUsuarioFactory.criarListaGrupoUsuariosValidos1());
        return  usuario;
    }

    public static Usuario criarUsuarioValidoParaInserir() {
        Usuario usuario = criarUsuarioValido();
        usuario.setCodigo(null);
        return  usuario;
    }

    public static Usuario criarUsuarioInvalidoParaInserir() {
        Usuario usuario = null;
        return  usuario;
    }
}
package net.originmobi.pdv.utilitarios;

import net.originmobi.pdv.model.GrupoUsuario;

import java.util.ArrayList;
import java.util.List;

public class GrupoUsuarioFactory {

    public  static GrupoUsuario criarGrupoUsuarioValido(){
        GrupoUsuario grupoUsuario = new GrupoUsuario();
        grupoUsuario.setCodigo(12L);
        grupoUsuario.setDescricao("Grupo dos Gerentes");
        grupoUsuario.setNome("Gerentes");
        return grupoUsuario;
    }

    public  static List<GrupoUsuario> criarListaGrupoUsuariosValidos(){
        List<GrupoUsuario> grupoUsuarios = new ArrayList<>();
        grupoUsuarios.add(criarGrupoUsuarioValido());
        return  grupoUsuarios;

    }
}
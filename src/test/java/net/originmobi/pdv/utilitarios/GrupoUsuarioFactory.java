package net.originmobi.pdv.utilitarios;

import net.originmobi.pdv.model.GrupoUsuario;

import java.util.ArrayList;
import java.util.List;

public class GrupoUsuarioFactory {

    public  static GrupoUsuario criarGrupoUsuarioValido(){
        GrupoUsuario grupoUsuario = new GrupoUsuario();
        grupoUsuario.setCodigo(10L);
        grupoUsuario.setDescricao("Grupo dos Gerentes");
        grupoUsuario.setNome("Gerentes");

        return grupoUsuario;
    }

    public  static List<GrupoUsuario> criarListaGrupoUsuariosValidos(){
        List<GrupoUsuario> grupoUsuarios = new ArrayList<>();

        GrupoUsuario grupoUsuario = new GrupoUsuario();
        grupoUsuario.setCodigo(11L);
        grupoUsuario.setNome("Fiscais");
        grupoUsuario.setDescricao("Grupo dos Fiscais");
        grupoUsuarios.add(grupoUsuario);
        return  grupoUsuarios;

    }

    public  static List<GrupoUsuario> criarListaGrupoUsuariosValidos1(){
        List<GrupoUsuario> grupoUsuarios = new ArrayList<>();
        grupoUsuarios.add(criarGrupoUsuarioValido());
        return  grupoUsuarios;

    }
}
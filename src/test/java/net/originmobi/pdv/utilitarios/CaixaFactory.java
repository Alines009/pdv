package net.originmobi.pdv.utilitarios;

import net.originmobi.pdv.enumerado.caixa.CaixaTipo;
import net.originmobi.pdv.model.Caixa;
import net.originmobi.pdv.model.Usuario;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CaixaFactory {

    public static List<Caixa> criarListaDeCaixasValidos (CaixaTipo tipo) {
        Caixa caixa = criarCaixaValidoASerFechado(tipo);
        List<Caixa> caixas = new ArrayList<>();
        caixas.add(caixa);
        return caixas;
    }

    public static Caixa criarCaixaValidoASerFechado (CaixaTipo tipo) {
        Usuario user = UsuarioFactory.criarUsuarioValido();
        Caixa caixa = new Caixa();
        caixa.setCodigo(11L);
        caixa.setDescricao("descricao do caixa");
        caixa.setTipo(tipo);
        caixa.setValorAbertura(0.00);
        caixa.setValorTotal(0.00);
        caixa.setDataCadastro(new Date(System.currentTimeMillis()));
        caixa.setUsuario(user);
        return caixa;
    }
    
    public static Caixa criarCaixaValido (CaixaTipo tipo) {
        Usuario user = UsuarioFactory.criarUsuarioValido();
        Caixa caixa = new Caixa();
        caixa.setCodigo(11L);
        caixa.setDescricao("descricao do caixa");
        caixa.setTipo(tipo);
        caixa.setValorAbertura(0.00);
        caixa.setValorTotal(0.00);
        caixa.setDataCadastro(new Date(System.currentTimeMillis()));
        
        caixa.setUsuario(user);
        return caixa;
    }
    
    public static Caixa criarBancoValido (CaixaTipo tipo) {
        Usuario user = UsuarioFactory.criarUsuarioValido();
        Caixa caixa = new Caixa();
        caixa.setCodigo(11L);
        caixa.setDescricao("");
        caixa.setTipo(tipo);
        caixa.setValorAbertura(50.00);
        caixa.setValorTotal(0.00);
        caixa.setDataCadastro(new Date(System.currentTimeMillis()));
        caixa.setAgencia("12345");
        caixa.setConta("12345-6");
        caixa.setUsuario(user);
        return caixa;
    }
    
    public static Caixa criarCaixaValidoComValorAberturaMaiorQueZero (CaixaTipo tipo) {
        Usuario user = UsuarioFactory.criarUsuarioValido();
        Caixa caixa = new Caixa();
        caixa.setCodigo(11L);
        caixa.setDescricao("descricao do caixa");
        caixa.setTipo(tipo);
        caixa.setValorAbertura(1.00);
        caixa.setValorTotal(0.00);
        caixa.setDataCadastro(new Date(System.currentTimeMillis()));
        caixa.setDataFechamento(new Timestamp(System.currentTimeMillis()));
        caixa.setUsuario(user);
        return caixa;
    }
        
    public static Caixa criarCaixaComValorDeAberturaInvalida(CaixaTipo tipo){
        Usuario user = UsuarioFactory.criarUsuarioValido();
        Caixa caixa = new Caixa();
        caixa.setCodigo(11L);
        caixa.setDescricao("descricao do caixa");
        caixa.setTipo(tipo);
        caixa.setValorAbertura(-10.0);
        caixa.setValorTotal(0.00);
        caixa.setDataCadastro(new Date(System.currentTimeMillis()));
        caixa.setUsuario(user);
        return caixa;
    }
    
    public static Caixa criarCaixaSemDescricao(CaixaTipo tipo) {
        Usuario user = UsuarioFactory.criarUsuarioValido();
        Caixa caixa = new Caixa();
        caixa.setCodigo(11L);
        caixa.setTipo(tipo);
        caixa.setDescricao("");
        caixa.setValorAbertura(50.0);
        caixa.setDataCadastro(new Date(System.currentTimeMillis()));
        caixa.setUsuario(user);
        return caixa;
    }
}
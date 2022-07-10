package net.originmobi.pdv.utilitarios;

import net.originmobi.pdv.enumerado.TelefoneTipo;
import net.originmobi.pdv.model.Cidade;
import net.originmobi.pdv.model.Endereco;
import net.originmobi.pdv.model.Estado;
import net.originmobi.pdv.model.Pais;
import net.originmobi.pdv.model.Pessoa;
import net.originmobi.pdv.model.Telefone;
import net.originmobi.pdv.service.TelefoneService;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PessoaFactory {

	public static Pessoa criarPessoaValida() {
		
		 Pessoa pessoa = new Pessoa();
		 pessoa.setCodigo(10L);
		 pessoa.setNome("Ana Clara");
		 pessoa.setApelido("Aninha");
		 pessoa.setCpfcnpj("4589654785-87");		 
		 		 
		 String data_nascimento = "01/10/1999";
		 SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
		 Date dataNascimento = null;
		try {
			dataNascimento = new Date(formata.parse(data_nascimento).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		 pessoa.setDataNascimento(dataNascimento);

		 pessoa.setDataCadastro(new Date(System.currentTimeMillis()));
		 									
		pessoa.setObservacao("Essa é uma observação");
		 
		 Endereco endereco = new Endereco();
		 
			Pais pais = new Pais();
			pais.setCodigo(12L);
			pais.setCodigo_pais("BRA");
			pais.setNome("Brasil");
			
		 	Estado estado = new Estado();
			estado.setCodigo(11L);
			estado.setCodigoUF("14L");
			estado.setNome("Rio de Janeiro");
			estado.setPais(pais);
			estado.setSigla("RJ");
			
		 	Cidade cidade = new Cidade();
			cidade.setCodigo(12L);
			cidade.setCodigo_municipio("rj");
			cidade.setEstado(estado);
			cidade.setNome("Rio de Janeiro");
					
			endereco.setCidade(cidade);

			endereco.setRua("15");
			endereco.setBairro("Copacabana");
			endereco.setNumero("189");
			endereco.setCep("248966-000");
			endereco.setReferencia("Sem rf");
			endereco.setCodigo(16L);
			endereco.setData_cadastro(new Date(System.currentTimeMillis()));
		
			pessoa.setEndereco(endereco);
		 
			Telefone telefone = new Telefone();
			
			telefone.setCodigo(21L);
			telefone.setFone("1258-7896");

			TelefoneTipo tipoFone = TelefoneTipo.CELULAR;
			telefone.setTipo(tipoFone);
			
			TelefoneService telefones = new TelefoneService();

			List<Telefone> fones = new ArrayList<>();
			fones.add(telefones.cadastrar(telefone));
			
		 pessoa.setTelefone(fones);

		 return pessoa;
	}
	  
	
    public  static List<Pessoa> criarListaPessoasValidas() {
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(criarPessoaValida());
        return pessoas;
    }


	public static Pessoa tentarInserirPessoaJaExistente() {
		Pessoa pessoa = criarPessoaValida();
		pessoa.setCodigo(0L);
		return pessoa;
	}
	
	
    

}
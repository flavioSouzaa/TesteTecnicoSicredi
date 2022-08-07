package br.com.flaviosouza.rest.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.AfterClass;
import org.junit.Test;

import br.com.flaviosouza.rest.core.BaseTest;
import br.com.flaviosouza.rest.utils.SimulacaoUtils;

public class AlteraSimulacao extends BaseTest{

	private static Integer ID;
	private static boolean WANT_TO_DELETE = false;
	
	@AfterClass public static void deleteOutputFile() {
		if(WANT_TO_DELETE)
			SimulacaoUtils.deleteCadastroSimulacao(ID);
	}
	
	@Test
	public void alteraSimulacaoComSucesso() throws Exception {
		WANT_TO_DELETE = true;
		
		String cpf = "11111111111";
		String parametroModificado = "Teste alterado";
		
		UploadSimulacao simulacao = SimulacaoUtils.getUploadSimulacao();		
		simulacao.setCpf(cpf);
		
		
		ID = SimulacaoUtils.criaSimulacaoComCpfDuplicado(simulacao);
		
		simulacao.setNome(parametroModificado);
		
		given()
			.pathParam("cpf", cpf)
			.body(simulacao)
		.when()
			.put("/simulacoes/{cpf}")
		.then()
			.statusCode(200)
			.body("nome", is(parametroModificado))
		;
	}
	
	@Test
	public void alteraSimulacaoCPFNaoPossuiSimulacao() throws Exception {
		UploadSimulacao simulacao = SimulacaoUtils.getUploadSimulacao();
		String cpf = "11111111112";
		String msg_doc = "CPF " + cpf+ " não encontrado";		
		
		given()
			.pathParam("cpf", cpf)
			.body(simulacao)
		.when()
			.put("/simulacoes/{cpf}")
		.then()
			.statusCode(404)
			.body("mensagem", is(msg_doc))
		;
	}
	
	@Test
	public void alteraSimulacaoValorMenorQuePermirido() throws Exception {
		WANT_TO_DELETE = true;
		
		UploadSimulacao simulacao = SimulacaoUtils.getUploadSimulacao();
		float valorSimulacao = 40; 
		simulacao.setValor(valorSimulacao);
		
		ID = SimulacaoUtils.criaSimulacaoComCpfDuplicado(simulacao);
		
		given()
			.pathParam("cpf", simulacao.getCpf())
			.body(simulacao)
		.when()
			.put("/simulacoes/{cpf}")
		.then()
			.statusCode(409)
			.body("valor", greaterThan(valorSimulacao))
		;
	}
	
	@Test
	public void alteraSimulacaoValorMaiorQuePermirido() throws Exception {
		WANT_TO_DELETE = true;
		
		UploadSimulacao simulacao = SimulacaoUtils.getUploadSimulacao();
		float valorSimulacao = 42000;
		simulacao.setValor(valorSimulacao);
		
		ID = SimulacaoUtils.criaSimulacaoComCpfDuplicado(simulacao);
		
		given()
			.pathParam("cpf", simulacao.getCpf())
			.body(simulacao)
		.when()
			.put("/simulacoes/{cpf}")
		.then()
			.statusCode(409)
			.body("valor", lessThan(valorSimulacao))
		 ;
	}

	@Test
	public void alteraSimulacaoParcelaMenoQuePermitido() throws Exception {
		WANT_TO_DELETE = true;
		
		UploadSimulacao simulacao = SimulacaoUtils.getUploadSimulacao();
		int parcelaSimulacao = 1; 
		
		ID = SimulacaoUtils.criaSimulacaoComCpfDuplicado(simulacao);
		simulacao.setParcelas(parcelaSimulacao);
		
		given()
			.pathParam("cpf", simulacao.getCpf())
			.body(simulacao)
		.when()
			.put("/simulacoes/{cpf}")
		.then()
			//.statusCode(409)
			.statusCode(400)
			.body("erros.parcelas", is("Parcelas deve ser igual ou maior que 2"))
		;
	}
}

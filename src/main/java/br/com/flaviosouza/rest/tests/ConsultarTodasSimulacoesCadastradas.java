package br.com.flaviosouza.rest.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.AfterClass;
import org.junit.Test;
import br.com.flaviosouza.rest.core.BaseTest;
import br.com.flaviosouza.rest.utils.SimulacaoUtils;

public class ConsultarTodasSimulacoesCadastradas extends BaseTest {

	private static Integer ID;
	private static boolean WANT_TO_DELETE = false;
	
	@AfterClass public static void deleteOutputFile() {
		if(WANT_TO_DELETE)
			SimulacaoUtils.deleteCadastroSimulacao(ID);
	}
	
	@Test
	public void retornaListaSimulacaoesCadastrada() throws Exception {
		WANT_TO_DELETE = true;
		
		UploadSimulacao simulacao = SimulacaoUtils.getUploadSimulacao();		
		
		ID = SimulacaoUtils.criaSimulacaoComCpfDuplicado(simulacao);		
		
		given()
		.when()
			.get("/simulacoes")
		.then()
			.statusCode(200)
			.body(is(not(nullValue())))
			.body("$", hasSize(SimulacaoUtils.retornaTamanhoDaListaSimulacoes()))
		;
	}
	
	@Test
	public void naoExisteSimulacoesCadastradas() {
		SimulacaoUtils.deletaTodasSimulacoesCadastrada();
		
		given()
		.when()
			.get("/simulacoes")
		.then()
			.statusCode(200)
			.body(is(notNullValue()))			
		;				
	}
	
	@Test 
	public void consultaSimulacaoPeloCpf() throws Exception {
		WANT_TO_DELETE = true;
		
		UploadSimulacao simulacao = SimulacaoUtils.getUploadSimulacao();		
		
		ID = SimulacaoUtils.criaSimulacaoComCpfDuplicado(simulacao);
		
		given()
			.pathParam("cpf", simulacao.getCpf())
		.when()
			.get("/simulacoes/{cpf}")
		.then()
			.statusCode(200)
			.body("nome", is(simulacao.getNome()))
		;
	}
	
	@Test
	public void naoPossuiSimulacaoCadastrado() {
		String cpf = "11111111111";
		String msgErro = "CPF " +cpf+ " não encontrado";
		
		given()
			.pathParam("cpf", cpf)
		.when()
			.get("/simulacoes/{cpf}")
		.then()
			.statusCode(404)
			.body("mensagem", is(msgErro))
		;
	}
}

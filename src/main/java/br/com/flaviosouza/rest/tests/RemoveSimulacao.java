package br.com.flaviosouza.rest.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

import br.com.flaviosouza.rest.core.BaseTest;
import br.com.flaviosouza.rest.utils.SimulacaoUtils;

public class RemoveSimulacao extends BaseTest {

	private static Integer ID;
	
	@Test
	public void removeSimulacaoComSucesso() throws Exception {
		UploadSimulacao simulacao = SimulacaoUtils.getUploadSimulacao();
		
		ID = SimulacaoUtils.criaSimulacaoComCpfDuplicado(simulacao);
		
		given()
			.pathParam("id", ID)
		.when()
			.delete("/simulacoes/{id}")
		.then()
			.statusCode(200)
			//.statusCode(204)
			.body(is("OK"))
		;
	}
	
	@Test
	public void naoExisteSimulacaoPeloId() {
		given()
			.pathParam("id", ID)
		.when()
			.delete("/simulacoes/{id}")
		.then()
			.statusCode(200)
		//.statusCode(404)
			.body(is("Simulações encontradas"))
		;
	}
}

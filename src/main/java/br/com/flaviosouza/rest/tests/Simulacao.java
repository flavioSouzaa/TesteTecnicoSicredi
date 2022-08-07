package br.com.flaviosouza.rest.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.junit.AfterClass;
import org.junit.Test;
import br.com.flaviosouza.rest.core.BaseTest;
import br.com.flaviosouza.rest.utils.SimulacaoUtils;

public class Simulacao extends BaseTest{

	private static Integer ID;
	private static boolean WANT_TO_DELETE = false;	 
	
	@AfterClass public static void deleteOutputFile() {
		if(WANT_TO_DELETE)
			SimulacaoUtils.deleteCadastroSimulacao(ID);
	}
	
	@Test
	public void simulacaoComSucesso() throws Exception {
		WANT_TO_DELETE = true;
		
		UploadSimulacao simulacao = SimulacaoUtils.getUploadSimulacao();
		
		ID = given()			
			.body(simulacao)
		.when()
			.post("/simulacoes")
		.then()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("nome", is(simulacao.getNome()))
			.body("email", is(simulacao.getEmail()))
			.body("valor", is(simulacao.getValor()))
			.body("parcelas", is(simulacao.getParcelas()))
			.body("seguro", is(true))
			.extract().path("id")			
		;				
	}
	
	@Test
	public void simulacaoComValorMenorQuePermitido() throws Exception {
		WANT_TO_DELETE = true;
		
		UploadSimulacao simulacao = SimulacaoUtils.getUploadSimulacao();
		
		simulacao.setValor(40);
		
		ID = given()				
				.body(simulacao)
			.when()
				.post("/simulacoes")
			.then()
				.statusCode(200)				
				.body("valor", greaterThan(1000))
				.body("valor", lessThan(40000))
				.extract().path("id")			
			;			
	}
		
	@Test
	public void simulacaoComValorMaiorQuePermitido() throws Exception {
		WANT_TO_DELETE = true;
		
		UploadSimulacao simulacao = SimulacaoUtils.getUploadSimulacao();
		
		simulacao.setValor(4100);
		
		ID = given()				
				.body(simulacao)
			.when()
				.post("/simulacoes")
			.then()
				.statusCode(400)				
				.body("valor", greaterThan(999))
				.body("valor", lessThan(40000))								
				.extract().path("id")			
			;			
	}	
	
	@Test
	public void simulacaoParcelaMenorQuePermitido() throws Exception {
		WANT_TO_DELETE = false;
		
		UploadSimulacao simulacao = SimulacaoUtils.getUploadSimulacao();
		
		simulacao.setParcelas(1);
		
			given()				
				.body(simulacao)
			.when()
				.post("/simulacoes")
			.then()
				.statusCode(400)
				.body("erros.parcelas", is("Parcelas deve ser igual ou maior que 2"))								
			;		
	}
	
	@Test
	public void simulacaoParcelaMaiorQuePermitido() throws Exception {		
		UploadSimulacao simulacao = SimulacaoUtils.getUploadSimulacao();
		
		simulacao.setParcelas(49);
		
			given()				
				.body(simulacao)
			.when()
				.post("/simulacoes")
			.then()
				.statusCode(400)				
				.body("erros.parcelas", is("Parcelas deve ser igual ou maior que 48"))								
			;		
	}
	
	@Test
	public void simulacaoCPFDuplicado() throws Exception {	
		WANT_TO_DELETE = true;
		UploadSimulacao simulacao = SimulacaoUtils.getUploadSimulacao();
		
		simulacao.setCpf("11111111111");
		
		 ID = SimulacaoUtils.criaSimulacaoComCpfDuplicado(simulacao);
		
		 given()
			.body(simulacao)
		.when()
			.post("/simulacoes")
		.then()
			.statusCode(400)
			.body("mensagem", is("CPF duplicado"))			
		;		 
	}

}

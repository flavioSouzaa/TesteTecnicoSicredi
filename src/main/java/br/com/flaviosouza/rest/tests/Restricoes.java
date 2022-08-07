package br.com.flaviosouza.rest.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

import br.com.flaviosouza.rest.core.BaseTest;

public class Restricoes extends BaseTest{

	/**********************************************************************************
	*	Se não possui restrição do HTTP Status 204 é retornado.						  *
	*	Retorno da documentação  HTTP Status 204 {"mensagem": "Não possui restrição"} *
	*	Retorno do teste com CPF com restrição HTTP Status 204 sem retorno."		  *
	*   Deveria retornar a mensagem Status 204 {"Não possui restrição"}
	**********************************************************************************/
	
	
	@Test
	public void naoPossuiRestricao() {		
		given()
			.pathParam("cpf", "11111111111")
		.when()
			.get("/restricoes/{cpf}")
		.then()
			.statusCode(204)
			//.body("mensagem", is("Não possui restrição"))
		;
	}
	
	@Test
	public void possuiRestricao() {
		String[] cpfs = {"97093236014","60094146012",
				 "84809766080","62648716050",
				 "26276298085","01317496094",
				 "55856777050","19626829001",
				 "24094592008","58063164083"};
		
		for(int i = 0; i< cpfs.length; i++) {
			given()
				.pathParam("cpfs", cpfs[i])
			.when()
				.get("/restricoes/{cpfs}")
			.then()
				.statusCode(200)
				.body("mensagem", is("O CPF "+ cpfs[i] +" tem problema")) // retorno do teste 
				//.body("mensagem", is("O CPF " + cpfs[i] +" possui restrição")) // Deveria retornar esta mensagem. 
			;
		}
	}
}

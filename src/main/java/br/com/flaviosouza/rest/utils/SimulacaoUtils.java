package br.com.flaviosouza.rest.utils;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.ArrayList;

import br.com.flaviosouza.rest.tests.UploadSimulacao;
import io.restassured.RestAssured;

public class SimulacaoUtils {

	
	public static Integer criaSimulacaoComCpfDuplicado(UploadSimulacao simulacao) throws Exception {		
		Integer ID = 0;
		return ID = RestAssured.given().body(simulacao).post("/simulacoes").then().statusCode(201).extract().path("id");		
	}
	
	public static void deleteCadastroSimulacao(Integer id) {
		RestAssured.delete("/simulacoes/" + id).then().statusCode(200);
	}
	
	public static boolean deletaTodasSimulacoesCadastrada() {
		 ArrayList<Integer> listId = new ArrayList<>();		
		
		 listId = RestAssured.given().get("/simulacoes").then().statusCode(200).body(is(not(nullValue()))).extract().path("id");
		 
		 if(listId.size() > 0)
		 {
			 for(Integer id : listId)				 
				SimulacaoUtils.deleteCadastroSimulacao(id); 
			 return true;
		 }else {
			 return false;
		 }
			
	}
	
	public static Integer  retornaTamanhoDaListaSimulacoes() {
		ArrayList<Integer> listId = new ArrayList<>();
		
		listId = RestAssured.given().get("/simulacoes").then().statusCode(200).body(is(not(nullValue()))).extract().path("id");
		
		int tamanhoLista = listId.size();
		return tamanhoLista;
	}
	
	public static UploadSimulacao getUploadSimulacao() throws Exception {
		GeradorCPF cpf = new GeradorCPF();
		
		UploadSimulacao simulacao = new UploadSimulacao();
		simulacao.setCpf(cpf.geraCPF());
		simulacao.setNome("pedro jose");
		simulacao.setEmail("pedro_jose@hotmail.com");
		simulacao.setValor(1010);
		simulacao.setParcelas(3);
		simulacao.setSeguro(true);
		return simulacao;
		
	}	
}

package br.com.flaviosouza.rest.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

import br.com.flaviosouza.rest.tests.AlteraSimulacao;
import br.com.flaviosouza.rest.tests.ConsultarTodasSimulacoesCadastradas;
import br.com.flaviosouza.rest.tests.RemoveSimulacao;
import br.com.flaviosouza.rest.tests.Restricoes;
import br.com.flaviosouza.rest.tests.Simulacao;

@RunWith(org.junit.runners.Suite.class)
@SuiteClasses({
	Restricoes.class,
	Simulacao.class,
	AlteraSimulacao.class,
	ConsultarTodasSimulacoesCadastradas.class,
	RemoveSimulacao.class
})
public class Suite {

}

package br.com.caelum.ingresso.model;

import java.math.BigDecimal;

public class Estudantes implements Desconto {
	@Override
	public BigDecimal aplicarDescontoSobre(BigDecimal precoOriginal) {
		return precoOriginal.divide(new BigDecimal("2.0"));
	}

	@Override
	public String getDescricao() {
		// TODO Auto-generated method stub
		return "Desconto Estudante";
	}
}

package br.com.caelum.ingresso.model;

import java.math.BigDecimal;

public class Estudantes implements Desconto {
	@Override
	public BigDecimal aplicarDescontoSobre(BigDecimal precoOriginal) {
		return precoOriginal.divide(new BigDecimal("2.0"));
	}
}

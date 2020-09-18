package br.com.gsn.minhasFinancas.exception;

public class RegraNegocioExpection extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RegraNegocioExpection(String mensagem) {
		super(mensagem);
	}
}

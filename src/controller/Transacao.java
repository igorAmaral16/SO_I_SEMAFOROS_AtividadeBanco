package controller;

import java.util.concurrent.Semaphore;

public class Transacao extends Thread{
	private String tipoTrans;
	private int codigoCon;
	private double saldoConta, valorTrans;
	
	private Semaphore semaforoDeposito;
	private Semaphore semaforoSaque;
	
	public Transacao(String _tipo_transacao, int _codigo_conta, double _saldo_conta, 
					 double _valor_transacao, Semaphore _semaforo_deposito, Semaphore _semaforo_saque) {
		
		tipoTrans = _tipo_transacao.toLowerCase();
		codigoCon = _codigo_conta;
		saldoConta = _saldo_conta;
		valorTrans = _valor_transacao;
		
		semaforoDeposito = _semaforo_deposito;
		semaforoSaque = _semaforo_saque;
		
	}

	public void run() {
		if(tipoTrans == "saque") {
			try {
				semaforoSaque.acquire();
				System.out.println(
						"Conta " + codigoCon 
						+ " - Saldo: R$ " + saldoConta 
						+ " Saque: R$ " + valorTrans 
						+ " -> Novo Saldo: R$ " + (saldoConta - valorTrans));
				
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				semaforoSaque.release();
			}
			
		}else if(tipoTrans == "deposito") {
			try {
				semaforoDeposito.acquire();
				System.out.println(
						"Conta " + codigoCon 
						+ " - Saldo: R$ " + saldoConta 
						+ " Deposito: R$ " + valorTrans 
						+ " -> Novo Saldo: R$ " + (codigoCon + valorTrans));
				
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				semaforoDeposito.release();
			}
		}
	}
}

package controller;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class IngressosController extends Thread {
	private int pessoas;
	private static int ingressos=100;
	private boolean status = false;
	private Semaphore semaforo;
	public IngressosController(int pessoas, Semaphore semaforo) {
		this.pessoas=pessoas;
		this.semaforo=semaforo;
	}
	
	@Override
	public void run() {
		Login();
		if(status==true) {
			processocompra();
			if(status==true) {
				try {
					semaforo.acquire();
					ValidaCompra();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					semaforo.release();}
				
				}
			}
		}
		
	
	
	//Login no sistemas
	public void Login() {
		Random gerador = new Random();
		int tempo=gerador.nextInt(1501)+50;
		if(tempo<1000) {
			status=true;
			}
		else {
			System.out.println("A pessoas com id#" + pessoas + " estorou o tempo limite de login");
		}
		
	}
	
	//Processo de compra
	public void processocompra() {
		status=false;
		Random gerador = new Random();
		int tempo=gerador.nextInt(2000)+1000;
		if(tempo<2500) {
			status=true;
		}else {
			System.out.println("A pessoas com id#" + pessoas + " estorou da sessao");
		}
	}
	
	//Valida compra
	public void ValidaCompra() {
		Random gerador = new Random();
		int compra=gerador.nextInt(4)+1;
		ingressos=ingressos-compra;
		if(ingressos>=0) {
			
			System.out.println("O cliente #" + pessoas+" COMPROU:" + compra + ". Ingressos Restantes:" + ingressos );
		}
		if(ingressos<0) {
			ingressos=compra+ingressos;
			System.out.println("Atenção cliente #" + pessoas + " Os Ingressos estao ESGOTADOS");
		}
	}
}

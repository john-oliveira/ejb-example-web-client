package br.com.fluentcode.controller;

import java.io.IOException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fluentcode.ejb.remote.ShoppingCartRemote;
import br.com.fluentcode.infra.mvc.controller.Controller;


public class ShoppingCartController implements Controller {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, NamingException {
		
		if (request.getParameter("method") == null) {
			ShoppingCartRemote cart = lookupCalculatorRemote();

			String item = request.getParameter("item");

			cart.addItem(item);
		} else if ("finishShopping".equals(request.getParameter("method"))) {
             this.finishShopping(request);
		}

		return "/shopping_cart.jsp";
	}
	
	/**
	 * 
	 * TODO ler coment�rio do m�todo
	 */
	private void finishShopping(HttpServletRequest request) throws NamingException{
		//N�o pode fazer lookup aqui n�o est� mantendo o estado (trazendo outro ejb)
		//Como obt�m um statefull ejb em um servlet, j� que o stateful n�o pode ser compartilhado???????
		ShoppingCartRemote cart = lookupCalculatorRemote();
		request.setAttribute("items", cart.getItems());
		cart.finishShopping();
	}

	private ShoppingCartRemote lookupCalculatorRemote() throws NamingException {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		p.put(Context.PROVIDER_URL, "remote://localhost:4447");
		p.put(Context.SECURITY_PRINCIPAL, "elton");
		p.put(Context.SECURITY_CREDENTIALS, "123");
		InitialContext ctx = new InitialContext(p);
		return (ShoppingCartRemote) ctx.lookup("EJB-Example/ShoppingCartBean!br.com.fluentcode.ejb.remote.ShoppingCartRemote");
	}

}

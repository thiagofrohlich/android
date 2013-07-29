package com.ufpr.casaminha;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ufpr.casaminha.dao.HousesDAO;
import com.ufpr.casaminha.model.House;

/**
 * Servlet implementation class Inserir
 */
@WebServlet("/Inserir")
public class Inserir extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Inserir() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		House house = new House();
		house.setEndereco("rua teste");
		house.setQtdQuartos(1);
		house.setTipo(House.APARTAMENTO);
		house.setValor(25555.00);
		house.setVendido(false);
		HousesDAO dao = new HousesDAO();
		dao.save(house);
		PrintWriter out = response.getWriter();
		out.print("<HTML><HEAD><TITLE>HuE</TITLE></HEAD><BODY>TESTE</BODY></HTML>");
	}

}



package com.ufpr.casaminha;

import java.io.IOException;

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
		house.setEndereco(request.getParameter("endereco"));
		house.setQtdQuartos(Integer.parseInt(request.getParameter("qtdQuartos")));
		house.setTipo(request.getParameter("tipo"));
		house.setValor(Double.parseDouble(request.getParameter("valor")));
		house.setValorCondominio(Double.parseDouble(request.getParameter("valorCondominio")));
		house.setVendido(request.getParameter("vendido").equals("false")? false : true);
		HousesDAO dao = new HousesDAO();
		dao.save(house);
	}

}

package com.ufpr.casaminha;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.ufpr.casaminha.model.House;

/**
 * Servlet implementation class GetOne
 */
@WebServlet("/GetOne")
public class GetOne extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetOne() {
        super();
        // TODO Auto-generated constructor stub
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
		Long id = Long.parseLong(request.getParameter("id"));
		
//		Buscar casa
		House casa = new House();
		casa.setId(1);
		casa.setEndereco("end");
		casa.setQtdQuartos(2);
		casa.setTipo(House.APARTAMENTO);
		casa.setValor(200000);
		casa.setValorCondominio(200);
		casa.setVendido(false);
		
		HashMap<String, House> hm = new HashMap<>();
		hm.put("casa", casa);
		
		JSONObject json = JSONObject.fromObject(hm);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		
	}

}

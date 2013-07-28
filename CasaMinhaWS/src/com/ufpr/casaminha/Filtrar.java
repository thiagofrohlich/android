package com.ufpr.casaminha;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.ufpr.casaminha.model.House;

/**
 * Servlet implementation class Filtrar
 */
@WebServlet("/Filtrar")
public class Filtrar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Filtrar() {
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
		String tipo = request.getParameter("tipo");
		Integer qtdQuatos = Integer.parseInt(request.getParameter("qtdQuartos"));
		Double valor = Double.parseDouble(request.getParameter("valor"));
		
//		Buscar casas
		List<House> casas = new ArrayList<>();
		
		HashMap<String, List<House>> hm = new HashMap<>();
		hm.put("message", casas);
		
		JSONObject json = JSONObject.fromObject(hm);
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
	}

}

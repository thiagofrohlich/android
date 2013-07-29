package com.ufpr.casaminha;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ufpr.casaminha.dao.HousesDAO;

/**
 * Servlet implementation class Excluir
 */
@WebServlet("/Excluir")
public class Excluir extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Excluir() {
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
		
		HashMap<String, String> hm = new HashMap<>();
		try {
			HousesDAO dao = new HousesDAO();
			dao.excluir(id);
			hm.put("sucess", Boolean.toString(true));
		}catch(Exception e) {
			hm.put("sucess", Boolean.toString(false));
			e.printStackTrace();
		}

	}

}

package fileattente.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fileattente.modele.AttenteService;

@WebServlet(loadOnStartup = 0, urlPatterns = "/attente")
public class AttenteControleurServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		AttenteServiceProvider.init(getServletContext());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		forwardVueFileAttente(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if ("suivant".equals(action)) {
			AttenteService attenteService = AttenteServiceProvider.get(req);
			attenteService.passerAuSuivant();
		}
		forwardVueFileAttente(req, resp);
	}

	private void forwardVueFileAttente(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/jsp/fileattente.jsp");
		rd.forward(req, resp);
	}
}

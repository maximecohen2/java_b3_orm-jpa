package fileattente.modele;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.*;
import java.util.LinkedList;
import java.util.Queue;

public class AttenteService {
	
	private static final int MAX_TAILLE_FILE_ATTENTE = 10;
	private LinkedList<RendezVous> file = new LinkedList<>();
	private final RendezVousComparator rendezVousComparator = new RendezVousComparator();


	public void mettreEnAttente(Civilite civilite, String prenom, String nom, boolean urgent) throws RendezVousInvalideException, FileAttentePleineException{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("fileAttente");
		if (isFilePleine()) {
			throw new FileAttentePleineException();
		}

		RendezVousInvalideException ex = new RendezVousInvalideException();
		if (civilite == null) {
			ex.addMessage("civilite", "La civilite doit être renseignée !");
		}
		if (prenom == null || prenom.isEmpty()) {
			ex.addMessage("prenom", "Le prénom doit être renseigné !");
		}
		if (nom == null || nom.isEmpty()) {
			ex.addMessage("nom", "Le nom doit être renseigné !");
		}
		if (ex.mustBeThrown()) {
			throw ex;
		}

		//RendezVous rendezVous = new RendezVous(civilite, prenom, nom, urgent);
		RendezVous rendezVous = new RendezVous();

		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		boolean successTransaction = false;
		try {
			entityManager.persist(rendezVous);
			successTransaction = true;
		} finally {
			if (successTransaction) {
				entityManager.getTransaction().commit();
			} else {
				entityManager.getTransaction().rollback();
			}
			entityManager.close();
		}
	}

	private void mettreEnAttente(RendezVous rendezVous) {
		int index = 0;
		for(RendezVous precedentRendezVous : this.file) {
			if (rendezVousComparator.compare(rendezVous, precedentRendezVous) < 0) {
				break;
			}
			index++;
		}
		file.add(index, rendezVous);
	}

	public void passerAuSuivant() {
		file.pollFirst();
	}
	
	public Queue<RendezVous> getFile() {
		return file;
	}
	
	public boolean isFilePleine() {
		return file.size() >= MAX_TAILLE_FILE_ATTENTE;
	}
}

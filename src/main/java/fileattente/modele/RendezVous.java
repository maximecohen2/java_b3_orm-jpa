package fileattente.modele;

import javax.persistence.*;
import java.util.Date;

@Entity
public class RendezVous {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Basic
	private Civilite civilite;

	@Basic
	@Column(length = 60, nullable = false)
	private String prenom;

	@Basic
	@Column(length = 60, nullable = false)
	private String nom;

	@Basic
	private boolean urgent;

	@Basic
	//@Temporal(TemporalType.DATE)
	private Date date;


	public Long getId() {
		return id;
	}

	public Civilite getCivilite() {
		return civilite;
	}

	public void setCivilite(Civilite civilite) {
		this.civilite = civilite;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public boolean isUrgent() {
		return urgent;
	}

	public void setUrgent(boolean urgent) {
		this.urgent = urgent;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}

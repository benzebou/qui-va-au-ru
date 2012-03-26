package fr.esstin.ru.ws;

import java.util.Vector;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;

public class WSUtils {

	public Object resultatRequeteSOAP = null;
	public SoapObject retour = null;
	public Object[] retourTab = null;
	public SoapObject objetSOAP;
	private HttpTransportSE connexionServeur = null;
	private SoapSerializationEnvelope envelope = null;

	public HttpTransportSE getConnexionServeur() {
		return connexionServeur;
	}

	// nom du service
	private String nomService = "";
	// url du service
	private String urlService = "";
	// m�thode du service
	private String methodeChoisie = "";

	// constructeur soap
	public WSUtils(String urlService) {
		this.urlService = urlService;

		try { // etape 1 cr�ation du module de connexion HTTP
			this.connexionServeur = new HttpTransportSE(this.urlService);
			this.connexionServeur.debug = true;

		} catch (Exception e) {
			Log.e("ERREUR SOAP", e.getMessage());
			e.printStackTrace();
		}
		// return connexionServeur;

	}

	// se connecter au serveur et retourner l'instance
	// public void connexionServeur(String urlService){}
	// cr�ation de l'objet SOAP et retourner l'objet
	public void objetSoap(String nomService, String methodeChoisie, Vector arg) {

		this.nomService = nomService;
		this.methodeChoisie = methodeChoisie;

		try {
			// cr�ation objet SOAP
			this.objetSOAP = new SoapObject(nomService, methodeChoisie);

			// ajout des propriet�s pour cette m�thode
			if (arg != null) {
				for (int i = 0; i < arg.size(); i++)
					this.objetSOAP.addProperty("arg" + i, arg.elementAt(i));
			}

		} catch (Exception e) {
			Log.e("ERREUR SOAP", e.getMessage());
			e.printStackTrace();
		}
		// return objetSOAP;
	}

	public void envelope() {

		try {

			// cr�ation d'un objet qui contiendra nos propri�t�s
			this.envelope = new SoapSerializationEnvelope(
					SoapSerializationEnvelope.VER11);
			this.envelope.bodyOut = this.objetSOAP;
			// cr�ation de l'objet SOAP ok

		} catch (Exception e) {

			e.printStackTrace();
		}
		// return this.envelope;
	}

	public SoapSerializationEnvelope getEnvelope() {
		return envelope;
	}

	public Object resultatRequeteSOAP() {

		// connexion au serveur
		try {
			// invoquation de la m�thode sur le serveur
			this.connexionServeur.call(null, this.envelope);
			// recuperation de la r�ponse du serveur
			this.resultatRequeteSOAP = this.envelope.getResponse();

		} catch (Exception aE) {
			aE.printStackTrace();
		}
		return this.resultatRequeteSOAP;
	}

	public Object[] resultatRequeteSOAPTabObject() {
		int nbr = 0;
		// connexion au serveur
		try {
			// invoquation de la m�thode sur le serveur
			this.connexionServeur.call(null, this.envelope);
			// r�cuperation de la r�ponse du serveur
			this.retour = (SoapObject) this.envelope.bodyIn;

			if (this.retour != null) {
				nbr = retour.getPropertyCount();

				retourTab = new Object[nbr];

				for (int i = 0; i < nbr; i++) {
					retourTab[i] = retour.getProperty(i);
				}
			}

		} catch (Exception aE) {
			Log.e("ERREUR SOAP", aE.getMessage());
			aE.printStackTrace();
		}
		return this.retourTab;
	}

}

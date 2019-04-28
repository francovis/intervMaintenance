package Models;

import java.util.Date;


public class Intervention extends Model {    
    
    private Integer noInterv;
    private Date dateSignalement;
    private String descriptifBrefProblème;
    private String signaleurIncident;
    private String preneurEnCharge;
    private String etatInterv;
    private Boolean suiviViaFournisseur;
    private Date dateContact;
    private Date datePrise;
    private Date dateRetour;
    private String etatRetour;
    private Date dateRemiseService;
    private Integer tempsInterne;
    private String résultat;
    private Fournisseur fkFournisseurIntervenant;
    private PcUnit fkPcUnit;
    private TypeIntervention fkTypeInterv;

    public Intervention() {}

    public Integer getNoInterv() {
        return noInterv;
    }

    public void setNoInterv(Integer noInterv) {
        this.noInterv = noInterv;
    }

    public Date getDateSignalement() {
        return dateSignalement;
    }

    public void setDateSignalement(Date dateSignalement) {
        this.dateSignalement = dateSignalement;
    }

    public String getDescriptifBrefProblème() {
        return descriptifBrefProblème;
    }

    public void setDescriptifBrefProblème(String descriptifBrefProblème) {
        this.descriptifBrefProblème = descriptifBrefProblème;
    }

    public String getSignaleurIncident() {
        return signaleurIncident;
    }

    public void setSignaleurIncident(String signaleurIncident) {
        this.signaleurIncident = signaleurIncident;
    }

    public String getPreneurEnCharge() {
        return preneurEnCharge;
    }

    public void setPreneurEnCharge(String preneurEnCharge) {
        this.preneurEnCharge = preneurEnCharge;
    }

    public String getEtatInterv() {
        return etatInterv;
    }

    public void setEtatInterv(String etatInterv) {
        this.etatInterv = etatInterv;
    }

    public Boolean getSuiviViaFournisseur() {
        return suiviViaFournisseur;
    }

    public void setSuiviViaFournisseur(Boolean suiviViaFournisseur) {
        this.suiviViaFournisseur = suiviViaFournisseur;
    }

    public Date getDateContact() {
        return dateContact;
    }

    public void setDateContact(Date dateContact) {
        this.dateContact = dateContact;
    }

    public Date getDatePrise() {
        return datePrise;
    }

    public void setDatePrise(Date datePrise) {
        this.datePrise = datePrise;
    }

    public Date getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(Date dateRetour) {
        this.dateRetour = dateRetour;
    }

    public String getEtatRetour() {
        return etatRetour;
    }

    public void setEtatRetour(String etatRetour) {
        this.etatRetour = etatRetour;
    }

    public Date getDateRemiseService() {
        return dateRemiseService;
    }

    public void setDateRemiseService(Date dateRemiseService) {
        this.dateRemiseService = dateRemiseService;
    }

    public Integer getTempsInterne() {
        return tempsInterne;
    }

    public void setTempsInterne(Integer tempsInterne) {
        this.tempsInterne = tempsInterne;
    }

    public String getRésultat() {
        return résultat;
    }

    public void setRésultat(String résultat) {
        this.résultat = résultat;
    }

    public Fournisseur getFkFournisseurIntervenant() {
        return fkFournisseurIntervenant;
    }

    public void setFkFournisseurIntervenant(Fournisseur fkFournisseurIntervenant) {
        this.fkFournisseurIntervenant = fkFournisseurIntervenant;
    }

    public PcUnit getFkPcUnit() {
        return fkPcUnit;
    }

    public void setFkPcUnit(PcUnit fkPcUnit) {
        this.fkPcUnit = fkPcUnit;
    }

    public TypeIntervention getFkTypeInterv() {
        return fkTypeInterv;
    }

    public void setFkTypeInterv(TypeIntervention fkTypeInterv) {
        this.fkTypeInterv = fkTypeInterv;
    }

    @Override
    public String idKey() {
        return "NoInterv";
    }
    
    @Override
    public void idKey(Object key){
        this.setNoInterv((Integer)key);
    }
    
    @Override
    public String toString(){
        return this.getNoInterv().toString();
    }

}

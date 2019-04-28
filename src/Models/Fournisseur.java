package Models;


public class Fournisseur extends Model {

    private String fournisseurId;
    private String nomFourn;
    private String adrFourn;
    private String localFourn;
    private String cPFourn;
    private String numTelFourn;
    private String adrCourrielFourn;

    public Fournisseur() {}

    public String getFournisseurId() {
        return fournisseurId;
    }

    public void setFournisseurId(String fournisseurId) {
        this.fournisseurId = fournisseurId;
    }

    public String getNomFourn() {
        return nomFourn;
    }

    public void setNomFourn(String nomFourn) {
        this.nomFourn = nomFourn;
    }

    public String getAdrFourn() {
        return adrFourn;
    }

    public void setAdrFourn(String adrFourn) {
        this.adrFourn = adrFourn;
    }

    public String getLocalFourn() {
        return localFourn;
    }

    public void setLocalFourn(String localFourn) {
        this.localFourn = localFourn;
    }

    public String getCPFourn() {
        return cPFourn;
    }

    public void setCPFourn(String cPFourn) {
        this.cPFourn = cPFourn;
    }

    public String getNumTelFourn() {
        return numTelFourn;
    }

    public void setNumTelFourn(String numTelFourn) {
        this.numTelFourn = numTelFourn;
    }

    public String getAdrCourrielFourn() {
        return adrCourrielFourn;
    }

    public void setAdrCourrielFourn(String adrCourrielFourn) {
        this.adrCourrielFourn = adrCourrielFourn;
    }

    @Override
    public String idKey() {
        return "FournisseurId";
    }
    
    @Override
    public void idKey(Object key){
        this.setFournisseurId((String)key);
    }
    
    @Override
    public String toString(){
        return this.getNomFourn();
    }

}

package Models;
import java.util.Date;

public class LotConfiguration extends Model {
    
    private Integer noLot;
    private Date dateAcquisition;
    private Date dateFinGarantie;
    private Integer nbreUnitésAcquises;
    private ConfigurationPc fkConfig;
    private Fournisseur fkFournisseurLot;

    public LotConfiguration() {}

    public Integer getNoLot() {
        return noLot;
    }

    public void setNoLot(Integer noLot) {
        this.noLot = noLot;
    }

    public Date getDateAcquisition() {
        return dateAcquisition;
    }

    public void setDateAcquisition(Date dateAcquisition) {
        this.dateAcquisition = dateAcquisition;
    }

    public Date getDateFinGarantie() {
        return dateFinGarantie;
    }

    public void setDateFinGarantie(Date dateFinGarantie) {
        this.dateFinGarantie = dateFinGarantie;
    }

    public Integer getNbreUnitésAcquises() {
        return nbreUnitésAcquises;
    }

    public void setNbreUnitésAcquises(Integer nbreUnitésAcquises) {
        this.nbreUnitésAcquises = nbreUnitésAcquises;
    }

    public ConfigurationPc getFkConfig() {
        return fkConfig;
    }

    public void setFkConfig(ConfigurationPc fkConfig) {
        this.fkConfig = fkConfig;
    }

    public Fournisseur getFkFournisseurLot() {
        return fkFournisseurLot;
    }

    public void setFkFournisseurLot(Fournisseur fkFournisseurLot) {
        this.fkFournisseurLot = fkFournisseurLot;
    }

    @Override
    public String idKey() {
        return "NoLot";
    }
    
    @Override
    public void idKey(Object key){
        this.setNoLot((Integer)key);
    }

    @Override
    public String toString(){
        return this.getNoLot().toString();
    }
}

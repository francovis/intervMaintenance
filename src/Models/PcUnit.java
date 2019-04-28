package Models;

public class PcUnit extends Model {
    
    private String idPcUnit;
    private String particularité;
    private String local;
    private LotConfiguration fkLot;
    
    public PcUnit(){}
    
    public String getIdPcUnit() {
        return idPcUnit;
    }

    public void setIdPcUnit(String idPcUnit) {
        this.idPcUnit = idPcUnit;
    }

    public String getParticularité() {
        return particularité;
    }

    public void setParticularité(String particularité) {
        this.particularité = particularité;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public LotConfiguration getFkLot() {
        return fkLot;
    }

    public void setFkLot(LotConfiguration fkLot) {
        this.fkLot = fkLot;
    }

    @Override
    public String idKey() {
        return "IdPcUnit";
    }
        
    @Override
    public void idKey(Object key){
        this.setIdPcUnit((String)key);
    }

    @Override
    public String toString(){
        return this.getIdPcUnit();
    }
}

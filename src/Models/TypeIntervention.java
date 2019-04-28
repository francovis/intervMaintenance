package Models;

public class TypeIntervention extends Model {

    private String codeTypeInt;
    private String libelleTypeInt;
    
    public TypeIntervention(){}

    public String getCodeTypeInt() {
        return codeTypeInt;
    }

    public void setCodeTypeInt(String codeTypeInt) {
        this.codeTypeInt = codeTypeInt;
    }

    public String getLibelleTypeInt() {
        return libelleTypeInt;
    }

    public void setLibelleTypeInt(String libelleTypeInt) {
        this.libelleTypeInt = libelleTypeInt;
    }

    @Override
    public String idKey() {
        return "CodeTypeInt";
    }
    
    @Override
    public void idKey(Object key){
        this.setCodeTypeInt((String)key);
    }
    
    @Override
    public String toString(){
        return this.getLibelleTypeInt();
    }

}

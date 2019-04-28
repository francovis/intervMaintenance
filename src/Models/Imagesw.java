package Models;


public class Imagesw extends Model {
    
    private String nomImage;
    private String risServer;
    private Integer tailleImage;
    private String descrBreveImage;

    public Imagesw() {}

    public String getNomImage() {
        return nomImage;
    }

    public void setNomImage(String nomImage) {
        this.nomImage = nomImage;
    }

    public String getRisServer() {
        return risServer;
    }

    public void setRisServer(String risServer) {
        this.risServer = risServer;
    }

    public Integer getTailleImage() {
        return tailleImage;
    }

    public void setTailleImage(Integer tailleImage) {
        this.tailleImage = tailleImage;
    }

    public String getDescrBreveImage() {
        return descrBreveImage;
    }

    public void setDescrBreveImage(String descrBreveImage) {
        this.descrBreveImage = descrBreveImage;
    }

    @Override
    public String idKey() {
        return "NomImage";
    }
    
    @Override
    public void idKey(Object key){
        this.setNomImage((String)key);
    }
    
    @Override
    public String toString(){
        return this.getNomImage();
    }

}

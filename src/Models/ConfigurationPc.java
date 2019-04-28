package Models;

public class ConfigurationPc extends Model{
    private Integer configId;
    private String carteMere;
    private String bioType;
    private String bioVersion;
    private String processeur;
    private Integer memoireVive;
    private Integer tailleDisque;
    private String carteSon;
    private String lecteurDvd;
    private Integer portsUsb;
    private Integer imageTpsCharg;
    private String commentaireCharg;
    private Imagesw fkImageACharger;

    public ConfigurationPc(){}
    
    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public String getCarteMere() {
        return carteMere;
    }

    public void setCarteMere(String carteMere) {
        this.carteMere = carteMere;
    }

    public String getBioType() {
        return bioType;
    }

    public void setBioType(String bioType) {
        this.bioType = bioType;
    }

    public String getBioVersion() {
        return bioVersion;
    }

    public void setBioVersion(String bioVersion) {
        this.bioVersion = bioVersion;
    }

    public String getProcesseur() {
        return processeur;
    }

    public void setProcesseur(String processeur) {
        this.processeur = processeur;
    }

    public Integer getMemoireVive() {
        return memoireVive;
    }

    public void setMemoireVive(Integer memoireVive) {
        this.memoireVive = memoireVive;
    }

    public Integer getTailleDisque() {
        return tailleDisque;
    }

    public void setTailleDisque(Integer tailleDisque) {
        this.tailleDisque = tailleDisque;
    }

    public String getCarteSon() {
        return carteSon;
    }

    public void setCarteSon(String carteSon) {
        this.carteSon = carteSon;
    }

    public String getLecteurDvd() {
        return lecteurDvd;
    }

    public void setLecteurDvd(String lecteurDvd) {
        this.lecteurDvd = lecteurDvd;
    }

    public Integer getPortsUsb() {
        return portsUsb;
    }

    public void setPortsUsb(Integer portsUsb) {
        this.portsUsb = portsUsb;
    }

    public Integer getImageTpsCharg() {
        return imageTpsCharg;
    }

    public void setImageTpsCharg(Integer imageTpsCharg) {
        this.imageTpsCharg = imageTpsCharg;
    }

    public String getCommentaireCharg() {
        return commentaireCharg;
    }

    public void setCommentaireCharg(String commentaireCharg) {
        this.commentaireCharg = commentaireCharg;
    }

    public Imagesw getFkImageACharger() {
        return fkImageACharger;
    }

    public void setFkImageACharger(Imagesw fkImageACharger) {
        this.fkImageACharger = fkImageACharger;
    }

    @Override
    public String idKey() {
        return "ConfigId";
    }
    
    @Override
    public void idKey(Object key){
        this.setConfigId((Integer)key);
    }
    
    @Override
    public String toString(){
        return this.getConfigId().toString();
    }

}

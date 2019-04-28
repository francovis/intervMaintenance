package GestionMatInfoException;


public class UnknownTypeException extends Exception{
    // on passe le type au format de string
    // voir handleKnownType et handleUnknownType de QueryHandler
    // c'est pas très propre de faire comme ça mais bon ... personne n'est parfait
    public UnknownTypeException(String unknownType){
        super(unknownType);
    }
}

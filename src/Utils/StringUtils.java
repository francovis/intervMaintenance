package Utils;

public class StringUtils {
    // concaténé les String avec des + n'est pas une bonne pratique
    public static String concat(String ... strs){
        // StringBuilder permet la concaténation de string de manière clean
        var sb = new StringBuilder();
        for(String str : strs)
            sb.append(str);
        return sb.toString();
    }
    // la première lettre du string sera toujours une minuscule
    public static String normalize(String str){
        return str.replaceFirst(str.substring(0,1), str.substring(0,1).toLowerCase());
    }
}

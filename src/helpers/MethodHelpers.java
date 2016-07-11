package helpers;

/**
 * Created by vprocure on 7/11/2016.
 */
public final class MethodHelpers {
    public static void checkParameterForNull(Object param, String message){
        if(param == null){
            throw new IllegalArgumentException(message);
        }
    }
}

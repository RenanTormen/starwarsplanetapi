package dev.renantormen.starwarsplanetapi.utils;

public class StringUtil {
 
    private StringUtil(){

    }

    public static String formatarPrimeiraLetraUppercase(String string){
        return string.substring(0,1).toUpperCase() + string.substring(1, string.length()).toLowerCase();
    }

}
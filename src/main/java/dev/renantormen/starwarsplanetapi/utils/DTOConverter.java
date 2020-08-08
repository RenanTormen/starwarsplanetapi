package dev.renantormen.starwarsplanetapi.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import dev.renantormen.starwarsplanetapi.dto.DTOGenerico;

public class DTOConverter {

    private static final Logger LOGGER = Logger.getLogger("DTOConverter");

    public static <C extends DTOGenerico> C toDTO(Class<C> dtoClass, Object entity) {
        C newDTO = null;

        if(Objects.nonNull(entity)) {
            try {
                newDTO = dtoClass.getDeclaredConstructor().newInstance();
                List<Method> setters = filtrarSettersDaClasse(dtoClass);
                for (Method setter : setters) {
                    setarValorNaNovaClasse(entity, newDTO, setter);
                }
                return newDTO;
            } catch (Throwable e) {
                LOGGER.log(Level.SEVERE, "Erro ao gerar DTO", e);
            }    
        }
        return newDTO;
    }

    public static  <E, C extends DTOGenerico> E toEntity(Class<E> entityClass, C dto){
        E newClass = null;
        try {
            newClass = entityClass.getDeclaredConstructor().newInstance();
            List<Method> setters = filtrarSettersDaClasse(entityClass);
            for (Method setter : setters) {
                setarValorNaNovaClasse(dto, newClass, setter);
            }
            return newClass;
        } catch (Throwable e) {
            LOGGER.log(Level.SEVERE, "Erro ao gerar DTO", e);
        }
        return null;
    }

    private static <C> void setarValorNaNovaClasse(Object valorDoGetter, C newClass, Method setter) {
        try {
            setter.invoke(newClass, retornarValorDoGetter(valorDoGetter, setter));
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            LOGGER.log(Level.SEVERE, "Erro ao gerar DTO", e);
        }
    }

    private static <C> List<Method> filtrarSettersDaClasse(Class<C> classe) {
        return Arrays.asList(classe.getMethods()).stream().filter(method -> method.getName().startsWith("set")).collect(Collectors.toList());
    }

    private static Object retornarValorDoGetter(Object entidade, Method setter) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return entidade.getClass().getMethod("get" + setter.getName().substring(3, setter.getName().length())).invoke(entidade);
    }
}
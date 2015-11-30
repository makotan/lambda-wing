package com.makotan.libs.lambda.wing.tool.apigateway.converter;

import com.google.common.io.CharStreams;
import com.makotan.libs.lambda.wing.core.util.StringUtils;
import io.swagger.models.Scheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by makotan on 2015/11/29.
 */
public class ConverterUtils {
    static Logger log = LoggerFactory.getLogger(ConverterUtils.class);

    static {

    }

    @FunctionalInterface
    public interface Setter<SWG,T> {
        SWG set(T val);
    }

    @FunctionalInterface
    public interface SetCheck<T> {
        boolean isNotValid(T val);
    }

    @FunctionalInterface
    public interface Convert<T,S> {
        T convert(S val);
    }

    public static List<Scheme> convertScheme(String[] schemes) {
        if (schemes == null || schemes.length == 0) {
            return null;
        }
        return Arrays.stream(schemes).map(Scheme::forValue).collect(Collectors.toList());
    }

    public static String readResource(String templateFileName) {
        String template = null;
        if (StringUtils.isNotEmpty(templateFileName)) {
            try (InputStream inputStream = Object.class.getResourceAsStream(templateFileName);
                 InputStreamReader reader = new InputStreamReader(inputStream)) {
                template = CharStreams.toString(reader);
            } catch (Throwable th) {
                log.error("load template" , th);
            }
        }
        return template;
    }

    public static <T> boolean isEmpty(T[] ary) {
        return ary == null || ary.length == 0;
    }

    public static <T> boolean isEmpty(List<T> ary) {
        return ary == null || ary.isEmpty() ;
    }

    public static <SWG,T,S> void setConvertVal(@NotNull Setter<SWG,T> setter, S val , @NotNull Convert<T,S> convert , @NotNull SetCheck<S> checker ) {
        if (val == null || checker.isNotValid(val)) {
            return;
        }
        setConvertVal(setter, val , convert);
    }

    public static <SWG,T,S> void setConvertVal(@NotNull Setter<SWG,T> setter, S val , @NotNull Convert<T,S> convert) {
        if (val != null) {
            setter.set(convert.convert(val));
        }
    }

    public static <SWG,T> void setVal(@NotNull Setter<SWG,T> setter, T val , @NotNull T defaultVal , @NotNull Supplier<T> supplier) {
        T setVal = val;
        if (defaultVal != null && defaultVal.equals(setVal) || setVal == null) {
            setVal = supplier.get();
        }
        setVal(setter,setVal,defaultVal);
    }

    public static <SWG,T> void setVal(@NotNull Setter<SWG,T> setter, T val , @NotNull T defaultVal) {
        if (defaultVal != null && defaultVal.equals(val)) {
            return;
        }
        setVal(setter, val);
    }

    public static <SWG,T> void setVal(@NotNull Setter<SWG,T> setter, T val) {
        if (val == null) {
            return;
        }
        setter.set(val);
    }

    public static <SWG,T> void setCheckVal(@NotNull Setter<SWG,T> setter, T val , @NotNull T defaultVal , @NotNull Supplier<T> supplier , @NotNull SetCheck<T> checker) {
        T setVal = val;
        if (defaultVal != null && defaultVal.equals(setVal) || setVal == null) {
            setVal = supplier.get();
        }
        setCheckVal(setter,setVal,defaultVal, checker);
    }

    public static <SWG,T> void setCheckVal(@NotNull Setter<SWG,T> setter, T val ,@NotNull  T defaultVal , @NotNull SetCheck<T> checker) {
        if (defaultVal != null && defaultVal.equals(val)) {
            return;
        }
        setCheckVal(setter, val, checker);
    }

    public static <SWG,T> void setCheckVal(@NotNull Setter<SWG,T> setter, T val , @NotNull SetCheck<T> checker) {
        if (checker.isNotValid(val)) {
            return;
        }
        setter.set(val);
    }

    public static <U,T> U foldLeft(Stream<T> stream , U identity , BiFunction<U, T , U> accumulator) {
        AtomicReference<U> ref = new AtomicReference<>(identity);
        stream.forEach( v -> {
            U u = accumulator.apply(ref.get() , v);
            ref.set(u);
        });
        return ref.get();
    }
}

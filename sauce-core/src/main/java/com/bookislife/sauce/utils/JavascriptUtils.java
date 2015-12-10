package com.bookislife.sauce.utils;

import javax.script.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by SidneyXu on 2015/12/10.
 */
public class JavascriptUtils {

    public static ScriptEngine getScriptEngineManager() {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        return engine;
    }

    public static Object evalJSFile(ScriptEngine engine, String filePath) throws FileNotFoundException, ScriptException {
        Object result = "";
        FileReader reader = null;
        try {
            File file = new File(filePath);
            reader = new FileReader(file);
            result = engine.eval(reader);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
        return result;
    }

    public static Object evalJSString(ScriptEngine engine, String script) throws ScriptException {
        return engine.eval(script);
    }

    public static void put(ScriptEngine engine, String key, String value) {
        engine.put(key, value);
    }

    public static void put(Bindings scope, String key, String value) {
        scope.put(key, value);
    }

    public static Bindings putInNewScope(ScriptEngine engine, String key, String value) {
        Bindings scope = engine.createBindings();
        put(scope, key, value);
        return scope;
    }

    public static Object get(ScriptEngine engine, String key, Object defaultValue) {
        Object result = engine.get(key);
        result = result == null ? defaultValue : result;
        return result;
    }

    public static Object get(Bindings scope, String key, Object defaultValue) {
        Object result = scope.get(key);
        result = result == null ? defaultValue : result;
        return result;
    }

    public static Object invoke(ScriptEngine engine, String methodName, Object... args) throws ScriptException, NoSuchMethodException {
        Invocable invocable = (Invocable) engine;
        return invocable.invokeFunction(methodName, args);
    }

    public static <T> T getInstanceFromJs(ScriptEngine engine, Class<T> interfaceName) {
        Invocable invocable = (Invocable) engine;
        return invocable.getInterface(interfaceName);
    }
}

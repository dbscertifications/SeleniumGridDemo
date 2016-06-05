package com.test.grid.javascript;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by my computers on 5/23/2016.
 */
public class jstest {

    public static void main(String[] args) throws Exception {
        // create a script engine manager
        String var1 = "happy";
        String var2 = "new";
        String var3 = "year";
        ScriptEngineManager factory = new ScriptEngineManager();
        // create a JavaScript engine
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        // evaluate JavaScript code from String
        try {
            engine.eval("print('Welocme to java world')");

            engine.eval(Files.newBufferedReader(Paths.get("C:/js/first.js"), StandardCharsets.UTF_8));

            Invocable inv = (Invocable) engine;
            // call function from script file
            //inv.invokeFunction("fn1", var1,var2,var3);
            String returnValue = (String)inv.invokeFunction("fn1", var1,var2,var3 );
            System.out.println(returnValue);
        }catch(ScriptException e){
            System.err.println(e);
        }

    }

}

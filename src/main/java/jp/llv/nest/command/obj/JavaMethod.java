/* 
 * The MIT License
 *
 * Copyright 2016 toyblocks.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jp.llv.nest.command.obj;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;
import jp.llv.nest.command.Arg;
import jp.llv.nest.command.CommandExecutor;
import jp.llv.nest.command.exceptions.ArgMismatchException;
import jp.llv.nest.command.exceptions.CommandException;
import jp.llv.nest.command.exceptions.InsufficientArgumentsException;
import jp.llv.nest.command.exceptions.InternalException;
import jp.llv.nest.command.exceptions.TypeMismatchException;
import jp.llv.nest.command.obj.bind.Binding;
import jp.llv.nest.command.Func;

/**
 *
 * @author toyblocks
 */
public class JavaMethod extends NestObjectAdapter<Method> implements NestExecutable<Method> {

    private final Object callee;
    private final Method method;
    private final Func command;
    private final String name;
    private final String[] aliases;
    private final Class<? extends NestObject<?>> returnType;
    private final ArgDescription[] descs;

    public JavaMethod(Object callee, Method method) throws IllegalArgumentException {
        Objects.requireNonNull(callee);
        Objects.requireNonNull(method);
        this.callee = callee;
        this.method = method;
        method.setAccessible(true);
        if (!method.isAnnotationPresent(Func.class)) {
            throw new IllegalArgumentException("No annotation present");
        } else if (method.getReturnType() == void.class) {
            this.returnType = null;
        } else if (NestObject.class.isAssignableFrom(method.getReturnType())) {
            this.returnType = (Class<? extends NestObject<?>>) method.getReturnType();
        } else {
            throw new IllegalArgumentException("Invalid return type");
        }
        this.command = method.getAnnotation(Func.class);
        this.descs = getDescription(method.getParameters());
        this.name = "".equalsIgnoreCase(this.command.name()) ? method.getName() : this.command.name();
        this.aliases = this.command.aliases();
    }

    @Override
    public NestObject<?> execute(CommandExecutor executor, NestCommandSender sender, Binding binding, NestObject<?>... args) throws CommandException {
        if (args.length < 1) {
            throw new InsufficientArgumentsException();
        }
        Object[] jargs = new Object[descs.length];
        int di = 0;
        int varArgsStart = -1;
        outer:
        for (int i = 1; i < args.length; i++) {
            TypeMismatchException lastEx = null;
            while (true) {
                if (di >= descs.length) {
                    throw lastEx == null ? new ArgMismatchException("The arg '" + args[i] + "' cannot be handled") : lastEx;
                }
                ArgDescription desc = descs[di];
                if (desc.isVarArgs()) {
                    varArgsStart = i;
                    break outer;
                }
                try {
                    jargs[di] = NestObject.to(args[i],desc.getType());
                    break;
                } catch (TypeMismatchException ex) {
                    if (desc.isRequired()) {
                        throw ex;
                    } else {
                        lastEx = ex;
                    }
                }
                di++;
            }
            di++;
        }
        if (varArgsStart >= 0) {
            ArgDescription desc = descs[descs.length - 1];
            Object[] varArgs = (Object[]) Array.newInstance(desc.getType(), args.length - varArgsStart);
            for (int i = varArgsStart; i < args.length; i++) {
                varArgs[i - varArgsStart] = NestObject.to(args[i],desc.getType());
            }
            jargs[di] = varArgs;
        }
        
        Object[] realArgs = new Object[jargs.length + 3];
        realArgs[0] = executor;
        realArgs[1] = sender;
        realArgs[2] = binding;
        System.arraycopy(jargs, 0, realArgs, 3, jargs.length);
        try {
            return (NestObject<?>) this.method.invoke(callee, realArgs);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new InternalException(ex);
        }
    }

    @Override
    public String[] getDescription() {
        return command.value().clone();
    }

    @Override
    public ArgDescription[] getArgDescriptions() {
        return this.descs.clone();
    }

    @Override
    public Class<? extends NestObject> getReturnType() {
        return returnType;
    }

    @Override
    public Class<? extends NestCommandSender> getAllowedSender() {
        return (Class<? extends NestCommandSender>) method.getParameterTypes()[0];
    }

    @Override
    public String getPermission() {
        return command != null ? ("".equals(command.permission()) ? null : command.permission()) : null;
    }

    public String getName() {
        return this.name;
    }

    public String[] getAliases() {
        return this.aliases.clone();
    }

    private static ArgDescription[] getDescription(Parameter[] parameters) {
        if (parameters.length < 2) {
            throw new IllegalArgumentException("Not enough parameters");
        } else if (!CommandExecutor.class.isAssignableFrom(parameters[0].getType())) {
            throw new IllegalArgumentException("Illegal 1st arg");
        } else if (!NestCommandSender.class.isAssignableFrom(parameters[1].getType())) {
            throw new IllegalArgumentException("Illegal 2nd arg");
        } else if (!Binding.class.isAssignableFrom(parameters[2].getType())) {
            throw new IllegalArgumentException("Illegal 3rd arg");
        }
        ArgDescription[] res = new ArgDescription[parameters.length - 3];
        for (int i = 3; i < parameters.length; i++) {
            Parameter p = parameters[i];
            Arg a = p.isAnnotationPresent(Arg.class) ? p.getAnnotation(Arg.class) : null;
            Class<?> t = p.isVarArgs() ? p.getType().getComponentType() : p.getType();
            if (!NestObject.class.isAssignableFrom(t)) {
                throw new IllegalArgumentException("Illegal 4th or later arg");
            }
            res[i - 3] = new ArgDescription((Class<? extends NestObject>) t,
                    p.getName(),
                    p.isVarArgs(),
                    a == null ? null : ("".equals(a.group()) ? null : a.group()),
                    a == null ? true : a.required());
        }
        return res;
    }

}

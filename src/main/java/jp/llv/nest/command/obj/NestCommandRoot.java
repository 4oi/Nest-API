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

import java.util.HashMap;
import java.util.Map;
import jp.llv.nest.command.CommandExecutor;
import jp.llv.nest.command.Context;
import jp.llv.nest.command.exceptions.CommandException;
import jp.llv.nest.command.exceptions.InsufficientArgumentsException;
import jp.llv.nest.command.exceptions.UndefinedVariableException;
import jp.llv.nest.command.obj.bind.Binding;

/**
 *
 * @author toyblocks
 */
public class NestCommandRoot extends NestObjectAdapter<Void> implements NestExecutable<NestCommandSender<?>, Void> {

    private static final ArgDescription ARG_DESC_0 = new ArgDescription(NestString.class, "subcommand", false, null, true);
    private static final ArgDescription ARG_DESC_1 =  new ArgDescription(NestObject.class, "args", true, null, true);
    private final Map<String, NestExecutable<NestCommandSender<?>, ?>> subCommands = new HashMap<>();
    
    @Override
    public NestObject<?> execute(Context<? extends NestCommandSender<?>> context, NestObject<?>... args) throws CommandException {
        if (args.length < 2) {
            throw new InsufficientArgumentsException();
        }
        String key = args[1].to(NestString.class).unwrap();
        if (!this.subCommands.containsKey(key)) {
            throw new UndefinedVariableException("No such subcommand");
        }
        NestObject[] newArgs = new NestObject[args.length-1];
        System.arraycopy(args, 1, newArgs, 0, newArgs.length);
        return this.subCommands.get(key).execute(context, newArgs);
    }

    @Override
    public String[] getDescription() {
        return new String[]{String.join("/", this.subCommands.keySet())};
    }

    @Override
    public ArgDescription[] getArgDescriptions() {
        return new ArgDescription[]{ARG_DESC_0, ARG_DESC_1};
    }
    
    public void put(String key, NestExecutable<NestCommandSender<?>, ?> executable) {
        this.subCommands.put(key, executable);
    }
    
    public NestExecutable<?, ?> get(String key) {
        return this.subCommands.get(key);
    }
    
    public NestExecutable<?, ?> remove(String key) {
        return this.subCommands.remove(key);
    }
    
}

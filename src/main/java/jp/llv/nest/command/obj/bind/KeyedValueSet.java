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
package jp.llv.nest.command.obj.bind;

import java.util.Collection;
import java.util.stream.Collectors;
import jp.llv.nest.command.Context;
import jp.llv.nest.command.exceptions.CommandException;
import jp.llv.nest.command.exceptions.InsufficientArgumentsException;
import jp.llv.nest.command.exceptions.UndefinedVariableException;
import jp.llv.nest.command.obj.NestCommandSender;
import jp.llv.nest.command.obj.NestExecutable;
import jp.llv.nest.command.obj.NestObject;
import jp.llv.nest.command.obj.NestString;

/**
 *
 * @author toyblocks
 */
public interface KeyedValueSet<E> extends NestExecutable<NestCommandSender, E> {
    
    NestObject<?> get(NestString key) throws UndefinedVariableException;
    
    Collection<String> keySet();

    @Override
    default ArgDescription[] getArgDescriptions() {
        return new ArgDescription[] {new ArgDescription(NestString.class,this.keySet().stream().collect(Collectors.joining("/")),false,null,true)};
    }

    @Override
    default Class<? extends NestObject> getReturnType() {
        return NestObject.class;
    }

    @Override
    default Class<NestCommandSender> getAllowedSender() {
        return NestCommandSender.class;
    }

    @Override
    default String getPermission() {
        return "nest.hash.access";
    }

    @Override
    default String[] getDescription() {
        return new String[]{"access hash"};
    }

    @Override
    default NestObject<?> execute(Context<? extends NestCommandSender> context, NestObject<?>... args) throws CommandException {
        if (args.length < 2) {
            throw new InsufficientArgumentsException();
        }
        return this.get(args[1].to(NestString.class));
    }
    
}

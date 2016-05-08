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
package jp.llv.nest;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import jp.llv.nest.command.exceptions.CommandException;
import jp.llv.nest.command.exceptions.UndefinedVariableException;
import jp.llv.nest.command.exceptions.UnmodifiableVariableException;
import jp.llv.nest.command.obj.NestCommandSender;
import jp.llv.nest.command.obj.NestList;
import jp.llv.nest.command.obj.NestObject;
import jp.llv.nest.command.obj.bind.Binding;
import jp.llv.nest.command.token.Token;

/**
 *
 * @author toyblocks
 */
public interface NestAPI {

    void put(String name, NestObject<?> obj) throws UnmodifiableVariableException;

    NestObject<?> get(String name) throws UndefinedVariableException;

    void del(String name) throws UnmodifiableVariableException;

    void registerFunc(Object obj);

    Token parse(String command) throws CommandException;

    CompletableFuture<? extends NestObject<?>> execute(NestCommandSender sender, Token command, Binding binding) throws CommandException;

    CompletableFuture<? extends NestObject<?>> execute(NestCommandSender sender, NestList<?> command, Binding binding) throws CommandException;

    Binding getGlobalBinding();

    default CompletableFuture<? extends NestObject<?>> execute(NestCommandSender sender, Token command) throws CommandException {
        return this.execute(sender, command, this.getGlobalBinding());
    }

    default CompletableFuture<? extends NestObject<?>> execute(NestCommandSender sender, String command, Binding binding) throws CommandException {
        return this.execute(sender, this.parse(command), binding);
    }

    default CompletableFuture<? extends NestObject<?>> execute(NestCommandSender sender, String command) throws CommandException {
        return this.execute(sender, this.parse(command));
    }

    default CompletableFuture<? extends NestObject<?>> execute(NestCommandSender sender, NestList<?> command) throws CommandException {
        return this.execute(sender, command, this.getGlobalBinding());
    }

    String getVersion();

    Logger getLogger();

}

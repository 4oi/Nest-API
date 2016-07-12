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

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import jp.llv.nest.command.exceptions.CommandException;
import jp.llv.nest.command.exceptions.UndefinedVariableException;
import jp.llv.nest.command.exceptions.UnmodifiableVariableException;
import jp.llv.nest.command.obj.NestCommandSender;
import jp.llv.nest.command.obj.NestList;
import jp.llv.nest.command.obj.NestObject;
import jp.llv.nest.command.obj.NestPermitter;
import jp.llv.nest.command.obj.bind.Binding;
import jp.llv.nest.command.obj.bind.GlobalBinding;
import jp.llv.nest.command.token.Token;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 *
 * @author toyblocks
 */
public interface NestAPI {

    void put(@NotNull String name, NestObject<?> obj) throws UnmodifiableVariableException;

    NestObject<?> get(@NotNull String name) throws UndefinedVariableException;

    void del(@NotNull String name) throws UnmodifiableVariableException;

    void registerFunc(@NotNull Object obj);

    @NotNull
    Token parse(@NotNull String command) throws CommandException;

    CompletableFuture<? extends NestObject<?>> execute(@NotNull NestPermitter<?> authority, @NotNull NestCommandSender<?> sender, @NotNull Token command, @NotNull Binding<?> binding) throws CommandException;

    CompletableFuture<? extends NestObject<?>> execute(@NotNull NestPermitter<?> authority, @NotNull NestCommandSender<?> sender, @NotNull NestList command, @NotNull Binding<?> binding) throws CommandException;

    @NotNull
    GlobalBinding getGlobalBinding();

    default CompletableFuture<? extends NestObject<?>> execute(@NotNull NestPermitter<?> authority, @NotNull NestCommandSender sender, @NotNull Token command) throws CommandException {
        return this.execute(authority, sender, command, this.getGlobalBinding());
    }

    default CompletableFuture<? extends NestObject<?>> execute(@NotNull NestPermitter<?> authority, @NotNull NestCommandSender sender, @NotNull String command) throws CommandException {
        return this.execute(authority, sender, this.parse(command));
    }

    default NestObject<?> executeNow(@NotNull NestPermitter<?> authority, @NotNull NestCommandSender sender, @NotNull String command) throws CommandException {
        return getResultNow(this.execute(authority, sender, this.parse(command)));
    }

    @NotNull
    String getVersion();

    @NotNull
    Logger getLogger();
    
    @NotNull
    Path getDataFolder();

    public static NestObject<?> getResultNow(CompletableFuture<? extends NestObject<?>> future) throws CommandException {
        try {
            return future.join();
        } catch (Exception ex) {
            throw CommandException.toCommandException(ex);
        }
    }

}

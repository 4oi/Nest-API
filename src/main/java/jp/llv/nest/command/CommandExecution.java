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
package jp.llv.nest.command;

import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import jp.llv.nest.command.exceptions.CommandException;
import jp.llv.nest.command.exceptions.InternalException;
import jp.llv.nest.command.obj.NestObject;
import jp.llv.nest.command.obj.bind.Binding;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author toyblocks
 */
public final class CommandExecution {

    private final CompletableFuture<? extends NestObject<?>> result;
    private final Binding binding;

    public CommandExecution(CompletableFuture<? extends NestObject<?>> result, @NotNull Binding binding) {
        this.result = result == null? CompletableFuture.completedFuture(null) : result;
        Objects.requireNonNull(binding);
        this.binding = binding;
    }

    public @NotNull CompletableFuture<? extends NestObject<?>> getResult() {
        return result;
    }

    public @NotNull Binding getBinding() {
        return binding;
    }

    public NestObject<?> getResultNow() throws CommandException {
        try {
            return this.getResult().join();
        } catch (CancellationException ex) {
            throw new InternalException(ex);
        } catch (CompletionException ex) {
            if (ex.getCause() instanceof CommandException) {
                throw (CommandException) ex.getCause();
            } else if (ex.getCause().getCause() instanceof CommandException) {
                throw (CommandException) ex.getCause().getCause();
            } else {
                throw new InternalException(ex);
            }
        }
    }

}

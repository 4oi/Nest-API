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

import java.util.Objects;
import jp.llv.nest.command.Context;
import jp.llv.nest.command.exceptions.CommandException;
import jp.llv.nest.command.exceptions.InternalException;
import jp.llv.nest.command.token.Token;

/**
 *
 * @author toyblocks
 */
public class NestFunction<S extends NestCommandSender<?>> extends ContextExecutable<S, Void> implements NestExecutable<S, Void> {

    private final Token command;

    public NestFunction(Context context, Token command) {
        super(context);
        Objects.requireNonNull(command);
        this.command = command;
    }
    
    @Override
    public NestObject<?> executeIn(Context<? extends S> context, NestObject<?>... args) throws CommandException {
        try {
            return this.command.execute(context).getResultNow();
        } catch (CommandException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalException(ex);
        }
    }

    @Override
    public String[] getDescription() {
        return new String[]{"execute given tokens"};
    }

    @Override
    public ArgDescription[] getArgDescriptions() {
        return ArgDescription.UNDEFINED;
    }
    
}

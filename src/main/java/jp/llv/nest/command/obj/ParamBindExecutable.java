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
import jp.llv.nest.command.exceptions.InsufficientArgumentsException;
import jp.llv.nest.command.exceptions.InternalException;
import jp.llv.nest.command.exceptions.TypeMismatchException;

/**
 *
 * @author toyblocks
 */
public class ParamBindExecutable<S extends NestCommandSender<?>, E> extends NestObjectAdapter<E> implements NestExecutable<S, E> {

    private final String[] args;
    private final NestExecutable<S, E> executable;

    public ParamBindExecutable(NestExecutable<S, E> executable, String... args) {
        Objects.requireNonNull(args);
        Objects.requireNonNull(executable);
        this.args = args;
        this.executable = executable;
    }

    @Override
    public final NestObject<?> execute(Context<? extends S> context, NestObject<?>... args) throws CommandException {
        try {
            Context c = this.executable instanceof ContextExecutable
                    ? ((ContextExecutable) this.executable).getContext().newInner()
                    : context.newInner();

            if (this.args.length > args.length) {
                throw new InsufficientArgumentsException();
            } else if (this.args.length == 0) {
                //do not bind anything
            } else if (this.args.length == args.length) {
                for (int i = 0; i < this.args.length; i++) {
                    c.getBinding().set(this.args[i], args[i]);
                }
            } else {
                for (int i = 0; i < this.args.length - 1; i++) {
                    c.getBinding().set(this.args[i], args[i]);
                }
                NestObject<?>[] varargs = new NestObject<?>[args.length - this.args.length + 1];
                for (int i = 0; i < varargs.length; i++) {
                    varargs[i] = args[this.args.length + i - 1];
                }
                c.getBinding().set(this.args[this.args.length - 1], new NestList(varargs));
            }

            if (this.executable instanceof ContextExecutable) {
                return ((ContextExecutable) this.executable).executeIn(c, args);
            } else {
                return this.executable.execute(c, args);
            }
        } catch (CommandException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalException(ex);
        }
    }

    @Override
    public String[] getDescription() {
        return this.executable.getDescription();
    }

    @Override
    public String getPermission() {
        return this.executable.getPermission();
    }

    @Override
    public Class<S> getAllowedSender() {
        return this.executable.getAllowedSender();
    }

    @Override
    public Class<? extends NestObject> getReturnType() {
        return this.executable.getReturnType();
    }

    @Override
    public ArgDescription[] getArgDescriptions() {
        return this.executable.getArgDescriptions();
    }

    @Override
    public <T> T to(Class<T> toClass) throws TypeMismatchException {
        if (toClass.isAssignableFrom(this.getClass())) {
            return (T) this;
        }
        return this.executable.to(toClass);
    }

    @Override
    public E unwrap() throws UnsupportedOperationException {
        return this.executable.unwrap();
    }

}

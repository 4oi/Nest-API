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

import jp.llv.nest.command.Context;
import jp.llv.nest.command.Type;
import jp.llv.nest.command.exceptions.CommandException;

/**
 *
 * @author toyblocks
 */
@Type("Type")
public class NestType<T extends NestObject> extends NestValueAdapter<Class<T>> implements NestExecutable<NestCommandSender, Class<T>> {

    private static final ArgDescription ARGDESC = new ArgDescription(NestObject.class, "toCast", false, null, false);

    public NestType(Class<T> value) {
        super(value);
    }

    @Override
    public NestObject<?> execute(Context<? extends NestCommandSender> context, NestObject<?>... args) throws CommandException {
        if (args.length == 1) {
            return args[0].to(super.value);
        } else {
            NestObject[] res = new NestObject[args.length];
            for (int i = 0; i < args.length; i++) {
                res[i] = args[i].to(super.value);
            }
            return new NestList(res);
        }
    }

    @Override
    public String[] getDescription() {
        return new String[]{"return <toCast> as " + this.getName()};
    }

    @Override
    public ArgDescription[] getArgDescriptions() {
        return new ArgDescription[]{ARGDESC};
    }

    public String getName() {
        if (super.value.isAnnotationPresent(Type.class)) {
            return super.value.getAnnotation(Type.class).value();
        } else {
            return super.value.getName();
        }
    }

}

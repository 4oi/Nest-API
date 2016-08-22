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
import jp.llv.nest.command.Type;
import jp.llv.nest.command.exceptions.TypeMismatchException;
import jp.llv.nest.command.exceptions.UndefinedVariableException;
import jp.llv.nest.command.obj.bind.Binding;

/**
 *
 * @author toyblocks
 */
@Type("Reference")
public class Reference implements NestObject<Void> {

    private final Binding<?> binding;
    private final String key;

    public Reference(Binding<?> binding, String key) {
        this.binding = binding;
        this.key = key;
    }

    @Override
    public <T> T to(Class<T> toClass) throws TypeMismatchException {
        if (toClass == Reference.class) {
            return (T) this;
        } else {
            try {
                return binding.get(key).to(toClass);
            } catch (UndefinedVariableException ex) {
                throw new TypeMismatchException(ex.getMessage(), ex);
            }
        }
    }

    @Override
    public String toString() {
        try {
            return NestObject.to(binding.get(key), NestString.class).unwrap();
        } catch (UndefinedVariableException | TypeMismatchException ex) {
            return "ref\"" + this.key + "\"@" + this.binding.toString();
        }
    }

}

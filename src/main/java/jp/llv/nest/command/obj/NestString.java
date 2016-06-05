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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import jp.llv.nest.command.exceptions.TypeMismatchException;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/**
 *
 * @author toyblocks
 */
public final class NestString extends NestValueAdapter<String> implements NestJson.Jsonable {
    
    public NestString(@NotNull String value) {
        super(value);
    }
    
    private NestString(JSONObject json) {
        super(json.getString("str"));
    }

    @Override
    public <T> T to(Class<T> toClass) throws TypeMismatchException {
        if (toClass.isAssignableFrom(this.getClass())) {
            return (T) this;
        } else if (toClass == NestBool.class) {
            return (T) NestBool.of(!value.equals("nil") && !value.equals("false"));
        } else {
            try {
                Constructor<?> c = toClass.getConstructor(String.class);
                return (T) c.newInstance(this.value);
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            }
        }
        if (NestList.class.isAssignableFrom(toClass)) {
            return (T) new NestList(this);
        }
        throw new TypeMismatchException(this, toClass);
    }

    @Override
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        obj.put("str", super.value);
        return obj;
    }
    
}

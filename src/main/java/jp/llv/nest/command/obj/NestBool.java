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

import java.io.Serializable;
import java.util.Objects;
import jp.llv.nest.command.Type;
import jp.llv.nest.command.exceptions.TypeMismatchException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

/**
 *
 * @author toyblocks
 */
@Type("Bool")
public final class NestBool extends NestObjectAdapter<Boolean> implements Serializable, NestJson.Jsonable {
    
    public static final @NotNull NestBool TRUE = new NestBool();
    public static final @Nullable NestBool FALSE = null;

    private NestBool() {
    }

    @Override
    @Deprecated
    public @NotNull Boolean unwrap() {
        return true;
    }

    @Override
    @Deprecated
    public @NotNull String toString() {
        return "true";
    }

    public static <T extends NestObject> T to(NestBool b, Class<T> toClass) throws TypeMismatchException {
        Objects.requireNonNull(toClass);
        if (toClass == NestBool.class) {
            return (T) b;
        }else if (toClass == NestString.class) {
            return (T) new NestString(NestBool.toString(b));
        } else if (toClass == NestList.class) {
            return (T) new NestList(b);
        }
        throw new TypeMismatchException(b, toClass);
    }
    
    public static boolean unwrap(NestBool b) {
        return b != null;
    }
    
    public static String toString(NestBool b) {
        return b == null ? "false" : b.toString();
    }
    
    public static @Nullable NestBool of(String name) throws TypeMismatchException {
        if ("true".equalsIgnoreCase(name)) {
            return TRUE;
        } else if ("false".equalsIgnoreCase(name)) {
            return FALSE;
        } else {
            throw new TypeMismatchException("Invalid bool value");
        }
    }
    
    public static @Nullable NestBool of(boolean val) {
        return val ? NestBool.TRUE : NestBool.FALSE;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("bool", true);
        return json;
    }
    
}

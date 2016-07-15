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

import jp.llv.nest.command.Type;
import jp.llv.nest.command.exceptions.TypeMismatchException;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

/**
 *
 * @author toyblocks
 */
@Type("Int")
public final class NestInt extends NestNumber<Long> implements Comparable<NestInt>, NestJson.Jsonable {

    private final long value;

    public NestInt(long value) {
        this.value = value;
    }
    
    public NestInt(@NotNull String value) throws TypeMismatchException {
        try {
            this.value = Long.parseLong(value);
        } catch(NumberFormatException ex) {
            throw new TypeMismatchException("Invalid number format", ex);
        }
    }
    
    private NestInt(JSONObject json) {
        this(json.getLong("num"));
    }

    @Override
    public <T> T to(Class<T> toClass) throws TypeMismatchException {
        return super.to(toClass, ifClass(NestDecimal.class, () -> new NestDecimal(value)));
    }
    
    @Override
    public @NotNull Long unwrap() throws UnsupportedOperationException {
        return this.value;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NestInt other = (NestInt) obj;
        return this.value == other.value;
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }

    @Override
    public int compareTo(NestInt o) {
        return Long.compare(this.value, o.value);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("num", this.value);
        return json;
    }
    
}

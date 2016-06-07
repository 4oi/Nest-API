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
@Type("Decimal")
public final class NestDecimal extends NestObjectAdapter<Double> implements Comparable<NestDecimal>, NestJson.Jsonable {
    
    private final double value;

    public NestDecimal(double value) {
        this.value = value;
    }
    
    public NestDecimal(@NotNull String value) throws TypeMismatchException {
        try {
            this.value = Double.parseDouble(value);
        } catch(NumberFormatException ex) {
            throw new TypeMismatchException("Invalid number format", ex);
        }
    }

    private NestDecimal(JSONObject json) {
        this(json.getDouble("num"));
    }
    
    @Override
    public <T> T to(Class<T> toClass) throws TypeMismatchException {
        return super.to(toClass, ifClass(NestInt.class, () -> new NestInt((long) value)));
    }

    @Override
    public @NotNull Double unwrap() throws UnsupportedOperationException {
        return value;
    }
    
    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
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
        final NestDecimal other = (NestDecimal) obj;
        return Double.doubleToLongBits(this.value) == Double.doubleToLongBits(other.value);
    }

    @Override
    public int compareTo(NestDecimal o) {
        return Double.compare(this.value, o.value);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("num", this.value);
        return json;
    }
    
}

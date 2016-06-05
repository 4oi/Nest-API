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
import java.util.Arrays;
import java.util.stream.Collectors;
import jp.llv.nest.command.exceptions.TypeMismatchException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author toyblocks
 */
public abstract class NestJson<T> extends NestValueAdapter<T> {

    public static final String TYPE_NAME_KEY = "type";
    public static final String SUPERCLASSES_NAME_KEY = "parents";
    public static final String INTERFACES_NAME_KEY = "ifs";
    public static final String VALUE_KEY = "val";

    private NestJson(T value) {
        super(value);
    }

    @Override
    public <T> T to(Class<T> toClass) throws TypeMismatchException {
        if (NestJson.class.isAssignableFrom(toClass)) {
            return (T) this;
        } else {
            try {
                return this.toObject().to(toClass);
            } catch(TypeMismatchException ex) {
                throw ex;
            } catch(ReflectiveOperationException | RuntimeException ex) {
                throw new TypeMismatchException("Failed to restore an object", ex);
            }
        }
    }

    public abstract NestObject<?> toObject() throws ReflectiveOperationException;

    public abstract boolean isReadable();

    public abstract boolean isRestorable();

    @Override
    public String toString() {
        return super.value.toString();
    }

    public static JSONObject toJson(NestObject<?> obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof NestList || obj instanceof NestJsonArray) {
            throw new UnsupportedOperationException("Use an overloaded method");
        } else if (obj instanceof NestObject) {
            return ((NestJsonObject) obj).unwrap();
        }
        JSONObject root = new JSONObject();

        Class<?> type = obj.getClass();
        root.put(TYPE_NAME_KEY, type.getName());

        JSONArray parents = new JSONArray();
        while (NestObject.class.isAssignableFrom(type = type.getSuperclass())) {
            parents.put(type.getName());
        }
        root.put(SUPERCLASSES_NAME_KEY, parents);

        root.put(INTERFACES_NAME_KEY, Arrays.stream(obj.getClass().getInterfaces())
                .filter(c -> c != NestObject.class)
                .filter(c -> c != Jsonable.class)
                .collect(Collectors.toList()));

        if (obj instanceof Jsonable) {
            root.put(VALUE_KEY, ((Jsonable) obj).toJson());
        }

        return root;
    }

    public static JSONArray toJson(NestList obj) {
        return obj == null ? null : obj.toJson();
    }

    public static NestJson toNestJson(NestObject<?> obj) {
        if (obj instanceof NestList) {
            return new NestJsonArray(toJson((NestList) obj));
        } else {
            return new NestJsonObject(toJson(obj));
        }
    }

    public static NestJsonArray toNestJson(NestObject<?>... objs) {
        JSONArray array = new JSONArray();
        for (NestObject<?> obj : objs) {
            if (obj == null) {
                array.put(JSONObject.NULL);
            } else {
                array.put(toJson(obj));
            }
        }
        return new NestJsonArray(array);
    }

    private static NestObject<?> toNestObject(JSONObject obj) throws ReflectiveOperationException {
        Class<?> type = Class.forName(obj.getString(TYPE_NAME_KEY));
        if (type == NestBool.class) {
            return NestBool.TRUE;
        }
        Constructor<?> constructor = type.getDeclaredConstructor(JSONObject.class);
        constructor.setAccessible(true);
        return (NestObject<?>) constructor.newInstance(obj.get(VALUE_KEY));
    }

    public static class NestJsonObject extends NestJson<JSONObject> {

        private NestJsonObject(JSONObject value) {
            super(value);
        }

        private NestJsonObject() {
            this(new JSONObject());
        }

        private NestJsonObject(String json) {
            this(new JSONObject(json));
        }

        @Override
        public NestObject<?> toObject() throws ReflectiveOperationException {
            return toNestObject(super.value);
        }

        public Class<?> getType() throws ClassNotFoundException {
            return Class.forName(super.value.getString(TYPE_NAME_KEY));
        }

        public JSONObject getContent() {
            return super.value.getJSONObject(VALUE_KEY);
        }

        @Override
        public boolean isReadable() {
            return super.value.has(VALUE_KEY);
        }

        @Override
        public boolean isRestorable() {
            if (!this.isReadable()) {
                return false;
            }
            try {
                this.getType().getConstructor(JSONObject.class);
                return true;
            } catch (ReflectiveOperationException ex) {
                return false;
            }
        }

    }

    public static class NestJsonArray extends NestJson<JSONArray> {

        private NestJsonArray(JSONArray value) {
            super(value);
        }

        private NestJsonArray() {
            this(new JSONArray());
        }

        private NestJsonArray(String json) {
            this(new JSONArray(json));
        }

        @Override
        public NestList toObject() throws ReflectiveOperationException {
            NestList list = new NestList();
            for (int i = 0; i < super.value.length(); i++) {
                if (super.value.isNull(i)) {
                    list.add(null);
                } else {
                    list.add(toNestObject(super.value.optJSONObject(i)));
                }
            }
            return list;
        }

        @Override
        public boolean isReadable() {
            return true;
        }

        @Override
        public boolean isRestorable() {
            return true;
        }

    }

    public static interface Jsonable {

        JSONObject toJson();

    }

}

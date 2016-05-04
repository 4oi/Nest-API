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

import jp.llv.nest.command.CommandExecutor;
import jp.llv.nest.command.exceptions.CommandException;
import jp.llv.nest.command.obj.bind.Binding;

/**
 *
 * @author toyblocks
 */
public interface NestExecutable<E> extends NestObject<E> {

    NestObject<?> execute(CommandExecutor executor, NestCommandSender sender, Binding binding, NestObject<?>... args) throws CommandException;

    String[] getDescription();

    default String getPermission() {
        return null;
    }

    default Class<? extends NestCommandSender> getAllowedSender() {
        return NestCommandSender.class;
    }

    default Class<? extends NestObject> getReturnType() {
        return NestObject.class;
    }

    ArgDescription[] getArgDescriptions();

    static class ArgDescription {

        public static ArgDescription[] UNDEFINED = {new ArgDescription(NestObject.class, "args", true, null, true)};
        
        private final Class<? extends NestObject> type;
        private final String name;
        private final boolean varArgs;
        private final String group;
        private final boolean required;

        public ArgDescription(Class<? extends NestObject> type, String name, boolean varArgs, String group, boolean required) {
            this.type = type;
            this.name = name;
            this.varArgs = varArgs;
            this.group = group;
            this.required = required;
        }

        public Class<? extends NestObject> getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public boolean isVarArgs() {
            return this.varArgs;
        }

        public String getGroup() {
            return group;
        }

        public boolean isRequired() {
            return required;
        }

        @Override
        public String toString() {
            return this.getName();
        }
    }

}

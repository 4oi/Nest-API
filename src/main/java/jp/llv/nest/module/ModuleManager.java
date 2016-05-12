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
package jp.llv.nest.module;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author toyblocks
 */
public interface ModuleManager {

    void load(@NotNull File ... file) throws IOException, InvalidModuleException, DependencyException;
    
    void load(@NotNull Collection<Class<?>> clazz) throws InvalidModuleException, DependencyException;

    @NotNull Optional<?> getModule(@NotNull String name);

    boolean isModule(@NotNull Class<?> clazz);

    default boolean isModule(@NotNull Object obj) {
        return this.isModule(obj.getClass());
    }

    @NotNull String getName(@NotNull Class<?> clazz) throws InvalidModuleException;

    default @NotNull String getName(@NotNull Object obj) throws InvalidModuleException {
        return this.getName(obj.getClass());
    }

    int getVersion(@NotNull Class<?> clazz) throws InvalidModuleException;

    default int getVersion(@NotNull Object obj) throws InvalidModuleException {
        return this.getVersion(obj.getClass());
    }

    @NotNull String getAuthor(@NotNull Class<?> clazz) throws InvalidModuleException;

    default @NotNull String getAuthor(@NotNull Object obj) throws InvalidModuleException {
        return this.getAuthor(obj.getClass());
    }

    <T> @NotNull Optional<T> getDependable(@NotNull Class<T> clazz, int min, int max);

    default <T> @NotNull Optional<T> getDependable(@NotNull Class<T> clazz) {
        return this.getDependable(clazz, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    ;
    
    void setDependable(@NotNull Object obj, int version);

    default void setDependable(@NotNull Object obj) {
        this.setDependable(obj, 0);
    }

}

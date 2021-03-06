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
package jp.llv.nest.i18n;

/**
 *
 * @author toyblocks
 */
public class I18n {
    
    private static final InjectableMessage NO_MANAGER_FOUND = new InjectableMessage("[MESSAGE ERROR: MANAGER IS NOT INITIALIZED]");
    
    private static InternationalizationManager manager;

    private I18n() {
        throw new UnsupportedOperationException("This is static utilities class");
    }
    
    public static InternationalizationKey key(String domain, String ... key) {
        return new InternationalizationKey(domain, key);
    }
    
    public static InjectableMessage get(String domain, String ... key) {
        if (manager == null) {
            return NO_MANAGER_FOUND;
        }
        return manager.getMessage(key(domain, key));
    }
    
    public static InjectableMessage get(InternationalizationKey key) {
        if (manager == null) {
            return NO_MANAGER_FOUND;
        }
        return manager.getMessage(key);
    }
    
    public static void setProvider(String domain, MessageProvider provider) {
        manager.setProvider(domain, provider);
    }
    
    /*package*/ static void setManager(InternationalizationManager m) {
        if (manager != null) {
            throw new IllegalStateException("Manager is already set");
        }
        manager = m;
    }
    
    public static InternationalizationManager getManager() {
        if (manager == null) {
            throw new IllegalStateException("Manager is not set");
        }
        return manager;
    }
    
}

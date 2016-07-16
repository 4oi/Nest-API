/*
 * Copyright (C) 2016 toyblocks
 */
package jp.llv.nest.command.obj.bind;

import jp.llv.nest.command.exceptions.UndefinedVariableException;
import jp.llv.nest.command.exceptions.UnmodifiableVariableException;
import jp.llv.nest.command.obj.NestObject;
import jp.llv.nest.command.obj.NestObjectAdapter;
import jp.llv.nest.command.obj.NestType;

/**
 *
 * @author toyblocks
 */
public abstract class TypeBinding extends NestObjectAdapter<Void> implements Binding<Void> {

    /*package*/ static TypeBinding INSTANCE;
    
    /*package*/ TypeBinding() {
    }

    @Override
    public abstract NestType<?> get(String key) throws UndefinedVariableException;

    @Override
    @Deprecated
    public abstract void del(String key) throws UnmodifiableVariableException;

    @Override
    @Deprecated
    public abstract void forceDefineHere(String key, NestObject<?> value) throws UnmodifiableVariableException;

    public abstract void findTypes(Class<?> source);
    
    public abstract void registerClass(Class<?> ... clazz);
    
    public abstract void registerClass(Class<?> clazz);

    public static TypeBinding getInstance() {
        return INSTANCE;
    }

}

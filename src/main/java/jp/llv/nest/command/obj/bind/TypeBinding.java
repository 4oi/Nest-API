/*
 * Copyright (C) 2016 toyblocks
 */
package jp.llv.nest.command.obj.bind;

import jp.llv.nest.command.exceptions.UndefinedVariableException;
import jp.llv.nest.command.exceptions.UnmodifiableVariableException;
import jp.llv.nest.command.obj.NestBool;
import jp.llv.nest.command.obj.NestCommandSender;
import jp.llv.nest.command.obj.NestDecimal;
import jp.llv.nest.command.obj.NestHash;
import jp.llv.nest.command.obj.NestInt;
import jp.llv.nest.command.obj.NestList;
import jp.llv.nest.command.obj.NestObject;
import jp.llv.nest.command.obj.NestObjectAdapter;
import jp.llv.nest.command.obj.NestPermitter;
import jp.llv.nest.command.obj.NestString;
import jp.llv.nest.command.obj.NestType;
import jp.llv.nest.command.obj.PairedValue;

/**
 *
 * @author toyblocks
 */
public abstract class TypeBinding extends NestObjectAdapter<Void> implements Binding<Void> {

    /*package*/ static TypeBinding INSTANCE;
    
    /*package*/ TypeBinding() {
        this.registerClass(NestBool.class, 
                NestCommandSender.class, 
                NestDecimal.class, 
                NestHash.class,
                NestInt.class,
                NestList.class,
                NestObject.class,
                NestPermitter.class,
                NestString.class,
                NestType.class,
                PairedValue.class);
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

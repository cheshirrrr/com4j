package com4j;

import java.lang.reflect.Proxy;
import java.io.File;

/**
 * @author Kohsuke Kawaguchi (kk@kohsuke.org)
 */
public class COM4J {

    public static<T extends Com4jObject>
    T createInstance( Class<T> primaryInterface, GUID guid ) {
        return createInstance(primaryInterface,guid.toString());
    }

    public static<T extends Com4jObject>
    T createInstance( Class<T> primaryInterface, String clsid ) {

        GUID iid = getIID(primaryInterface);

        // create instance
        return wrap( primaryInterface, Native.createInstance(clsid,iid.l1,iid.l2) );
    }

    /**
     * Gets the interface VTID associated with the given interface.
     */
    public static GUID getIID( Class<? extends Com4jObject> _interface ) {
        IID iid = _interface.getAnnotation(IID.class);
        return new GUID(iid.value());
    }

    public static Com4jObject loadTypeLibrary( File typeLibraryFile ) {
        return wrap(Com4jObject.class, Native.loadTypeLibrary(typeLibraryFile.getAbsolutePath()));
    }


    static <T>
    T wrap( Class<T> primaryInterface, int ptr ) {
        return primaryInterface.cast(Proxy.newProxyInstance(
            primaryInterface.getClassLoader(),
            new Class<?>[]{primaryInterface},
            new Wrapper(ptr)));
    }

    static Wrapper unwrap( Com4jObject obj ) {
        return (Wrapper)Proxy.getInvocationHandler(obj);
    }

    static {
        System.out.println("loading the native code");
        System.loadLibrary("com4j");
    }
}

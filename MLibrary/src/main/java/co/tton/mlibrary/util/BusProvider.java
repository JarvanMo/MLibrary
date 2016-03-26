package co.tton.mlibrary.util;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by mo on 15-11-5.
 *
 */
public class BusProvider {


    private static Bus bus;

    private BusProvider(){

    }

    public static Bus singleton(){

        if(bus == null){
            bus = new Bus(ThreadEnforcer.ANY);
        }
        return bus;
    }

}

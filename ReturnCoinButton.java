package com.wayfarer.VendingKata;

import java.util.Enumeration;
import java.util.Vector;

public class ReturnCoinButton {

protected Vector<ReturnCoinEvent> _listeners;
    
    public void addReturnCoinEventListener(ReturnCoinEvent listener)
    {
        if (_listeners == null)
            _listeners = new Vector<ReturnCoinEvent>();
             
        _listeners.addElement(listener);
    }

    protected void returnCoins()
    {
        if (_listeners != null && _listeners.isEmpty())
        {
            Enumeration<ReturnCoinEvent> e = _listeners.elements();
            while (e.hasMoreElements())
            {
            	ReturnCoinEvent i = e.nextElement();
                i.ReturnCoins();
            }
        }
    }
    
}

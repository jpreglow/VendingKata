package com.wayfarer.VendingKata;

import java.util.Enumeration;
import java.util.Vector;

public class CoinSlot {

	protected Vector<InsertCoinEvent> _listeners;
    
    public void addInsertCoinEventListener(InsertCoinEvent listener)
    {
        if (_listeners == null)
            _listeners = new Vector<InsertCoinEvent>();
             
        _listeners.addElement(listener);
    }

    protected void coinInserted()
    {
        if (_listeners != null && _listeners.isEmpty())
        {
            Enumeration<InsertCoinEvent> e = _listeners.elements();
            while (e.hasMoreElements())
            {
                InsertCoinEvent i = e.nextElement();
                i.CoinInserted(new Coin(GetCoinType()));
            }
        }
    }
    
    protected CoinEnum GetCoinType()
    {
    	return CoinEnum.Unknown;
    }
}

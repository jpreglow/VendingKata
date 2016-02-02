package com.wayfarer.VendingKata;

import java.util.Enumeration;
import java.util.Vector;

public class ProductButton {

	protected Product myProduct;
	
	public ProductButton(Product p) {
		myProduct = p;
	}
	
protected Vector<ProductButtonEvent> _listeners;
    
    public void addProductButtonEventListener(ProductButtonEvent listener)
    {
        if (_listeners == null)
            _listeners = new Vector<ProductButtonEvent>();
             
        _listeners.addElement(listener);
    }

    protected void selectProduct()
    {
        if (_listeners != null && _listeners.isEmpty())
        {
            Enumeration<ProductButtonEvent> e = _listeners.elements();
            while (e.hasMoreElements())
            {
            	ProductButtonEvent i = e.nextElement();
                switch(myProduct.Type)
                {
                case Chips:
                	i.ChipsSelected((Chips)myProduct);
                	break;
                case Cola:
                	i.ColaSelected((Cola)myProduct);
                	break;
                case Candy:
                	i.CandySelected((Candy)myProduct);
                	break;
                }
            }
        }
    }

}

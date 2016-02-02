package com.wayfarer.VendingKata;

public class Cola implements Product{
	
	public double Cost;
	public ProductEnum Type;
	
	public Cola() {
		Type = ProductEnum.Cola;
		Cost = 1.00;
	}
}

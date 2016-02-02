package com.wayfarer.VendingKata;

public class Chips implements Product{
		public double Cost;
		public ProductEnum Type;
		
	public Chips() {
		Type = ProductEnum.Chips;
		Cost = 0.50;
	}
}

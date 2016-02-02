package com.wayfarer.VendingKata;

public class Candy implements Product{
		public double Cost;
		public ProductEnum Type;
		
	public Candy() {
		Type = ProductEnum.Candy;
		Cost = 0.65;
	}
}

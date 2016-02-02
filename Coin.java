package com.wayfarer.VendingKata;

public class Coin {

	public static final double QUARTER_WEIGHT = 5.67; //grams
	public static final double DIME_WEIGHT = 2.268; //grams
	public static final double NICKEL_WEIGHT = 5.0; //grams
	
	public static final double QUARTER_DIAM = 24.26; //millimeters
	public static final double DIME_DIAM = 17.71; //millimeters
	public static final double NICKEL_DIAM = 21.21; //millimeters
	
	public double Value = 0.0;
	public CoinEnum Type = CoinEnum.Unknown;
	
	public Coin() {}
	
	public Coin(CoinEnum c)
	{
		switch(c)
		{
		case Nickel:
			Value = 0.05;
			break;
		case Dime:
			Value = 0.1;
			break;
		case Quarter:
			Value = 0.25;
			break;
		case Unknown:
		default:
			Value = 0.0;
			break;
		}
		Type = c;
	}
	
}

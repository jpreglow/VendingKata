package com.wayfarer.VendingKata;

import java.util.ArrayList;


public class VendingMachine implements InsertCoinEvent,
										ReturnCoinEvent,
										ProductButtonEvent
{
	
	public static final String DEFAULT_MSG = "INSERT COINS";
	public static final String EXACT_MSG = "EXACT CHANGE ONLY";
	public static final String OUT_MSG = "OUT OF STOCK";
	
	public static final int DISPLAY_DURATION = 2000;

	public ArrayList<Coin> QuartersCollected = new ArrayList<Coin>();
	public ArrayList<Coin> DimesCollected = new ArrayList<Coin>();
	public ArrayList<Coin> NickelsCollected = new ArrayList<Coin>();
	
	public ArrayList<Candy> CandyInventory = new ArrayList<Candy>();
	public ArrayList<Cola> ColaInventory = new ArrayList<Cola>();
	public ArrayList<Chips> ChipsInventory = new ArrayList<Chips>();
	
	public String DisplayMessage;
	
	public double CurrentAmount = 0.0;
	public ArrayList<Coin> CurrentCoinsAccepted = new ArrayList<Coin>();
	public double CurrentChange = 0.0;
	
	public boolean ExactChange;
	
	public VendingMachine(){
		
		DisplayMessage = DEFAULT_MSG;
		
		InitInventories();
		
		InitMoney();
		
		CoinSlot coinSource = new CoinSlot();
        coinSource.addInsertCoinEventListener(this); // Adds itself as a listener for the event
        ReturnCoinButton returnSource = new ReturnCoinButton();
        returnSource.addReturnCoinEventListener(this);
        ProductButton colaSource = new ProductButton(new Cola());
        colaSource.addProductButtonEventListener(this);
        ProductButton chipSource = new ProductButton(new Chips());
        chipSource.addProductButtonEventListener(this);
        ProductButton candySource = new ProductButton(new Candy());
        candySource.addProductButtonEventListener(this);
        
	}
	
	public void CoinInserted(Coin c)
	{
		if(c.Type != CoinEnum.Unknown)
		{
			AcceptCoin(c);
		}
		else
		{
			RejectCoin(c);
		}
	}
	
	private void InitInventories()
	{
		//initialize product inventory...possibly from stored file, or weight of
		//products in each slot
	}
	
	private void InitMoney()
	{
		//initialize coin inventory...possibly from stored file, or weight of
		//coins in each receptacle
		CheckExactChange();
	}
	
	private void CheckExactChange()
	{
		int dimesPresent = DimesCollected.size();
		int nickelsPresent = NickelsCollected.size();
		
		if(dimesPresent >= 1)
		{
			ExactChange = false;
		}
		else if(nickelsPresent >= 2)
		{
			ExactChange = false;
		}
		else
		{
			ExactChange = true;
		}
		
		if(ExactChange)
			DisplayMessage = EXACT_MSG;
	}
	
	private void AcceptCoin(Coin c)
	{
		if(CurrentAmount < 1.0)
		{
			CurrentAmount += c.Value;
			DisplayMessage = String.valueOf(CurrentAmount);
			if(CurrentCoinsAccepted == null)
				CurrentCoinsAccepted = new ArrayList<Coin>();
			
			CurrentCoinsAccepted.add(c);
		}
		else
			RejectCoin(c);
	}
	
	private void RejectCoin(Coin c)
	{
		//eject this coin
	}

	public void ReturnCoins()
	{
		if(CurrentCoinsAccepted != null)
		{
			for(Coin c : CurrentCoinsAccepted)
			{
				RejectCoin(c);
			}
			
			CurrentCoinsAccepted = null;
			CurrentAmount = 0.0;
			
			CheckExactChange();
		}
	}

	public void ChipsSelected(Chips c) {
		
		if(ChipsInventory.size() == 0)
		{
			DisplayOutOfStock();
		}
		else
		{
			if(CurrentAmount < c.Cost)
			{
				DisplayCost(c);
			}
			else
			{
				if(ExactChange && CurrentAmount > c.Cost)
					ReturnCoins();
				else
					Dispense(c);
			}
		}
	}

	public void ColaSelected(Cola c) {
		if(ColaInventory.size() == 0)
		{
			DisplayOutOfStock();
		}
		else
		{
			if(CurrentAmount < c.Cost)
			{
				DisplayCost(c);
			}
			else
			{
				if(ExactChange && CurrentAmount > c.Cost)
					ReturnCoins();
				else
					Dispense(c);
			}
		}		
	}

	public void CandySelected(Candy c) {
		if(CandyInventory.size() == 0)
		{
			DisplayOutOfStock();
		}
		else
		{
			if(CurrentAmount < c.Cost)
			{
				DisplayCost(c);
			}
			else
			{
				if(ExactChange && CurrentAmount > c.Cost)
					ReturnCoins();
				else
					Dispense(c);
			}
		}		
	}
	
	public void DisplayCost(Product p)
	{
		String temp = DisplayMessage;
		DisplayMessage = String.valueOf(p.Cost);
		Pause();
		DisplayMessage = temp;
	}
	
	public void Dispense(Product p)
	{
		switch(p.Type)
		{
		case Chips:
			ChipsInventory.remove(0);
			break;
		case Cola:
			ColaInventory.remove(0);
			break;
		case Candy:
			CandyInventory.remove(0);
			break;
		}
		
		for(Coin c : CurrentCoinsAccepted)
		{
			switch(c.Type)
			{
			case Quarter:
				QuartersCollected.add(c);
				break;
			case Dime:
				DimesCollected.add(c);
				break;
			case Nickel:
				NickelsCollected.add(c);
				break;
			}
		}
		
		CurrentCoinsAccepted = null;
		
		if(!ExactChange)
			MakeChange(p);
		
		CheckExactChange();
	}
	
	public void MakeChange(Product p)
	{
		double changeAmount = CurrentAmount - p.Cost;
		
		while(changeAmount > 0.0)
		{
			if(changeAmount >= .05)
			{
				if(changeAmount >= .1)
				{
					if(changeAmount >= .25)
					{
						if(QuartersCollected.size() > 0)
						{
							QuartersCollected.remove(0);
							changeAmount -= .25;
							continue;
						}
						else if(DimesCollected.size() > 0)
						{
							DimesCollected.remove(0);
							changeAmount -= .10;
							continue;
						}
						else
						{
							NickelsCollected.remove(0);
							changeAmount -= .05;
							continue;
						}
					}
					
					if(DimesCollected.size() > 0)
					{
						DimesCollected.remove(0);
						changeAmount -= .10;
						continue;
					}
					else
					{
						NickelsCollected.remove(0);
						changeAmount -= .05;
						continue;
					}
				}
				
				NickelsCollected.remove(0);
				changeAmount -= .05;
				continue;
			}
		}
	}
	
	public void DisplayOutOfStock()
	{
		String temp = DisplayMessage;
		DisplayMessage = OUT_MSG;
		Pause();
		DisplayMessage = temp;
	}
	
	public void Pause()
	{
		try{
			Thread.sleep(DISPLAY_DURATION);
		}
		catch(Exception ex)
		{
			
		}
	}
}

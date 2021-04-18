package macnaught.cory;

import java.util.EventListener;
import java.util.EventObject;

@SuppressWarnings("serial")
public class LargeNumberSelectorComponentEvent extends EventObject
{
	private int value;
	
	public LargeNumberSelectorComponentEvent(Object source)
	{
		super(source);
		if (source instanceof Integer)
			this.value = (int)source;
	}
	
	public int getValue() {
		return this.value;
	}
}

 interface LargeNumberSelectorComponentEventListener extends EventListener
 {
	 public void largeNumberSelectorComponentEventOccurred(LargeNumberSelectorComponentEvent evt);
 }
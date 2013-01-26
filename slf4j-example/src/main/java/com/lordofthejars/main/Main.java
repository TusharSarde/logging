package com.lordofthejars.main;

import com.lordofthejars.bar.BarComponent;
import com.lordofthejars.foo.FooComponent;

public class Main {

	public static void main(String args[]) {
		
		BarComponent barComponent = new BarComponent();
		barComponent.bar();
		
		FooComponent fooComponent = new FooComponent();
		fooComponent.foo();
		
	}
	
}

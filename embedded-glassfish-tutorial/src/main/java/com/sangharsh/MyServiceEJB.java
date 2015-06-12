package com.sangharsh;
 
import java.util.Date;

import javax.ejb.Stateless;
 
@Stateless
public class MyServiceEJB {
	public String printDate() {
		return "Sang The date is: " + new Date().toString();
	}
}
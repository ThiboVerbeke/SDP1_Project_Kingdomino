package main;

import cui.SpelerApplicatie;
import domein.DomeinController;

public class StartUp {
	public static void main(String[] args) {
		SpelerApplicatie applicatie = new SpelerApplicatie(new DomeinController());
		applicatie.start();
	}
}

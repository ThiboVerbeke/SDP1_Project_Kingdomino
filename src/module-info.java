module KingDomino_G31
{
	exports persistentie;
	exports cui;
	exports utils;
	exports main to javafx.graphics;
	exports domein;
	exports testen;
	exports dto;
	exports exceptions;

	requires javafx.fxml;

	requires java.sql;
	requires jsch;
	requires org.junit.jupiter.api;
	requires org.junit.jupiter.params;
	requires javafx.graphics;
	requires junit;
	requires transitive javafx.controls;

	opens gui to javafx.fxml;

}
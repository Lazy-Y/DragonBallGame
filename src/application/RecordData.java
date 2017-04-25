package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class RecordData{
	public RecordData(String name, int score, int kill){
		this.name = new SimpleStringProperty(name);
		this.score = new SimpleIntegerProperty(score);
		this.kill = new SimpleIntegerProperty(kill);
	}
	StringProperty name;
	IntegerProperty score;
	IntegerProperty kill;
}
package BellSystem;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

public class BellRecord extends RecursiveTreeObject<BellRecord>  implements Comparable<BellRecord>{
    private final SimpleStringProperty index;
    private final SimpleStringProperty bellName;
    private final SimpleStringProperty bellTime;

    public BellRecord(String index, String bellName, String bellTime){
        this.index = new SimpleStringProperty(index);
        this.bellName = new SimpleStringProperty(bellName);
        this.bellTime = new SimpleStringProperty(bellTime);
    }

    public String getIndex() {
        return this.index.getValue();
    }

    public SimpleStringProperty indexProperty() {
        return index;
    }

    public void setIndex(String index) {
        this.index.set(index);
    }

    public String getBellName() {
        return bellName.get();
    }

    public SimpleStringProperty bellNameProperty() {
        return bellName;
    }

    public void setBellName(String bellName) {
        this.bellName.set(bellName);
    }

    public String getBellTime() {
        return bellTime.get();
    }

    public SimpleStringProperty bellTimeProperty() {
        return bellTime;
    }

    public void setBellTime(String bellTime) {
        this.bellTime.set(bellTime);
    }

    @Override
    public int compareTo(BellRecord b) {
        return Integer.parseInt(getIndex()) -  Integer.parseInt(b.getIndex());
    }
}

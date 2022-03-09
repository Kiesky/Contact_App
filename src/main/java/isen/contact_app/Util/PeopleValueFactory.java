package isen.contact_app.Util;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import isen.contact_app.Entities.People;

public class PeopleValueFactory implements Callback<TableColumn.CellDataFeatures<People, String>, ObservableValue<String>> {

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<People, String> cellData) {
        return new SimpleStringProperty(cellData.getValue().getFirstName());
    }
}

package com.javafxps.control.tableview;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class ButtonTableColumn extends TableColumn<Void, Void> {


    public ButtonTableColumn (String nome){
        super(nome);
        Callback<TableColumn<Void, Void>, TableCell<Void, Void>> cellFactory = new Callback<TableColumn<Void, Void>, TableCell<Void, Void>>() {
            @Override
            public TableCell<Void, Void> call(final TableColumn<Void, Void> param) {

                final TableCell<Void, Void> cell = new TableCell<Void, Void>() {

                    private final Button btn = new Button("Visualizar");

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        btn.setOnAction(
                                (ActionEvent event) -> {
                                    //Data data = getTableView().getItems().get(getIndex());
                                    System.out.println("selectedData: " + "data");
                                }
                        );
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        this.setCellFactory(cellFactory);
    }
}

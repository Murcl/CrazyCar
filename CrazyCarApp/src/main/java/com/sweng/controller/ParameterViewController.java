package com.sweng.controller;

import com.sweng.drive.CarImpl;
import com.sweng.interfaces.Car;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
public class ParameterViewController {
    @Autowired
    Car carImpl;
    @FXML
    TextField speed,steering,xacc,yacc,sf,sl,sr;

    private DecimalFormat df1 = new DecimalFormat("#.##");

    @FXML
    public void initialize() {

    }

    public void setValues() {
        speed.setText(df1.format(carImpl.getSpeed()));
        steering.setText(df1.format(carImpl.getSteering()));
        xacc.setText(df1.format(carImpl.getAccelX()));
        yacc.setText(df1.format(carImpl.getAccelY()));
        sf.setText(df1.format(carImpl.getDistFront()));
        sl.setText(df1.format(carImpl.getDistLeft()));
        sr.setText(df1.format(carImpl.getDistRight()));
    }
}

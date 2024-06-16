package sample;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class sample extends Frame implements ActionListener {
    
    TextField cityNameField, temperatureField, positionField;
    TextArea displayArea;
    Button addButton, deleteButton, updateButton, insertButton, displayButton;
    Label cityLabel, tempLabel, posLabel;

    ArrayList<String> cityList = new ArrayList<>();
    ArrayList<Double> tempList = new ArrayList<>();

    ArrayList<Double> prevDayTempList = new ArrayList<>();

    public sample() {
        setLayout(new BorderLayout());

        Panel inputPanel = new Panel(new GridLayout(3, 2, 10, 10));
        cityLabel = new Label("City Name:");
        cityNameField = new TextField(15);
        tempLabel = new Label("Temperature (C):");
        temperatureField = new TextField(5);
        posLabel = new Label("Position:");
        positionField = new TextField(3);

        inputPanel.add(cityLabel);
        inputPanel.add(cityNameField);
        inputPanel.add(tempLabel);
        inputPanel.add(temperatureField);
        inputPanel.add(posLabel);
        inputPanel.add(positionField);

        Panel buttonPanel = new Panel(new GridLayout(5, 1, 5, 5));
        addButton = new Button("Add City");
        deleteButton = new Button("Delete City");
        updateButton = new Button("Update City");
        insertButton = new Button("Insert City at Position");
        displayButton = new Button("Display Previous Day Record");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(insertButton);
        buttonPanel.add(displayButton);

        displayArea = new TextArea(10, 40);

        add(inputPanel, BorderLayout.NORTH);
        add(displayArea, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);

        Color mixedColor = new Color(0, 128, 128);
        inputPanel.setBackground(mixedColor);
        buttonPanel.setBackground(mixedColor);
        displayArea.setBackground(mixedColor);

        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        updateButton.addActionListener(this);
        insertButton.addActionListener(this);
        displayButton.addActionListener(this);

        setTitle("City Temperature Manager");
        setSize(600, 400);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose(); 
                System.exit(0); 
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        String cityName = cityNameField.getText();
        String tempText = temperatureField.getText();
        String positionText = positionField.getText();

        if (e.getSource() == addButton) {
            if (!cityName.isEmpty() && !tempText.isEmpty()) {
                if (cityList.contains(cityName)) {
                    showMessageDialog("Error", "City " + cityName + " already exists.");
                } else {
                    double tempCelsius = Double.parseDouble(tempText);
                    cityList.add(cityName);
                    tempList.add(tempCelsius);
                    prevDayTempList.add(tempCelsius);
                    displayArea.append("City added: " + cityName + " with temperature: " + tempCelsius + "°C\n");
                }
            } else {
                displayArea.append("Please enter both city name and temperature.\n");
            }
        } else if (e.getSource() == deleteButton) {
            if (cityList.contains(cityName)) {
                int index = cityList.indexOf(cityName);
                cityList.remove(index);
                tempList.remove(index);
                prevDayTempList.remove(index);
                displayArea.setText("");
                updateDisplayArea();
            } else {
                displayArea.append("City not found.\n");
            }
        } else if (e.getSource() == updateButton) {
            if (cityList.contains(cityName) && !tempText.isEmpty()) {
                int index = cityList.indexOf(cityName);
                double tempCelsius = Double.parseDouble(tempText);
                prevDayTempList.set(index, tempList.get(index));
                tempList.set(index, tempCelsius);
                displayArea.setText(""); 
                updateDisplayArea();
            } else {
                displayArea.append("City not found or temperature is empty.\n");
            }
        } else if (e.getSource() == insertButton) {
            if (!cityName.isEmpty() && !tempText.isEmpty() && !positionText.isEmpty()) {
                int position = Integer.parseInt(positionText);
                if (position >= 0 && position <= cityList.size()) {
                    double tempCelsius = Double.parseDouble(tempText);
                    cityList.add(position, cityName);
                    tempList.add(position, tempCelsius);
                    prevDayTempList.add(position, tempCelsius);
                    displayArea.setText(""); 
                    updateDisplayArea();
                } else {
                    displayArea.append("Invalid position.\n");
                }
            } else {
                displayArea.append("Please enter city name, temperature and position.\n");
            }
        } else if (e.getSource() == displayButton) {
            if (cityList.contains(cityName)) {
                int index = cityList.indexOf(cityName);
                double tempCelsius = prevDayTempList.get(index);
                double tempFahrenheit = tempCelsius * 9 / 5 + 32;
                displayArea.append("Previous day's temperature for " + cityName + ": " + tempCelsius + "°C (" + tempFahrenheit + "°F)\n");
            } else {
                displayArea.append("City not found.\n");
            }
        }
    }

    private void updateDisplayArea() {
        for (int i = 0; i < cityList.size(); i++) {
            displayArea.append("City: " + cityList.get(i) + ", Temperature: " + tempList.get(i) + "°C\n");
        }
    }

    private void showMessageDialog(String title, String message) {
        Dialog dialog = new Dialog(this, title, true);
        dialog.setLayout(new FlowLayout());
        Label messageLabel = new Label(message);
        Button okButton = new Button("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        dialog.add(messageLabel);
        dialog.add(okButton);
        dialog.setSize(300, 100);
        
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    
    public static void main(String[] args) {
        sample obj = new sample();
        obj.setSize(new Dimension(600, 400));
        obj.setTitle("Weather Forecasting");
        obj.setVisible(true);
    }
}

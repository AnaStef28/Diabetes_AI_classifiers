package com.example.aiproject;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import src.Service.Service;

import java.io.File;
import java.util.List;

public class HelloController {

    private String filePath;
    private Service service;
    private boolean knnTrained = false;
    private boolean naiveTrained = false;
    private boolean percTrained = false;
    @FXML private TabPane tabPane;
    @FXML Label label;
    @FXML private Label tpKNN;
    @FXML private Label fpKNN;
    @FXML private Label tnKNN;
    @FXML private Label fnKNN;
    @FXML private Label confirmKNN;
    @FXML private Label f1KNN;

    @FXML private Label tpNaive;
    @FXML private Label fpNaive;
    @FXML private Label tnNaive;
    @FXML private Label fnNaive;
    @FXML private Label confirmNaive;
    @FXML private Label f1Naive;

    @FXML private Label tpPerc;
    @FXML private Label fpPerc;
    @FXML private Label tnPerc;
    @FXML private Label fnPerc;
    @FXML private Label confirmPerc;
    @FXML private Label f1Perc;

    @FXML private Spinner spinnerKNN;
    @FXML private Spinner spinnerNaive;
    @FXML private Spinner spinnerPerc;
    @FXML private TextField kValue;
    @FXML private TextField ratePerc;
    @FXML private TextField iterationsPerc;


    @FXML
    public void trainKNN(){
        double split = Integer.parseInt(spinnerKNN.getValue().toString());
        try{
        if (kValue.getText().isEmpty()){
            throw new Exception("Please enter a k value");
        }
        int k = Integer.parseInt(kValue.getText());
        if(service==null){
            throw new Exception("Please select a file");
        }
        service.createKNN(k);
        service.trainKNN(split);
        knnTrained = true;
        confirmKNN.setText("KNN trained");
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void testKNN(){
        int split = Integer.parseInt(spinnerKNN.getValue().toString());
        try{
        if (knnTrained){
            List<Double> results = service.testKNN(split);
            tpKNN.setText(Double.toString(results.get(0)));
            tnKNN.setText(Double.toString(results.get(1)));
            fpKNN.setText(Double.toString(results.get(2)));
            fnKNN.setText(Double.toString(results.get(3)));
            double f1= 2*results.get(0)/(2*results.get(0)+results.get(2)+results.get(3)); //f1=2tp/(2tp+fp+fn)
            f1KNN.setText("F1 score: "+ Double.toString(f1));
        }
        else{
            throw new Exception("You have to train the model first.");
        }}
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void trainNaive(){
        double split = Integer.parseInt(spinnerNaive.getValue().toString());
        try{
            if(service==null){
                throw new Exception("Please select a file");
            }
            service.createNaiveBayes();
            service.trainNaive(split);
            naiveTrained = true;
            confirmNaive.setText("Naive Bayes trained");
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void testNaive(){
        int split = Integer.parseInt(spinnerNaive.getValue().toString());
        try{
            if (naiveTrained){
                List<Double> results = service.testNaive(split);
                tpNaive.setText(Double.toString(results.get(0)));
                tnNaive.setText(Double.toString(results.get(1)));
                fpNaive.setText(Double.toString(results.get(2)));
                fnNaive.setText(Double.toString(results.get(3)));
                double f1= 2*results.get(0)/(2*results.get(0)+results.get(2)+results.get(3)); //f1=2tp/(2tp+fp+fn)
                f1Naive.setText("F1 score: "+ Double.toString(f1));
            }
            else{
                throw new Exception("You have to train the model first.");
            }}
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void handleFileSelection() {
        try{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File");

        fileChooser.setInitialDirectory(new File("D:\\Facultate\\Java\\AssignmentGUI\\untitled\\Data"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());

            label.setText(selectedFile.getAbsolutePath());
            filePath = selectedFile.getAbsolutePath();
            service = new Service(filePath);
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void trainPerc(){
        double split = Integer.parseInt(spinnerPerc.getValue().toString());
        try{
            if(service==null){
                throw new Exception("Please select a file");
            }
            if (ratePerc.getText().isEmpty() || iterationsPerc.getText().isEmpty()){
                throw new Exception("Please enter the values");
            }
            double rate = Double.parseDouble(ratePerc.getText());
            int iterations = Integer.parseInt(iterationsPerc.getText());
            service.createPerceptron(rate, iterations);
            service.trainPerc(split);
            percTrained = true;
            confirmPerc.setText("Perceptron trained");
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void testPerc(){
        int split = Integer.parseInt(spinnerPerc.getValue().toString());
        try{
            if (percTrained){
                List<Double> results = service.testPerc(split);
                tpPerc.setText(Double.toString(results.get(0)));
                tnPerc.setText(Double.toString(results.get(1)));
                fpPerc.setText(Double.toString(results.get(2)));
                fnPerc.setText(Double.toString(results.get(3)));
                double f1= 2*results.get(0)/(2*results.get(0)+results.get(2)+results.get(3)); //f1=2tp/(2tp+fp+fn)
                f1Perc.setText("F1 score: "+ Double.toString(f1));
            }
            else{
                throw new Exception("You have to train the model first.");
            }}
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}

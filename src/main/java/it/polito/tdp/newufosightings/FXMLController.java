package it.polito.tdp.newufosightings;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.newufosightings.model.Model;
import it.polito.tdp.newufosightings.model.Stato;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

//controller turno A --> switchare al branch master_turnoB per turno B

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private TextField txtAnno;

    @FXML
    private Button btnSelezionaAnno;

    @FXML
    private ComboBox<String> cmbBoxForma;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private TextField txtT1;

    @FXML
    private TextField txtAlfa;

    @FXML
    private Button btnSimula;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	try {
    		Integer anno = Integer.valueOf(this.txtAnno.getText());
    		String forma = this.cmbBoxForma.getValue();
    		
    		if(anno>=1910 && anno<=2014) {
	    		model.creaGrafo(anno, forma);
	    		this.txtResult.appendText(String.format("Grafo creato!\n#vertici: %d\n#archi: %d",model.nVertici(), model.nArchi()));
	    		
	    		this.txtResult.appendText("\n\nPer ogni stato, peso adiacenti:\n");
	    		
	    		for(Stato s : model.stati()) {
	    			this.txtResult.appendText(s.toString()+" "+model.sommaPesi(s)+"\n");
	    			
	    		}
    		}else{
    			this.txtResult.appendText("Inserisci valore tra 1910 e 2014");
    		}
    	}catch(NumberFormatException nfe) {
    		this.txtResult.appendText("Inserire valore corretto");
    	}
    	

    }

    @FXML
    void doSelezionaAnno(ActionEvent event) {
    	this.cmbBoxForma.getItems().clear();

    	try {
    		Integer anno = Integer.valueOf(this.txtAnno.getText());
    		
    		if(anno>=1910 && anno<=2014) {
	    		
	    		this.cmbBoxForma.getItems().addAll(model.forme(anno));
	    		this.cmbBoxForma.setValue(model.forme(anno).get(0));
    		}else {
    			this.txtResult.appendText("Inserisci valore tra 1910 e 2014");
    		}
    		
    	}catch(NumberFormatException nfe) {
    		this.txtResult.appendText("Inserire valore corretto");
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	
    	try {
    		Integer anno = Integer.valueOf(this.txtAnno.getText());
    		String forma = this.cmbBoxForma.getValue();
    		Long t1 = Long.valueOf(this.txtT1.getText());
    		Double alfa = Double.valueOf(this.txtAlfa.getText());
    		
    		if(anno>=1910 && anno<=2014) {
	    		 if(alfa>=0 && alfa<=100) {
	    			 if(t1<365) {
	    				 
	    				 model.simula(t1, alfa, anno, forma);
	    				 for(Stato s : model.stati()) {
	    					 this.txtResult.appendText(s.toString()+" "+s.getDefcon()+"\n");
	    				 }
	    				 
	    			 }else {
	    				 txtResult.appendText("Inserire nGiorni");
	    			 }
	    			 
	    		 }else {
	    			 txtResult.appendText("Valore di alfa tra 0 e 100");
	    		 }
	    			
	    	}else{
    				this.txtResult.appendText("Inserisci valore tra 1910 e 2014");
    			}
    	}catch(NumberFormatException nfe) {
    		this.txtResult.appendText("Inserire valore corretto");
    	}
    	

    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnSelezionaAnno != null : "fx:id=\"btnSelezionaAnno\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert cmbBoxForma != null : "fx:id=\"cmbBoxForma\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtT1 != null : "fx:id=\"txtT1\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert txtAlfa != null : "fx:id=\"txtAlfa\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'NewUfoSightings.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}

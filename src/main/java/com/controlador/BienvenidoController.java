/*
 * Copyright (c) 2011, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.modelo.Host;
import com.modelo.User;
import com.negocio.Facade;
import com.negocio.FileABM;
import com.negocio.HostABM;
import com.negocio.UserABM;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BienvenidoController implements Initializable {

	@FXML
	private Text titulo;	
	
	@FXML
	private Text estado;	
	
	@FXML
	private ListView<String> list = new ListView<String>();
	
	@FXML
	private Pane root;
	
	@FXML
	private Label copyright;
	
	@FXML
	private Label iduser;
	


	public void setUsername(String dato) {
		String parts[]=dato.split("=");
		this.titulo.setText(parts[0]);
		this.iduser.setText(dato.substring(dato.lastIndexOf('=') + 1));
		
	}


	@Override
	public void initialize(URL url, ResourceBundle rb) { // TODO }
		Facade facade= new Facade();
		HostABM lista= facade.getHostABM();
		FileABM file=facade.getFileABM();
		
		this.estado.setText(estado.getText()+": "+file.estadoServicio());
		
		list.setItems(FXCollections.observableArrayList(lista.getHost()));
	}

	
	@FXML
	protected void salir(ActionEvent event) {
		
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();

	}
	
	@FXML
	protected void acercaDe(ActionEvent event) {
		copyright.setVisible(true);	
		copyright.setText("Direcci�n General del Cuerpo de Agentes de Tr�nsito y Seguridad Vial, Desarrollos Inform�ticos � 2018");
		copyright.setAlignment(Pos.CENTER);
	}
	
	@FXML
	protected void crearInicio() {
		Facade sistema=new Facade();
		FileABM file=sistema.getFileABM();
		
		copyright.setVisible(true);	
		copyright.setAlignment(Pos.CENTER);
		try {
			copyright.setText(file.crearInicio());
			this.estado.setText("Estado del servicio: "+file.estadoServicio());
		} catch (IOException e) {
			copyright.setText("Error al inciar el servicio :"+e.getMessage());	
		}	

	}
	
	
	@FXML
	protected void agregarHost(ActionEvent event) {
		copyright.setVisible(true);	
		copyright.setAlignment(Pos.CENTER);
		
		try {
			Facade facade= new Facade();
			HostABM se= facade.getHostABM();
			Host host=se.getHostMemoria();
			host.setObservacion("Alta de host");
			host.setLoginultimomov(Long.valueOf(iduser.getText()));		
			//titulo.getText();				
			se.agregarHost(host);
			
			
			copyright.setText("Host registrado exitosamente!!");	
		} catch (Exception e) {
			copyright.setText("Error al agregar Host :"+e.getMessage());	
		}
		
	}
	


}

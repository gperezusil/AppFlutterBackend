package com.essalud.main.service;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.essalud.main.entity.Usuario;
import com.essalud.main.entity.Usuario_Datos;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Query;

@Service
public class FirebaseService {

	private Firestore db;
	
	public FirebaseService() {
		intIDB();
	}
		// Agregar todos los ususarios de la base de datos oracle a firebase 
	public boolean agregar() {
		deleteCollection();
		List<Usuario> usuario = leerTxt();
		String stdId=UUID.randomUUID().toString();
		CollectionReference collRef = db.collection("usuario");
		Map<String, Usuario> data = new HashMap<>();
		for(Usuario u : usuario) {
		data.put(stdId, u);
		collRef.add(data);
		}
		return true;
	}
	
	public Usuario obtenerUsuario(String correo )
	{
		Usuario listaUsuarios = new Usuario();
		try {
			CollectionReference cities = db.collection("usuario");
			// Create a query against the collection.
			Query query = cities.whereEqualTo("user_name", correo);
			// retrieve  query results asynchronously using query.get()
			ApiFuture<QuerySnapshot> querySnapshot = query.get();
			for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
			  System.out.println(document.getId());
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaUsuarios;
		
	}
	
	public void deleteCollection() {
		  try {
			  CollectionReference collection = db.collection("usuario");
			    // retrieve a small batch of documents to avoid out-of-memory errors
			    ApiFuture<QuerySnapshot> future = collection.limit(100).get();
			    int deleted = 0;
			    // future.get() blocks on document retrieval
			    List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			    for (QueryDocumentSnapshot document : documents) {
			      document.getReference().delete();
			      ++deleted;
			    }
			    if (deleted >= 100) {
			      // retrieve and delete another batch
			    	deleteCollection();
			    }
			  } catch (Exception e) {
			    System.err.println("Error deleting collection : " + e.getMessage());
			  }
		
	}
	
	public List<Usuario> leerTxt() {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		List<Usuario> usuario = new ArrayList<Usuario>();
	    File file= new File("C:\\Users\\gherson.perez\\Documents\\workspace-spring-tool-suite-4-4.2.2.RELEASE\\AppFlutterBackend\\datos\\archivo.txt");
        Scanner scanner;
           try {
               scanner = new Scanner(file);
               while(scanner.hasNextLine())
               {
                   String linea = scanner.nextLine();
                   String[] separa  = linea.split("\\t");
                   Usuario s = new Usuario();
                   s.setId(Long.parseLong(separa[0]));
                   s.setNombres(separa[1]);
                   s.setA_paterno(separa[2]);
                   s.setA_materno(separa[3]);
                   s.setUser_name(separa[4]);
                   Usuario_Datos d = new Usuario_Datos();
                   d.setId(s.getId());
                   d.setCorreo(separa[5]);;
                   s.setUsuario_datos(d);
                   s.setContrasena(separa[6]);
                   usuario.add(s);
               }
               scanner.close();
           } catch (FileNotFoundException ex) {
        	   logger.info(ex.getMessage());
           }
	      
		return usuario;
	}
	
	
	private void intIDB() {

		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File configFile = new File(classLoader.getResource("tienda-flutter-firebase-admin-sdk.json").getFile());
			InputStream serviceAcount = new FileInputStream(configFile);
			GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAcount);
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(credentials)
					.build();
			FirebaseApp.initializeApp(options);
			
			this.db = FirestoreClient.getFirestore();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

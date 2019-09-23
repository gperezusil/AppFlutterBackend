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
import com.essalud.main.entity.UsuarioCarga;
import com.essalud.main.entity.Usuario_Datos;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class FirebaseService {

	private Firestore db;

	public FirebaseService() {
		intIDB();
	}

	// Agregar todos los ususarios de la base de datos oracle a firebase
	public boolean agregar() {

		try {
			
			deleteCollection();
			List<Usuario> usuario = leerTxt();
			CollectionReference cities = db.collection("usuario");
			List<ApiFuture<WriteResult>> futures = new ArrayList<>();
			for (Usuario u : usuario) {
				if(u.getUsuario_datos()!=null) {
				String stdId=UUID.randomUUID().toString();
				futures.add(cities.document(stdId).set(new UsuarioCarga(u.getId(),u.getUser_name(),
						u.getA_paterno(),u.getA_materno(),u.getNombres(),u.getContrasena(),
						u.getUsuario_datos().getCorreo())));
				}
			}
			ApiFutures.allAsList(futures).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public List<Usuario> obtenerUsuario(String correo,String contrasena) {
		crearToken();
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		try {
			ApiFuture<QuerySnapshot> future = db.collection("usuario")
					.whereEqualTo("correo", correo)
					.whereEqualTo("contrasena", contrasena).get();
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			for (QueryDocumentSnapshot document : documents) {
				listaUsuarios.add(document.toObject(Usuario.class));
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
		File file = new File(
				"C:\\Users\\gherson.perez\\Documents\\workspace-spring-tool-suite-4-4.2.2.RELEASE\\AppFlutterBackend\\datos\\archivo.txt");
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String linea = scanner.nextLine();
				String[] separa = linea.split("\\t");
				Usuario s = new Usuario();
				s.setId(Long.parseLong(separa[0]));
				s.setNombres(separa[1]);
				s.setA_paterno(separa[2]);
				s.setA_materno(separa[3]);
				s.setUser_name(separa[4]);
				Usuario_Datos d = new Usuario_Datos();
				d.setId(s.getId());
				d.setCorreo(separa[5]);
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
			FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(credentials).build();
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
	
	public void crearToken()
	{
		String uid = "some-uid";

		try {
			Map<String, Object> additionalClaims = new HashMap<String, Object>();
			additionalClaims.put("premiumAccount", true);
			String customToken = FirebaseAuth.getInstance()
				    .createCustomToken(uid, additionalClaims);
			
			System.out.println(customToken);
		} catch (FirebaseAuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

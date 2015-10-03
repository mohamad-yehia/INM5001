package Taxi.Libre;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InscClients extends Activity {

    String nomClient;
    String prenomClient;
    String telClient;
    String utilClient;
    String passeClient;
    Button retourInsc = null;
    Context context = this;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insc_client);

        final Button button = (Button) findViewById(R.id.button_send);
        final EditText textNomClient = (EditText) findViewById(R.id.nomClient);
        final EditText textPrenomClient = (EditText) findViewById(R.id.prenomClient);
        final EditText textTelClient = (EditText) findViewById(R.id.telClient);
        final EditText textUtilClient = (EditText) findViewById(R.id.utilClient);
        final EditText textPasseClient = (EditText) findViewById(R.id.passeClient);
        final TextView resulEnreg = (TextView) findViewById(R.id.resultat);
        retourInsc = (Button) findViewById(R.id.retourInsc);
        
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                java.sql.Connection conn = null;
                java.sql.PreparedStatement stmt = null;

                nomClient = textNomClient.getText().toString();
                prenomClient = textPrenomClient.getText().toString();
                telClient = textTelClient.getText().toString();
                utilClient = textUtilClient.getText().toString();
                passeClient = textPasseClient.getText().toString();

                //Register driver
                try {
                    //mEdit2.setText(nomClient);
                    DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
                } catch (SQLException ex) {

                }
                //Etablir la connexion
                String URL = "jdbc:oracle:thin:@zeta2.labunix.uqam.ca:1521:baclab";
                String USER = "ae491447";
                String PASS = "mWeGvhJS";
                try {
                    conn = DriverManager.getConnection(URL, USER, PASS);
                } catch (SQLException ex) {

                }

                try {
                    if (conn != null) {
                        if (!nomClient.equals("") && !prenomClient.equals("") && !telClient.equals("") && !utilClient.equals("") && !passeClient.equals("")) {
                            stmt = conn.prepareStatement("INSERT INTO CLIENTS (ID_CLIENT, NOM, PRENOM, TELEPHONE, POSITION, NOM_UTILISATEUR, MOT_PASSE)"
                                    + "VALUES(?,?,?,?,?,?,?)");
                            stmt.setInt(1, 1);
                            stmt.setString(2, nomClient);
                            stmt.setString(3, prenomClient);
                            stmt.setString(4, telClient);        
                            stmt.setString(5, "ABCDEF");
                            stmt.setString(6, utilClient);
                            stmt.setString(7, passeClient);
                            stmt.executeUpdate();

                            textNomClient.setText("");
                            textPrenomClient.setText("");
                            textTelClient.setText("");
                            textUtilClient.setText("");
                            textPasseClient.setText("");
                            resulEnreg.setText("Enregistrement complete");
                        }else{
                            resulEnreg.setText("Un ou plusieurs champs vide");
                        }
                    }
                } catch (SQLException ex) {

                }

                //Fermer le statement
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException ex) {

                }

                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {

                }
            }
        });
        
        retourInsc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(context, Inscription.class);
                startActivity(intent);
            }
        });
    }
//}
}

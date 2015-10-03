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

public class InscChauffeurs extends Activity {

    String nomChauffeur;
    String prenomChauffeur;
    String matriculeChauffeur;
    String telChauffeur;
    String utilChauffeur;
    String passeChauffeur;
    final Context context = this;
    Button retourInsc = null;
    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insc_chauffeur);

        final Button button = (Button) findViewById(R.id.Enreg_Chauffeur);
        final EditText textNomChauffeur = (EditText) findViewById(R.id.nomChauffeur);
        final EditText textPrenomChauffeur = (EditText) findViewById(R.id.prenomChauffeur);
        final EditText textTelChauffeur = (EditText) findViewById(R.id.telChauffeur);
        final EditText textMatChauffeur = (EditText) findViewById(R.id.matriculeChauffeur);
        final EditText textUtilChauffeur = (EditText) findViewById(R.id.utilChauffeur);
        final EditText textPasseChauffeur = (EditText) findViewById(R.id.passeChauffeur);
        final TextView resulEnreg = (TextView) findViewById(R.id.resultat);
        retourInsc = (Button) findViewById(R.id.retourInsc);
        
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                java.sql.Connection conn = null;
                java.sql.PreparedStatement stmt = null;

                nomChauffeur = textNomChauffeur.getText().toString();
                prenomChauffeur = textPrenomChauffeur.getText().toString();
                telChauffeur = textTelChauffeur.getText().toString();
                matriculeChauffeur = textMatChauffeur.getText().toString();
                utilChauffeur = textUtilChauffeur.getText().toString();
                passeChauffeur = textPasseChauffeur.getText().toString();

                //Register driver
                try {
                    //mEdit2.setText(nomChauffeur);
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
                        if (!nomChauffeur.equals("") && !prenomChauffeur.equals("") && !telChauffeur.equals("") && !matriculeChauffeur.equals("")
                                && !utilChauffeur.equals("") && !passeChauffeur.equals("")) {
                            stmt = conn.prepareStatement("INSERT INTO CHAUFFEURS (ID_CHAUFFEUR, NOM, PRENOM, "
                                    + "TELEPHONE, MATRICULE, POSITION, DISPONIBLE, "
                                    + "NOM_UTILISATEUR, MOT_PASSE)"
                                    + "VALUES(?,?,?,?,?,?,?,?,?)");
                            stmt.setInt(1, 1);
                            stmt.setString(2, nomChauffeur);
                            stmt.setString(3, prenomChauffeur);
                            stmt.setString(4, telChauffeur);
                            stmt.setString(5, matriculeChauffeur);
                            stmt.setString(6, "ABCDEF");
                            stmt.setString(7, "Y");
                            stmt.setString(8, utilChauffeur);
                            stmt.setString(9, passeChauffeur);
                            stmt.executeUpdate();

                            textNomChauffeur.setText("");
                            textPrenomChauffeur.setText("");
                            textTelChauffeur.setText("");
                            textMatChauffeur.setText("");
                            textUtilChauffeur.setText("");
                            textPasseChauffeur.setText("");
                            resulEnreg.setText("Inscription complete");
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

package com.example.maraicherandroid.Controller;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import com.example.maraicherandroid.Activities.HomePageActivity;
import com.example.maraicherandroid.Activities.LoginActivity;
import com.example.maraicherandroid.Modele.TCP;

import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private Socket csocket;
    private Boolean logged;

    private TCP tcp;
    private static Controller instance;

    private Controller()
    {
        tcp = new TCP();
    }

    public static synchronized Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void startLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public void startHomeActivity(Context context) {
        Intent intent = new Intent(context, HomePageActivity.class);
        context.startActivity(intent);
    }

    //----------------------------------------------------------------------------------
    //---------		Pour Connexion
    //----------------------------------------------------------------------------------

    public void onConnecter(String IP, String port) throws Exception{
        //System.out.println(WindowPortIp.getTextFieldPort().getText() + " " + WindowPortIp.getTextFieldIpAdresse().getText());

        String regex = "^[0-9.]+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(IP);

        if (!matcher.matches()) throw new Exception("l'adresse IP ne peut contenir que des chiffres et des points");

        regex = "^[0-9]+$";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(port);

        if (!matcher.matches()) throw new Exception("le port ne peut être qu'un numéro");

        String ipS = IP;
        int PortS = Integer.parseInt(port);

        csocket = tcp.ClientSocket(ipS,PortS);
    }

    //----------------------------------------------------------------------------------
    //---------		LOGIN/LOGOUT
    //----------------------------------------------------------------------------------

    public Boolean onLogin(String username, String password, boolean check) throws Exception{
        int nvClient;

        if(check) nvClient = 1;
        else nvClient = 0;

        String Requete = "LOGIN#" + username + "#" + password + "#" + nvClient;

        System.out.println(Requete);

        String Reponse = SendRec(Requete);

        System.out.println(Reponse);

        String[] tokens;

        tokens = Reponse.split("#");

        if(tokens[0].equals("LOGIN"))
        {
            if(tokens[1].equals("ok"))
            {
//                if(maraicherWindow.getCheckBoxNvClient().isSelected())
//                    JOptionPane.showMessageDialog(null, "Vous avez été inscrit avec succès", "Login", JOptionPane.INFORMATION_MESSAGE);
//
//                JOptionPane.showMessageDialog(null, "Vous êtes connecté avec succès", "Login", JOptionPane.INFORMATION_MESSAGE);
//
//                Logger();

                logged = true;

                //numFacture = Integer.parseInt(tokens[2]);

                //System.out.println("Numero de facture: " + numFacture);

//                getCaddie();
//
//                ConsultArticle(1);

                return true;
            }
            else
                throw new Exception(tokens[2]);
        }

        return false;
    }


    //----------------------------------------------------------------------------------
    //---------		AUTRES
    //----------------------------------------------------------------------------------

    private String SendRec(String Requete) throws Exception {
        int taille = tcp.Send(csocket, Requete);

        if(taille == -1) throw new Exception("Erreur send!");

        String Reponse = tcp.Receive(csocket);

        if(Reponse == null) throw new Exception("Erreur Receive!");

        return Reponse;
    }

    public void showMessage(String title, String message, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.create().show();
    }
}

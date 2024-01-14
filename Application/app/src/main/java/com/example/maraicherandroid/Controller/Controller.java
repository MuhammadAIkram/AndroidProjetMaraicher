package com.example.maraicherandroid.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import com.example.maraicherandroid.Activities.HomePageActivity;
import com.example.maraicherandroid.Activities.LoginActivity;
import com.example.maraicherandroid.Activities.ProfileActivity;
import com.example.maraicherandroid.Modele.Article;
import com.example.maraicherandroid.Modele.TCP;

import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private Socket csocket;
    private Boolean logged;
    private int numFacture;
    private int nbArticles;
    private float totalCaddie;
    private Article articleCourant;
    private LinkedList<Article> Caddie;
    private static HomePageActivity activity;

    private TCP tcp;
    private static Controller instance;

    private Controller()
    {
        tcp = new TCP();

        numFacture = 0;
        nbArticles = 0;
        totalCaddie = 0.0F;

        Caddie = new LinkedList<>();
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

    public void SetHome(HomePageActivity homePageActivity){
        activity = homePageActivity;
    }

    public void startPofileActivity(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        context.startActivity(intent);
    }

    public static HomePageActivity getActivity() {
        return activity;
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
                logged = true;

                numFacture = Integer.parseInt(tokens[2]);

                System.out.println("Numero de facture: " + numFacture);

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

    public Boolean onLogout() throws Exception{
        String Requete = "LOGOUT";

        System.out.println(Requete);

        String Reponse = SendRec(Requete);

        System.out.println(Reponse);

        String[] tokens;

        tokens = Reponse.split("#");

        if(tokens[0].equals("LOGOUT"))
        {
            if(tokens[1].equals("ok"))
            {
//                if(nbArticles > 0)
//                    VidePanier();

                logged = false;

                finishHome();

                return true;
            }
            else return false;
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

    public static void finishHome() {
        activity.finish();
    }
}

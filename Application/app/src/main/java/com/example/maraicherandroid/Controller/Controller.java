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
    //---------		Pour Connexion
    //----------------------------------------------------------------------------------

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

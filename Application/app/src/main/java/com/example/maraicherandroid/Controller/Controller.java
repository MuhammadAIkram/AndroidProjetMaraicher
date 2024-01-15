package com.example.maraicherandroid.Controller;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import com.example.maraicherandroid.Activities.HomePageActivity;
import com.example.maraicherandroid.Activities.LoginActivity;
import com.example.maraicherandroid.Activities.ProfileActivity;
import com.example.maraicherandroid.Modele.Article;
import com.example.maraicherandroid.Modele.TCP;

import java.net.Socket;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    private Socket csocket;
    private Boolean logged;
    private int numFacture;
    private int nbArticles;
    private float totalCaddie;
    public Article articleCourant;
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

    public LinkedList<Article> getCaddieList(){
        return Caddie;
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

                getCaddie();

                System.out.println(Caddie);

                ConsultArticle(1);

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
                if(nbArticles > 0)
                    VidePanier();

                logged = false;

                finishHome();

                return true;
            }
            else return false;
        }

        return false;
    }

    public boolean onAvant() throws Exception {
        ConsultArticle(articleCourant.getId() - 1);
        return true;
    }

    public boolean onSuivante() throws Exception {
        ConsultArticle(articleCourant.getId() + 1);
        return true;
    }

    public boolean onAchat(int stockSpin) throws Exception {
        if(stockSpin <= 0)
            throw new Exception("Veuillez sélectionner une valeur supérieure à 0");

        int i = 0;

        for (Article art: Caddie) {
            if(art.getId() == articleCourant.getId())
            {
                break;
            }

            i++;
        }

        if(Caddie.size() == i) i = 10;

        System.out.println(i);

        if(nbArticles == 10 && i == 10)
            throw new Exception("votre panier est plein, merci d'acheter les articles ou de supprimer un article du panier !");

        String Requete = "ACHAT#" + articleCourant.getId() + "#" + stockSpin;

        System.out.println(Requete);

        String Reponse = SendRec(Requete);

        System.out.println(Reponse);

        String[] tokens;

        tokens = Reponse.split("#");

        if(tokens[0].equals("ACHAT"))
        {
            if(!tokens[1].equals("-1"))
            {
                if(tokens[2].equals("0")) throw new Exception("stock insuffisant!");
                else
                {
                    articleCourant.setStock(articleCourant.getStock() - stockSpin);

                    if(i == 10)
                    {
                        System.out.println("nouveau article");

                        Caddie.add(new Article(articleCourant.getId(), articleCourant.getIntitule(), articleCourant.getPrix(), stockSpin, articleCourant.getImage()));

                        totalCaddie += (stockSpin*articleCourant.getPrix());

                        Requete = "UPDATE_CAD#" + numFacture + "#0#0#" + totalCaddie + "#" + articleCourant.getId() + "#" + stockSpin;

                        System.out.println(Requete);

                        Reponse = SendRec(Requete);

                        System.out.println(Reponse);

                        nbArticles++;
                    }
                    else
                    {
                        System.out.println("update article");

                        Caddie.get(i).setStock(Caddie.get(i).getStock() + stockSpin);

                        totalCaddie = 0.0F;

                        for (Article art: Caddie) {
                            totalCaddie += (art.getPrix()*art.getStock());
                        }

                        Requete = "UPDATE_CAD#" + numFacture + "#0#1#" + totalCaddie + "#" + articleCourant.getId() + "#" + Caddie.get(i).getStock();

                        System.out.println(Requete);

                        Reponse = SendRec(Requete);

                        System.out.println(Reponse);
                    }

                    return true;
                }
            }
        }

        return false;
    }


    //----------------------------------------------------------------------------------
    //---------		AUTRES
    //----------------------------------------------------------------------------------

    private void ConsultArticle(int id) throws Exception {
        String Requete = "CONSULT#" + id;

        System.out.println(Requete);

        String Reponse = SendRec(Requete);

        System.out.println(Reponse);

        String[] tokens;

        tokens = Reponse.split("#");

        if(tokens[0].equals("CONSULT"))
        {
            if(!tokens[1].equals("-1"))
            {
                String intitule = tokens[2];
                int stock = Integer.parseInt(tokens[3]);
                float prix = Float.parseFloat(tokens[4]);
                String image = tokens[5];

                articleCourant = new Article(Integer.parseInt(tokens[1]), intitule, prix, stock, image);
            }
        }
    }

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

    private void VidePanier() throws Exception {
        String Requete = "CANCEL_ALL#" + nbArticles;

        for (Article art:Caddie) {
            Requete += "#" + art.getId() + "&" + art.getStock();
        }

        System.out.println(Requete);

        String Reponse = SendRec(Requete);

        System.out.println(Reponse);

        String[] tokens;

        tokens = Reponse.split("#");

        if(tokens[0].equals("CANCEL_ALL"))
        {
            if(tokens[1].equals("ko")) throw new Exception("Vous n'avez rien dans votre panier !");

            totalCaddie = 0.0F;

            Caddie = new LinkedList<>();

            nbArticles = 0;
        }
    }

    public void getCaddie() throws Exception {
        String Requete = "CADDIE#" + numFacture;

        System.out.println(Requete);

        String Reponse = SendRec(Requete);

        System.out.println(Reponse);

        StringTokenizer tokenizer = new StringTokenizer(Reponse, "#");
        String ptr = tokenizer.nextToken();

        if (ptr.equals("CADDIE")) {
            nbArticles = Integer.parseInt(tokenizer.nextToken());

            if (nbArticles > 0) {
                int i = 0;

                while (i < nbArticles) {
                    StringTokenizer itemTokenizer = new StringTokenizer(tokenizer.nextToken(), "$");

                    int id = Integer.parseInt(itemTokenizer.nextToken());
                    String intitule = itemTokenizer.nextToken();
                    int stock = Integer.parseInt(itemTokenizer.nextToken());
                    float prix = Float.parseFloat(itemTokenizer.nextToken());

                    Article art = new Article(id, intitule, prix, stock, "");

                    Caddie.add(art);

                    totalCaddie += (stock * prix);

                    i++;
                }
            }
        }
    }
}

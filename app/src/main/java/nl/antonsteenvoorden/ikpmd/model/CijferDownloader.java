//package nl.antonsteenvoorden.ikpmd.model;
//
//import android.content.Context;
//import android.os.AsyncTask;
//import android.util.Log;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.net.CookieHandler;
//import java.net.CookieManager;
//import java.net.MalformedURLException;
//import java.net.ProtocolException;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.security.KeyManagementException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.X509Certificate;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSession;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//
//import nl.antonsteenvoorden.ikpmd.model.Module;
//
//public class CijferDownloader extends AsyncTask<Void, Void, Void>{
//    private static final String URL_BASE = "https://studievolg.hsleiden.nl/student/Personalia.do";
//    private static final String URL_AUTH = "https://studievolg.hsleiden.nl/student/AuthenticateUser.do";
//    private static final String URL_RES = "https://studievolg.hsleiden.nl/student/ToonResultaten.do";
//    private static final String DOMAIN = "https://studievolg.hsleiden.nl";
//
//    private String resultsURL;
//    private String authURL;
//    private String osirisUsername;
//    private String osirisPassword;
//
//    private String resultsHTML;
//    private ArrayList<Module> tmpCijfers;
//    private HttpsURLConnection conn;
//    private List<String> cookies;
//    private int somethingWentWrong = 0;
//
//     public CijferDownloader(Context context,  String osirisUsername, String osirisPassword) {
//        tmpCijfers = new ArrayList<Module>();
//        this.osirisUsername = osirisUsername;
//        this.osirisPassword = osirisPassword;
//    }
//
//    @Override
//    protected Void doInBackground(Void... params) {
//
//        try {
//
//            disableSslVerification();
//
//            //Set the cookie handler
//            CookieHandler.setDefault(new CookieManager());
//
//            //do first request on Osiris server
//            String firstOsirisReqHtml = getPageContent(URL_RES);
//
//            //Check if login is needed
//            if (Jsoup.parse(firstOsirisReqHtml).title().equals("OSIRIS - Resultaten")) {
//                //Login not needed
//                Log.v("Server - login", "Not needed to login. Already logged in.");
//
//                resultsHTML = firstOsirisReqHtml;
//
//            } else {
//                //Login is needed. Start login process
//                Log.v("Server - login", "Trying to log in");
//                String logoutHtml = getPageContent("https://studievolg.hsleiden.nl/student/Logout.do");
//                String loginHtml = getPageContent(URL_RES);
//                System.out.println(loginHtml);
//                String postParamsTmp;
//                try {
//                    postParamsTmp = getFormParams(loginHtml, osirisUsername, osirisPassword);
//
//                    String postParams = "" + postParamsTmp + "&event=login";
//
//                    //sent login to OSIRIS server
//                    sendPost(URL_AUTH, postParams);
//
//                    //try new request
//                    String secondOsirisReqHtml = getPageContent(URL_RES);
//                    if (Jsoup.parse(secondOsirisReqHtml).title().equals("OSIRIS - Resultaten")) {
//                        //yup.... you're in..
//                        resultsHTML = secondOsirisReqHtml;
//                    }
//                    else {
//                        Log.d("THIS", Jsoup.parse(secondOsirisReqHtml).title());
//                        //whoops... correct credentials?
//                        somethingWentWrong = 1;
//                        Log.d("somethingWentWrong" , "" + somethingWentWrong);
//                        return null;
//
//                    }
//
//
//                } catch (Exception e) {
//                    Log.e("Server - sending POST", "Error: " + e.toString());
//                    somethingWentWrong = 2;
//                    Log.d("somethingWentWrong" , "" + somethingWentWrong);
//                    return null;
//                }
//            }
//
//        } catch (Exception e) {
//            somethingWentWrong = 3;
//            Log.d("somethingWentWrong" , "" + somethingWentWrong);
//            Log.d("class" , "" + e.getClass().toString());
//            Log.w("ERROR", e.toString());
//            return null;
//
//        }
//
//        if (resultsHTML == null) {
//            somethingWentWrong = 4;
//            Log.d("somethingWentWrong" , "" + somethingWentWrong);
//            return null;
//        }
//
//
//        try {
//            Document doc = Jsoup.parse(resultsHTML);
//            if (doc.getElementsByTag("title").first().text().equals("OSIRIS - Inloggen")) {
//                //huh realy?
//                somethingWentWrong = 5;
//                Log.d("somethingWentWrong" , "" + somethingWentWrong);
//                return null;
//            }
//
//            //try to parse the results page
//            Element tabel = doc.getElementsByClass("OraTableContent").first();
//            Elements cijferRij = tabel.getElementsByTag("tr");
//            //removes the first row.. no cijfers in here
//            cijferRij.remove(0);
//
//            int i;
//            i = 1;
//
//            //for every row (cijfer) parse it into a "Cijfer" and add to list 'tmpCijfers'
//            for (Element elementen : cijferRij) {
//                Elements cijferCol = elementen.getElementsByTag("td");
//
//                String toetsDatumString = cijferCol.get(0).text();
//                String toetsNaamString = cijferCol.get(1).text();
//                String toetsNaamUitgebreidString = cijferCol.get(2).text();
//                String toetsSoortString = cijferCol.get(3).text();
//                String toetsWegingString = cijferCol.get(4).text();
//                String toetsResultaatString = cijferCol.get(6).text();
//                String toetsMutatiedatumString = cijferCol.get(8).text();
//                Boolean isDef;
//
//                //Is the result a concept?
//                if (toetsResultaatString.length() >= 10) {
//                    if (toetsResultaatString.substring(toetsResultaatString.length() - 9).equals("(concept)")) {
//                        toetsResultaatString = toetsResultaatString.substring(0, (toetsResultaatString.length() - 10));
//                        isDef = false;
//                    } else {
//                        isDef = true;
//                    }
//                } else {
//                    isDef = true;
//                }
//
//
//                Module cijfer = new Module();
//                cijfer.setName(toetsNaamString);
////                toetsResultaatString,
////                    toetsDatumString, toetsNaamString, toetsSoortString,
////                    toetsWegingString, toetsMutatiedatumString,
////                    toetsNaamUitgebreidString, isDef, false);
//
//                tmpCijfers.add(cijfer);
//
//                Log.i("FOUND" , cijfer.getName());
//
//                i += 1;
//
//            }
//
//        } catch (Exception e) {
//            somethingWentWrong = 6;
//            Log.d("somethingWentWrong" , "" + somethingWentWrong);
//            Log.d("ERROR", "Melding: " + e.getMessage());
//            return null;
//
//        }
//        return null;
//    }
//
//    private static void disableSslVerification() {
//        try
//        {
//            // Create a trust manager that does not validate certificate chains
//            TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
//                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//                public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                }
//                public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                }
//            }
//            };
//
//            // Install the all-trusting trust manager
//            SSLContext sc = SSLContext.getInstance("SSL");
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//
//            // Create all-trusting host name verifier
//            HostnameVerifier allHostsValid = new HostnameVerifier() {
//                public boolean verify(String hostname, SSLSession session) {
//                    return true;
//                }
//            };
//
//            // Install the all-trusting host verifier
//            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String getPageContent(String pageURL) {
//        URL obj = null;
//        try {
//            obj = new URL(pageURL);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        try {
//            if (obj != null) {
//                conn = (HttpsURLConnection) obj.openConnection();
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            conn.setRequestMethod("GET");
//        } catch (ProtocolException e) {
//            e.printStackTrace();
//        }
//
//        conn.setUseCaches(false);
//
//        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
//        conn.setRequestProperty("Accept",
//                "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
//        conn.setRequestProperty("Accept-Language", "nl-NL,nl;q=0.8,en-US;q=0.6,en;q=0.4");
//        if (cookies != null) {
//            for (String cookie : this.cookies) {
//                conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
//                Log.i("Koekje in", cookie);
//            }
//        }
//
//        BufferedReader in =
//                null;
//        try {
//            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String inputLine;
//        StringBuilder response = new StringBuilder();
//
//        try {
//            if (in != null) {
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//            }
//        } catch (IOException e) {
//            Log.e("Error", "cannot add new lines to output");
//        }
//        try {
//            assert in != null;
//            in.close();
//        } catch (IOException e) {
//            Log.e("Error", "in cannot be closed");
//        }
//
//
//        if (conn.getHeaderFields().get("Set-Cookie") != null) {
//            setCookies(conn.getHeaderFields().get("Set-Cookie"));
//            for (String koekje : conn.getHeaderFields().get("Set-Cookie")) {
//                Log.d("cookies moeten erin", koekje);
//            }
//
//        }
//
//        return response.toString();
//    }
//
//    public String getFormParams(String html, String username, String password) throws UnsupportedEncodingException {
//
//        Log.v("Server", "Extracting form's data...");
//
//        Document doc = Jsoup.parse(html);
//        Element loginform = doc.getElementById("loginForm");
//        Elements inputElements = loginform.getElementsByTag("input");
//        List<String> paramList = new ArrayList<String>();
//        for (Element inputElement : inputElements) {
//            String key = inputElement.attr("name");
//            String value = inputElement.attr("value");
//
//            if (key.equals("gebruikersNaam"))
//                value = username;
//            else if (key.equals("wachtWoord"))
//                value = password;
//            paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
//        }
//
//        // build parameters list
//        StringBuilder result = new StringBuilder();
//        for (String param : paramList) {
//            if (result.length() == 0) {
//                result.append(param);
//            } else {
//                result.append("&").append(param);
//            }
//        }
//        return result.toString();
//    }
//
//    public void setCookies(List<String> cookies) {
//        this.cookies = cookies;
//    }
//
//    private void sendPost(String url, String postParams) throws Exception {
//
//        URL obj = new URL(url);
//        conn = (HttpsURLConnection) obj.openConnection();
//
//        // Acts like a browser
//        conn.setUseCaches(false);
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Host", "studievolg.hsleiden.nl");
//        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
//        conn.setRequestProperty("Accept",
//            "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//        conn.setRequestProperty("Accept-Language", "nl-NL,nl;q=0.8,en-US;q=0.6,en;q=0.4");
//        for (String cookie : this.cookies) {
//            conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
//        }
//        conn.setRequestProperty("Connection", "keep-alive");
//        conn.setRequestProperty("Referer", "https://studievolg.hsleiden.nl/student/ToonResultaten.do");
//        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//        conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));
//
//        conn.setDoOutput(true);
//        conn.setDoInput(true);
//
//        // Send post request
//        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
//        wr.writeBytes(postParams);
//        wr.flush();
//        wr.close();
//
//        int responseCode = conn.getResponseCode();
//        Log.v("Server", "SENDING POST request to " + url);
//        Log.v("Server", "Post parameters : " + postParams);
//        Log.v("Server", "Response code : " + responseCode);
//
//        if (conn.getHeaderFields().get("Set-Cookie") != null) {
//            setCookies(conn.getHeaderFields().get("Set-Cookie"));
//            for (String koekje : conn.getHeaderFields().get("Set-Cookie")) {
//                Log.d("cookies moeten erin", koekje);
//            }
//
//        }
//
//        BufferedReader in =
//                new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        String inputLine;
//        StringBuilder response = new StringBuilder();
//
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//    }
//}

package nl.antonsteenvoorden.ikpmd.service;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import nl.antonsteenvoorden.ikpmd.interfaces.Callback;
import nl.antonsteenvoorden.ikpmd.model.Module;

/**
 * Created by Anton on 17/02/2016.
 * Credits for a lot of this file go to Lars van der Voorden
 */
public class ModuleObtainer extends AsyncTask<Void,Void,Void>  {
  private static final String URL_BASE = "https://studievolg.hsleiden.nl/student/Personalia.do";
  private static final String URL_AUTH = "https://studievolg.hsleiden.nl/student/AuthenticateUser.do";
  private static final String URL_RES = "https://studievolg.hsleiden.nl/student/ToonResultaten.do";
  private static final String DOMAIN = "studievolg.hsleiden.nl";

  private String osirisUsername;
  private String osirisPassword;

  private String resultsHTML;
  private HttpsURLConnection conn;
  private List<String> cookies;

  private Callback callBack;

  private boolean loggedIn;

  public ModuleObtainer(String osirisUsername, String osirisPassword) {
    this.osirisUsername = osirisUsername;
    this.osirisPassword = osirisPassword;
  }

  @Override
  protected Void doInBackground(Void... params) {
    disableSSL();
    //Set the cookie handler
    CookieHandler.setDefault(new CookieManager());

    //do first request on Osiris server
    String firstOsirisReqHtml = obtainPage(URL_RES);
    System.out.println("Logged in:" + loggedIn);
    loggedIn = checkLogin(firstOsirisReqHtml);
    System.out.println("Logged in:" + loggedIn);
    if (!loggedIn) {
      login(obtainPage(URL_RES));
    }
    return null;
  }

  @Override
  protected void onPostExecute(Void aVoid) {
    if(callBack != null ) {
      callBack.handleCallBack(loggedIn);
    }
  }

  private void login(String HTML) {
    String postParamsTmp;
    System.out.println("ModuleObtainer.login : " + HTML);
    try {
      postParamsTmp = getFormParams(HTML);
      String postParams = "" + postParamsTmp + "&event=login";

      //sent login to OSIRIS server
      authenticate(postParams);

      //try new request
      resultsHTML = obtainPage(URL_RES);
      if (Jsoup.parse(resultsHTML).title().equals("OSIRIS - Resultaten")) {
        System.out.println("Logged in!!");
        loggedIn = true;
        parseToGrade();
      } else {
        Log.d("THIS", Jsoup.parse(resultsHTML).title());
        //TODO:ERROR
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String getFormParams(String html) {
    StringBuilder result = null;
    try {
      Document doc = Jsoup.parse(html);
      Element loginform = doc.getElementById("loginForm");
      Elements inputElements = loginform.getElementsByTag("input");
      List<String> paramList = new ArrayList<String>();
      for (Element inputElement : inputElements) {
        String key = inputElement.attr("name");
        String value = inputElement.attr("value");

        if (key.equals("gebruikersNaam"))
          value = osirisUsername;
        else if (key.equals("wachtWoord"))
          value = osirisPassword;
        paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
      }

      // build parameters list
      result = new StringBuilder();
      for (String param : paramList) {
        if (result.length() == 0) {
          result.append(param);
        } else {
          result.append("&").append(param);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result.toString();
  }


  private void parseToGrade() {
    try {
      Document doc = Jsoup.parse(resultsHTML);
      if (doc.getElementsByTag("title").first().text().equals("OSIRIS - Inloggen")) {
        //TODO:ERROR
        authenticate(getFormParams(resultsHTML));
      }

      //try to parse the results page
      Element tabel = doc.getElementsByClass("OraTableContent").first();
      Elements cijferRij = tabel.getElementsByTag("tr");
      //removes the first row.. no cijfers in here

      cijferRij.remove(0);
      List<Module> opgehaaldeModules = new ArrayList<Module>();
      //for every row (cijfer) parse it into a "Cijfer" and add to list 'tmpCijfers'
      DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
      NumberFormat numberFormat = NumberFormat.getInstance(Locale.GERMANY);

      for (Element elementen : cijferRij) {
        Elements cijferCol = elementen.getElementsByTag("td");
        String toetsDatumString = cijferCol.get(0).text();
        String toetsNaamString = cijferCol.get(1).text();
        String toetsNaamUitgebreidString = cijferCol.get(2).text();
        String toetsSoortString = cijferCol.get(3).text();
        String toetsWegingString = cijferCol.get(4).text();
        String toetsResultaatString = cijferCol.get(6).text();
        String toetsMutatiedatumString = cijferCol.get(8).text();

        Module module = new Module();
        module.setName(toetsNaamString);
        module.setLongName(toetsNaamUitgebreidString);

        Date date = format.parse(toetsDatumString);
        module.setToetsDatum(new java.sql.Date(date.getTime()));

        module.setToetsType(toetsSoortString);
        Date mutatieDate = format.parse(toetsMutatiedatumString);
        module.setMutatieDatum(new java.sql.Date(mutatieDate.getTime()));
        module.setEcts(0);
        module.setPeriod(0);
        toetsResultaatString = toetsResultaatString.replaceAll("V", "7,5");
        toetsResultaatString = toetsResultaatString.replaceAll("O", "2,5");
        //Is the result a concept?
        if (toetsResultaatString.length() >= 10) {
          if (toetsResultaatString.substring(toetsResultaatString.length() - 9).equals("(concept)")) {
            toetsResultaatString = toetsResultaatString.substring(0, (toetsResultaatString.length() - 10));
            Number grade = numberFormat.parse(toetsResultaatString);
            module.setGrade(grade.doubleValue());
            module.setDefinitief(0);
          }
        } else {
          Number grade = numberFormat.parse(toetsResultaatString);
          module.setGrade(grade.doubleValue());
          module.setDefinitief(1);
        }

        System.out.println("ModuleObtainer.parseToGrade : " + module.toString());
        opgehaaldeModules.add(module);
      }
      Module.insertList(opgehaaldeModules);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private boolean checkLogin(String HTML) {
    if (Jsoup.parse(HTML).title().equals("OSIRIS - Resultaten")) {
      System.out.println("Logged in");
      return true;
    } else {
      return false;
    }
  }

  private String obtainPage(String url) {
    URL obj = null;
    BufferedReader in = null;
    StringBuilder response = new StringBuilder();

    try {
      obj = new URL(url);
      if (obj != null) {
        conn = (HttpsURLConnection) obj.openConnection();
        conn.setRequestMethod("GET");
        conn.setUseCaches(false);
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        conn.setRequestProperty("Accept",
            "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language", "nl-NL,nl;q=0.8,en-US;q=0.6,en;q=0.4");
      }

      if (cookies != null) {
        for (String cookie : this.cookies) {
          conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
        }
      }

      in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String inputLine;

      if (in != null) {
        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
      }
      in.close();

      if (conn.getHeaderFields().get("Set-Cookie") != null) {
        setCookies(conn.getHeaderFields().get("Set-Cookie"));
        for (String koekje : conn.getHeaderFields().get("Set-Cookie")) {
          Log.d("cookies moeten erin", koekje);
        }

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(response.toString());
    return response.toString();
  }

  private void disableSSL() {
    try {
      // Create a trust manager that does not validate certificate chains
      TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
          return null;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {
        }
      }
      };

      // Install the all-trusting trust manager
      SSLContext sc = SSLContext.getInstance("SSL");
      sc.init(null, trustAllCerts, new java.security.SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

      // Create all-trusting host name verifier
      HostnameVerifier allHostsValid = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
          return true;
        }
      };

      // Install the all-trusting host verifier
      HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (KeyManagementException e) {
      e.printStackTrace();
    }
  }

  private void authenticate(String postParams) {
    try {
      URL url = new URL(URL_AUTH);
      conn = (HttpsURLConnection) url.openConnection();

      // Acts like a browser
      conn.setUseCaches(false);
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Host", DOMAIN);
      conn.setRequestProperty("User-Agent", "Mozilla/5.0");
      conn.setRequestProperty("Accept",
          "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
      conn.setRequestProperty("Accept-Language", "nl-NL,nl;q=0.8,en-US;q=0.6,en;q=0.4");
      for (String cookie : this.cookies) {
        conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
      }
      conn.setRequestProperty("Connection", "keep-alive");
      conn.setRequestProperty("Referer", URL_RES);
      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));

      conn.setDoOutput(true);
      conn.setDoInput(true);

      // Send post request
      DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
      wr.writeBytes(postParams);
      wr.flush();
      wr.close();

      int responseCode = conn.getResponseCode();
      Log.v("Server", "SENDING POST request to " + url);
      Log.v("Server", "Post parameters : " + postParams);
      Log.v("Server", "Response code : " + responseCode);

      if (conn.getHeaderFields().get("Set-Cookie") != null) {
        setCookies(conn.getHeaderFields().get("Set-Cookie"));
        for (String koekje : conn.getHeaderFields().get("Set-Cookie")) {
          Log.d("cookies moeten erin", koekje);
        }

      }

      BufferedReader in =
          new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String inputLine;
      StringBuilder response = new StringBuilder();

      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setCookies(List<String> cookies) {
    this.cookies = cookies;
  }

  public void setOsirisUsername(String osirisUsername) {
    this.osirisUsername = osirisUsername;
  }

  public void setOsirisPassword(String osirisPassword) {
    this.osirisPassword = osirisPassword;
  }

  public void setCallBack(Callback callBack) {
    this.callBack = callBack;
  }
}

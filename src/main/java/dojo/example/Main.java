package dojo.example;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dojo.example.models.*;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import io.javalin.rendering.template.JavalinFreemarker;
import kong.unirest.core.Unirest;
import kong.unirest.core.json.JSONArray;
import kong.unirest.core.json.JSONObject;


import java.io.Reader;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static double longitude = 110.335403;
    static double latitude = -7.716165;
    static String apiKey = "bc7e68cbc3c0c6ed0d691481e40339ff";
    static Gson gson;
    static Map<String, String> url = new HashMap<>();
    static Map<String, Double> latt = new HashMap<>();
    static Map<String, Double> longg = new HashMap<>();
    static Map<String, String> timee = new HashMap<>();
    static String currentUrl;
    static String currentTime;
    static String kotaa;
    static boolean set =false;

    private static JsonObject jsonObject = new JsonObject();

    public static void main(String[] args) {
        url.put("default", "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey);
        timee.put("default", "https://timeapi.io/api/Time/current/coordinate?latitude=" + latitude + "&longitude="+ longitude);
        currentUrl = url.get("default");
        currentTime = timee.get("default");
        gson = new Gson();
        System.out.println(getMain().temp);
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "1234"));
        jsonObject.add("lokasi", new JsonArray());

        Javalin app = Javalin.create(cfg -> {
            cfg.fileRenderer(new JavalinFreemarker());
            cfg.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "public";
                staticFiles.location = Location.CLASSPATH;
            });
        });

        app.get("/", ctx -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("user", "Arya");
            MainStatus mainStatus = getMain();
            WeatherStatus weatherStatus = getWeather();
            ctx.render("views/index.html", attributes);
        });

        app.get("/switch/<name>", ctx -> {
            kotaa = ('"'+ ctx.pathParam("name") +'"');
            for (Map.Entry<String, String> entry : url.entrySet()) {
                System.out.println(" Switch : Key: " + entry.getKey() + ", Value: " + entry.getValue());
            }
            currentTime = timee.get(kotaa);
            currentUrl = url.get(kotaa);
            set = true;
            System.out.println(currentTime);
            System.out.println(currentUrl);
            ctx.redirect("/");
        });


        app.post("/submit", ctx -> {
            String kota = ctx.formParam("kota");
            //mengonversikannya menjadi objek Double
            Double lat = Double.valueOf(ctx.formParam("lat"));
            Double longg = Double.valueOf(ctx.formParam("longg"));

            InputStatus lokasi = new InputStatus(kota, longg, lat);
            Gson gson = new Gson();
            //toJsonTree() : konversi lokasi dari objek lokasi ke JsonElemen
            JsonObject lokasiJson = gson.toJsonTree(lokasi).getAsJsonObject();

            JsonArray lokasiArray = jsonObject.getAsJsonArray("lokasi");
            lokasiArray.add(lokasiJson);
            List<InputStatus> x = getLokasi();

            ctx.redirect("/");
        });

        app.get("api/lokasi", ctx -> {
            MetaData metaData = new MetaData(200, "OK");
            List<InputStatus> inputStatus = getLokasi();
            MyResponse myResponse = new MyResponse(metaData, inputStatus);
            ctx.json(myResponse);
        });

        app.get("api/name", ctx -> {
            MetaData metaData = new MetaData(200, "OK");
            Name inputName = getName();
            MyResponse myResponse = new MyResponse(metaData, inputName);
            ctx.json(myResponse);
        });

        app.get("api/main_now_mapping", ctx -> {
            MetaData metaData = new MetaData(200, "OK");
            MainStatus mainStatus = getMain();
            MyResponse myResponse = new MyResponse(metaData, mainStatus);

            ctx.json(myResponse);
        });

        app.get("api/weather_now", ctx -> {
            MetaData metaData = new MetaData(200, "OK");
            WeatherStatus weatherStatus = getWeather();
            MyResponse myResponse = new MyResponse(metaData, weatherStatus);

            ctx.json(myResponse);
        });

        app.get("api/time", ctx -> {
            MetaData metaData = new MetaData(200, "OK");
            TimeStatus times  = getTime();
            MyResponse myResponse = new MyResponse(metaData, times);

            ctx.json(myResponse);
        });

        app.start(port);
    }

    public static MainStatus getMain() {
        JSONObject main = Unirest.get(currentUrl)
                .asJson()
                .getBody()
                .getObject()
                .getJSONObject("main");
        String mainData = main.toString();
        return gson.fromJson(mainData, MainStatus.class);
    }

    public static Name getName() {
        JSONObject main = Unirest.get(currentUrl)
                .asJson()
                .getBody()
                .getObject();
        String mainData = main.toString();
        return gson.fromJson(mainData, Name.class);
    }

    public static TimeStatus getTime() {
        JSONObject main = Unirest.get(currentTime)
                .asJson()
                .getBody()
                .getObject();
        String mainData = main.toString();
        return gson.fromJson(mainData, TimeStatus.class);
    }

    public static WeatherStatus getWeather() {
        System.out.println("keambil :" + currentUrl);
        JSONObject response = Unirest.get(currentUrl)
                .asJson()
                .getBody()
                .getObject();

        //mengambil nilai array JSON dengan kunci "weather" dari objek response dan menyimpannya dalam variabel weatherArray.
        JSONArray weatherArray = response.getJSONArray("weather");

        Gson gson = new Gson();
        //mengonversi JSON ke objek Java
        //weatherArray.get(0): Mengambil elemen pertama dari weatherArray yang merupakan JSONArray ubah ke string
        WeatherStatus weatherStatus = gson.fromJson(weatherArray.get(0).toString(), WeatherStatus.class);
        return weatherStatus;
    }

    public static List<InputStatus> getLokasi() {

        //mengambil JsonArray "lokasi" dari JsonObject. Buat sebuah List kosong bernama inputStatusList untuk menyimpan objek InputStatus
        JsonArray array = jsonObject.getAsJsonArray("lokasi");
        List<InputStatus> inputStatusList = new ArrayList<>();
        Gson gson = new Gson();

        //terasi pada setiap elemen dalam array
        for (JsonElement element : array) {
            //Untuk setiap elemen yang berupa JsonObject, elemen dionversi JsonObject .getAsJsonObject(). Kemudian, gson mengonversi JsonObject -> objek InputStatus. Objek InputStatus yang baru dibuat ini kemudian ditambahkan ke dalam inpustatus list
            if (element.isJsonObject()) {
                JsonObject jsonObject = element.getAsJsonObject();
                InputStatus inputStatus = gson.fromJson(jsonObject, InputStatus.class);
                inputStatusList.add(inputStatus);
            }
        }

        for (JsonElement value : array) {
            //valude objek jsonelemen -> jsonobjek
            //Karena kode mengasumsikan bahwa setiap elemen dalam array adalah sebuah objek JSON (JsonObject), maka perlu dilakukan casting atau konversi tipe dari JsonElement menjadi JsonObject
            //mengonversi tipe data suatu objek atau nilai dari satu tipe ke tipe yang lain
            JsonObject objLokasi = (JsonObject) value;
            String kota = String.valueOf(objLokasi.get("kota"));
            Double longitude = Double.valueOf(String.valueOf(objLokasi.get("longg")));
            Double latitude = Double.valueOf(String.valueOf(objLokasi.get("lat")));
            latt.put(kota, latitude);
            longg.put(kota, longitude);
            url.put(kota, "https://api.openweathermap.org/data/2.5/weather?lat=" + latt.get(kota) + "&lon=" + longg.get(kota) + "&appid=" + apiKey);
            timee.put(kota, "https://timeapi.io/api/Time/current/coordinate?latitude=" + latt.get(kota) +"&longitude=" + longg.get(kota));
            System.out.println("Input " + kota + " : " + url.get(kota));
            currentTime = timee.get(kota);
            currentUrl = url.get(kota);
        }
        if (set == true){
            currentUrl = url.get(kotaa);
        }
        set = false;

        return inputStatusList;
    }
}
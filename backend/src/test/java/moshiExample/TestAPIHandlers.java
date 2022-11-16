package moshiExample;
import edu.brown.cs32.examples.moshiExample.csv.CSVWrapper;
import edu.brown.cs32.examples.moshiExample.geojsonjavatypes.GeoJsonResponse;
import edu.brown.cs32.examples.moshiExample.handlers.GeoHandler;
import edu.brown.cs32.examples.moshiExample.handlers.GetCSVHandler;
import edu.brown.cs32.examples.moshiExample.handlers.LoadCSVHandler;
import edu.brown.cs32.examples.moshiExample.handlers.WeatherHandler;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import okio.Buffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.*;
import spark.Spark;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This the test class that tests for all the handler classes and their interaction with the API
 * and local server
 */
public class TestAPIHandlers {

  @BeforeAll
  public static void setEverythingUp() {
    Spark.port(0);
    Logger.getLogger("").setLevel(Level.WARNING);
  }

  final CSVWrapper csv = new CSVWrapper();

  @BeforeEach
  public void setUp() {
    // In fact, restart the entire Spark server for every test!
    Spark.get("/loadcsv", new LoadCSVHandler(csv));
    Spark.get("/getcsv", new GetCSVHandler(csv));
    Spark.get("/weather", new WeatherHandler());
    Spark.get("/geodata", new GeoHandler());
    Spark.init();
    Spark.awaitInitialization(); // don't continue until the server is listening
  }


  // general api test
  @Test
  public void geoDataTest1() throws IOException, InterruptedException {
    HttpURLConnection clientConnection = tryRequest("geodata");
    assertEquals(200, clientConnection.getResponseCode());
    String latmin = "-75.000000";
    String lonmin = "40.000000";
    String latmax = "-74.900000";
    String lonmax = "40.100000";
    String search = "http://localhost:" + Spark.port() + "/geodata?" +
            "latmin=" + latmin + "&lonmin=" + lonmin + "&latmax=" + latmax
            + "&lonmax=" + lonmax;
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(search))
            .build();
    HttpResponse<String> response = HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals("{\"result\":\"success\",\"data\":" + "{\"features\":[{\"geometry\":{\"coordinates\":[[[[-74.9918,40.05232],[-74.98647,40.047222],[-74.98852,40.045876],[-74.98799,40.045258],[-74.98972,40.044205],[-74.98935,40.04364],[-74.9912,40.042564],[-74.99795,40.04853],[-75.00496,40.0549],[-75.00118,40.057358],[-74.99845,40.058193],[-74.9918,40.05232]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"PA\",\"city\":\"Philadelphia\",\"holc_id\":\"C12\",\"holc_grade\":\"C\",\"neighborhood_id\":7271.0,\"area_description_data\":{\"1\":\"12 Philadelphia C\",\"2\":\"Level\",\"3\":\"Suburban development - fair transportation. \",\"4\":\"Mixture of nondescript houses. No sewers- streets unimproved. \",\"7\":\"4500-7000 25-28 $3000.-$4500.  June 1937 35-50 $3,500.00 55% $45.00 65%  $25.-$35.  1933-34  $30.00 65% 2500-4000 $3,000.00  55% $25.00  $5,500.00\",\"13\":\"Static \",\"14\":\"Unrestricted section of nondescript development. River front properties are not good housing. Undesirable people. \",\"15\":\"June 10 7 Rowland & Banister\",\"8c\":\"75-80\",\"9b\":\"single- $3000.-$3500. \",\"10c\":\"fair\",\"5e\":\"no\",\"12a\":\"very limited\",\"6b\":\"frame\",\"5g\":\"  \",\"9a\":\"fair\",\"12b\":\"\",\"6a\":\"single- detached\",\"5a\":\"clerks- skilled mechanics\",\"10a\":\"good\",\"5d\":\"no \",\"8b\":\"100\",\"5c\":\"50 no \",\"11b\":\"none\",\"8a\":\"50\",\"9c\":\"fair\",\"10b\":\"single-$25.-$35. \",\"6c\":\"5-25 yrs. \",\"5b\":\"$1000.-$2000.\",\"5f\":\"nominal\",\"6d\":\"fair\",\"11a\":\"none\"}}},{\"geometry\":{\"coordinates\":[[[[-75.011795,40.04034],[-75.0143,40.04228],[-75.018005,40.045307],[-75.01813,40.04548],[-75.01716,40.04624],[-75.01378,40.049007],[-75.008835,40.05241],[-75.009285,40.0539],[-75.008934,40.056995],[-75.00945,40.059593],[-75.00772,40.06032],[-75.00252,40.065964],[-74.995,40.061886],[-74.99336,40.061306],[-74.99144,40.06094],[-74.990654,40.060413],[-74.985855,40.063034],[-74.98345,40.063995],[-74.98215,40.06425],[-74.98172,40.064198],[-74.981766,40.060867],[-74.98124,40.059795],[-74.983475,40.05774],[-74.9846,40.057503],[-74.98543,40.05694],[-74.98535,40.056465],[-74.9918,40.05232],[-74.99845,40.058193],[-75.00118,40.057358],[-75.00496,40.0549],[-74.99795,40.04853],[-75.00156,40.046246],[-75.005394,40.04412],[-75.00712,40.04293],[-75.011795,40.04034]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"PA\",\"city\":\"Philadelphia\",\"holc_id\":\"E5\",\"holc_grade\":\"C\",\"neighborhood_id\":7272.0,\"area_description_data\":{\"1\":\"Suburban Philadelphia, Pa.  C #E-6\",\"2\":\"Rolling Ground\",\"3\":\"\",\"4\":\"\",\"7\":\"                     \",\"13\":\"\",\"14\":\"Very sparsely settled section because of its distance from the center of the city, expected to take its general character from C-12 immediately adjacent. \",\"15\":\"  \",\"6c\":\"\",\"10a\":\"\",\"10b\":\"\",\"10c\":\"\",\"11a\":\"\",\"11b\":\"\",\"12a\":\"\",\"12b\":\"\",\"5a\":\"\",\"5b\":\"\",\"5c\":\" \",\"5d\":\"  Yes (Scattered)\",\"5e\":\"\",\"5f\":\"\",\"5g\":\"  \",\"6a\":\"\",\"6b\":\"\",\"6d\":\"\",\"8a\":\"\",\"8b\":\"\",\"8c\":\"\",\"9a\":\"\",\"9b\":\"\",\"9c\":\"\"}}}],\"type\":\"FeatureCollection\"}}", response.body());
  }
  
  @Test
  public void geoDataTest2() throws IOException, InterruptedException {
    HttpURLConnection clientConnection = tryRequest("geodata");
    assertEquals(200, clientConnection.getResponseCode());
    String latmin = "-74.000000";
    String lonmin = "39.000000";
    String latmax = "-73.900000";
    String lonmax = "40.100000";
    String search = "http://localhost:" + Spark.port() + "/geodata?" +
            "latmin=" + latmin + "&lonmin=" + lonmin + "&latmax=" + latmax
            + "&lonmax=" + lonmax;
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(search))
            .build();
    HttpResponse<String> response = HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals("{\"result\":\"success\",\"data\":" + "{\"features\":[],\"type\":\"FeatureCollection\"}}", response.body());
  }

  // bad coordinate input (not  bounding box)
  @Test
  public void geoDataTest3() throws IOException, InterruptedException {
    HttpURLConnection clientConnection = tryRequest("geodata");
    assertEquals(200, clientConnection.getResponseCode());
    String latmin = "-74.000000";
    String lonmin = "39.000000";
    String latmax = "-75.000000";
    String lonmax = "38.000000";
    String search = "http://localhost:" + Spark.port() + "/geodata?" +
            "latmin=" + latmin + "&lonmin=" + lonmin + "&latmax=" + latmax
            + "&lonmax=" + lonmax;
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(search))
            .build();
    HttpResponse<String> response = HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals("{\"result\":\"success\",\"data\":" + "{\"features\":[],\"type\":\"FeatureCollection\"}}", response.body());
  }
  // bad inputs

  @Test
  public void geoDataTest4() throws IOException, InterruptedException {
    HttpURLConnection clientConnection = tryRequest("geodata");
    assertEquals(200, clientConnection.getResponseCode());
    String latmin = "-fasdklf.000000";
    String lonmin = "39.000000";
    String latmax = "-75.000000";
    String lonmax = "38.000000";
    String search = "http://localhost:" + Spark.port() + "/geodata?" +
            "latmin=" + latmin + "&lonmin=" + lonmin + "&latmax=" + latmax
            + "&lonmax=" + lonmax;
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(search))
            .build();
    HttpResponse<String> response = HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals("{\"result\":\"error_bad_request\"}", response.body());
  }

  // missing inputs
  @Test
  public void geoDataTest5() throws IOException, InterruptedException {
    HttpURLConnection clientConnection = tryRequest("geodata");
    assertEquals(200, clientConnection.getResponseCode());
    String latmin = "-fasdklf.000000";
    String latmax = "-75.000000";
    String lonmax = "38.000000";
    String search = "http://localhost:" + Spark.port() + "/geodata?" +
            "latmin=" + latmin + "&latmax=" + latmax
            + "&lonmax=" + lonmax;
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(search))
            .build();
    HttpResponse<String> response = HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals("{\"result\":\"error_bad_request\"}", response.body());
  }

  // no inputs
  @Test
  public void geoDataTest6() throws IOException, InterruptedException {
    HttpURLConnection clientConnection = tryRequest("geodata");
    assertEquals(200, clientConnection.getResponseCode());
    String search = "http://localhost:" + Spark.port() + "/geodata?" +
            "latmin=" + "&latmax=" + "&lonmax=";
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(search))
            .build();
    HttpResponse<String> response = HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals("{\"result\":\"error_bad_request\"}", response.body());
  }

  @Test
  public void geoDataTest7() throws IOException, InterruptedException {
    HttpURLConnection clientConnection = tryRequest("geodata");
    assertEquals(200, clientConnection.getResponseCode());
    String search = "http://localhost:" + Spark.port() + "/geodata?";
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(search))
            .build();
    HttpResponse<String> response = HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals("{\"result\":\"error_bad_request\"}", response.body());
  }

  // test showing multiple calls
  @Test
  public void geoDataTest8() throws IOException, InterruptedException {
    HttpURLConnection clientConnection = tryRequest("geodata");
    assertEquals(200, clientConnection.getResponseCode());
    String latmin = "-75.000000";
    String lonmin = "40.000000";
    String latmax = "-74.900000";
    String lonmax = "40.100000";
    String search = "http://localhost:" + Spark.port() + "/geodata?" +
            "latmin=" + latmin + "&lonmin=" + lonmin + "&latmax=" + latmax
            + "&lonmax=" + lonmax;
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(search))
            .build();
    HttpResponse<String> response = HttpClient.newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals("{\"result\":\"success\",\"data\":" + "{\"features\":[{\"geometry\":{\"coordinates\":[[[[-74.9918,40.05232],[-74.98647,40.047222],[-74.98852,40.045876],[-74.98799,40.045258],[-74.98972,40.044205],[-74.98935,40.04364],[-74.9912,40.042564],[-74.99795,40.04853],[-75.00496,40.0549],[-75.00118,40.057358],[-74.99845,40.058193],[-74.9918,40.05232]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"PA\",\"city\":\"Philadelphia\",\"holc_id\":\"C12\",\"holc_grade\":\"C\",\"neighborhood_id\":7271.0,\"area_description_data\":{\"1\":\"12 Philadelphia C\",\"2\":\"Level\",\"3\":\"Suburban development - fair transportation. \",\"4\":\"Mixture of nondescript houses. No sewers- streets unimproved. \",\"7\":\"4500-7000 25-28 $3000.-$4500.  June 1937 35-50 $3,500.00 55% $45.00 65%  $25.-$35.  1933-34  $30.00 65% 2500-4000 $3,000.00  55% $25.00  $5,500.00\",\"13\":\"Static \",\"14\":\"Unrestricted section of nondescript development. River front properties are not good housing. Undesirable people. \",\"15\":\"June 10 7 Rowland & Banister\",\"8c\":\"75-80\",\"9b\":\"single- $3000.-$3500. \",\"10c\":\"fair\",\"5e\":\"no\",\"12a\":\"very limited\",\"6b\":\"frame\",\"5g\":\"  \",\"9a\":\"fair\",\"12b\":\"\",\"6a\":\"single- detached\",\"5a\":\"clerks- skilled mechanics\",\"10a\":\"good\",\"5d\":\"no \",\"8b\":\"100\",\"5c\":\"50 no \",\"11b\":\"none\",\"8a\":\"50\",\"9c\":\"fair\",\"10b\":\"single-$25.-$35. \",\"6c\":\"5-25 yrs. \",\"5b\":\"$1000.-$2000.\",\"5f\":\"nominal\",\"6d\":\"fair\",\"11a\":\"none\"}}},{\"geometry\":{\"coordinates\":[[[[-75.011795,40.04034],[-75.0143,40.04228],[-75.018005,40.045307],[-75.01813,40.04548],[-75.01716,40.04624],[-75.01378,40.049007],[-75.008835,40.05241],[-75.009285,40.0539],[-75.008934,40.056995],[-75.00945,40.059593],[-75.00772,40.06032],[-75.00252,40.065964],[-74.995,40.061886],[-74.99336,40.061306],[-74.99144,40.06094],[-74.990654,40.060413],[-74.985855,40.063034],[-74.98345,40.063995],[-74.98215,40.06425],[-74.98172,40.064198],[-74.981766,40.060867],[-74.98124,40.059795],[-74.983475,40.05774],[-74.9846,40.057503],[-74.98543,40.05694],[-74.98535,40.056465],[-74.9918,40.05232],[-74.99845,40.058193],[-75.00118,40.057358],[-75.00496,40.0549],[-74.99795,40.04853],[-75.00156,40.046246],[-75.005394,40.04412],[-75.00712,40.04293],[-75.011795,40.04034]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"PA\",\"city\":\"Philadelphia\",\"holc_id\":\"E5\",\"holc_grade\":\"C\",\"neighborhood_id\":7272.0,\"area_description_data\":{\"1\":\"Suburban Philadelphia, Pa.  C #E-6\",\"2\":\"Rolling Ground\",\"3\":\"\",\"4\":\"\",\"7\":\"                     \",\"13\":\"\",\"14\":\"Very sparsely settled section because of its distance from the center of the city, expected to take its general character from C-12 immediately adjacent. \",\"15\":\"  \",\"6c\":\"\",\"10a\":\"\",\"10b\":\"\",\"10c\":\"\",\"11a\":\"\",\"11b\":\"\",\"12a\":\"\",\"12b\":\"\",\"5a\":\"\",\"5b\":\"\",\"5c\":\" \",\"5d\":\"  Yes (Scattered)\",\"5e\":\"\",\"5f\":\"\",\"5g\":\"  \",\"6a\":\"\",\"6b\":\"\",\"6d\":\"\",\"8a\":\"\",\"8b\":\"\",\"8c\":\"\",\"9a\":\"\",\"9b\":\"\",\"9c\":\"\"}}}],\"type\":\"FeatureCollection\"}}", response.body());
    String latmin2 = "-75.kkakjsdfajksd";
    String lonmin2 = "40.000000";
    String latmax2 = "-74.900000";
    String lonmax2 = "40.100000";
    String search2 = "http://localhost:" + Spark.port() + "/geodata?" +
            "latmin=" + latmin2 + "&lonmin=" + lonmin2 + "&latmax=" + latmax2
            + "&lonmax=" + lonmax2;
    HttpRequest request2 = HttpRequest.newBuilder()
            .uri(URI.create(search2))
            .build();
    HttpResponse<String> response2 = HttpClient.newBuilder()
            .build()
            .send(request2, HttpResponse.BodyHandlers.ofString());
    assertEquals("{\"result\":\"error_bad_request\"}", response2.body());
    String latmin3 = "-87.000000";
    String lonmin3 = "33.500000";
    String latmax3 = "-86.300000";
    String lonmax3 = "33.510000";
    String search3 = "http://localhost:" + Spark.port() + "/geodata?" +
            "latmin=" + latmin3 + "&lonmin=" + lonmin3 + "&latmax=" + latmax3
            + "&lonmax=" + lonmax3;
    HttpRequest request3 = HttpRequest.newBuilder()
            .uri(URI.create(search3))
            .build();
    HttpResponse<String> response3 = HttpClient.newBuilder()
            .build()
            .send(request3, HttpResponse.BodyHandlers.ofString());
    assertEquals("{\"result\":\"success\",\"data\":{\"features\":[{\"geometry\":{\"coordinates\":[[[[-86.756775,33.497543],[-86.75692,33.49579],[-86.76202,33.491924],[-86.76227,33.488922],[-86.75449,33.48883],[-86.75396,33.48764],[-86.75289,33.4869],[-86.75225,33.48779],[-86.75225,33.490437],[-86.752174,33.491745],[-86.75207,33.492104],[-86.74605,33.49662],[-86.7459,33.49704],[-86.7458,33.50016],[-86.74508,33.499214],[-86.744125,33.49878],[-86.743416,33.49866],[-86.74318,33.497433],[-86.74327,33.49656],[-86.743935,33.495808],[-86.74489,33.49525],[-86.74503,33.49406],[-86.74465,33.493073],[-86.744125,33.492435],[-86.74289,33.49295],[-86.7418,33.49303],[-86.74066,33.492992],[-86.73975,33.492676],[-86.73947,33.4922],[-86.73937,33.491802],[-86.7399,33.491447],[-86.74056,33.49105],[-86.7409,33.490257],[-86.74028,33.489502],[-86.73562,33.491325],[-86.73534,33.4922],[-86.73481,33.494022],[-86.726204,33.4987],[-86.72564,33.4985],[-86.72526,33.497986],[-86.72483,33.49747],[-86.72911,33.492794],[-86.732056,33.490295],[-86.732574,33.489544],[-86.733246,33.48891],[-86.73628,33.486965],[-86.73862,33.48514],[-86.74002,33.48451],[-86.74049,33.4855],[-86.7413,33.485577],[-86.74245,33.4857],[-86.743256,33.485577],[-86.7443,33.484943],[-86.745155,33.484825],[-86.745964,33.48514],[-86.7462,33.484905],[-86.747246,33.48467],[-86.74791,33.484547],[-86.74853,33.483994],[-86.74872,33.48312],[-86.75143,33.4809],[-86.75438,33.477806],[-86.75538,33.477848],[-86.75637,33.47741],[-86.75728,33.476856],[-86.75747,33.4761],[-86.75785,33.475784],[-86.75894,33.47527],[-86.76008,33.474792],[-86.76089,33.474277],[-86.76137,33.473602],[-86.76122,33.473087],[-86.76122,33.472256],[-86.76137,33.47186],[-86.761604,33.471542],[-86.76265,33.47202],[-86.76365,33.47293],[-86.76403,33.473564],[-86.76346,33.474556],[-86.76327,33.47511],[-86.76374,33.476063],[-86.764786,33.476814],[-86.76612,33.47741],[-86.76792,33.478325],[-86.76836,33.47876],[-86.76935,33.48007],[-86.769775,33.48074],[-86.77101,33.481415],[-86.77235,33.48201],[-86.77329,33.48308],[-86.773056,33.483437],[-86.772964,33.483795],[-86.77311,33.48419],[-86.77244,33.484547],[-86.7722,33.484943],[-86.77206,33.48554],[-86.77168,33.48617],[-86.77111,33.486965],[-86.768974,33.48839],[-86.76736,33.489304],[-86.76664,33.49006],[-86.765884,33.490574],[-86.764786,33.49089],[-86.76336,33.491722],[-86.76193,33.492634],[-86.76112,33.493706],[-86.76122,33.494022],[-86.76184,33.494022],[-86.76246,33.4945],[-86.76246,33.494896],[-86.76208,33.495293],[-86.76146,33.495808],[-86.76065,33.497314],[-86.76046,33.498184],[-86.76032,33.498463],[-86.75813,33.50088],[-86.75724,33.501793],[-86.75653,33.501766],[-86.756775,33.497543]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"Mountain Brook Estates and Country Club Gardens (outside city limits)\",\"holc_id\":\"A1\",\"holc_grade\":\"A\",\"neighborhood_id\":244.0,\"area_description_data\":{\"5\":\"Both sales and rental prices in 1929 were off about 20% from 1925-28 peak. Location of property within this area will justify policy of holding for its value.\",\"6\":\"Mountain Brook Estates and County Club Gardens (outside city limits) Ample 1\",\"31\":\"40%\",\"32\":\"10%\",\"33\":\"50%\",\"1c\":\"Residents of area required to pay moderate school tuition if students attend city schools (usually pertains only to high school students). Bus transportation available only to north edge.\",\"1b\":\"Highly restricted. Near two high grade country clubs. in valley south of Red Mountain; hence protected from industrial ordors and noises of city. Outside of city limits; therefore subject to lower taxation. One of the community's newest localities. Large lots; well landscaped. Layout of community adds charm and appeal. Community stores. Schools.\",\"3n\":\"50-100 62 75-125 1936 No rentals N/A N/A\",\"1d\":\"35%\",\"3d\":\"Excellent Excellent Excellent\",\"1e\":\"Up\",\"2b\":\"5000-50,000 (with few $100,000)\",\"3h\":\"15000-35000 None 35000-90000\",\"3p\":\"No rentals Good to fair Poor\",\"2f\":\"None\",\"1a\":\"Hilly and roling to undulating\",\"3o\":\"N/A No rentals 100-150 N/A 75 60-125 1938\",\"3q\":\"No rentals Poor Good to fair\",\"3m\":\"No rentals 90-150 No rentals\",\"3a\":\"2 story singles 2 story singles 2 story singles\",\"3j\":\"72% 25000-60000 1938 10000-25000 11000-25000 69 N/A\",\"2g\":\"N/A N/A Moderately\",\"3l\":\"Fair Good Poor\",\"2d\":\"None N/A\",\"3k\":\"Good Poor Good\",\"3f\":\"85% 100% 90%\",\"4a\":\"Ample\",\"3e\":\"95% 99% 98%\",\"3b\":\"Brick Veneer Brick Veneer Brick Veneer\",\"3i\":\"11000-25000 N/A 72% 69 1936 10000-25000 25000-60000\",\"2a\":\"Executives, business men, and retired professional men\",\"4b\":\"Ample\",\"3g\":\"65 ($10,000-25,000) None None\",\"3c\":\"2 10 10\",\"2e\":\"None\",\"2c\":\"None N/A\"}}},{\"geometry\":{\"coordinates\":[[[[-86.75867,33.50933],[-86.760925,33.508064],[-86.75689,33.50363],[-86.75742,33.503384],[-86.75804,33.50326],[-86.75947,33.503708],[-86.76119,33.501625],[-86.762505,33.501156],[-86.76339,33.50076],[-86.76387,33.500263],[-86.765175,33.498825],[-86.765564,33.498257],[-86.76624,33.49786],[-86.76723,33.49776],[-86.76842,33.49786],[-86.76916,33.497536],[-86.76975,33.497066],[-86.77129,33.496796],[-86.773346,33.49543],[-86.7743,33.49538],[-86.77421,33.493797],[-86.77846,33.49392],[-86.77843,33.49367],[-86.78122,33.4937],[-86.781364,33.496025],[-86.79392,33.490402],[-86.79455,33.49253],[-86.79458,33.493076],[-86.79413,33.49377],[-86.79278,33.494736],[-86.79055,33.494267],[-86.785645,33.496918],[-86.78555,33.497387],[-86.78478,33.49786],[-86.783775,33.49823],[-86.78276,33.49833],[-86.782974,33.49969],[-86.782585,33.500336],[-86.78196,33.50071],[-86.78125,33.50093],[-86.780594,33.50103],[-86.779884,33.500782],[-86.77596,33.502766],[-86.77543,33.503113],[-86.773735,33.505318],[-86.77314,33.505737],[-86.77186,33.50653],[-86.77052,33.507423],[-86.76916,33.50787],[-86.76844,33.50841],[-86.76809,33.508884],[-86.76699,33.50963],[-86.76619,33.50985],[-86.76488,33.51027],[-86.76452,33.511013],[-86.76408,33.51156],[-86.763336,33.51183],[-86.76188,33.511955],[-86.761345,33.5124],[-86.75867,33.50933]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"Redmont Park, Rockridge Park, Warwick Manor, and southern portion of Milner Heights\",\"holc_id\":\"A2\",\"holc_grade\":\"A\",\"neighborhood_id\":193.0,\"area_description_data\":{\"5\":\"Both sales and rental prices in 1929 were off about 20% from 1926-28 peak. Location of property within this area will justify policy of holding for its value.\",\"6\":\"Redmont Park, Rockridge Park, Warwick Manor, and southern portion of Milner Heights A 2\",\"31\":\"83\",\"32\":\"8\",\"33\":\"4\",\"3n\":\"1936 No rentals 55 No rentals N/A 50-100 N/A\",\"3j\":\"N/A 10000-30000 61 10000-27500 1938 No sales N/A\",\"3g\":\"None None 18 (1000-3000)\",\"1d\":\"65%\",\"3m\":\"No rentals 25-200 No rentals\",\"3c\":\"2 15 15\",\"3l\":\"None Fair Fair\",\"3k\":\"Fair FPoor Fair\",\"4a\":\"Ample\",\"1c\":\"Dead-end railroad siding bisects area, but is infrequently used. Residents of south half of area required to pay school truition if students attend city schools (usually pertains to high school students)\",\"1b\":\"Highly restricted. About one-half of area is outsid of city and other half is on crest of mountain, overlooking city (about 200 feet above city). Large lots; well landscaped. Layout adds charm and appeal. Protected by mountain from industrial odors and noises of city.\",\"2e\":\"None\",\"2d\":\"N/a None\",\"2f\":\"None\",\"3q\":\"No rentals No rentals Good to fair\",\"1e\":\"Up\",\"4b\":\"Ample\",\"3b\":\"Brick Veneer Brick Veneer Brick Veneer and solid stone\",\"3h\":\"15000-50000 30000-150000 None\",\"3p\":\"No rentals Good to fair No rentals\",\"1a\":\"Sloping upward from valley to crest of Red Mountain\",\"3o\":\"No rentals N/A N/A 60-125 No rentals 67 1938\",\"2c\":\"None N/A\",\"3e\":\"100 98 100\",\"3f\":\"100 100 90\",\"2b\":\"5000-35000 (with few up too 100M)\",\"3i\":\"N/A 10000-30000 10000-37500 30000-100000 1936 61 N/A\",\"2a\":\"Executives, business men and retired professional men\",\"3d\":\"Excellent Good Good\",\"3a\":\"2 story singles 2 story singles 1-2 story singles\",\"2g\":\"N/A N/A Moderately\"}}},{\"geometry\":{\"coordinates\":[[[[-86.756775,33.497543],[-86.75196,33.50135],[-86.74605,33.500904],[-86.7458,33.50016],[-86.7459,33.49704],[-86.74605,33.49662],[-86.75207,33.492104],[-86.752174,33.491745],[-86.75225,33.490437],[-86.75225,33.48779],[-86.75289,33.4869],[-86.75396,33.48764],[-86.75449,33.48883],[-86.76227,33.488922],[-86.76202,33.491924],[-86.75692,33.49579],[-86.756775,33.497543]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"Colonial Hills, Pine Crest (outside city limits)\",\"holc_id\":\"A3\",\"holc_grade\":\"A\",\"neighborhood_id\":206.0,\"area_description_data\":{\"5\":\"Generally speaking, houses are not built in this area for rental purpose. Both sales and rental prices in 1929 were off about 20% from 1926-28 peak. Location of property within this area will justify policy of holdings for its value. This is a difficult area to classify and might almost be considered a \\\"high blue\\\".\",\"6\":\"A Colonial Hills, Pince Crest (outside city limits) 3\",\"31\":\"75\",\"32\":\"25\",\"33\":\"N/A\",\"2c\":\"N/A None\",\"4b\":\"Ample on conservative basis\",\"2g\":\"Moderately N/A N/A\",\"3e\":\"98 90 N/A\",\"3h\":\"N/A None 8500-15000\",\"3i\":\"66 5000-9000 N/A 7000-10000 N/A 1936 N/A\",\"1d\":\"20\",\"2f\":\"None\",\"3j\":\"1938 N/A N/A N/A 7000-10000 5000-9000 66\",\"3k\":\"N/A Good Fair\",\"3c\":\"2 10 N/A\",\"3g\":\"None 10 ($7000-10000) N/A\",\"3f\":\"90 N/A 100\",\"3q\":\"Good to fair N/A No rentals\",\"1e\":\"Up\",\"2e\":\"None\",\"3a\":\"N/A 1 story singles 1-2 story singles\",\"2a\":\"Executives, business men and few clerical workers\",\"3l\":\"Good Fair N/A\",\"2d\":\"None N/A\",\"3b\":\"Brick veneer N/A Brick veneer\",\"4a\":\"Ample on conservative basis\",\"1a\":\"Slightly rolling land in Shades Valley\",\"3m\":\"No rentals N/A 75-100\",\"3d\":\"N/A Excellent Good \",\"1b\":\"Well landscaped lots. Lay-out adds charm and appeal. Located betwen two high grade country clubs. Outside of city, hence subject to lower taxation. Convenient to grade schools. Highly restricted. Protected by mountains from industrial odors and noises of city.\",\"1c\":\"Residents must pay school truition if studetns attend high school in city.\",\"3p\":\"N/A Good to fair No rentals\",\"2b\":\"2500-10000\",\"3n\":\"No rentals N/A N/A N/A 1936 40-55 54\",\"3o\":\"No rentals 50-65 66 N/A N/A 1938 N/A\"}}},{\"geometry\":{\"coordinates\":[[[[-86.75689,33.50363],[-86.760925,33.508064],[-86.75867,33.50933],[-86.75504,33.504993],[-86.75689,33.50363]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"Better part of Crestline Heights (outside of city limits)\",\"holc_id\":\"B15\",\"holc_grade\":\"B\",\"neighborhood_id\":194.0,\"area_description_data\":{\"5\":\"About 20 houses in area\",\"6\":\"15 Better part of Crestline Heights (outside of city limits) B\",\"31\":\"90\",\"32\":\"N/A\",\"33\":\"N/A\",\"3l\":\"N/A Fair N/A\",\"3f\":\"N/A 85 N/A\",\"3q\":\"N/A Good N/A\",\"2d\":\"None N/A\",\"3p\":\"N/A Good N/A\",\"3h\":\"N/A N/A 5000-8000\",\"2c\":\"None N/A\",\"2b\":\"1200-3000\",\"1c\":\"Unrestricted. No streets paved. Septic tanks.\",\"3c\":\"N/A 10-15 N/A\",\"1b\":\"Protected by Red Mountain from smoke and dirt of industry. Convenient to bus transportation, country club, and community stores.\",\"3o\":\"1938 N/A N/A N/A N/A N/A 25-45\",\"3i\":\"N/A N/A 3500-6500 1936 N/A 7 N/A\",\"2g\":\"N/A Slowly N/A\",\"1a\":\"Level to steeply sloping land.\",\"3b\":\"N/A Frame N/A\",\"2a\":\"Principally clerical workers\",\"3n\":\"N/A 1936 N/A N/A N/A 25-45 N/A\",\"3a\":\"N/A N/A 1 story singles\",\"4a\":\"Limited\",\"3m\":\"N/A No rentals N/A\",\"3k\":\"N/A Fair N/A\",\"4b\":\"Limited\",\"3d\":\"N/A Fair N/A\",\"2f\":\"None\",\"3g\":\"None N/A N/A\",\"3e\":\"N/A N/A 100\",\"3j\":\"N/A 3500-6500 7 N/A N/A N/A 1938\",\"1d\":\"10\",\"1e\":\"Slowly upward\",\"2e\":\"Jewish families/Slow\"}}},{\"geometry\":{\"coordinates\":[[[[-86.75196,33.50135],[-86.756775,33.497543],[-86.75653,33.501766],[-86.75724,33.501793],[-86.75804,33.50326],[-86.75742,33.503384],[-86.75689,33.50363],[-86.754105,33.50144],[-86.75196,33.50135]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"White dairy section (outside city limits)\",\"holc_id\":\"B16\",\"holc_grade\":\"B\",\"neighborhood_id\":205.0,\"area_description_data\":{\"5\":\"About 12 houses in this area. This is low B grade are with future trend questionable although area is well located.\",\"6\":\"16 White dairy section (outside city limits) B\",\"31\":\"100\",\"32\":\"N/A\",\"33\":\"N/A\",\"1d\":\"2\",\"1e\":\"Static\",\"3f\":\"50 N/A N/A\",\"3o\":\"N/A N/A 10-17.50 1938 N/A N/A N/A\",\"3n\":\"N/A N/A N/A 1936 N/A 10-17.50 N/A\",\"2g\":\"N/A N/A Yes\",\"3c\":\"1-40 N/A N/A\",\"2c\":\"None N/A\",\"2a\":\"N/A\",\"3m\":\"N/A N/A No rentals\",\"3d\":\"Fair N/A N/A\",\"3e\":\"N/A 100 N/A\",\"2b\":\"N/A\",\"2f\":\"None\",\"3l\":\"None N/A N/A\",\"3k\":\"N/A Poor N/A\",\"3j\":\"N/A N/A 1938 N/A N/A 1000-2750 N/A\",\"4b\":\"Limited\",\"3i\":\"N/A N/A 1936 N/A N/A N/A 1000-2750\",\"3a\":\"N/A N/A 1 story singles\",\"1a\":\"Level to rolling\",\"3h\":\"N/A N/A N/A\",\"1b\":\"Good bus transportation. Near county schools and church and stores.\",\"2e\":\"None\",\"1c\":\"Unrestricted. Septic tanks. Lack of public utilities. Frantically all area is open undeveloped land. \",\"4a\":\"Limited\",\"3q\":\"N/A Fair N/A\",\"3g\":\"None N/A N/A\",\"3p\":\"N/A N/A Fair\",\"3b\":\"N/A N/A Frame\",\"2d\":\"None N/A\"}}},{\"geometry\":{\"coordinates\":[[[[-86.75696,33.519325],[-86.75124,33.51317],[-86.75228,33.512623],[-86.75308,33.512253],[-86.75407,33.511658],[-86.75558,33.510296],[-86.7562,33.50997],[-86.757454,33.509525],[-86.75867,33.50933],[-86.761345,33.5124],[-86.76188,33.511955],[-86.763336,33.51183],[-86.76408,33.51156],[-86.76452,33.511013],[-86.76488,33.51027],[-86.76619,33.50985],[-86.76699,33.50963],[-86.76809,33.508884],[-86.76844,33.50841],[-86.769455,33.509182],[-86.76922,33.512177],[-86.77435,33.512352],[-86.77424,33.514854],[-86.7754,33.514927],[-86.77935,33.513294],[-86.78017,33.51433],[-86.77902,33.51484],[-86.7791,33.514988],[-86.77514,33.5168],[-86.77507,33.51597],[-86.772,33.51597],[-86.76983,33.516502],[-86.76983,33.517513],[-86.76872,33.517574],[-86.76919,33.52123],[-86.76576,33.52334],[-86.76391,33.52334],[-86.76387,33.522865],[-86.76145,33.519268],[-86.75696,33.519325]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"Forrest Park\",\"holc_id\":\"B2\",\"holc_grade\":\"B\",\"neighborhood_id\":191.0,\"area_description_data\":{\"5\":\"Both sales and rental prices in 1929 were off about 20% from the 1926-28 peak. Location of property in this area will justify policy of holding for its value.\",\"6\":\"Forrest Park B 2\",\"31\":\"49\",\"32\":\"49\",\"33\":\"2\",\"1c\":\"Practicaly none\",\"3q\":\"Good  No rentals Good \",\"3k\":\"Fair Fair Fair\",\"3o\":\"60-100 No rentals 80 80 1938 40-60 N/A\",\"3g\":\"None None 2 ($8500-15000)\",\"1e\":\"Static\",\"3p\":\"Good  No rentals Good \",\"3j\":\"8500-15000 8500-17500 56 75 1938 5500-9500 N/A\",\"3e\":\"98 100 98\",\"3a\":\"1 story singles 1 story singles 50%, 2 story singles 50% 2 story singles\",\"3h\":\"None 12500-40000 7500-12500\",\"2g\":\"N/A N/A Slowly\",\"3i\":\"75 56 8500-17500 N/A 1936 5500-9500 None\",\"2f\":\"None\",\"3m\":\"No rentals 50-75 75-125\",\"2e\":\"Jewish families (slow)\",\"3f\":\"50 55 100\",\"2d\":\"N/A None\",\"2c\":\"N/A None\",\"3b\":\"Brick venner Brick venner Brick venner\",\"2b\":\"3000-15000 (with a few over)\",\"3n\":\"No rentals 50-75 N/A 64 69 35-50 1936\",\"2a\":\"Executives, business men and soon clerical workers\",\"1d\":\"50% in older portion\",\"4a\":\"Ample\",\"3c\":\"2 16 16\",\"3d\":\"Good to fair Good to fair Good\",\"1a\":\"Rolling\",\"1b\":\"High restricted. Near municpal golf course. Convenient to city. Good schools, churches, and transportation (7 cents bus fare). Elevated land.\",\"4b\":\"Ample\",\"3l\":\"Poor Fair Fair\"}}},{\"geometry\":{\"coordinates\":[[[[-86.779594,33.5124],[-86.77813,33.51126],[-86.77816,33.508587],[-86.777176,33.508957],[-86.77653,33.50804],[-86.77457,33.50777],[-86.774506,33.508858],[-86.769455,33.509182],[-86.76844,33.50841],[-86.76916,33.50787],[-86.77052,33.507423],[-86.77186,33.50653],[-86.77314,33.505737],[-86.773735,33.505318],[-86.77543,33.503113],[-86.77596,33.502766],[-86.779884,33.500782],[-86.780594,33.50103],[-86.78125,33.50093],[-86.78196,33.50071],[-86.782585,33.500336],[-86.782974,33.49969],[-86.78276,33.49833],[-86.783775,33.49823],[-86.78478,33.49786],[-86.78555,33.497387],[-86.785645,33.496918],[-86.79055,33.494267],[-86.79278,33.494736],[-86.79248,33.49543],[-86.79251,33.496002],[-86.79233,33.496273],[-86.789536,33.496323],[-86.789474,33.497585],[-86.79055,33.501377],[-86.7898,33.503136],[-86.78737,33.50264],[-86.78713,33.50212],[-86.78666,33.501923],[-86.78627,33.50207],[-86.78603,33.50259],[-86.786,33.50341],[-86.78558,33.504078],[-86.78464,33.504696],[-86.78368,33.504425],[-86.78306,33.5044],[-86.78294,33.504547],[-86.78294,33.505787],[-86.78274,33.506107],[-86.7808,33.50673],[-86.780716,33.50871],[-86.78295,33.51185],[-86.78203,33.5123],[-86.78089,33.512535],[-86.779594,33.5124]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"Highland Avenue, Milner Heights, and Milner Crescent\",\"holc_id\":\"B5\",\"holc_grade\":\"B\",\"neighborhood_id\":192.0,\"area_description_data\":{\"5\":\"*These rental prices do not include apartment rentals. there are a few large apartment buildings in the area which have units renting from $35 to $150 per month. Both sales and rental prices in 1929 were off about 20% from 1926-28 peak. Even though this is now a desirable area, the chance are that due to age of properties, sales prices of properties in the area will not increase from residential standpoint, although there is some possibility of price increase from business or semi-business standpoint. This is a difficult area to classify and might be considered a \\\"high yellow.\\\" Highland Ave. section was formerly \\\"bon ton\\\" section of the city.\",\"6\":\"5 Highland Avenue, Milner Heights, and Milner Crescent B\",\"31\":\"70\",\"32\":\"25\",\"33\":\"5\",\"2g\":\"N/A Yes N/A\",\"3m\":\"50-90* N/A 40-65\",\"1d\":\"90\",\"4b\":\"Ample on restricted basis\",\"1e\":\"Static for next few years, then slowly downward\",\"3g\":\"None None N/A\",\"3d\":\"Fair to poor N/A Fair to poor\",\"2f\":\"None\",\"2c\":\"None N/A\",\"3e\":\"N/A 90 90\",\"2e\":\"Lower income groups and foreigners\",\"3j\":\"1938 55 68 N/A 5000-12500 4000-6500 N/A\",\"3a\":\"Apartments 2 story singles 1 story singles\",\"3b\":\"N/A Frame Frame\",\"2b\":\"2000-15000 (with some in excess)\",\"2d\":\"N/a None\",\"3q\":\"N/A Fair Fair\",\"3p\":\"N/A Fair Fair\",\"3i\":\"1936 4000-6500 N/A 55 68 N/A 5000-12500\",\"3k\":\"N/A Fair to poor Fair to poor\",\"3c\":\"25 N/A 25\",\"1a\":\"Hilly to rolling\",\"3h\":\"N/A 8500-25000 5500-10500\",\"3l\":\"Fair to poor Fair to poor N/A\",\"3o\":\"N/A 1938 32.50-52.50 40-75* N/A 82 81\",\"4a\":\"Ample on restricted basis\",\"3n\":\"27.50-42.50 N/A N/A 66 1936 30-65* 67\",\"1b\":\"Well landscaped. Beautiful parks. Adjoins golf course. Seven cent bus transportation. Majority zoned and restricted for residetial. School, churches, and business centers. Proximity to center of city.\",\"1c\":\"Age of majority of properties in area. Encroachment of business and apartments. Boarding houses and private schools in area. Fumes and smoke from city adversely effect northwestern portion of area.\",\"3f\":\"N/A 50 50\",\"2a\":\"Executives, business men, and clerical workers\"}}},{\"geometry\":{\"coordinates\":[[[[-86.85266,33.51183],[-86.85278,33.510185],[-86.85435,33.510166],[-86.854996,33.508797],[-86.854706,33.508244],[-86.85713,33.508224],[-86.85706,33.507828],[-86.8658,33.507866],[-86.865776,33.507233],[-86.87566,33.50743],[-86.875595,33.509434],[-86.87438,33.509453],[-86.87272,33.510284],[-86.87191,33.511097],[-86.87134,33.51175],[-86.87065,33.51201],[-86.86998,33.51207],[-86.865944,33.51189],[-86.86592,33.515396],[-86.86089,33.515297],[-86.86084,33.513138],[-86.86015,33.512978],[-86.85937,33.512604],[-86.85868,33.512226],[-86.8534,33.512264],[-86.85266,33.51183]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"Bush Hills, Ridgewood Park, Owenton-Ensley Highlands, Virginia Heights, and Highland Lake Company Surveys\",\"holc_id\":\"B6\",\"holc_grade\":\"B\",\"neighborhood_id\":221.0,\"area_description_data\":{\"5\":\"Both sales and rental prices in 1929 were off from 15% to 20% of the 1926-28 peak, during which peak the area experienced heavy inflation. Location of property within this area justifies the policy of selling rather than holding for possible price increase, and in as much as rental and sales prices in the area are subject to heavy fluctuation since residents of the area are employed principally in nearby industrial plants, whose operations are irregular, the proper time to sell property in this area is when the plants are operating at near capacity.\",\"6\":\"6 B Bush Hills, Ridgewood Park, Owenton-Ensley Highlands, Virginia Heights, and Highland Lake Company Surveys\",\"31\":\"75\",\"32\":\"20\",\"33\":\"5\",\"2b\":\"2000-15000\",\"3o\":\"N/A 75 N/A 1938 42.50-55 72 50-60\",\"3c\":\"10 1 10\",\"3b\":\"Brick veneer Brick veneer Brick veneer\",\"1e\":\"Slightly upward\",\"2f\":\"None\",\"3q\":\"Good N/A Good\",\"3k\":\"Poor Fair Fair to poor\",\"3p\":\"N/A Good Good\",\"1a\":\"Rolling and elevated.\",\"1b\":\"Proximity to schools, churches, and community business centers. Near occupants source of employment, layout of subdivisions adds charm and appeal. Good street car transportation. Main portion of area well restricted and entire area zoned for residential. Near Birmingham Southern college.\",\"3m\":\"60-100 N/a 55-75\",\"3j\":\"7000-12000 N/A 82 6000-9000 1938 7000-15000 65\",\"2g\":\"N/A N/A Slowly\",\"3i\":\"1936 73 N/a N/A 7000-1500 5000-8500 65\",\"2c\":\"N/A None\",\"3h\":\"6500-12500 10000-25000 N/A\",\"3g\":\"None 16 ($7000-12000) None\",\"2d\":\"N/A None\",\"4a\":\"Ample\",\"3l\":\"Fair to poor Poor Poor\",\"4b\":\"Ample\",\"3f\":\"100 70 50\",\"3n\":\"1936 32.50-45 N/A N/A 60 61 40-55\",\"1c\":\"About one and one-half miles from Thomas furnaces, hence, subject to occasional smoke.\",\"3e\":\"99 100 99\",\"2a\":\"Executive, business men and clerical workers\",\"1d\":\"45\",\"2e\":\"None\",\"3a\":\"1 story single 2 story single 1 and 2 story single\",\"3d\":\"Good Good Excellent\"}}},{\"geometry\":{\"coordinates\":[[[[-86.83477,33.498898],[-86.83964,33.496536],[-86.839615,33.50155],[-86.83539,33.502724],[-86.8346,33.501434],[-86.83477,33.498898]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"Arlington\",\"holc_id\":\"B7\",\"holc_grade\":\"B\",\"neighborhood_id\":229.0,\"area_description_data\":{\"5\":\"Both sales and rental prices in 1929 were off about 20% from the 1926-28 peak. Location of property within this area justifies policy of selling rather than holding for possible price increase. Area is surrounded on three sides by vacant property which shades off into D grade negro property.\",\"6\":\"Arlington B 7\",\"31\":\"70\",\"32\":\"30\",\"33\":\"N/A\",\"3e\":\"N/A 95 95\",\"3i\":\"70 N/A 4250-8500 8500-10500 1936 78 N/A\",\"3d\":\"Good Good N/A\",\"4b\":\"Ample on conservative basis\",\"4a\":\"Ample on conservative basis\",\"2g\":\"Yes N/A N/A\",\"3j\":\"N/A 1938 4250-8500 70 N/A 8500-10500 78\",\"1c\":\"Main line of A. G. S. R. B. forms southern boundary of area. Smoke and dirt from city and railroads. Area composed of filled-in land hence subject to dampness in rainy season. Termites in low portion of area.\",\"3k\":\"Fair Fair N/A\",\"3c\":\"15 15 N/A\",\"3l\":\"Fair N/A Fair\",\"3b\":\"N/A Brick veneer Brick veneer\",\"1b\":\"Highly restricted and zoned for residential. Good transportation (seven cent street car). Larger lots. Convenient to schools, churches, and community business centers. Layout of subdivision adds charm and appeal. Close to center of city.\",\"3m\":\"45-75 N/A 65-85\",\"3a\":\"1 story singles 2 story singles N/A\",\"3o\":\"1938 45-60 73 70 N/A N/A 35-50\",\"3n\":\"N/A 57 1936 N/A 30-40 60 35-50\",\"2c\":\"N/A None\",\"2a\":\"Business and professional men\",\"2b\":\"3500-10000\",\"1a\":\"Level to slightly rolling\",\"3p\":\"Good Good N/A\",\"3f\":\"70 60 N/A\",\"1e\":\"Static\",\"3q\":\"Good Good N/A\",\"3g\":\"N/A None None\",\"2e\":\"None\",\"2d\":\"N/A None\",\"3h\":\"6750-11000 N/A 10000-15800\",\"1d\":\"25\",\"2f\":\"None\"}}},{\"geometry\":{\"coordinates\":[[[[-86.80597,33.487404],[-86.81807,33.487423],[-86.818016,33.489803],[-86.818794,33.4908],[-86.819824,33.492123],[-86.81699,33.49337],[-86.81743,33.494164],[-86.8135,33.496025],[-86.8135,33.49785],[-86.81136,33.49767],[-86.81134,33.497395],[-86.80877,33.49745],[-86.808205,33.49664],[-86.79815,33.501793],[-86.79608,33.498425],[-86.795555,33.498726],[-86.79233,33.496273],[-86.79251,33.496002],[-86.79248,33.49543],[-86.79278,33.494736],[-86.79413,33.49377],[-86.79458,33.493076],[-86.79455,33.49253],[-86.80597,33.487404]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"South side (west of 20th St.) Glen Iris, Idlewild, Glenwild\",\"holc_id\":\"C11\",\"holc_grade\":\"C\",\"neighborhood_id\":200.0,\"area_description_data\":{\"5\":\"Practically no sales of 2 story singles have been made in this area in last 8 or 9 years. **Limited to Title II loans in Glen Iris and Glenwild. Both sales and rental prices in 1929 were off about 20% from 1926-28 peak. Location of property within this area justifies policy of selling rather than holding for possible price increase. \",\"6\":\"11 South side (west of 20th St.) Glen Iris, Idlewild, Glenwild C\",\"31\":\"45\",\"32\":\"45\",\"33\":\"10\",\"3i\":\"1936 2500-7000 3500-10000 N/A 73 77 N/A\",\"3e\":\"100 90 90\",\"3l\":\"N/A Fair None\",\"4a\":\"Very limited\",\"3o\":\"78 1938 65 35-65 20-45 27.50-50 78\",\"1a\":\"Level to rolling and mountainous\",\"3f\":\"40 0 25\",\"2a\":\"Business men, clerical workers, and skilled mechanics\",\"2f\":\"None\",\"2e\":\"See 1-c above\",\"1b\":\"Good transportation, parks, playgrounds, schools, churches, and community business centers.\",\"3p\":\"Fair Good Good\",\"1e\":\"Slowly downward\",\"3q\":\"Good Good Fair\",\"1d\":\"90\",\"3j\":\"N/A N/A 77 2500-7000 73 1938 3500-10000\",\"1c\":\"Rather rapid infiltration of lower income and different racial groups. Influx of Jewish families about 12 years ago. Boarding houses in area; hospital also. Encroachment or apartments. Vandalism. \",\"3g\":\"None None None\",\"4b\":\"Very limited\",\"3k\":\"Fair N/A Poor\",\"3d\":\"Fair to poor Good to fair Fair to poor\",\"3c\":\"25 10-25 15\",\"3h\":\"3500-8500 4500-15000 N/A\",\"3m\":\"42.50-75 45-85 25-60\",\"3b\":\"Brick Frame Frame\",\"2d\":\"N/A None\",\"3a\":\"Apartments 1 story singles 2 story singles\",\"3n\":\"63 27.50-55 69 17.50-40 22.50-40 1936 53\",\"2g\":\"N/A Yes N/A\",\"2c\":\"10 Italians, Russians, and Greeks\",\"2b\":\"1000-5000\"}}},{\"geometry\":{\"coordinates\":[[[[-86.86592,33.515396],[-86.865944,33.51189],[-86.86998,33.51207],[-86.87065,33.51201],[-86.87134,33.51175],[-86.87191,33.511097],[-86.87272,33.510284],[-86.87438,33.509453],[-86.875595,33.509434],[-86.87566,33.50743],[-86.865776,33.507233],[-86.8658,33.507866],[-86.85706,33.507828],[-86.85713,33.508224],[-86.854706,33.508244],[-86.854996,33.508797],[-86.85435,33.510166],[-86.85278,33.510185],[-86.85266,33.51183],[-86.84909,33.51173],[-86.846375,33.51175],[-86.84608,33.509212],[-86.84599,33.508522],[-86.849014,33.508324],[-86.849014,33.50559],[-86.86773,33.505608],[-86.8741,33.500237],[-86.87436,33.50002],[-86.87862,33.498234],[-86.87902,33.498505],[-86.88192,33.500454],[-86.87895,33.500492],[-86.87857,33.50392],[-86.88327,33.50402],[-86.88325,33.50218],[-86.88452,33.50214],[-86.885376,33.502934],[-86.88602,33.503983],[-86.88616,33.505367],[-86.88435,33.505726],[-86.880554,33.506042],[-86.87951,33.50773],[-86.87637,33.50975],[-86.87654,33.515656],[-86.86592,33.515396]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"Outlying portions of Ensley Highlands from Ensley Ave. northeast to McLendon Park including Fairview\",\"holc_id\":\"C14\",\"holc_grade\":\"C\",\"neighborhood_id\":220.0,\"area_description_data\":{\"5\":\"Both sales and rental prices in 1929 were off from 15% to 20% of the 1926-28 peak. Location of property within area justifies policy of selling rather than holding for possible price increase.\",\"6\":\"C 14 Outlying portions of Ensley Highlands from Ensley Ave. northeast to McLendon Park including Fairview\",\"31\":\"75\",\"32\":\"20\",\"33\":\"5\",\"3p\":\"N/A Good Good\",\"3o\":\"71 71 N/A 1938 N/A 22.50-40 22.50-40\",\"3j\":\"77 1938 2000-6000 75 4000-7500 N/A N/A\",\"3a\":\"1 story singles 2 story singles Apartment houses\",\"3e\":\"98 98 N/A\",\"3n\":\"71 71 N/A N/A 22.50-40 22.50-40 1936\",\"2e\":\"None\",\"2g\":\"N/A N/A Yes\",\"2f\":\"A few\",\"3k\":\"N/A Poor Good to fair\",\"3m\":\"30-60 30-60 N/a\",\"3l\":\"N/A Very poor Poor\",\"4b\":\"Limited on conservative basis\",\"2d\":\"None N/A\",\"1c\":\"Extreme northeastern sections and central southern section adjacent to negro property. Much jerry-building and poor construction in area. Advanced age of many properties. Encroachment of business and commercial.\",\"3d\":\"N/A Fair Fair\",\"1d\":\"75\",\"1b\":\"Proximity to schools, churches, parks and community business centers. Close to occupants' source of employment. Good transportations.\",\"2c\":\"4 Italians and Greeks\",\"2b\":\"600-7500\",\"3f\":\"40 N/A 40\",\"4a\":\"Limited on conservative basis\",\"3c\":\"N/A 15-25 15-25\",\"1e\":\"Slowly downward\",\"3g\":\"N/A None None\",\"2a\":\"Business & professional men, clerical workers & steel workers\",\"1a\":\"Level to rollling\",\"3h\":\"2500-8500 N/A 6000-10000\",\"3b\":\"N/A Frame Frame\",\"3i\":\"1936 N/A 4000-7500 76 N/a 77 2000-6000\",\"3q\":\"Good N/A Good\"}}},{\"geometry\":{\"coordinates\":[[[[-86.88452,33.50214],[-86.887955,33.502163],[-86.887955,33.500298],[-86.88742,33.50032],[-86.887436,33.498676],[-86.88218,33.49869],[-86.88209,33.50044],[-86.88192,33.500454],[-86.87902,33.498505],[-86.88047,33.49707],[-86.8799,33.496624],[-86.87808,33.494186],[-86.88104,33.492226],[-86.878975,33.49029],[-86.87955,33.489758],[-86.878975,33.489014],[-86.88311,33.48574],[-86.8829,33.482113],[-86.88518,33.482143],[-86.88525,33.478664],[-86.89095,33.478783],[-86.89081,33.4814],[-86.89605,33.48131],[-86.896065,33.482788],[-86.89609,33.486633],[-86.89195,33.486458],[-86.88974,33.488506],[-86.88981,33.489517],[-86.88853,33.48958],[-86.88842,33.490204],[-86.88753,33.49023],[-86.88753,33.492283],[-86.895996,33.492477],[-86.897865,33.49252],[-86.897865,33.49353],[-86.90033,33.4949],[-86.90093,33.493683],[-86.902145,33.493652],[-86.90229,33.49389],[-86.90109,33.49551],[-86.90034,33.49684],[-86.899254,33.497826],[-86.89807,33.498554],[-86.89657,33.499207],[-86.89444,33.500854],[-86.89159,33.502834],[-86.88616,33.505367],[-86.88602,33.503983],[-86.885376,33.502934],[-86.88452,33.50214]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"Birmingham - Ensley survey and Central Park south of Warrior Rd.\",\"holc_id\":\"C15\",\"holc_grade\":\"C\",\"neighborhood_id\":225.0,\"area_description_data\":{\"5\":\"Both sales and rental prices in 1929 were off from 15% to 20% of the 1926-28 peak. Locations of property within area justifies policy of selling rather than holding for possible price increase.\",\"6\":\"Birmingham-Ensley survey and Central Park south of Warrior Rd. C 15\",\"31\":\"90\",\"32\":\"8\",\"33\":\"2\",\"4a\":\"Limited on conservative basis\",\"1c\":\"Number of poorly constructed houses scattered throughout area. Lack of paving in portion of area. Smoke and dirt from nearby industrial plants.\",\"1d\":\"60\",\"2g\":\"N/A Slowly N/A\",\"2f\":\"A few\",\"1e\":\"Static\",\"2e\":\"None\",\"1a\":\"Ranges from flat and level to rolling and elevated.\",\"3m\":\"N/A 20-45 30-45\",\"3a\":\"1 story singles 2 story singles Apartments\",\"2d\":\"N/A None\",\"2c\":\"2 Italians and Greeks\",\"3b\":\"Frame Frame N/A\",\"3c\":\"15 N/A 15\",\"3d\":\"Fair N/A Fair\",\"3e\":\"98 98 N/A\",\"2a\":\"Business men, clerical workers and steel workers\",\"3f\":\"50 N/A 50\",\"3o\":\"N/A 78 N/A 78 23.50-35 22.50-25 1938\",\"3g\":\"None N/A None\",\"4b\":\"Limited on conservative basis\",\"3h\":\"N/A 2500-6000 No sales\",\"3i\":\"1500-4500 N/A 1936 N/A No sales 68 N/A\",\"2b\":\"900-4000\",\"3q\":\"Good Good N/A\",\"3p\":\"N/A Good Good\",\"3j\":\"No sales N/A 1500-4500 N/A 1938 N/A 68\",\"3l\":\"None Fair N/A\",\"1b\":\"Clear to occupants source of employment, schools, churches, parks, playgrounds, and community business centers. Street car and bus transportation.\",\"3n\":\"17.50-30 68 N/A 62 N/A 1936 17.50-30\",\"3k\":\"Poor N/A Good to fair\"}}},{\"geometry\":{\"coordinates\":[[[[-86.83539,33.502724],[-86.839615,33.50155],[-86.83964,33.496536],[-86.842606,33.49503],[-86.848595,33.49146],[-86.84919,33.491104],[-86.85105,33.493263],[-86.8545,33.491123],[-86.855736,33.49255],[-86.861084,33.489517],[-86.86258,33.491364],[-86.865715,33.490868],[-86.86577,33.486584],[-86.870186,33.486546],[-86.870094,33.491142],[-86.867805,33.491123],[-86.86777,33.49279],[-86.86535,33.49263],[-86.853775,33.499966],[-86.84846,33.500042],[-86.843704,33.504066],[-86.84033,33.504326],[-86.83947,33.503372],[-86.83603,33.504387],[-86.83539,33.502724]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"West End and Oakwood Place\",\"holc_id\":\"C2\",\"holc_grade\":\"C\",\"neighborhood_id\":230.0,\"area_description_data\":{\"5\":\"Both sales and rental prices in 1929 were off about 20% from the 1926-28 peak. Location of property within this area justifies policy of selling rather than holding for possible price increase. West End is one of the city's oldest sections 95% of houses in West End are old antiquated whereas in Oakwood Place 90% of the houses are fairly modern.\",\"6\":\"2 C West End and Oakwood Place\",\"31\":\"80\",\"32\":\"15\",\"33\":\"5\",\"3f\":\"N/A 35 40\",\"2f\":\"None\",\"1c\":\"Encroachment of business and apartments. Restrictions expiring. Heavy traffic, smoke and noise, railroads on each side of area. Hospital in area. Much of area filled-in land, hence damp during rainy season. Termites in low portions. Slow infiltration of lower income groups. Some vandalism.\",\"2g\":\"N/A N/A Yes\",\"3m\":\"N/A 35-65 35-55\",\"3a\":\"1 story singles Apartments 2 story singles\",\"3k\":\"N/A Poor Fair\",\"1d\":\"85\",\"2b\":\"1500-4000\",\"3q\":\"Good to fair N/A Good to fair\",\"3o\":\"1938 25-40 72 30-50 82 N/A N/A\",\"1e\":\"Static\",\"1a\":\"Low flat land.\",\"3e\":\"N/A 90 90\",\"3b\":\"N/A Frame Frame\",\"2e\":\"See 1-c above\",\"3l\":\"Fair Practically none N/A\",\"3g\":\"None N/A None\",\"4a\":\"Very limited\",\"3i\":\"1936 N/A N/A 62 3000-5000 59 2000-5000\",\"3p\":\"Good Good N/A\",\"2a\":\"Some business men, clerical workers, and skilled mechanics\",\"4b\":\"Very limited\",\"3h\":\"4000-7500 5000-8000 N/A\",\"3c\":\"N/A 38 35\",\"3n\":\"N/A N/A 17.50-35 25-40 1936 57 57\",\"1b\":\"Proximity to center of city, schools, churches, parks, and community business centers. Good street car transportation.\",\"2d\":\"N/A None\",\"3j\":\"3000-5000 N/A 62 1938 2000-5000 59 N/A\",\"3d\":\"Fair to poor Fair to poor N/A\",\"2c\":\"Italians, Russians, and Greeks 1\"}}},{\"geometry\":{\"coordinates\":[[[[-86.75689,33.50363],[-86.75504,33.504993],[-86.75867,33.50933],[-86.757454,33.509525],[-86.7562,33.50997],[-86.75558,33.510296],[-86.75407,33.511658],[-86.75308,33.512253],[-86.75228,33.512623],[-86.75124,33.51317],[-86.75057,33.512497],[-86.73785,33.52064],[-86.7361,33.521652],[-86.73386,33.52159],[-86.72983,33.516243],[-86.73082,33.515022],[-86.73535,33.511307],[-86.73553,33.510506],[-86.7381,33.508602],[-86.73877,33.50869],[-86.74009,33.50792],[-86.740486,33.506847],[-86.74106,33.50655],[-86.74565,33.505035],[-86.74683,33.50453],[-86.748505,33.50328],[-86.74961,33.50245],[-86.75196,33.50135],[-86.754105,33.50144],[-86.75689,33.50363]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"Crestline Heights, Overlook, McElwain (all outside of city limits)\",\"holc_id\":\"C20\",\"holc_grade\":\"C\",\"neighborhood_id\":204.0,\"area_description_data\":{\"5\":\"About 50 houses in this area.\",\"6\":\"20 Crestline Heights, Overlook, McElwain (all outside of city limits) C\",\"31\":\"96\",\"32\":\"2\",\"33\":\"2\",\"2d\":\"None N/A\",\"2e\":\"None\",\"3g\":\"N/A None 5 ($2500-3500)\",\"3n\":\"N/A N/A No rentals N/A 62 15-22.50 1936\",\"3b\":\"Frame Frame Frame\",\"3i\":\"N/A N/A N/A No sales 1936 1750-2750 74\",\"2c\":\"None N/A\",\"3c\":\"7-15 1 N/A\",\"1a\":\"Level to rolling.\",\"3j\":\"N/A 1938 1750-2750 74 N/A N/A No sales\",\"2b\":\"300-3000\",\"3q\":\"N/A Fair N/A\",\"2a\":\"Executives, clerical workers skilled mechanics & dairy men\",\"3d\":\"N/A Excellent Good to fair\",\"3m\":\"25-35 No rentals N/A\",\"3p\":\"N/A N/A Fair\",\"1b\":\"Away from smoke and dirt of city\",\"3o\":\"1938 No rentals N/A 62 N/A N/A 15-22.50\",\"3e\":\"100 95 N/A\",\"2g\":\"Slowly N/A N/A\",\"1c\":\"Unrestricted. Limited water supply. Distance from transportation, stores, schools and churches. No sanitary sewer, no gas and less than 2% of streets paved.\",\"1d\":\"1\",\"3k\":\"Fair Fair N/A\",\"3h\":\"No sales N/A 2500-3750\",\"3l\":\"N/A Fair Fair\",\"4b\":\"Limited\",\"3f\":\"N/A 50 100\",\"3a\":\"1 story singles 1 story singles 2 story singles\",\"2f\":\"A few\",\"4a\":\"Limited\",\"1e\":\"Slight upward tendency\"}}},{\"geometry\":{\"coordinates\":[[[[-86.88192,33.500454],[-86.88209,33.50044],[-86.88218,33.49869],[-86.887436,33.498676],[-86.88742,33.50032],[-86.887955,33.500298],[-86.887955,33.502163],[-86.88452,33.50214],[-86.88325,33.50218],[-86.88327,33.50402],[-86.87857,33.50392],[-86.87895,33.500492],[-86.88192,33.500454]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"Better portion of Ensley Highlands\",\"holc_id\":\"C4\",\"holc_grade\":\"C\",\"neighborhood_id\":223.0,\"area_description_data\":{\"5\":\"Both sales and rental prices were, in 1929, off from 15% to 20% of the 1926-28 peak. Location of property within area justifies policy of selling rather than holding for possible price increase. There are a few apartment houses in this area which are all well occupied, the majoirty being four unit apartments. \",\"6\":\"Better portion of Ensley Highlands C 4\",\"31\":\"75\",\"32\":\"25\",\"33\":\"N/A\",\"3k\":\"N/A Fair Fair\",\"3l\":\"Poor Poor N/A\",\"3m\":\"50-70 N/A 45-65\",\"3d\":\"Good N/A Good\",\"3c\":\"12 12 N/A\",\"3n\":\"1936 75 N/A N/A 67 32.50-50 35-45\",\"3b\":\"Frame N/a Frame\",\"3o\":\"1938 N/A 75 N/A 32.50-50 67 35-45\",\"2g\":\"N/A N/A Yes\",\"3a\":\"1 story singles  N/A 2 story singles\",\"3p\":\"Good Good N/A\",\"2f\":\"None\",\"3e\":\"N/A 98 98\",\"3q\":\"N/A Good Good\",\"1a\":\"Elevated and rolling\",\"1b\":\"Zoned for residential. Proximity to schools, churches, parks, and community business centers. Close to occupants source of employment. Street car transportation.\",\"1c\":\"Age of some of the properties in the area. \",\"4a\":\"Limited on conservative basis\",\"2d\":\"None N/A\",\"4b\":\"Limited on conservative basis\",\"1d\":\"70\",\"1e\":\"Static\",\"2a\":\"Business and professional men, clerical and steel workers\",\"2b\":\"1800-7500\",\"2e\":\"None\",\"2c\":\"N/A None\",\"3g\":\"N/A None None\",\"3h\":\"N/A 7500-15000 6000-10000\",\"3i\":\"N/A 4500-7500 1936 75 6000-10000 74 N/A\",\"3j\":\"4500-7500 74 N/A 1938 N/A 75 6000-10000\",\"3f\":\"60 60 N/A\"}}},{\"geometry\":{\"coordinates\":[[[[-86.78295,33.51185],[-86.780716,33.50871],[-86.7808,33.50673],[-86.78274,33.506107],[-86.78294,33.505787],[-86.78294,33.504547],[-86.78306,33.5044],[-86.78368,33.504425],[-86.78464,33.504696],[-86.78558,33.504078],[-86.786,33.50341],[-86.78603,33.50259],[-86.78627,33.50207],[-86.78666,33.501923],[-86.78713,33.50212],[-86.78737,33.50264],[-86.7898,33.503136],[-86.79055,33.501377],[-86.789474,33.497585],[-86.789536,33.496323],[-86.79233,33.496273],[-86.795555,33.498726],[-86.79312,33.498947],[-86.79532,33.50212],[-86.793686,33.504574],[-86.7903,33.506157],[-86.79066,33.5069],[-86.790474,33.50834],[-86.78923,33.508835],[-86.78988,33.50985],[-86.78712,33.51115],[-86.78507,33.511982],[-86.78422,33.511227],[-86.78295,33.51185]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"Highland Avenue, east of Twentieth Street\",\"holc_id\":\"C9\",\"holc_grade\":\"C\",\"neighborhood_id\":196.0,\"area_description_data\":{\"5\":\"Both sales and rental prices in 1929 were off about 20% from 1926-28 peak. This is one of the city's oldest sections and contains some of the largest and most expensive apartments in the city. Location of property within this area justifies policy of selling rather than holding for possible price increase.\",\"6\":\"9 Highland Avenue, east of Twentieth Street C\",\"31\":\"60\",\"32\":\"25\",\"33\":\"15\",\"3b\":\"Brick and hollow tile Frame Frame\",\"2e\":\"See 10c above\",\"2f\":\"None\",\"1a\":\"Rolling\",\"3a\":\"Apartments 1 story singels 2 story singles\",\"2a\":\"Mixture business men, professional men and clerical workers\",\"2g\":\"Yes N/A N/A\",\"4a\":\"Practically none\",\"1e\":\"Downward\",\"1d\":\"85\",\"3h\":\"4500-7000 N/A 4500-9000\",\"2b\":\"1500-15000\",\"3g\":\"None None None\",\"3n\":\"58 50 54 17.50-35 1936 25-100 26-45\",\"3f\":\"30 25 0\",\"3l\":\"Poor N/A Fair\",\"3i\":\"81 N/A N/A 1936 3500-5000 75 3750-7000\",\"3e\":\"90 90 90\",\"4b\":\"Practically none\",\"3j\":\"N/A N/A 75 3500-5000 3750-7000 1938 81\",\"2c\":\"Italians, Russians, and Greeks 2\",\"3o\":\"69 35-55 25-40 1938 67 75 35-125\",\"3p\":\"Good Good Good to fair\",\"3k\":\"Fair N/A Fair\",\"3d\":\"Good to fair Fair to poor Fair to poor\",\"3q\":\"Good Good to fair Good\",\"2d\":\"N/A None\",\"3m\":\"50-200 45-75 35-60\",\"3c\":\"25 10 25\",\"1b\":\"Good transportation. Parks, playgrounds, schools, churches, and community business centers. Near municipal gold course. Proximity to center of city.\",\"1c\":\"Encroachment of apartments and business. Largest hospital in city in area. Smoke nuisance from railroads and city. Heavy traffic. Slow infiltration of both lower income and different racial groups. Advanced age of properties. Boarding houses and private schools in area. Vandalism.\"}}},{\"geometry\":{\"coordinates\":[[[[-86.89975,33.508892],[-86.9069,33.499317],[-86.9036,33.497353],[-86.90305,33.497353],[-86.902596,33.497192],[-86.90202,33.49662],[-86.90109,33.49551],[-86.90229,33.49389],[-86.90263,33.49417],[-86.90919,33.494144],[-86.90933,33.4975],[-86.91097,33.495777],[-86.91244,33.496407],[-86.91633,33.4915],[-86.9169,33.487335],[-86.91672,33.48502],[-86.91528,33.48659],[-86.90937,33.48638],[-86.90966,33.482906],[-86.896065,33.482788],[-86.89605,33.48131],[-86.89081,33.4814],[-86.89095,33.478783],[-86.88525,33.478664],[-86.88518,33.482143],[-86.8829,33.482113],[-86.88311,33.48574],[-86.878975,33.489014],[-86.87955,33.489758],[-86.878975,33.49029],[-86.88104,33.492226],[-86.87808,33.494186],[-86.8799,33.496624],[-86.88047,33.49707],[-86.87902,33.498505],[-86.87862,33.498234],[-86.87436,33.50002],[-86.8741,33.500237],[-86.86773,33.505608],[-86.849014,33.50559],[-86.849014,33.508324],[-86.84599,33.508522],[-86.84608,33.509212],[-86.839676,33.51036],[-86.83923,33.509422],[-86.83691,33.508293],[-86.83686,33.507164],[-86.83653,33.506084],[-86.845184,33.504402],[-86.84893,33.50366],[-86.85296,33.50262],[-86.8659,33.49709],[-86.86816,33.49602],[-86.868034,33.500477],[-86.870636,33.501698],[-86.87638,33.49694],[-86.87606,33.496227],[-86.876274,33.495632],[-86.873604,33.492092],[-86.876045,33.48924],[-86.880646,33.483173],[-86.88442,33.47833],[-86.88738,33.474728],[-86.90667,33.47479],[-86.90988,33.471043],[-86.91405,33.47265],[-86.914085,33.475],[-86.91843,33.475086],[-86.91843,33.48493],[-86.918076,33.49281],[-86.91537,33.49596],[-86.9143,33.497208],[-86.91184,33.500004],[-86.91394,33.500538],[-86.914085,33.500896],[-86.91779,33.500923],[-86.917725,33.497177],[-86.92285,33.496704],[-86.92542,33.496643],[-86.935585,33.49691],[-86.93569,33.502083],[-86.930916,33.50223],[-86.93152,33.51216],[-86.92628,33.512367],[-86.92613,33.5088],[-86.9127,33.50937],[-86.91305,33.504936],[-86.909164,33.503124],[-86.90239,33.512577],[-86.9,33.51127],[-86.90136,33.509754],[-86.89975,33.508892]]],[[[-86.86986,33.5446],[-86.86969,33.53668],[-86.87426,33.536617],[-86.87421,33.532993],[-86.87794,33.53297],[-86.87816,33.529068],[-86.87832,33.528316],[-86.87861,33.515755],[-86.87654,33.515656],[-86.87637,33.50975],[-86.87951,33.50773],[-86.880554,33.506042],[-86.88435,33.505726],[-86.88616,33.505367],[-86.89159,33.502834],[-86.89379,33.504215],[-86.89626,33.505768],[-86.89308,33.50963],[-86.892654,33.509354],[-86.88597,33.50939],[-86.886024,33.511395],[-86.89175,33.511395],[-86.89527,33.513454],[-86.89089,33.518906],[-86.892654,33.519737],[-86.88726,33.526455],[-86.89294,33.528515],[-86.88895,33.532337],[-86.88705,33.533764],[-86.88324,33.537052],[-86.882835,33.533688],[-86.88419,33.532063],[-86.88471,33.53184],[-86.885666,33.531605],[-86.88291,33.530495],[-86.878746,33.534775],[-86.8788,33.536064],[-86.87906,33.536636],[-86.879654,33.537746],[-86.87998,33.538124],[-86.88068,33.53826],[-86.88177,33.538322],[-86.883026,33.538082],[-86.88538,33.540123],[-86.88526,33.544918],[-86.86986,33.5446]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"Owenton, old portion of Graymont, Wylam, Pratt City, Ensley, Central Park, Fairview and Fairfield (all in city limits except Fairfield)\",\"holc_id\":\"D3\",\"holc_grade\":\"D\",\"neighborhood_id\":222.0,\"area_description_data\":{\"5\":\"Both sales and rental prices in 1929 were off from 15-20% of the 1926-28 peak. Location of property within area justifies policy of selling rather than holding for posible price increase. Real estate activity in this area is strictly dependent upon operations of nearby steel plants and mines, hence area is subject to wide fluctuations. When plants are operating at near capactiy, property is very much in demand, while the reverse is true when operations are off. For this reason, the time to sell  property in this area is when the plants are busy. There are a few duplexes and apartments in this area, but they represent only a negligible part of the total. **affects sales and rental prices of residential property int his area. Fair grounds and muncipal stadium in area.\",\"6\":\"3 D Owenton, old portion of Graymont, Wylam, Pratt City, Ensley, Central Park, Fairview and Fairfield (all in city limits except Fairfield)\",\"31\":\"70\",\"32\":\"30\",\"33\":\"N/A\",\"3n\":\"N/A 70 N/A 1936 6-27.50 72 6-22.50\",\"1a\":\"Level to hilly\",\"1b\":\"Close to occupants source of employment. Good street car and bus transportation (on super-highway to Bessemer). Schools, churches, community centers, parks and playgrounds.\",\"2a\":\"Business men, clerical and steel workers\",\"3m\":\"8-40 8-35 N/A\",\"3d\":\"N/A Poor to dilapidated Poor to dilapidated\",\"3k\":\"Fair to poor Fair to poor N/A\",\"3o\":\"N/A 73 6-25 1938 6-35 82 N/A\",\"2c\":\"20 Italians, Russians and Greeks\",\"2g\":\"N/A N/A Slowly\",\"3h\":\"1500-5500 N/A 1500-5500\",\"1e\":\"Slightly downward\",\"2f\":\"Many\",\"3q\":\"N/a Good Good\",\"3p\":\"Good N/A Good\",\"4b\":\"Limited principally to indivual lenders\",\"2d\":\"Yes 40\",\"3f\":\"20 N/A 20\",\"3a\":\"N/A 1 story singles 1 story singles\",\"3i\":\"1936 57 750-3500 N/A N/A 750-3500 57\",\"3c\":\"N/A 20-30 20-30\",\"3l\":\"N/A Poor Poor\",\"2b\":\"300-3500\",\"3g\":\"None N/A None\",\"3j\":\"1938 57 750-3500 750-3500 N/A 57 N/A\",\"4a\":\"Limited principally to indivual lenders\",\"1c\":\"50% of streets unpaved. Obnoxious odors, smoke and dirt from industrial plants and incinerator (in area). Heavytraffic. Encroachment of industial and business. Difficulty of rental collections. Vandalism. Age of buildings and poor repair conditions. Location of company-owned houses in adjoining industrial area adversely.\",\"1d\":\"70\",\"3b\":\"N/A Frame Frame\",\"3e\":\"N/A 90 90\",\"2e\":\"None\"}}},{\"geometry\":{\"coordinates\":[[[[-86.75888,33.522205],[-86.76178,33.52712],[-86.76244,33.526764],[-86.76349,33.526646],[-86.76411,33.526325],[-86.765015,33.52597],[-86.77043,33.52284],[-86.774376,33.520977],[-86.7738,33.520103],[-86.77614,33.518875],[-86.77542,33.517887],[-86.7807,33.515152],[-86.78141,33.5161],[-86.782936,33.515507],[-86.78089,33.512535],[-86.78203,33.5123],[-86.78295,33.51185],[-86.78422,33.511227],[-86.78507,33.511982],[-86.78712,33.51115],[-86.78788,33.51222],[-86.79424,33.509403],[-86.797005,33.513763],[-86.78284,33.52074],[-86.78203,33.51975],[-86.7778,33.52181],[-86.77851,33.52272],[-86.77452,33.524586],[-86.77642,33.52815],[-86.77998,33.526722],[-86.77951,33.527397],[-86.7796,33.534252],[-86.7875,33.534412],[-86.78916,33.537067],[-86.78331,33.53996],[-86.78507,33.54269],[-86.78712,33.54194],[-86.78916,33.54503],[-86.78702,33.54598],[-86.78821,33.547325],[-86.78593,33.54828],[-86.786354,33.548992],[-86.78336,33.552517],[-86.78341,33.563686],[-86.78678,33.563847],[-86.78678,33.568756],[-86.78616,33.569313],[-86.7865,33.578777],[-86.78098,33.57779],[-86.77523,33.581352],[-86.77238,33.584164],[-86.77024,33.585194],[-86.76839,33.58666],[-86.7674,33.585884],[-86.77323,33.580204],[-86.77894,33.576523],[-86.78312,33.573193],[-86.7816,33.57153],[-86.78417,33.56943],[-86.78165,33.566936],[-86.7825,33.566223],[-86.78098,33.564796],[-86.78117,33.55842],[-86.7767,33.5583],[-86.77647,33.56357],[-86.777466,33.56452],[-86.77732,33.572044],[-86.77471,33.5705],[-86.77124,33.570698],[-86.77105,33.57248],[-86.76986,33.57256],[-86.76986,33.578026],[-86.767006,33.57961],[-86.76596,33.581787],[-86.75723,33.581272],[-86.75715,33.5704],[-86.761925,33.570263],[-86.7653,33.570698],[-86.764915,33.561707],[-86.77162,33.557705],[-86.77885,33.555134],[-86.778984,33.5398],[-86.77566,33.5396],[-86.77561,33.54602],[-86.77252,33.545902],[-86.77233,33.548553],[-86.76159,33.54828],[-86.76168,33.552082],[-86.75503,33.55192],[-86.755264,33.549347],[-86.75712,33.548355],[-86.76131,33.54828],[-86.76154,33.54804],[-86.76154,33.546158],[-86.76188,33.53645],[-86.75826,33.530903],[-86.758644,33.53029],[-86.757835,33.529736],[-86.758354,33.529022],[-86.756645,33.527874],[-86.75631,33.52752],[-86.75422,33.525295],[-86.75888,33.522205]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"Avondale, Kingston, East Birmingham and poorer sections of Inglenook and Tarrant City\",\"holc_id\":\"D4\",\"holc_grade\":\"D\",\"neighborhood_id\":183.0,\"area_description_data\":{\"5\":\"Both sales and rental prices in 1929 were off about 20% from 1926-28 peak. Location of property within area justify policy of selling rather than holding for possible price increase.\",\"6\":\"Avondale, Kingston, East Birmingham and poorer sections of Inglenook and Tarrant City D 4\",\"31\":\"98\",\"32\":\"N/A\",\"33\":\"N/A\",\"3m\":\"N/A N/A 8-20\",\"3c\":\"25 N/A N/A\",\"3j\":\"1000-3500 N/A N/A N/A 1938 62 N/A\",\"4a\":\"None\",\"3d\":\"Fair to dilapidated N/A N/A\",\"4b\":\"None\",\"3e\":\"85 N/A N/A\",\"3a\":\"1 story singles N/A N/A\",\"1d\":\"50\",\"2d\":\"40 Yes\",\"3o\":\"6-22.50 N/A N/A N/A N/A 75 1938\",\"3q\":\"Fair to poor N/A N/A\",\"1c\":\"Obnoxious odors, smoke, and dirt from industrial plants. Heavy traffic. Vandalism. Difficulty of rental collections. Encroachment of commercial and industrial properties. 50% of streets unpaved.\",\"3h\":\"N/A N/A 2000-4750\",\"3n\":\"1936 N/A N/A N/A 61 N/A 5-17.50\",\"3f\":\"N/A N/A 20\",\"2e\":\"None\",\"1b\":\"Close to occupants source of employment. Street car transportation, parks, playgrounds, churches, schools, and community business centers.\",\"2a\":\"Clerical and factory workers, skilled mechanics, laborers & domestics\",\"2b\":\"300-2000\",\"3p\":\"Fair N/A N/A\",\"1a\":\"Level to hilly\",\"2g\":\"N/A N/A Yes\",\"3g\":\"N/A None N/A\",\"2f\":\"Many\",\"3i\":\"N/A 1936 1000-3500 62 N/A N/A N/A\",\"2c\":\"2 Italians, Russians and Greeks\",\"1e\":\"Static\",\"3k\":\"Poor N/A N/A\",\"3b\":\"Frame N/A N/A\",\"3l\":\"N/A Poor N/A\"}}},{\"geometry\":{\"coordinates\":[[[[-86.79815,33.501793],[-86.808205,33.49664],[-86.80877,33.49745],[-86.81134,33.497395],[-86.81136,33.49767],[-86.8135,33.49785],[-86.813194,33.498653],[-86.81443,33.500492],[-86.80894,33.50302],[-86.80156,33.50642],[-86.79815,33.501793]]],[[[-86.793686,33.504574],[-86.79532,33.50212],[-86.79725,33.504845],[-86.79571,33.505165],[-86.796776,33.50678],[-86.78988,33.50985],[-86.78923,33.508835],[-86.790474,33.50834],[-86.79066,33.5069],[-86.7903,33.506157],[-86.793686,33.504574]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"Oldest portion of Southside\",\"holc_id\":\"D5\",\"holc_grade\":\"D\",\"neighborhood_id\":197.0,\"area_description_data\":{\"5\":\"Both sales and rental prices in 1929 were off about 20% from 1926-28 peak. Location of property within area justifies policy of selling rather than holding for possible price increase.\",\"6\":\"5 D Oldest portion of Southside\",\"31\":\"75\",\"32\":\"25\",\"33\":\"N/A\",\"3m\":\"9-35 N/A 35-60\",\"1c\":\"Zoned for business and semi-business. Encroachment of business and apartments. Heavy traffic. Vandalism. City Hospital in area. Absence of restrictions. Smoke and dirt from city. Difficulty of rental collections. Slow infiltration of negroes. Advanced age and repair conditions of properties.\",\"3h\":\"2000-3750 2000-4500 N/A\",\"1d\":\"95\",\"3o\":\"N/A N/A 1938 6-28 69 25-45 73\",\"1e\":\"Slowly downward\",\"3f\":\"25 15 N/A\",\"2a\":\"Clerical workrs, mechanics, laborers and domestics\",\"2b\":\"300-1500\",\"3i\":\"1750-3500 68 N/A 1250-2500 N/A 1936 65\",\"2c\":\"Italians, Russians and Greeks 10\",\"2d\":\"Yes 50\",\"3e\":\"90 90 N/A\",\"3p\":\"Fair Fair N/A\",\"2e\":\"See 1-c above\",\"3d\":\"Poor to dilapidated Poor to dilapidated N/a\",\"3q\":\"N/A Fair Fair\",\"3j\":\"73 1500-3000 1938 77 2250-3750 N/A N/A\",\"2f\":\"Few\",\"2g\":\"N/A Slowly N/A\",\"3c\":\"30 N/A 20\",\"3b\":\"N/A Frame Frame\",\"3a\":\"N/A 1 story singles 2 story singles\",\"3k\":\"Poor Poor N/A\",\"3l\":\"N/A Practically none Practically none\",\"4b\":\"None\",\"4a\":\"None\",\"3n\":\"1936 N/A 60 4-17.50 48 N/A 20-27.50\",\"1a\":\"Level to rolling\",\"3g\":\"None None N/A\",\"1b\":\"Within walking distance to center of city. Negro and white schools, negro churches, parks.\"}}},{\"geometry\":{\"coordinates\":[[[[-86.83603,33.504387],[-86.83947,33.503372],[-86.84033,33.504326],[-86.843704,33.504066],[-86.84846,33.500042],[-86.853775,33.499966],[-86.86535,33.49263],[-86.86777,33.49279],[-86.867805,33.491123],[-86.870094,33.491142],[-86.870186,33.486546],[-86.86577,33.486584],[-86.865715,33.490868],[-86.86258,33.491364],[-86.861084,33.489517],[-86.855736,33.49255],[-86.8545,33.491123],[-86.85105,33.493263],[-86.84919,33.491104],[-86.848595,33.49146],[-86.84854,33.481598],[-86.84427,33.48139],[-86.84419,33.477467],[-86.83995,33.477345],[-86.83964,33.496536],[-86.83477,33.498898],[-86.83403,33.49769],[-86.834526,33.49729],[-86.8312,33.492496],[-86.82832,33.493923],[-86.82683,33.491623],[-86.8135,33.49785],[-86.8135,33.496025],[-86.81743,33.494164],[-86.81699,33.49337],[-86.819824,33.492123],[-86.818794,33.4908],[-86.82324,33.491055],[-86.827095,33.48597],[-86.82275,33.48588],[-86.82275,33.484245],[-86.81816,33.48421],[-86.818275,33.48014],[-86.824165,33.48026],[-86.823746,33.48115],[-86.83114,33.48127],[-86.83253,33.48014],[-86.84062,33.47458],[-86.85032,33.47485],[-86.85021,33.477196],[-86.86011,33.477524],[-86.86032,33.480675],[-86.870125,33.47684],[-86.87305,33.474968],[-86.87667,33.474876],[-86.87888,33.473778],[-86.87956,33.47333],[-86.88059,33.4711],[-86.88135,33.470207],[-86.8827,33.469555],[-86.869865,33.469437],[-86.86968,33.463757],[-86.87083,33.46331],[-86.87076,33.45861],[-86.87328,33.45861],[-86.87375,33.44921],[-86.88687,33.44948],[-86.887405,33.442635],[-86.90305,33.44341],[-86.906944,33.439304],[-86.911255,33.444508],[-86.90855,33.44692],[-86.907295,33.445553],[-86.9062,33.4468],[-86.89813,33.452747],[-86.899956,33.454296],[-86.884926,33.467236],[-86.884926,33.46762],[-86.885956,33.467857],[-86.89173,33.467888],[-86.891594,33.47312],[-86.88371,33.472706],[-86.88211,33.47431],[-86.882645,33.47568],[-86.88261,33.47809],[-86.88442,33.47833],[-86.880646,33.483173],[-86.876045,33.48924],[-86.873604,33.492092],[-86.87235,33.49355],[-86.87175,33.494144],[-86.86911,33.49557],[-86.86816,33.49602],[-86.8659,33.49709],[-86.85296,33.50262],[-86.84893,33.50366],[-86.845184,33.504402],[-86.83653,33.506084],[-86.83603,33.504387]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"Outlying portion of West End, southeastern portion of Elyton, Cleveland, and Powderly (Powderly outside of city limits)\",\"holc_id\":\"D6\",\"holc_grade\":\"D\",\"neighborhood_id\":231.0,\"area_description_data\":{\"5\":\"Both sales and rental prices in1929 were off about 20% from 1926-28 peak. Locations of property within area justifies policy of selling rather than holding for possible price increase. In Cleveland portions of area there is a small sub-area bounded roughly by Alameda and Claburn Avenues and 16th Way and 14th Place, which is improved principally with one story frame bungalows in the $2000-4000 price place which area is entitled to a C grade rating.\",\"6\":\"D 6 Outlying portion of West End, southeastern portion of Elyton, Cleveland, Powderly (Powdely outside of city limits)\",\"31\":\"90\",\"32\":\"8\",\"33\":\"2\",\"3g\":\"None N/A None\",\"3b\":\"N/A Frame Frame\",\"3m\":\"30-50 N/A 9-40\",\"3f\":\"N/A 35 25\",\"4a\":\"Practically none\",\"3n\":\"1936 N/A 70 4-27.50 57 22.50-32.50 N/A\",\"2g\":\"N/A N/A Slowly\",\"3p\":\"N/A Good to fair Fair\",\"2b\":\"300-2000\",\"3c\":\"N/A 30 30\",\"3e\":\"85 85 N/A\",\"2a\":\"Clerical and factory workers, skilled mechanics and laborers\",\"3o\":\"1938 N/A N/A 81 25-40 69 6-32.50\",\"2f\":\"Many\",\"1b\":\"Perks and playgrounds. Bus and street car transportation. Close to occupants source of employment.\",\"3d\":\"Fair to dilipated Fair to dilipated N/A\",\"3k\":\"Poor Fair  to poor N/A\",\"1a\":\"Low to level flat land\",\"2d\":\"Yes 30\",\"3h\":\"N/A 3000-4500 1250-3750\",\"1d\":\"40\",\"3j\":\"750-3000 1938 3000-3500 70 72 N/A N/A\",\"1e\":\"Static\",\"3a\":\"Apartments 2 story singles 1 story singles\",\"3l\":\"Poor Poor N/A\",\"1c\":\"50% of streets unpaved. Absence of zoning and restrictions for protections of neighborhood. Heavy traffic. Vandalism. Difficulty of rental collections. Advanced age and poor repair conditions of properties. 3 main line railroad tracks traverse area, hence smoke and dust nuisance. Termites in low and damp portion of area. Encroachment of business.\",\"3q\":\"N/A Good to fair Fair\",\"3i\":\"N/A 67 N/A 2000-3000 64 750-2500 1936\",\"2c\":\"3 Italians, Russians and Greeks\",\"2e\":\"None\",\"4b\":\"Practically none\"}}},{\"geometry\":{\"coordinates\":[[[[-86.83653,33.506084],[-86.83686,33.507164],[-86.83691,33.508293],[-86.83923,33.509422],[-86.839676,33.51036],[-86.841324,33.51388],[-86.8409,33.514038],[-86.84092,33.51531],[-86.83776,33.51523],[-86.837715,33.521294],[-86.852806,33.521748],[-86.85284,33.523407],[-86.85077,33.523705],[-86.84689,33.523605],[-86.843094,33.523487],[-86.84137,33.523743],[-86.84092,33.524258],[-86.84042,33.524754],[-86.8389,33.52549],[-86.83755,33.526123],[-86.83626,33.52646],[-86.83519,33.52636],[-86.83424,33.526062],[-86.833626,33.525627],[-86.83263,33.524258],[-86.83153,33.521503],[-86.83032,33.51988],[-86.82849,33.51877],[-86.82255,33.52097],[-86.81941,33.522594],[-86.81521,33.52488],[-86.81492,33.525017],[-86.81111,33.519646],[-86.81251,33.518932],[-86.81173,33.517666],[-86.81425,33.516457],[-86.81337,33.515327],[-86.81638,33.51392],[-86.81508,33.512177],[-86.8218,33.509106],[-86.82133,33.508232],[-86.8271,33.505836],[-86.825966,33.504368],[-86.82784,33.503895],[-86.82644,33.50209],[-86.8262,33.49894],[-86.82245,33.49894],[-86.8214,33.497272],[-86.81862,33.498543],[-86.81836,33.503593],[-86.811584,33.506786],[-86.80894,33.50302],[-86.81443,33.500492],[-86.813194,33.498653],[-86.8135,33.49785],[-86.82683,33.491623],[-86.82832,33.493923],[-86.8312,33.492496],[-86.834526,33.49729],[-86.83403,33.49769],[-86.83477,33.498898],[-86.8346,33.501434],[-86.83539,33.502724],[-86.83603,33.504387],[-86.83653,33.506084]],[[-86.83138,33.51388],[-86.83157,33.516556],[-86.83616,33.516754],[-86.83618,33.51388],[-86.83138,33.51388]]]],\"type\":\"MultiPolygon\"},\"type\":\"Feature\",\"properties\":{\"state\":\"AL\",\"city\":\"Birmingham\",\"name\":\"Graymont, Smithfield and northeast portion of Elyton\",\"holc_id\":\"D7\",\"holc_grade\":\"D\",\"neighborhood_id\":217.0,\"area_description_data\":{\"5\":\"Both sales and rental prices in 1929 were about 20% from 1926-28 peak. Located in this area is Smithfield Court a few negro low-cost slum clearance project containing 544 units. This area is generally considered real negro property in Birmingham. Locations of property within this area justifies policy of selling rather than holding few possible price increase. **traverse area. Vandalism. Difficulty of rental collections.\",\"6\":\"D 7 Graymont, Smithfield, and northeast portion of Elyton\",\"31\":\"85\",\"32\":\"15\",\"33\":\"N/A\",\"3e\":\"85 N/A 80\",\"3f\":\"20 35 N/A\",\"3o\":\"17.50-35 N/A 1938 6.00-25 N/a 61 73\",\"3a\":\"2 story singles 1 story singles N/a\",\"3k\":\"Fair Fair N/A\",\"3j\":\"N/A 74 2000-4000 N/A 1250-3750 71 1938\",\"2c\":\"Italians, Russians and Greeks 5\",\"3q\":\"Good to fair N/A Good to fair\",\"1e\":\"Static\",\"2d\":\"Yes 85\",\"3b\":\"N/A Frame Frame\",\"1c\":\"Absence of zoning or restrictions for protection of neighborhood. Hence encroachment of business. City jail in area. Heavy traffic. Pan-American Petroleum Refinery in northwest portion presents fire hazard. Birmingham Gas Co. in area. Obnoxious odors, noises and dirt. Fertilizer plant in area. 3 main line railroads.\",\"2g\":\"N/A Yes N/A\",\"2f\":\"Many\",\"3m\":\"25-50 N/A 12.50-40\",\"3l\":\"Fair Fair N/A\",\"3i\":\"1800-3750 1250-3300 1936 N/a 65 N/A 65\",\"2e\":\"None\",\"2a\":\"Skilled mechanics, factory workers, domestics, laborers\",\"3n\":\"N/A N/A 15-30 45 60 5.00-20 1936\",\"3c\":\"30 N/A 30\",\"3h\":\"1750-5000 3250-5000 N/A\",\"4a\":\"None\",\"2b\":\"300-3000\",\"1d\":\"60\",\"3g\":\"N/A None None\",\"4b\":\"None\",\"3d\":\"Fair to dilapidated N/A Fair to dilapidated\",\"1b\":\"Close to center of city. Parks, recreation centers, community business centers, good transportation, schools, and churches. 80% of streets paved. Close to occupants' source of employment.\",\"1a\":\"Level to rolling\",\"3p\":\"N/A Good to fair Good to fair\"}}}],\"type\":\"FeatureCollection\"}}", response3.body());
  }


  static private String getResponseString(HttpURLConnection clientConnection) throws IOException {
    return new Buffer().readFrom(clientConnection.getInputStream()).toString();
  }

  // ideation for fuzz came from cmoran5
  @Test
  public void fuzzTestCoords1() throws IOException, InterruptedException {
    // Ensure successful connection
    HttpURLConnection clientConnection = tryRequest("geodata");
    assertEquals(200, clientConnection.getResponseCode());

    int numTests = 100;


    for (int i = 0; i < numTests; i++) {
      String latmin = String.valueOf((Math.random() * 180) - 90);
      String lonmin = String.valueOf((Math.random() * 360) - 180);
      String latmax = String.valueOf((Math.random() * 180) - 90);
      String lonmax = String.valueOf((Math.random() * 360) - 180);
      String search = "http://localhost:" + Spark.port() + "/geoData?" +
              "latmin=" + latmin + "&lonmin=" + lonmin + "&latmax=" + latmax
              + "&lonmax=" + lonmax;
      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(search))
              .build();
      String responseString = getResponseString(clientConnection);
      Assertions.assertNotNull(responseString);
    }
  }

  @AfterEach
  public void teardown() {
    // Gracefully stop Spark listening on both endpoints
    Spark.unmap("/loadcsv");
    Spark.unmap("/getcsv");
    Spark.unmap("/weather");
    Spark.unmap("/geodata");
    Spark.awaitStop(); // don't proceed until the server is stopped
  }

  /**
   * Helper to start a connection to a specific API endpoint/params.
   *
   * @param apiCall the call string, including endpoint (NOTE: this would be better if it had more
   *                structure!)
   * @return the connection for the given URL, just after connecting
   * @throws IOException if the connection fails for some reason
   */
  static HttpURLConnection tryRequest(String apiCall) throws IOException {
    // Configure the connection (but don't actually send the request yet)
    URL requestUrl = new URL("http://localhost:" + Spark.port() + "/" + apiCall);
    HttpURLConnection clientConnection = (HttpURLConnection) requestUrl.openConnection();

    // The default method is "GET", which is what we're using here.
    // If we were using "POST", we'd need to say so.
    //clientConnection.setRequestMethod("GET");

    clientConnection.connect();
    return clientConnection;
  }

  @Test
  public void testing() {
    assertEquals(1, 1);
  }

  /**
   * Tests the various functionality and errors of when we call a loadcsv request
   * @throws IOException
   * @throws InterruptedException
   */
//  @Test
//  public void testLoadCSV() throws IOException, InterruptedException {
//    HttpURLConnection clientConnection = tryRequest("loadcsv");
//    assertEquals(200, clientConnection.getResponseCode());
//
//    //testing loadcsv with valid csv filepath 1
//    String filepath = "/Users/vivian/Desktop/FALL22/CSCI0320/sprint-2-alippman-vli18/data/testdata/small-csv.csv";
//    HttpRequest request = HttpRequest.newBuilder()
//        .uri(URI.create("http://localhost:" + Spark.port() + "/loadcsv?filepath=" + filepath))
//        .build();
//    HttpResponse<String> response = HttpClient.newBuilder()
//        .build()
//        .send(request, BodyHandlers.ofString());
//    assertEquals("{\"result\":\"success\",\"filepath\":\"" + filepath + "\"}", response.body());
//
//    //testing loadcsv with valid csv filepath 2
//    filepath = "/Users/vivian/Desktop/FALL22/CSCI0320/sprint-2-alippman-vli18/data/stars/ten-star.csv";
//    request = HttpRequest.newBuilder()
//        .uri(URI.create("http://localhost:" + Spark.port() + "/loadcsv?filepath=" + filepath))
//        .build();
//    response = HttpClient.newBuilder()
//        .build()
//        .send(request, BodyHandlers.ofString());
//    assertEquals("{\"result\":\"success\",\"filepath\":\"" + filepath + "\"}", response.body());
//
//    //testing loadcsv with no filepath request
//    request = HttpRequest.newBuilder()
//        .uri(URI.create("http://localhost:" + Spark.port() + "/loadcsv"))
//        .build();
//    response = HttpClient.newBuilder()
//        .build()
//        .send(request, BodyHandlers.ofString());
//    assertEquals("{\"result\":\"error_bad_json\"}", response.body());
//
//    //testing loadcsv with an invalid filepath
//    request = HttpRequest.newBuilder()
//        .uri(URI.create("http://localhost:" + Spark.port() + "/loadcsv?filepath=invalid"))
//        .build();
//    response = HttpClient.newBuilder()
//        .build()
//        .send(request, BodyHandlers.ofString());
//    assertEquals("{\"result\":\"error_datasource\"}", response.body());
//
//    //testing loadcsv with extra request parameters
//    request = HttpRequest.newBuilder()
//        .uri(URI.create(
//            "http://localhost:" + Spark.port() + "/loadcsv?filepath=" + filepath + "&extra=param"))
//        .build();
//    response = HttpClient.newBuilder()
//        .build()
//        .send(request, BodyHandlers.ofString());
//    assertEquals("{\"result\":\"error_bad_request\"}", response.body());
//  }
//
//  /**
//   * Testing the various functionality and errors of when we call a getcsv request
//   * @throws IOException
//   * @throws InterruptedException
//   */
//  @Test
//  public void testGetCSV() throws IOException, InterruptedException {
//    HttpURLConnection clientConnection = tryRequest("getcsv");
//    assertEquals(200, clientConnection.getResponseCode());
//
//    //testing getcsv before anything is loaded through loadcsv
//    HttpRequest request1 = HttpRequest.newBuilder()
//        .uri(URI.create("http://localhost:" + Spark.port() + "/getcsv"))
//        .build();
//    HttpResponse<String> response1 = HttpClient.newBuilder()
//        .build()
//        .send(request1, BodyHandlers.ofString());
//    assertEquals("{\"result\":\"error_datasource\"}", response1.body());
//
//    //loading a csv with an invalid filepath
//    HttpRequest requestLoad1 = HttpRequest.newBuilder()
//        .uri(URI.create("http://localhost:" + Spark.port() + "/loadcsv?filepath=invalid"))
//        .build();
//    HttpResponse<String> responseLoad1 = HttpClient.newBuilder()
//        .build()
//        .send(requestLoad1, BodyHandlers.ofString());
//    assertEquals("{\"result\":\"error_datasource\"}", responseLoad1.body());
//
//    //testing getcsv after loading an invalid filepath
//    HttpRequest request2 = HttpRequest.newBuilder()
//        .uri(URI.create("http://localhost:" + Spark.port() + "/getcsv"))
//        .build();
//    HttpResponse<String> response2 = HttpClient.newBuilder()
//        .build()
//        .send(request2, BodyHandlers.ofString());
//    assertEquals("{\"result\":\"error_datasource\"}", response2.body());
//
//    //loading a csv with a valid filepath
//    String filepath = "/Users/vivian/Desktop/FALL22/CSCI0320/sprint-2-alippman-vli18/data/testdata/small-csv.csv";
//    HttpRequest requestLoad2 = HttpRequest.newBuilder()
//        .uri(URI.create("http://localhost:" + Spark.port() + "/loadcsv?filepath=" + filepath))
//        .build();
//    HttpResponse responseLoad2 = HttpClient.newBuilder()
//        .build()
//        .send(requestLoad2, BodyHandlers.ofString());
//    assertEquals("{\"result\":\"success\",\"filepath\":\"" + filepath + "\"}",
//        responseLoad2.body());
//
//    //testing getcsv after loading a valid filepath
//    String smallcsv = "{\"result\":\"success\",\"data\":"
//        + "[[\"apple\",\"red\",\"small\",\"20\"],"
//        + "[\"banana\",\"yellow\",\"medium\",\"12\"],"
//        + "[\"orange\",\"orange\",\"medium\",\"4\"]]}";
//    HttpRequest request3 = HttpRequest.newBuilder()
//        .uri(URI.create("http://localhost:" + Spark.port() + "/getcsv"))
//        .build();
//    HttpResponse<String> response3 = HttpClient.newBuilder()
//        .build()
//        .send(request3, BodyHandlers.ofString());
//    assertEquals(smallcsv, response3.body());
//
//    //loading a csv with a valid filepath
//    filepath = "/Users/vivian/Desktop/FALL22/CSCI0320/sprint-2-alippman-vli18/data/stars/ten-star.csv";
//    HttpRequest requestLoad3 = HttpRequest.newBuilder()
//        .uri(URI.create("http://localhost:" + Spark.port() + "/loadcsv?filepath=" + filepath))
//        .build();
//    HttpResponse responseLoad3 = HttpClient.newBuilder()
//        .build()
//        .send(requestLoad3, BodyHandlers.ofString());
//    assertEquals("{\"result\":\"success\",\"filepath\":\"" + filepath + "\"}",
//        responseLoad3.body());
//
//    //testing getcsv after loading a valid filepath
//    String tenStar = "{\"result\":\"success\",\"data\":"
//        + "[[\"0\",\"Sol\",\"0\",\"0\",\"0\"],"
//        + "[\"1\",\"\",\"282.43485\",\"0.00449\",\"5.36884\"],"
//        + "[\"2\",\"\",\"43.04329\",\"0.00285\",\"-15.24144\"],"
//        + "[\"3\",\"\",\"277.11358\",\"0.02422\",\"223.27753\"],"
//        + "[\"3759\",\"96 G. Psc\",\"7.26388\",\"1.55643\",\"0.68697\"],"
//        + "[\"70667\",\"Proxima Centauri\",\"-0.47175\",\"-0.36132\",\"-1.15037\"],"
//        + "[\"71454\",\"Rigel Kentaurus B\",\"-0.50359\",\"-0.42128\",\"-1.1767\"],"
//        + "[\"71457\",\"Rigel Kentaurus A\",\"-0.50362\",\"-0.42139\",\"-1.17665\"],"
//        + "[\"87666\",\"Barnard's Star\",\"-0.01729\",\"-1.81533\",\"0.14824\"],"
//        + "[\"118721\",\"\",\"-2.28262\",\"0.64697\",\"0.29354\"]]}";
//    HttpRequest request5 = HttpRequest.newBuilder()
//        .uri(URI.create("http://localhost:" + Spark.port() + "/getcsv"))
//        .build();
//    HttpResponse<String> response5 = HttpClient.newBuilder()
//        .build()
//        .send(request5, BodyHandlers.ofString());
//    assertEquals(tenStar, response5.body());
//
//    //loading a csv with an invalid filepath after loading a csv with a valid filepath
//    requestLoad3 = HttpRequest.newBuilder()
//        .uri(URI.create("http://localhost:" + Spark.port() + "/loadcsv?filepath=invalid"))
//        .build();
//    responseLoad3 = HttpClient.newBuilder()
//        .build()
//        .send(requestLoad3, BodyHandlers.ofString());
//    assertEquals("{\"result\":\"error_datasource\"}", responseLoad3.body());
//
//    //testing getcsv after loading an invalid filepath
//    request5 = HttpRequest.newBuilder()
//        .uri(URI.create("http://localhost:" + Spark.port() + "/getcsv"))
//        .build();
//    response5 = HttpClient.newBuilder()
//        .build()
//        .send(request5, BodyHandlers.ofString());
//    assertEquals("{\"result\":\"error_datasource\"}", response5.body());
//
//    //testing getcsv with parameters
//    HttpRequest request4 = HttpRequest.newBuilder()
//        .uri(URI.create("http://localhost:" + Spark.port() + "/getcsv?request=hi"))
//        .build();
//    HttpResponse<String> response4 = HttpClient.newBuilder()
//        .build()
//        .send(request4, BodyHandlers.ofString());
//    assertEquals("{\"result\":\"error_bad_request\"}", response4.body());
//  }
//
//  /**
//   * Testing the various functionality and errors of when we call a weather request
//   * @throws IOException
//   * @throws InterruptedException
//   */
//  @Test
//  public void testWeather() throws IOException, InterruptedException {
//    // Ensure successful connection
//    HttpURLConnection clientConnection = tryRequest("weather");
//    assertEquals(200, clientConnection.getResponseCode());
//
//    HttpUtilities http = new HttpUtilities();
//
//    //testing weather with valid lat and lon request params
//    String params = "?lat=41.824&lon=-71.4128";
//    HttpRequest request = HttpRequest.newBuilder()
//        .uri(URI.create("http://localhost:" + Spark.port() + "/weather" + params))
//        .build();
//    HttpResponse<String> response = HttpClient.newBuilder()
//        .build()
//        .send(request, BodyHandlers.ofString());
//    assertEquals("{\"result\":\"success\",\"temperature\":66,\"lon\":\"-71.4128\",\"lat\":\"41.824\"}", response.body());
//    //testing HttpUtilities getRequest()
//    assertEquals(http.getRequest("http://localhost:" + Spark.port() + "/weather" + params), response.body());
//
//    //testing weather with no request params
//    request = HttpRequest.newBuilder()
//        .uri(URI.create("http://localhost:" + Spark.port() + "/weather"))
//        .build();
//    response = HttpClient.newBuilder()
//        .build()
//        .send(request, BodyHandlers.ofString());
//    assertEquals("{\"result\":\"error_bad_request\"}", response.body());
//
//    //testing weather with only a lat request param
//    request = HttpRequest.newBuilder()
//        .uri(URI.create("http://localhost:" + Spark.port() + "/weather?lat=41.824"))
//        .build();
//    response = HttpClient.newBuilder()
//        .build()
//        .send(request, BodyHandlers.ofString());
//    assertEquals("{\"result\":\"error_bad_request\"}", response.body());
//
//    //testing weather with invalid latitude and longitude
//    request = HttpRequest.newBuilder()
//        .uri(URI.create("http://localhost:" + Spark.port() + "/weather?lat=123.023&lon=42.0324"))
//        .build();
//    response = HttpClient.newBuilder()
//        .build()
//        .send(request, BodyHandlers.ofString());
//    assertEquals("{\"result\":\"error_datasource\"}", response.body());
//
//    //testing weather with an extra param
//    request = HttpRequest.newBuilder()
//        .uri(URI.create("http://localhost:" + Spark.port() + "/weather" + params + "&extra=param"))
//        .build();
//    response = HttpClient.newBuilder()
//        .build()
//        .send(request, BodyHandlers.ofString());
//    assertEquals("{\"result\":\"error_bad_request\"}", response.body());
//  }
//
//  /**
//   * Fuzz testing weather request with random latitude and longitude coordinates
//   * @throws IOException
//   * @throws InterruptedException
//   */
//  @Test
//  public void fuzzTestCoords() throws IOException, InterruptedException {
//    // Ensure successful connection
//    HttpURLConnection clientConnection = tryRequest("weather");
//    assertEquals(200, clientConnection.getResponseCode());
//
//    int numTests = 100;
//
//    for (int i = 0; i < numTests; i++) {
//      String lat = String.valueOf((Math.random() * 180) - 90);
//      String lon = String.valueOf((Math.random() * 360) - 180);
//      HttpRequest request = HttpRequest.newBuilder()
//          .uri(URI.create(
//              "http://localhost:" + Spark.port() + "/weather?lat=" + lat + "&lon=" + lon))
//          .build();
//      HttpResponse<String> response = HttpClient.newBuilder()
//          .build()
//          .send(request, BodyHandlers.ofString());
//
//      Type map = Types.newParameterizedType(Map.class, String.class, Object.class);
//      JsonAdapter<Map<String, Object>> tempAdapter = new Moshi.Builder().build().adapter(map);
//      Map<String, Object> temperatureMap = tempAdapter.fromJson(response.body());
//
//      assertTrue(temperatureMap.containsValue("success") || temperatureMap.containsValue(
//          "error_datasource"));
//    }
//  }
}

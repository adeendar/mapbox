README for Sprint 2: Alec Lippman and Vivian Li

Project Details:
Project Name: Sprint 2
Project Description: Creating a localserver
that can accept queries to load and get CSVs,
along with accessing the Weather API to get temperature
from inputted coordinates.
Contributions: adeendar, lshack, cbobowic, bbutaney, cyang102
Time: 16 hours
Repo: https://github.com/cs0320-f2022/sprint-2-alippman-vli18.git
Design Choices:
For this Sprint, we built off Sprint 0 adding in a handler
package, server package, and building on the testing package.
The handler package contains a Get/Load/Weather Handler class
that handle any request with the specified tag /getcsv
/loadcsv and /weather. Each handler class properly takes
in the input response and displays a json either showing
the desired output or what error occured. Each handler method
is wrapped in a dataSource, which calls Spark.get. This
allows for more user functionality and code that can integrated
with other handlers! The response class serializes all outputted
jsons to the local server for every handler. This class makes
displaying json data in the localserver very easy and scalable if
new handlers are included. It always takes in a map of string to
object so a user can input whatever they desire. The get and load handlers
both take in the same CSVParserClass which allows for getCSV
to access stringData from loadCSV. This stringData is passed to
getCSV as a Collections.unmodifiablelist for defensive programming purposes!

The server package runs the server and its main method that sets up every handler
when the localserver is called in the web browser. Http utilities allows
for any user of the program to call the class for a get request, and the
user can simply enter the URL of their choice for acesssing an online API.

The server package runs the server and its main method that sets up every handler
when the localserver is called in the web browser. Http utilities allows
for any user of the program to call the class for a get request, and the
user can simply enter the URL of their choice for acesssing an online API.

Errors/Bugs:
WeatherAPIUtilities contains deserialization methods for parsing the weather Json(s).
This APIUtilities class is specifically for WeatherAPIUtilities, but
we explored with the possibility of creating an APIUtilitiesClass that passes
in the type of what the Jsonadapter uses, however we were unable to do so
without typecasting. We decided not to implement this becuase we did not want to typecast
for design purposes.

There are no other bugs we know of.
Tests:
Additionally, mvn package allows for the user to use Jacoco testing through Maven which ensures 
that our tests have significant coverage over the code we have written for Sprint 2.

Fuzz testing is also done to test the weather handler by randomly generating latitude and 
longitudes that can be passed into the weather API's get request, ensuring this weather handler 
generally works with a variety of different randomized inputs.

Fuzz testing is done on the geohandler as well to randomly generate latitude and longitude max and mins
to ensure the handler can properly parse a variety of inputted coordinates.

A combination of JUnit testing and Jacoco coverage testing ensures that our handler classes and 
http utilities and weather utilities are functioning as expected! Integration testing is also done 
to ensure the handlers work in relation to each other. Extensive experimentation and calling our 
handlers from the localServer was done to ensure no bugs existed in the user front-end when passing 
queries in the URL request.

How to Run Tests:
There are two ways of running tests:
1. Running the classes in the test package in IntelliJ
2. mvn site allows the user to use Jacaco to check testing coverage of code

How to Use the localServer:
1. Run the server class in IntelliJ to run the main method and start the server
2. Using the Spark.port(<portNum>), navigate to localhost:<portNum> in your browser
3. For using loadcsv, use url localhost:<portNum>/filepath?<filepath>
   where filepath is the filepath of the CSV you are loading
4. For using getcsv, which serializes the loaded csv, use url localhost:<portnum>/getcsv
5. For using weather, use url localhost:<portNum>/weather?lat=<latcoordinate>&lon=<loncoordinate>
6. For using geodata, use url localhost:<portNum>/geodata?maxlat=<coord>&maxlon=<coord>&minlat=<coord>&minlon<coord>
7. These calls allow you to use the localserver!
   just sending to myself

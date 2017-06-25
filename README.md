# url-shortener
URL shortener service

Build the project using <i>mvn clean package</i>

Run the shell script <i>sh start.sh</i> to start the spring boot application

This is a web service that supports below API's
Please use port 9876 for dev profile and 80 for prod. This can be configured in application.properties file under resources

1. http://localhost:9876/shorten?url={your long url to be shortened}
2. put short url from above request in format http://localhost:9876/{unique id}


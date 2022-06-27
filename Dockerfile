FROM openjdk:11
WORKDIR /url-shortener
COPY . .
RUN ./gradlew build -x test
ADD build/libs/url-shortener-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
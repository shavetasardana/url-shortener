FROM openjdk:11
WORKDIR /url-shortener
COPY . .
ENV JAR_FILE=build/libs/url-shortener-0.0.1-SNAPSHOT.jar
RUN ./gradlew build -x test
RUN mv ${JAR_FILE} ./app.jar
CMD ["java", "-jar", "app.jar"]
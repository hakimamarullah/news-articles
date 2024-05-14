#FROM amazoncorretto:17.0.11-al2023-headless
#ENV java_opts=""
#ENV java_args=""
#LABEL maintainer="hakimamarullah@gmail.com"
#WORKDIR /app
#COPY target/programmingtest1*.jar /app/app.jar
#ENTRYPOINT exec java $java_opts -jar app.jar $java_args
#EXPOSE 8080

FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","id.com.sonarplatform.programmingtest1.ProgrammingTest1Application"]
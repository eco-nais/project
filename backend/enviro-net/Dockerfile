# FROM maven:3.8.4-openjdk-17 AS build
# WORKDIR /workspace/app

# COPY . .
# RUN mvn install -DskipTests

# RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# FROM openjdk:17-jdk-slim
# VOLUME /tmp
# ARG DEPENDENCY=/workspace/app/target/dependency
# COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
# COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
# COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# EXPOSE 8080

# ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.eco.environet.EnviroNetApplication"]



# Build stage
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /workspace/app

COPY . .
RUN mvn install -DskipTests

RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Migration stage
FROM maven:3.8.4-openjdk-17 AS migration
WORKDIR /workspace/app

# Copy only the migration scripts
COPY src/main/resources/db/migration /flyway/sql
COPY --from=build /workspace/app/target/dependency/BOOT-INF/lib /flyway/lib
COPY --from=build /workspace/app/target/dependency/META-INF /flyway/META-INF
COPY --from=build /workspace/app/target/dependency/BOOT-INF/classes /flyway/classes

# Add Flyway CLI
RUN curl -L https://repo1.maven.org/maven2/org/flywaydb/flyway-commandline/8.5.11/flyway-commandline-8.5.11-linux-x64.tar.gz | tar xz -C /flyway --strip-components=1

# Set environment variables for Flyway
ENV FLYWAY_URL=jdbc:postgresql://postgresdb:5432/environet
ENV FLYWAY_USER=postgres
ENV FLYWAY_PASSWORD=super
ENV FLYWAY_SCHEMAS=environet

# Run the migration
CMD ["/flyway/flyway", "migrate"]

# Final stage
FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 8080

ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.eco.environet.EnviroNetApplication"]

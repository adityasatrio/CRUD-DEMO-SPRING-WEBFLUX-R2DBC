FROM maven:3.8.7-eclipse-temurin-19

RUN apt-get update && apt-get install -y lsb-release gnupg gnupg2 gnupg1
RUN echo "deb http://apt.postgresql.org/pub/repos/apt/ $(lsb_release -sc)-pgdg main" > /etc/apt/sources.list.d/pgdg.list \
    && wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | apt-key add - \
    && apt-get update
RUN apt-get install -y postgresql-client-15

WORKDIR /app
COPY pom.xml .
COPY src/ src/
RUN mvn -f pom.xml clean package -DskipTests=true
COPY target/demo-crud-0.0.1-SNAPSHOT.jar my-project.jar

CMD service postgresql status || /etc/init.d/postgresql start && \
    su - postgres -c "psql -c 'CREATE DATABASE my_database;'" && \
    java -jar my-project.jar
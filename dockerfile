# Menggunakan image OpenJDK sebagai base image
FROM openjdk:17-jdk-slim

# Menetapkan direktori kerja
WORKDIR /app

# Menyalin file JAR hasil build ke dalam image
COPY target/api-public-service-0.0.1-SNAPSHOT.jar app.jar

# Menetapkan command untuk menjalankan aplikasi
ENTRYPOINT ["java", "-jar", "app.jar"]

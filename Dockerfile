FROM maven:3.9.0-eclipse-temurin-17 AS build
WORKDIR /app

# Copy project files
COPY . .

# Pass GitHub credentials as build arguments
ARG GITHUB_USERNAME
ARG GITHUB_TOKEN

# Create settings.xml with credentials from build args
RUN mkdir -p ~/.m2 && \
    echo '<settings>' > ~/.m2/settings.xml && \
    echo '  <servers>' >> ~/.m2/settings.xml && \
    echo '    <server>' >> ~/.m2/settings.xml && \
    echo '      <id>github</id>' >> ~/.m2/settings.xml && \
    echo "      <username>${GITHUB_USERNAME}</username>" >> ~/.m2/settings.xml && \
    echo "      <password>${GITHUB_TOKEN}</password>" >> ~/.m2/settings.xml && \
    echo '    </server>' >> ~/.m2/settings.xml && \
    echo '  </servers>' >> ~/.m2/settings.xml && \
    echo '</settings>' >> ~/.m2/settings.xml

# Build the app
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY .env /app/.env
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
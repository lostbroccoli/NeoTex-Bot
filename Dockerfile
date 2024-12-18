# This is the construction manuel that docker builds in order to run the bot.
FROM eclipse-temurin:21
COPY bot.jar /
COPY .env /
CMD ["java", "-jar", "bot.jar"]

# Make sure the bot.jar is built and in the same folder as the Dockerfile and docker-example.yml (you will need to rename the docker-example.yml to docker-compose.yml if you dont run portainer)

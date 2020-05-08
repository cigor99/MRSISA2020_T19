cd Klinicki-centar
mvnw clean install -D skipTests=false -B
mvnw sonar:sonar -D sonar.projectKey=cigor99_MRSISA2020_T19 -D sonar.organization=tim19 -D sonar.host.url=https://sonarcloud.io -D sonar.login=bd5e21c61e921f61bab61f7e27518c4810286ee8

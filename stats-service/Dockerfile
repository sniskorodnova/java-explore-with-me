FROM amazoncorretto:11
USER root
ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY target/*.jar stats-service.jar
ENTRYPOINT ["java","-jar","stats-service.jar"]
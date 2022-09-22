FROM amazoncorretto:11
#USER root
#ENV TZ=Europe/Moscow
#RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY target/*.jar ewm-service.jar
ENTRYPOINT ["java","-jar","ewm-service.jar"]
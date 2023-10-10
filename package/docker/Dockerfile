FROM amazoncorretto:17
ENV ESANJEEVANI_AUDIT_DIRECTORY=/opt/esanjeevani/audit
RUN mkdir -p $ESANJEEVANI_AUDIT_DIRECTORY
COPY target/eSanjeevani-service-1.0.0-SNAPSHOT.jar /opt/eSanjeevani-service.jar
CMD java $DEBUG_OPTS -jar /opt/eSanjeevani-service.jar

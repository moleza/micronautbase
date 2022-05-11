#!/bin/sh
./gradlew dockerBuildNative
echo
echo
echo "To run the docker container execute:"
echo "    $ docker run -p 8080:8080 micronautbase"
echo "Or to Run the compose file use:"
echo "    $ docker-compose up"
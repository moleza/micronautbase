FROM oracle/graalvm-ce:20.1.0-java11 as graalvm
RUN gu install native-image

COPY . /home/app/micronautbase
WORKDIR /home/app/micronautbase

RUN native-image --no-server -cp build/libs/micronautbase-*-all.jar

FROM frolvlad/alpine-glibc
RUN apk update && apk add libstdc++
EXPOSE 8080
COPY --from=graalvm /home/app/micronautbase/micronautbase /app/micronautbase
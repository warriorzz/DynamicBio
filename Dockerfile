FROM adoptopenjdk/openjdk15-openj9 as builder

WORKDIR /user/app

COPY . /user/app

RUN chmod +x ./gradlew

RUN ./gradlew --no-daemon installDist

FROM adoptopenjdk/openjdk15-openj9

COPY --from=builder build/install/DynamicBio ./

ENTRYPOINT ["/user/app/bin/DynamicBio"]

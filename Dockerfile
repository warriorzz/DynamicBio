FROM adoptopenjdk/openjdk15-openj9 as builder

COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew --no-daemon installDist

FROM adoptopenjdk/openjdk15-openj9

WORKDIR /user/app

COPY --from=builder build/install/DynamicBio ./

ENTRYPOINT ["/user/app/bin/DynamicBio"]

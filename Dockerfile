FROM adoptopenjdk/openjdk16:alpine as builder

COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew --no-daemon installDist

FROM adoptopenjdk/openjdk16:alpine

WORKDIR /user/app

COPY --from=builder build/install/DynamicBio ./

ENTRYPOINT ["/user/app/bin/DynamicBio"]

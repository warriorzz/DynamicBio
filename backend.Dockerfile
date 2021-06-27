FROM adoptopenjdk/openjdk16:alpine as builder

COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew --no-daemon :backend:installDist

FROM adoptopenjdk/openjdk16:alpine

WORKDIR /user/app

COPY --from=builder backend/build/install/backend ./

ENTRYPOINT ["/user/app/bin/backend"]
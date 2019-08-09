FROM gradle:5.5.1-jdk8

ENV APP_HOME /app
WORKDIR $APP_HOME

COPY . .

WORKDIR /app

CMD ["gradle", "build"]

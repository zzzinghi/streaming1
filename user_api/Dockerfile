# 1단계: Gradle을 사용해 프로젝트 빌드 +
FROM gradle:7.5.1-jdk17 AS build
WORKDIR /app
# 도커 안의 경로 - >  // 빌드에 필요한 파일을 app에 넣음 -> 빌드가 jar 만들고, >> 이러면 하나의 프로젝트가 돌아감
# copy . . >>> 모듈 안의 모든 경로를 app 위치로 보낸다
# 프로젝트의 모든 파일을 컨테이너로 복사 +
COPY . .

# 프로젝트 빌드 (여기서 JAR 파일이 생성됨) +
RUN gradle build --no-daemon


# 1. Base image 선택
FROM openjdk:21-jdk-slim

# 3. 작업 디렉토리 설정
WORKDIR /app

# 빌드된 JAR 파일을 복사 (멀티 모듈일 경우 하위 모듈에 따라 경로가 다를 수 있음) +
COPY --from=build /app/build/libs/user_api-0.0.1-SNAPSHOT.jar user_api.jar

# 2. 빌드된 JAR 파일을 컨테이너 내에 복사
# COPY build/libs/user-api-0.0.1-SNAPSHOT.jar user-api.jar

# 4. 컨테이너가 시작될 때 실행할 명령어 지정
ENTRYPOINT ["java", "-jar", "user_api.jar"]

# 5. (선택 사항) 포트 노출
EXPOSE 6071
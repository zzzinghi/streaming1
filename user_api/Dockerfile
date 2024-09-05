## 1단계: Gradle을 사용해 프로젝트 빌드 +
#FROM gradle:7.5.1-jdk21 AS build
#WORKDIR /app
## 도커 안의 경로 - >  // 빌드에 필요한 파일을 app에 넣음 -> 빌드가 jar 만들고, >> 이러면 하나의 프로젝트가 돌아감
## copy . . >>> 모듈 안의 모든 경로를 app 위치로 보낸다
## 프로젝트의 모든 파일을 컨테이너로 복사 +
#COPY . .
#
## 프로젝트 빌드 (여기서 JAR 파일이 생성됨) +
#RUN gradle build --no-daemon


## 1. Base image 선택
#FROM openjdk:21-jdk-slim
#
## 3. 작업 디렉토리 설정
#WORKDIR /app
#
## 빌드된 JAR 파일을 복사 (멀티 모듈일 경우 하위 모듈에 따라 경로가 다를 수 있음) +
#COPY --from=build /app/build/libs/user_api-0.0.1-SNAPSHOT.jar user_api.jar
#
## 2. 빌드된 JAR 파일을 컨테이너 내에 복사
## COPY build/libs/user-api-0.0.1-SNAPSHOT.jar user-api.jar
#
## 4. 컨테이너가 시작될 때 실행할 명령어 지정
#ENTRYPOINT ["java", "-jar", "user_api.jar"]
#
## 5. (선택 사항) 포트 노출
#EXPOSE 6071

# 1단계: OpenJDK 21을 베이스로 사용하여 Gradle을 수동으로 설치하고 빌드하는 이미지
FROM openjdk:21-jdk-slim AS build

# Gradle 버전 설정 (최신 버전으로 필요시 업데이트)
ARG GRADLE_VERSION=7.6

# Gradle을 다운로드하여 설치
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip && \
    unzip gradle-${GRADLE_VERSION}-bin.zip -d /opt/gradle && \
    rm gradle-${GRADLE_VERSION}-bin.zip

# 환경 변수 설정
ENV GRADLE_HOME /opt/gradle/gradle-${GRADLE_VERSION}
ENV PATH ${GRADLE_HOME}/bin:${PATH}

# 작업 디렉토리 설정
WORKDIR /app

# 프로젝트의 모든 파일을 컨테이너로 복사
COPY . .

# Gradle을 사용하여 프로젝트 빌드 (여기서 JAR 파일 생성)
RUN gradle build --no-daemon

# 2단계: OpenJDK 21을 사용해 빌드된 JAR 파일 실행을 위한 이미지
FROM openjdk:21-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# 1단계에서 빌드된 JAR 파일을 복사 (멀티 모듈일 경우 각 모듈 경로에 맞춰 변경)
COPY --from=build /app/build/libs/user_api-0.0.1-SNAPSHOT.jar user_api.jar

# 컨테이너가 시작될 때 실행할 명령어 지정
ENTRYPOINT ["java", "-jar", "user_api.jar"]

# 포트 노출 (필요한 포트로 변경)
EXPOSE 6071
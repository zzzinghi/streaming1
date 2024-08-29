#FROM openjdk:21-jdk-slim
## openjdk 버전과 alpine 베이스 이미지의 조합이 docker hub에 존재하지 않음
#WORKDIR /app
#COPY build/libs/video-api-0.0.1-SNAPSHOT.jar video-api.jar
#ENTRYPOINT ["java", "-jar", "video-api.jar"]
#EXPOSE 6062


# 1단계: Gradle을 사용해 프로젝트 빌드
FROM gradle:7.5.1-jdk17 AS build
WORKDIR /app

# 프로젝트의 모든 파일을 컨테이너로 복사
COPY . .

# 프로젝트 빌드 (여기서 JAR 파일이 생성됨)
RUN gradle build --no-daemon

# 2단계: 최종 이미지를 생성
FROM openjdk:21-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 JAR 파일을 복사 (멀티 모듈일 경우 하위 모듈에 따라 경로가 다를 수 있음)
COPY --from=build /app/build/libs/video_api-0.0.1-SNAPSHOT.jar video_api.jar

# 애플리케이션 실행 명령
ENTRYPOINT ["java", "-jar", "user_api.jar"]

# (선택 사항) 포트 노출
EXPOSE 6072

# 1. 베이스 이미지 지정
FROM eclipse-temurin:21-jdk-alpine

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 빌드된 JAR 파일을 컨테이너로 복사
COPY build/libs/*.jar /app/myapp.jar


# 4. JAR 파일 실행 명령어 설정
ENTRYPOINT ["java", "-jar", "/app/myapp.jar"]
# Release

```
./gradlew build release -Prelease.useAutomaticVersion=true
```

# DDL 생성

## 명령어
```
./gradlew generateSchema
```

## 출력 위치
```
build/generated-schema/create.sql
```

# IntelliJ Setting

* Settings
  * Build, Execution, Deployment > Build Tools > Gradle > Runner
    * Delegate IDE build/run actiosns to Gradle 활성화

# 상태

## 공정

| 상태   | 설명                                                 |
|--------|------------------------------------------------------|
| 작성중 | 공정을 작성 중인 상태로 공정유형을 변경 가능         |
| 가확정 | 공정 내용중 공정 유형만 확정한 상태 생산은 진행 불가 |
| 확정   | 확정인 상태로 이후 생산 등을 진행 가능               |

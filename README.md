# Spring-Boot-JPA-Utilization

## 개발 환경
- Spring Boot : 2.5.12
- Java : JDK 11
- Mysql : 8.0.28
- IntelliJ

## 사용 라이브러리

### 핵심 라이브러리
- 스프링 MVC
- 스프링 ORM
- JPA, 하이버네이트 : 1.4.200
- 스프링 데이터 JPA

### 기타 라이브러리

- Mysql Connector
- Lombok
- jackson-datatype-hibernate5
- jackson-datatype-jsr310  
  : json을 Serialize하여 client에 리턴할 때 사용하였습니다.
- Junit5  
  : 비즈니스 로직의 단위 테스트를 위해 사용하였습니다.

## Database
Mysql을 사용하였습니다.
JPA을 사용하여, 추가로 DDL을 사용하지 않더라도 테이블들과 관계가 설정됩니다.

하지만 초기 세팅을 위해 사용자(User)와 장소(Place)는 데이터 입력이 필요합니다.
해당하는 내용은 `ddl.sql`에 담았습니다.

## Test
먼저 `ddl.sql`의 사용자(User)와 장소(Place) `Insert`를 부탁드리겠습니다.

`Swagger Api`를 사용하여 테스트 하도록 구축하였습니다.

해당 프로젝트를 실행 후 (ClubMileageApplication 실행), 
```
http://localhost:8080/swagger-ui/index.html
```
로 접속하시면 테스트가 가능합니다.

### 리뷰 테스트 시나리오
리뷰 생성 (ADD)

```json
{
	"action": "ADD",
	"reviewId":"450a0658-dc5f-4878-9381-ebb7b2667772",
	"content":"좋아요!",
	"attachedPhotoIds":[
	"e4ffa64e-a531-46de-88d0-ff0ed70c0bb8",
	"afffcef2-851d-4a50-bb07-9cc15cbdc332"],
	"userId":"3ede0ef2-92b7-4817-a5f3-0c575361f745",
	"placeId":"2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}
```

리뷰 수정 (MOD)
  - 첨부된 사진을 제거하여 점수를 깎는다.
```json
{
	"action": "MOD",
	"reviewId":"450a0658-dc5f-4878-9381-ebb7b2667772",
	"content":"너무 좋아요!!!",
	"attachedPhotoIds":[],
	"userId":"3ede0ef2-92b7-4817-a5f3-0c575361f745"
}
```

리뷰 수정 (MOD)
- 사진을 다시 추가하여 점수를 올린다.
```json
{
	"action": "MOD",
	"reviewId":"450a0658-dc5f-4878-9381-ebb7b2667772",
	"content":"굉장해 엄청나!",
	"attachedPhotoIds":["97f0eb42-ff88-11ec-b939-0242ac120002"],
	"userId":"3ede0ef2-92b7-4817-a5f3-0c575361f745"
}
```

리뷰 삭제 (Delete)
```json
{
	"action": "DELETE",
	"reviewId":"450a0658-dc5f-4878-9381-ebb7b2667772"
}
```
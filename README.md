# 룸 프로젝트

</br>

## 제작 기간 & 참여 인원

-   2024년 02월 02일 ~ 
-   팀 프로젝트(2인)

</br>

## 배포주소
> 주소 :

</br>

## 팀원소개
|김승우|오승찬|
|:----------:|:----------:|
|<img src="https://avatars.githubusercontent.com/u/128001994?v=4" style="width:250px; height:250px;"/>|<img src="https://avatars.githubusercontent.com/u/126455161?s=64&v=4" style="width:250px; height:250px;"/>|
|[@KIMSEUNGWOO](https://github.com/KIMSEUNGWO)|[@SEUNGCHAN](https://github.com/seungchan5)
|팀장 백엔드|백엔드|

</br>

## 사용 기술

#### `Back-end`

-   Java 17 / Gradle
-   Oracle 11g
-   Apache Tomcat 9.0.78
-   Spring Boot 2.7.14
-   Git, Github

#### `Front-end`

-   HTML5
-   CSS3
-   Javascript
</br>

## 프로젝트 관리

### 노션 바로가기

[<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/2456b22d-3903-425a-b6e4-22a20a20df7e" style="width:200px;"/>](https://tmd8635.notion.site/4b36eab2dd094e8ab0d33081cb9b6e7c?pvs=4)

### 노션 소개

<details>
    <summary>노션 소개 펼쳐보기</summary>
  
#### > 워크플레이스

> ***프로젝트의 스케쥴과 기능들을 담은 메인화면***

|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/00285a12-7528-4c12-bb62-0ac0e2e4958c"/>|
|-|

#### > 기획 및 기능 (1/2)

> ***프로젝트의 기획 및 기능을 정리해둔 문서목록***

|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/d6bb522c-ade0-4c14-ae8a-01183ce90e06"/>|
|-|

#### > 기획 및 기능 (2/2)

> ***기획 및 기능에는 제한사항이 작성되어있다.***

|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/8cb1771c-01c6-4f72-b24a-868886616e73"/>|
|-|

#### > 공통 API문서

> ***공통 API문서가 작성되어있습니다. 객체 생성규칙, 반환 객체 정의 등***

|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/55f2bd54-d0af-4ae6-ae4b-41207f27ad3d"/>|
|-|

#### > API 문서

> ***RestFul API 문서를 정리한 목록***

|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/2291c2d4-d3b2-472f-97be-a587c0d926f8">|
|-|

#### > API 문서 상세

|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/35fc8817-1d95-4de5-ab67-3b2bcd3f9d5e "><img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/ea070e2b-5dc5-4f19-996a-364248a8fd17"><img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/2449cfbb-7578-44a6-bd9e-bc3f933d7e8d"><img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/d4e09667-35ed-44c0-854e-94ee43182d9e">|
|-|

#### > 자료

> ***프로젝트에 관한 자료나 공유해야하는 자료를 담은 자료실***

|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/0c4fad85-eb78-40e7-b4c8-ae2e24ad2db5">|
|-|

#### > 자료상세

|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/8ed30215-447e-475a-a2c8-c5d4fd269f77 "><img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/96a2d56d-b605-47c8-bd87-7cfc13f503c0"><img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/df3af525-b6e5-46a6-9b4e-fca1f97cc30e">|
|-|

</details>

## 프로젝트 방향성

### OOP(객체 지향 프로그래밍)

- Getter, Setter 를 지양한다.
  - 모든 데이터는 객체 내부에서 처리하도록 한다. -> 캡슐화
- Request, Response 데이터는 모두 객체화한다.
  - 필드값 변경시 수정사항 최소화, Collection 의존도 낮아짐
  - 각 객체는 용도에 따라 Request, Response 뒤에 변수명을 붙혀 용도를 명확히한다.
- ResponseBody 반환객체는 ResponseEntity로 감싸고, 객체는 ResponseDto를 사용한다.
  - 응답객체는 ResponseDto를 상속받아 사용한다. [ResponseDto 객체](https://github.com/KIMSEUNGWO/Room_Project/blob/develop/src/main/java/project/study/dto/abstractentity/ResponseDto.java)
  - 응답 데이터는 ResponseDto를 상속받은 ResponseObject를 사용한다. [ResponseObject<T>](https://github.com/KIMSEUNGWO/Room_Project/blob/develop/src/main/java/project/study/dto/abstractentity/ResponseObject.java)
  - Controller 반환타입 구현체 변경 시 변경범위 최소화, 새로운 객체를 무분별하게 추가하지 않아 코드복잡성 낮아짐.
- 하위 Entity는 상위 Entity로부터 접근한다.
  - Member Entity 안에 Profile Entity가 있다면 Member -> Profile Entity 순서로 접근한다.
  - ex) member.hasProfile() -> (profile != null) profile.hasProfile() : false;
  - 코드 복잡성이 낮아진다, 무분별한 조건식을 작성할 가능성이 낮아진다, 예외 null처리가 간편해진다.
- 변경 가능성 있는 class는 인터페이스화 한다. -> 다형성
  - ex) DB에 의존하는 Native SQL 이 존재하는 Repository는 인터페이스화 해서 Config에 직접 @Bean을 등록시킨다.
  

### MVC 패턴

- Controller는 최대한 코드가 없어야하고, 결과값을 출력하는 일만 한다.
- Controller를 보고 무슨 일을 하는지 한눈에 파악할 수 있게 작성한다.
- Service 로직은 오로지 Service 내부에서만 진행한다.
- 메서드 내부에 메서드 들여쓰기를 지양한다.
  - ex) model.addAttribute("memberList", roomService.getResponseRoomMemberList(room, member)); -> 반환타입과 메서드의 일에 대해 파악하기 어려움
  - List<ResponseRoomMemberList\> memberList = roomService.getResponseRoomMemberList(room, member); model.addAttribute("memberList", memberList); 반환타입과 변수명을 확인할 수 있도록 분리한다.


## 화면 구성

<details>
    <summary>화면 구성 펼쳐보기</summary>

### 메인

|메인화면|방생성|
|:-----:|:-----:|
|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/e74817e4-7cd1-4362-9d86-4d9108edb174" width="1000"/>|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/1410f6c5-d061-4074-bc23-61ade9d6d0d8">|
|로그인|채팅방 나가기|
|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/5be8ca57-2ec3-4e6a-a265-ed220e1b9371">|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/d5508481-63ba-420e-81cd-581922a9532e" style="height: 400px">|

1. 방 리스트 화면 스크롤을 통한 Get API 호출, Jpa Pageable 활용해 Pagenation 구현 [코드보기 search Method](https://github.com/KIMSEUNGWO/Room_Project/blob/develop/src/main/java/project/study/controller/MainController.java)
2. 검색조건 QueryDSL를 사용해 제목, 소개글, 태그내에서 검색 [코드보기 search Method](https://github.com/KIMSEUNGWO/Room_Project/blob/develop/src/main/java/project/study/repository/JoinRoomRepository.java)
3. 카카오 로그인 API 적용 [코드보기](https://github.com/KIMSEUNGWO/Room_Project/blob/develop/src/main/java/project/study/controller/api/kakaologin/KakaoLoginController.java)
4. 로그인로직 디자인패턴 - 생성패턴 - Factory Method Pattern 적용 [코드보기](https://github.com/KIMSEUNGWO/Room_Project/tree/develop/src/main/java/project/study/dto/login), [적용이유](https://tmd8635.notion.site/59124067d6ab413a84736cc73bf4432f)
5. 아이디, 비밀번호 찾기 SMS API 적용 [코드보기](https://github.com/KIMSEUNGWO/Room_Project/blob/develop/src/main/java/project/study/controller/api/sms/SmsController.java)

<br>

### 관리자
|이용중인 회원리스트|탈퇴한 회원리스트|
|:------:|:------:|
|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/126455161/206e0bdf-9a62-4aa1-96f8-3081802b82ad" width="" height=""/>|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/126455161/94253175-3f5c-48d3-8076-acf2c0ec7f45" width="" height=""/>|
|현재 생성된 방 리스트|신고 리스트|
|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/126455161/9fce0886-499a-4e5d-86bf-4ab4bca39de6" width="" height=""/>|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/126455161/20a07e51-7ca2-4341-bfe3-044362d12977" width="" height=""/>|
|신고내용 자세히 보기|신고당한 회원 정보 및 정지처리|
|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/126455161/6cf7bada-4205-47a8-bdc0-b615297c080e" width="" height=""/>|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/126455161/05f1942b-18d7-4ad4-b412-4f9e45bd00ce" width="" height=""/>|
1. 구현내용 자유롭게 추가
2.


<br>



### 채팅방
|채팅방화면|설정변경|
|:------:|:------:|
|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/9076534e-7204-41d2-bbb5-86eca0850d52" width="1000" height=""/>|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/971f5b96-f27f-4d1e-ad9f-3ecfd85dd00b" width="" height=""/>|
|공지사항변경|방장권한|
|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/8fd92229-77b4-4599-9fc3-4a1085f9c0dc" width="" height=""/>|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/6026aaff-29f7-4fa7-9853-d7f6306f6dec" width="" height=""/>|
|신고기능|비공개방 참여|
|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/9328b8f7-4821-48e9-a8ae-44ecd4d2df69" width="" height=""/>|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/2a9e5bdf-203f-4120-9e65-bc334c5fedc9" width="" height="400" />|

1. WebSocket 적용, 방 설정변경 -> MessageMapping 결과반환 [코드보기 JAVA](https://github.com/KIMSEUNGWO/Room_Project/blob/develop/src/main/java/project/study/chat/ChatController.java) [코드보기 JS](https://github.com/KIMSEUNGWO/Room_Project/blob/develop/src/main/resources/static/js/socket.js)
2. MessageType Enum으로 관리 [코드보기](https://github.com/KIMSEUNGWO/Room_Project/blob/develop/src/main/java/project/study/chat/MessageType.java)
3. 채팅방 접속 시 AccessToken 발급 [코드보기](https://github.com/KIMSEUNGWO/Room_Project/blob/develop/src/main/java/project/study/chat/component/ChatAccessToken.java)
4. 방장의 권한확인 코드 [코드보기](https://github.com/KIMSEUNGWO/Room_Project/blob/develop/src/main/java/project/study/authority/member/MemberAuthorizationCheck.java)

<br>

### 마이페이지
|마이페이지화면|휴대폰변경|
|:------:|:------:|
|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/b8b389bf-4a6a-4323-b2cc-410ab71492f9" width="" height=""/>|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/849f0aeb-db7b-40cb-8d38-37596062ff99" width="" height=""/>|
|비밀번호변경|계정삭제|
|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/849f0aeb-db7b-40cb-8d38-37596062ff99" width="" height=""/>|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/fe4fc60a-6878-49e9-9c69-b5fe0704b12e" width="" height=""/>|

1. 개인정보수정, 비밀번호 변경(소셜회원 제외), 계정 삭제 로직 [코드보기](https://github.com/KIMSEUNGWO/Room_Project/blob/develop/src/main/java/project/study/controller/MainController.java)

<br>

### 화상회의(구현중)
|화상채팅화면|
|:---:|
|<img src="https://github.com/KIMSEUNGWO/Room_Project/assets/128001994/e050ccc0-5917-4c36-9321-6d87c76942bd">|

1. 구현중

<br>

</details>

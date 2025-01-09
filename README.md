# API 명세-

## UserController API

| **Endpoint**          | **Method** | **Description**    | **Request Parameters**                          | **Response**                                                                                       |
|-----------------------|------------|--------------------|-------------------------------------------------|----------------------------------------------------------------------------------------------------|
| `/do`                 | `GET`      | 현재 사용자 역할 반환       | 없음                                              | `200 OK`: 사용자 역할 반환 (예: `"ROLE_USER"`, `"ROLE_ADMIN"`)                                     |
| `/join`               | `POST`     | 새로운 사용자 가입 처리      | `UserDto`                                       | `200 OK`: `"joined"` 반환 <br> `401 Unauthorized`: `"join failed"` 반환                           |
| `/user/update`        | `POST`     | 사용자 정보 업데이트        | `UserDto`                                       | `200 OK`: `"updated"` 반환 <br> `400 Bad Request`: `"update failed"` 반환                          |
| `/user/delete`        | `POST`     | 사용자를 삭제            | `username` (String)                             | `200 OK`: `"deleted"` 반환 <br> `400 Bad Request`: `"delete failed"` 반환                          |
| `/follow`             | `POST`     | 사용자를 팔로우하거나 팔로우 취소 | `username` (String)                             | `200 OK`: `"followed"` 반환 <br> `400 Bad Request`: `"follow failed"` 반환                         |
| `/follower/delete`    | `POST`     | 팔로워를 삭제            | `username` (String)                             | `200 OK`: `"deleted"` 반환 <br> `400 Bad Request`: `"delete failed"` 반환                          |
| `/followingList`      | `GET`      | 팔로잉 목록 조회 (페이징)    | `username` (String), `size` (int), `page` (int) | `200 OK`: `{ "userCnt": 총 사용자 수, "users": 사용자 목록 }` 반환                                   |
| `/followerList`       | `GET`      | 팔로워 목록 조회 (페이징)    | `username` (String), `size` (int), `page` (int) | `200 OK`: `{ "userCnt": 총 사용자 수, "users": 사용자 목록 }` 반환                                   |
| `/userPage`           | `GET`      | 사용자 페이지 정보 반환      | `username` (String)                             | `200 OK`: `{ "following": 팔로잉 수, "followers": 팔로워 수 }` 반환                                 |
| `/myReserve`          | `GET`      | 현재 예약 내역 조회        | 없음                                              | `200 OK`: `List<MyReserveDto>` 반환                                                               |
| `/myReserve/previous` | `GET`      | 지난 예약 내역 조회        | 없음                                              | `200 OK`: `List<MyReserveDto>` 반환                                                               |
| `/like/post`          | `GET`      | 좋아한 게시글목록 출력       | `username` (String), `page` (int), `size` (int) | `200 OK`: 검색된 게시글 목록 반환 (Page of `PostDto` `userDto`)                                          |

---

## ReserveController API

| **Endpoint**              | **Method** | **Description**                            | **Request Parameters**                                  | **Response**                                                                                       |
|---------------------------|------------|--------------------------------------------|-------------------------------------------------------|----------------------------------------------------------------------------------------------------|
| `/schedule`               | `GET`      | 영화 상영 스케줄 조회                     | `movieId` (int)                                       | `200 OK`: 상영 스케줄 목록 반환 (List of `ScheduleHallDto`)                                        |
| `/reservedSeat`           | `GET`      | 특정 상영 스케줄의 예약된 좌석 조회       | `scheduleId` (long)                                   | `200 OK`: 예약된 좌석 목록 반환 (List of `ReservedSeatDto`)                                       |
| `/reserve`                | `POST`     | 영화 예약 처리                            | `ReserveDto` (JSON)                                   | `200 OK`: `"Reserved Successfully"` 반환 <br> `400 Bad Request`: `"Reservation Failed"` 반환       |
| `/reserve/delete`         | `POST`     | 예약 취소                                 | `seatId` (String), `scheduleId` (long)               | `200 OK`: `"ReservedDelete Successfully"` 반환 <br> `400 Bad Request`: `"ReservedDelete Failed"`  |

---

## AdminController API

| **Endpoint**              | **Method** | **Description**                            | **Request Parameters**                                  | **Response**                                                                                       |
|---------------------------|------------|--------------------------------------------|-------------------------------------------------------|----------------------------------------------------------------------------------------------------|
| `/admin/userManage`       | `GET`      | 모든 사용자 관리 (페이징)                  | `size` (int), `page` (int)                             | `200 OK`: `{ "userCnt": 총 사용자 수, "users": 사용자 목록 }` 반환                                   |
| `/admin/join`             | `POST`     | 관리자로 사용자 가입                      | `UserDto`                                             | `200 OK`: `"joined"` 반환 <br> `400 Bad Request`: `"join failed"` 반환                              |

---

## PostController API

| **Endpoint**             | **Method** | **Description**                            | **Request Parameters**                                              | **Response**                                                                                   |
|--------------------------|------------|--------------------------------------------|----------------------------------------------------------------------|------------------------------------------------------------------------------------------------|
| `/posts/write`           | `POST`     | 게시글 작성 (이미지 파일 첨부 가능)         | `title` (String), `content` (String), `files` (List of MultipartFile)| `200 OK`: 성공 응답 반환                                                                        |
| `/posts/{postId}`        | `GET`      | 게시글 상세 조회 (댓글 포함 예정)           | `postId` (Long)                                                     | `200 OK`: 게시글 상세 정보 반환                                                                |
| `/posts/update/{postId}` | `PUT`      | 게시글 수정                                | `postId` (Long), `title` (String), `content` (String)               | `200 OK`: 수정된 게시글 반환                                                                   |
| `/posts/delete/{postId}` | `POST`     | 게시글 삭제                                | `postId` (Long)                                                     | `200 OK`: 삭제 성공 응답 반환                                                                  |
| `/posts/like`            | `POST`     | 게시글 좋아요                              | `postId` (Long), `username` (String)                                | `200 OK`: 좋아요 처리 성공                                                                     |
| `/posts/list/page`       | `GET`      | 게시글 전체 조회 (페이지네이션)            | `page` (int), `size` (int)                                          | `200 OK`: 페이지네이션된 게시글 목록 반환 (Page of `PostDto`)                                  |
| `/posts/search/title`    | `GET`      | 제목으로 게시글 검색                       | `keyword` (String), `page` (int), `size` (int)                      | `200 OK`: 검색된 게시글 목록 반환 (Page of `PostDto` `userDto`)                                          |
| `/posts/search/content`  | `GET`      | 내용으로 게시글 검색                       | `keyword` (String), `page` (int), `size` (int)                      | `200 OK`: 검색된 게시글 목록 반환 (Page of `PostDto` `userDto`)                                          |
| `/posts/search/username` | `GET`      | 작성자(username)로 게시글 검색             | `username` (String), `page` (int), `size` (int)                     | `200 OK`: 검색된 게시글 목록 반환 (Page of `PostDto` `userDto` )                                          |
| `/posts/search`          | `GET`      | 제목 또는 내용으로 게시글 검색             | `keyword` (String), `page` (int), `size` (int)                      | `200 OK`: 검색된 게시글 목록 반환 (Page of `PostDto` `userDto`)                                          |

---

## MovieController API

| **Endpoint**             | **Method** | **Description**       | **Request Parameters**                                | **Response**                                           |
|--------------------------|------------|-----------------------|-------------------------------------------------------|--------------------------------------------------------|
| `/movie`                 | `GET`      | 영화 랭킹                 | 없음                                                    | `200 OK`: 성공 응답 반환                                     |
| `/movie/{id}`            | `GET`      | 특정 영화 상세 페이지(리뷰 포함)   | `movieId` (Long)                                          | `200 OK`: 영화 상세 정보 및 리뷰 반환                             |

---

## ReviewController API

| **Endpoint**         | **Method** | **Description**   | **Request Parameters**            | **Response**       |
|----------------------|------------|-------------------|-----------------------------------|--------------------|
| `/movie/{id}/write`  | `POST`     | 영화 상세 페이지 내 리뷰 작성 | `reviewDto`, `movieId` (Long)     | `200 OK`: 성공 응답 반환 |
| `/movie/{id}/update` | `PUT`      | 리뷰 수정             | `reviewDto`, `movieId` (Long)     | `200 OK`: 영화 리뷰 수정 |
| `/movie/{id}/delete` | `POST`     | 리뷰 삭제             | `userId` (Long), `movieId` (Long) | `200 OK`: 영화 리뷰 삭제 |

---

## SearchController API

| **Endpoint**        | **Method** | **Description**   | **Request Parameters** | **Response**               |
|---------------------|------------|-------------------|------------------------|----------------------------|
| `/search/{word}`    | `GET`      | 통합검색(제목, 감독, 캐스팅) | `word` (String)        | `200 OK`: 성공 응답 반환         |

---

## DTO 클래스 정의

| **Class Name**         | **Field Name**     | **Type**           | **Description**                                     |
|------------------------|--------------------|--------------------|----------------------------------------------------|
| **MyReserveDto**       | `seatId`          | `String`           | 좌석 ID                                            |
|                        | `scheduleId`      | `Long`             | 상영 스케줄 ID                                     |
|                        | `method`          | `String`           | 결제 방법                                          |
|                        | `amount`          | `int`              | 결제 금액                                          |
|                        | `pDate`           | `String`           | 결제 날짜                                          |
|                        | `date`            | `String`           | 상영 날짜                                          |
|                        | `startTime`       | `String`           | 상영 시작 시간                                     |
|                        | `movieId`         | `int`              | 영화 ID                                            |
|                        | `day`             | `String`           | 상영 요일                                          |
|                        | `name`            | `String`           | 상영관 이름                                        |
| **ReservedSeatDto**    | `seatId`          | `String`           | 예약된 좌석 ID                                     |
| **ReserveDto**         | `method`          | `String`           | 결제 방법                                          |
|                        | `amount`          | `int`              | 결제 금액                                          |
|                        | `pDate`           | `String`           | 결제 날짜                                          |
|                        | `scheduleId`      | `long`             | 상영 스케줄 ID                                     |
|                        | `seatId`          | `String`           | 좌석 ID                                            |
| **ScheduleHallDto**    | `scheduleId`      | `Long`             | 상영 스케줄 ID                                     |
|                        | `hallId`          | `Long`             | 상영관 ID                                          |
|                        | `movieId`         | `int`              | 영화 ID                                            |
|                        | `startTime`       | `String`           | 상영 시작 시간                                     |
|                        | `date`            | `String`           | 상영 날짜                                          |
|                        | `day`             | `String`           | 상영 요일                                          |
|                        | `price`           | `int`              | 영화 가격                                          |
|                        | `name`            | `String`           | 상영관 이름                                        |
| **UserDto**            | `id`              | `String`           | 사용자 ID                                          |
|                        | `password`        | `String`           | 사용자 비밀번호                                    |
|                        | `role`            | `String`           | 사용자 역할 (`ROLE_USER`, `ROLE_ADMIN` 등)         |
|                        | `nickname`        | `String`           | 사용자 닉네임                                      |
|                        | `phone`           | `String`           | 사용자 전화번호                                    |
|                        | `birth`           | `String`           | 사용자 생년월일                                    |
| **UserPageDto**        | `following`       | `long`             | 팔로잉 수                                          |
|                        | `followers`       | `long`             | 팔로워 수                                          |
| **UserPagingDto**      | `userCnt`         | `Long`             | 전체 사용자 수                                     |
|                        | `users`           | `List<UserDto>`    | 사용자 목록                                        |
| **PostDto**            | `postId`           | `long`             | 게시글 ID                                          |
|                        | `userId`           | `long`             | 작성자 ID                                          |
|                        | `title`            | `String`           | 게시글 제목                                        |
|                        | `content`          | `String`           | 게시글 내용                                        |
|                        | `created`          | `String`           | 게시글 작성일                                      |
|                        | `cnt`              | `long`             | 게시글 조회수                                      |
|                        | `heart`            | `long`             | 게시글 좋아요 수                                    |
|                        | `fileAttached`     | `int`              | 첨부 파일 개수                                      |
|                        | `files`            | `List<String>`     | 첨부된 파일 목록                                    |
| **PostDto**            | `postId`           | `long`             | 게시글 ID                                          |
|                        | `userId`           | `long`             | 작성자 ID                                          |

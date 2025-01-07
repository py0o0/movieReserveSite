# API 

### UserController API
| **Endpoint**           | **Method** | **Description**                            | **Request Parameters**                                  | **Response**                                                                                       |
|------------------------|------------|--------------------------------------------|-------------------------------------------------------|----------------------------------------------------------------------------------------------------|
| `/do`                  | `GET`      | 현재 사용자 역할 반환                      | 없음                                                  | `200 OK`: 사용자 역할 반환 (예: `"ROLE_USER"`, `"ROLE_ADMIN"`)                                     |
| `/join`                | `POST`     | 새로운 사용자 가입 처리                   | `UserDto`                                             | `200 OK`: `"joined"` 반환 <br> `401 Unauthorized`: `"login failed"` 반환                           |
| `/user/update`         | `POST`     | 사용자 정보 업데이트                      | `UserDto`                                         | `200 OK`: `"updated"` 반환 <br> `400 Bad Request`: `"update failed"` 반환                          |
| `/user/delete`         | `POST`     | 사용자를 삭제                              | `username` (String)                                   | `200 OK`: `"deleted"` 반환 <br> `400 Bad Request`: `"delete failed"` 반환                          |
| `/follow`              | `POST`     | 사용자를 팔로우하거나 팔로우 취소          | `username` (String)                                   | `200 OK`: `"followed"` 반환 <br> `400 Bad Request`: `"follow failed"` 반환                         |
| `/flwDelete`           | `POST`     | 팔로워를 삭제                              | `username` (String)                                   | `200 OK`: `"deleted"` 반환 <br> `400 Bad Request`: `"delete failed"` 반환                          |
| `/followingList`       | `GET`      | 팔로잉 목록 조회 (페이징)                  | `username` (String), `size` (int), `page` (int)       | `200 OK`: `{ "userCnt": 총 사용자 수, "users": 사용자 목록 }` 반환                                   |
| `/followerList`        | `GET`      | 팔로워 목록 조회 (페이징)                  | `username` (String), `size` (int), `page` (int)       | `200 OK`: `{ "userCnt": 총 사용자 수, "users": 사용자 목록 }` 반환                                   |
| `/userPage`            | `GET`      | 사용자 페이지 정보 반환                   | `username` (String)                                   | `200 OK`: `{ "following": 팔로잉 수, "followers": 팔로워 수 }` 반환                                 |

---

### AdminController API
| **Endpoint**           | **Method** | **Description**                            | **Request Parameters**                                  | **Response**                                                                                       |
|------------------------|------------|--------------------------------------------|-------------------------------------------------------|----------------------------------------------------------------------------------------------------|
| `/admin/userManage`    | `GET`      | 모든 사용자 관리 (페이징)                  | `size` (int), `page` (int)                             | `200 OK`: `{ "userCnt": 총 사용자 수, "users": 사용자 목록 }` 반환                                   |
| `/admin/join`          | `POST`     | 관리자로 사용자 가입                      | `UserDto`                                         | `200 OK`: `"joined"` 반환 <br> `400 Bad Request`: `"join failed"` 반환                              |

---

### ReserveController API
| **Endpoint**           | **Method** | **Description**                            | **Request Parameters**                                  | **Response**                                                                                       |
|------------------------|------------|--------------------------------------------|-------------------------------------------------------|----------------------------------------------------------------------------------------------------|
| `/schedule`            | `GET`      | 영화 상영 스케줄 조회                     | `movieId` (int)                                       | `200 OK`: 상영 스케줄 목록 반환 (List of `ScheduleHallDto`)                                        |
| `/reservedSeat`        | `GET`      | 특정 상영 스케줄의 예약된 좌석 조회       | `scheduleId` (long)                                   | `200 OK`: 예약된 좌석 목록 반환 (List of `ReservedSeatDto`)                                       |
| `/reserve`             | `POST`     | 영화 예약 처리                            | `ReserveDto` (JSON)                                   | `200 OK`: `"Reserved Successfully"` 반환 <br> `400 Bad Request`: `"Reservation Failed"` 반환       |


### DTO 클래스 정의

| **Class Name**         | **Field Name**     | **Type**           | **Description**                                     |
|------------------------|--------------------|--------------------|----------------------------------------------------|
| **ReservedSeatDto**    | `seatId`          | `String`           | 예약된 좌석 ID                                     |
| **ReserveDto**         | `method`          | `String`           | 결제 방법                                          |
|                        | `amount`          | `int`              | 결제 금액                                          |
|                        | `pDate`           | `String`           | 결제 날짜                                          |
|                        | `scheduleId`      | `long`             | 상영 스케줄 ID                                     |
|                        | `seatId`          | `String`           | 좌석 ID                                            |
| **ScheduleHallDto**    | `scheduleId`      | `Long`             | 상영 스케줄 ID                                     |
|                        | `hallId`          | `Long`             | 상영관 ID                                          |
|                        | `movieId`         | `int`              | 영화 ID                                            |
|                        | `session`         | `int`              | 상영 횟수                                          |
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

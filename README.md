# API 

## UserController API
| **Endpoint**         | **Method** | **Description**                            | **Request Parameters**                           | **Response**                                                                                          |
|----------------------|------------|--------------------------------------------|------------------------------------------------|-------------------------------------------------------------------------------------------------------|
| `/do`               | `GET`      | 현재 사용자 역할 반환                      | 없음                                           | `200 OK`: 사용자 역할 반환 (예: `"ROLE_USER"`, `"ROLE_ADMIN"`)                                      |
| `/login`             | `POST`     | 로그인 처리                                | `username` (String), `password` (String)      | `200 OK`: `"joined"` 반환 <br> `401 Bad Request`: `"login failed"` 반환                              |
| `/join`             | `POST`     | 새로운 사용자 가입 처리                   | `username` (String), `password` (String)      | `200 OK`: `"joined"` 반환 <br> `400 Bad Request`: `"join failed"` 반환                              |
| `/user/delete`      | `POST`     | 사용자를 삭제                               | `username` (String)                            | `200 OK`: `"deleted"` 반환 <br> `400 Bad Request`: `"delete failed"` 반환                          |
| `/follow`           | `POST`     | 사용자를 팔로우하거나 팔로우 취소          | `username` (String)                            | `200 OK`: `"followed"` 반환 <br> `400 Bad Request`: `"follow failed"` 반환                         |
| `/flwDelete`        | `POST`     | 팔로워를 삭제                               | `username` (String)                            | `200 OK`: `"deleted"` 반환 <br> `400 Bad Request`: `"delete failed"` 반환                          |
| `/followingList`    | `GET`      | 팔로잉 목록 조회 (페이징)                     | `username` (String), `size` (int), `page` (int)| `200 OK`: 팔로잉 목록 반환 <br> **Response Body**: `{ "userCnt": 총 사용자 수, "users": 사용자 목록 }`      |
| `/followerList`     | `GET`      | 팔로워 목록 조회 (페이징)                     | `username` (String), `size` (int), `page` (int)| `200 OK`: 팔로워 목록 반환 <br> **Response Body**: `{ "userCnt": 총 사용자 수, "users": 사용자 목록 }`      |
| `/userPage`         | `GET`      | 사용자 페이지 정보 반환                    | `username` (String)                            | `200 OK`: 팔로워 및 팔로잉 수 반환 <br> **Response Body**: `{ "following": 팔로잉 수, "followers": 팔로워 수 }` |



## AdminController API

| **Endpoint**             | **Method** | **Description**                          | **Request Parameters**         | **Response**                                                                      |
|--------------------------|------------|------------------------------------------|--------------------------------|-----------------------------------------------------------------------------------|
| `/admin/userManage`            | `GET`      | 사용자 관리 정보 조회 (페이징)              | `size` (int), `page` (int)     | **Response Body**: `UserPageResponseDto` 객체 반환 <br> 포함된 데이터: `userCnt` (총 사용자 수), `users` (사용자 목록) |
| `/admin/join`        | `POST`       | 관리자 계정을 생성 | `username` (String), `password` (String) | `200 OK`: `"joined"` 반환 <br> `400 Bad Request`: `"join failed"` 반환                  |

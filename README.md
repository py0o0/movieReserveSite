# 목차

1. [프로젝트 목적](#프로젝트-목적)
2. [프로젝트 소개](#프로젝트-소개)
3. [기술 스택](#기술-스택)
4. [주요 기능](#주요-기능)
5. [API 명세](#api-명세)
6. [SnapShot](#snapshot)
7. [시연 영상](#시연-영상)
<br></br>

# 프로젝트 목적
리액트와 통신하는 rest api 스프링 서버 학습
<br></br>
# 프로젝트 소개

영화에 대한 정보를 얻을 수 있는 사이트입니다.

이 사이트는 사용자들이 사담을 나눌 수 있는 자유게시판, 영화 정보를 얻을 수 있는 영화 목록 게시판, 현재 상영 중인 영화를 예매할 수 있는 기능을 제공합니다.
<br></br>
## 개발 기간

25.01.06 ~ 25.01.13 (1주)
<br></br>
## 팀원

| Backend | Backend | Backend | Frontend | Frontend | 
|:-------:|:-------:|:-------:|:-------:|:-------:|
| <img src="https://github.com/user-attachments/assets/03b048bc-9299-4c6b-a084-57fbc3da9499" alt="증사 2" width="150" height="200"> | <img src="https://github.com/user-attachments/assets/44c5ca02-64c7-4a53-8e27-dc125462651d" alt="프로필" width="170" height="200"> |  <img src="https://github.com/user-attachments/assets/44c5ca02-64c7-4a53-8e27-dc125462651d" alt="프로필" width="170" height="200"> | <img src="https://github.com/user-attachments/assets/44c5ca02-64c7-4a53-8e27-dc125462651d" alt="프로필" width="170" height="200"> | <img src="https://github.com/user-attachments/assets/44c5ca02-64c7-4a53-8e27-dc125462651d" alt="프로필" width="170" height="200"> |
| [채승표](https://github.com/py0o0)  | [안병욱](https://github.com/ByeongukYun)  |[이은비](https://github.com/eunqoo)  |[한신](https://github.com/Shining17)  |[백욱진](https://github.com/ukjinSPACE)  |

<br></br>

## 역할 분담

### 🍊채승표

- **기능**
    - 유저 관련 기능
        -  회원 가입(관리자 포함), 로그인, 정보 수정, 탈퇴, 팔로우, 특정 유저의 팔로우/팔로워 불러오기, 유저 목록 불러오기, 접근 권한 설정 
    - 게시글 관련 기능
        -  게시글  crud (이미지 첨부 가능), 게시글 상세 확인, 게시글 좋아요, 특정 유저가 작성한 글 불러오기, 내가 좋아한 글 불러오기
    - 예매 관련 기능
        -   영화 스케쥴, 홀 정보, 좌석 정보 전처리, 영화 스케쥴 불러오기, 특정 스케쥴의 상영 홀 정보 불러오기, 예매하기, 내가 예매한 영화 불러오기
    - 영화 관련 기능
        -   찜 ,내가 찜한 영화 불러오기

<br>
    
### 👻윤병욱

- **기능**
    - 영화 데이터 전처리
        - ERD 스키마 <-> SQL DB간 대응
        - aws rds 인스턴스 운용
    - 영화 관련 기능
        - 영화 전체 목록 불러오기
        - 영화 평점 랭킹 TOP 10 불러오기
        - 검색(제목, 감독, 배우) 기능
        - 영화 상세 정보 (리뷰 포함)
    - 리뷰 관련 기능
        - 리뷰 CRUD

<br>

### 😎이은비

- **기능**
    - 게시글  crud, 게시글 상세 확인, 게시글 조회수, 댓글 crud

<br>

### 🐬한신

- **UI**
    - 페이지 : 게시글 페이지, 영화 목록 페이지, 예매 페이지

<br>

 ### 🦊백욱진

- **UI**
    - 페이지 : 마이 페이지, 관리자 페이지, 회원 관리 및 인증 페이지
   
<br>
<br>

 ## ERD
<img src= "https://github.com/user-attachments/assets/858655d4-eaad-4c74-9759-544628c65456" alt="DB" width="100%" height="auto">

<br>

# 기술 스택

## **백엔드**
- ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=Spring&logoColor=white)
- ![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat-square&logo=SpringSecurity&logoColor=white)
- ![JPA](https://img.shields.io/badge/JPA-6DB33F?style=flat-square&logo=Hibernate&logoColor=white)
- ![MyBatis](https://img.shields.io/badge/MyBatis-000000?style=flat-square&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAFklEQVR42mNgGAWjYBSMglEwCgAAGBQAE3AAhL8AAAAASUVORK5CYII=&logoColor=white)
- ![AWS RDS](https://img.shields.io/badge/AWS%20RDS-FF9900?style=flat-square&logo=AmazonAWS&logoColor=white)
- ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white)
- ![Java](https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white)

## **프론트엔드**
- ![React](https://img.shields.io/badge/React-61DAFB?style=flat-square&logo=React&logoColor=white)
- ![ES6](https://img.shields.io/badge/ES6-F7DF1E?style=flat-square&logo=JavaScript&logoColor=white)
- ![Bootstrap](https://img.shields.io/badge/Bootstrap-7952B3?style=flat-square&logo=Bootstrap&logoColor=white)
- ![AJAX](https://img.shields.io/badge/AJAX-0086FF?style=flat-square&logo=AJAX&logoColor=white)

## **협업 툴**
- ![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white)

## **개발 툴**
- ![VS Code](https://img.shields.io/badge/VS%20Code-007ACC?style=flat-square&logo=VisualStudioCode&logoColor=white)
- ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=flat-square&logo=IntelliJIDEA&logoColor=white)
  
 <br></br>
# 주요 기능

## 1. 회원 관리
- 회원가입, 로그인, 로그아웃, 회원탈퇴
- 회원 정보 수정  
- 팔로우/언팔로우 기능  

## 2. 게시판 기능
- 게시글 CRUD (작성, 조회, 수정, 삭제)  
- 댓글 작성 및 관리  
- 좋아요 기능  
- 조회수 카운트  
- 게시글 검색 (제목, 내용, 작성자 기반)  
- 이미지 파일 업로드 기능  

## 3. 영화 게시판 기능
- 전처리된 영화 정보 조회  
- 영화 리뷰 작성 및 조회  
- 영화 정보 검색  

## 4. 예매 기능
- 상영 중인 영화 스케줄 조회  
- 상영관 선택 및 영화 예매  

## 5. 마이페이지
- 좋아요한 글 목록  
- 작성한 글 목록  
- 팔로우/팔로워 리스트 조회  
- 찜한 영화 목록  
- 예매한 영화 내역 조회  

## 6. 관리자 페이지
- 유저 강제 탈퇴 기능  
- 관리자 계정 생성 기능  
 <br></br>

# API 명세

[API 명세 바로가기](https://patch-brochure-60e.notion.site/API-17a3e509776d8021ba21fec75f953ff0?pvs=4)

# SnapShot

[SnapShot 바로가기](https://patch-brochure-60e.notion.site/SnapShot-17e3e509776d80cea23cc7df82619998?pvs=74)

# 시연 영상

[시연 영상 바로가기](https://youtu.be/Pl9vYLuvfoY)

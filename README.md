# MEWSINSA
온라인 편집샵 무신사 서비스를 클론하여 만든 API 서버입니다.


## ✨  프로젝트 구조도
<img width="700" alt="image" src="https://github.com/f-lab-edu/mewsinsa/assets/64718002/5815aa75-f4bb-49fa-b659-1a466187447b">


- 로컬에서는 Redis를 JWT 저장소로 사용하고 있지만, 배포 버전에서는 MariaDB에 JWT를 저장하고 있습니다.
  - 추후 Docker를 통해 Redis를 연결하려는 계획을 하고 있습니다.
- Jenkins로 CI/CD 파이프라인 구축을 시도하고 있습니다.


## ✨  사용 기술
#### Framework
  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white">

#### Build Tool
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">

#### DBMS
  <img src="https://img.shields.io/badge/mariaDB-003545?style=for-the-badge&logo=mariaDB&logoColor=white"> ![redis](https://img.shields.io/badge/redis-F07A5B.svg?&style=for-the-badge&logo=redis&logoColor=white)

#### Deployment
  <img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white"> 



## ✨ ERD
![primary_mewsinsa](https://private-user-images.githubusercontent.com/64718002/327694326-c3f8cfaf-b96b-4c34-a6b3-ad21c526b5f0.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MzE5ODkyMzQsIm5iZiI6MTczMTk4ODkzNCwicGF0aCI6Ii82NDcxODAwMi8zMjc2OTQzMjYtYzNmOGNmYWYtYjk2Yi00YzM0LWE2YjMtYWQyMWM1MjZiNWYwLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDExMTklMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQxMTE5VDA0MDIxNFomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPThjNTg0MTQ0MWI2MDAwMjFiOTI3NDk5YWQzNWU4ZDA4YzVkNWFhOTc0NmJmYWE3MmM4OWExM2YyY2I2Mjk1MTMmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0In0.cqYPsTYu3hK0b77OPJr2IGL5HMLboNrG4iSHl7dz1Pc)



## ✨  API 명세
[Wiki API Design 보러가기](https://github.com/clean2001/mewsinsa/wiki/4.-API-Design)


## ✨  프로젝트 중점 사항
- 무신사의 상품 전시, 프로모션, 주문, 배송 정책을 분석/설계/구현하며 **리테일 서비스에 대한 전반적인 이해**를 목적으로 진행하였습니다.
- `Spring Security`를 이용하지 않고 직접 Jwt 인증을 구현함으로써 **Jwt 인증 프로세스를 정확히 이해**하고자 하였습니다.
- `Lombok` 라이브러리를 쓰지 않고 직접 getter, setter, constructor, builder 등을 구현함으로써, `Lombok` 라이브러리의 내부 동작을 이해하고자 하였습니다.
- 커밋 컨벤션을 준수하고, 이해하기 쉬운 문서(README.md, Wiki) 작성에 집중하였습니다.


## 프로젝트 위키
프로젝트에 대한 자세한 내용은 [Project Wiki](https://github.com/clean2001/mewsinsa/wiki)에서 확인하실 수 있습니다.


# Spring Security OAuth 2
- 학습 목표 : OAuth2 인증/인가 및 JWT 이해 및 심화
- 선수 학습
  - Spring Security Core

---

# 02장: OAuth 2.0 용어 이해

- <a href="/note/02장 - OAuth 2.0 용어 이해/2.1 OAuth 2.0 소개.md" target="_blank">2.1 OAuth 2.0 소개</a>
- <a href="/note/02장 - OAuth 2.0 용어 이해/2.2 OAuth2 오픈 소스 - Keycloak 설치 및 설정.md" target="_blank">2.2 OAuth2 오픈 소스 - Keycloak 설치 및 설정</a>
- <a href="/note/02장 - OAuth 2.0 용어 이해/2.3 OAuth 2.0 Roles 이해.md" target="_blank">2.3 OAuth 2.0 Roles 이해</a>
- <a href="/note/02장 - OAuth 2.0 용어 이해/2.4 OAuth 2.0 Client Types 이해.md" target="_blank">2.4 OAuth 2.0 Client Types 이해</a>
- <a href="/note/02장 - OAuth 2.0 용어 이해/2.5 OAuth 2.0 Token Types 이해.md" target="_blank">2.5 OAuth 2.0 Token Types 이해</a>

---

# 03장: OAuth 2.0 권한부여 유형

- <a href="/note/03장 - OAuth 2.0 권한부여 유형/3.1 OAuth 2.0 Grant Type 개요.md" target="_blank">3.1 OAuth 2.0 Grant Type 개요</a>
- <a href="/note/03장 - OAuth 2.0 권한부여 유형/3.2 OAuth 2.0 매개변수 용어.md" target="_blank">3.2 OAuth 2.0 매개변수 용어</a>
- <a href="/note/03장 - OAuth 2.0 권한부여 유형/3.3 Authorization Code Grant Type - 권한 부여 코드 승인방식.md" target="_blank">3.3 Authorization Code Grant Type - 권한 부여 코드 승인방식</a>
- <a href="/note/03장 - OAuth 2.0 권한부여 유형/3.4 Implicit Grant Type - 암묵적 승인 방식.md" target="_blank">3.4 Implicit Grant Type - 암묵적 승인 방식</a>
- <a href="/note/03장 - OAuth 2.0 권한부여 유형/3.5 Resource Owner Password Credentials Grant Type - 패스워드 자격증명 승인 방식.md" target="_blank">3.5 Resource Owner Password Credentials Grant Type - 패스워드 자격증명 승인 방식</a>
- <a href="/note/03장 - OAuth 2.0 권한부여 유형/3.6 Client Credentials Grant Type - 클라이언트 자격증명 승인 방식.md" target="_blank">3.6 Client Credentials Grant Type - 클라이언트 자격증명 승인 방식</a>
- <a href="/note/03장 - OAuth 2.0 권한부여 유형/3.7 Refresh Token Grant Type - 리프레시 토큰 승인 방식.md" target="_blank">3.7 Refresh Token Grant Type - 리프레시 토큰 승인 방식</a>
- <a href="/note/03장 - OAuth 2.0 권한부여 유형/3.8 PKCE-enhanced Authorization Code Grant Type - PKCE 권한부여 코드 승인 방식.md" target="_blank">3.8 PKCE-enhanced Authorization Code Grant Type - PKCE 권한부여 코드 승인 방식</a>

---

# 04장: OAuth 2.0 Open ID Connect

- <a href="/note/04장 - OAuth 2.0 Open ID Connect/4.1 OAuth 2.0 Open ID Connect - 개요 및 특징.md" target="_blank">4.1 OAuth 2.0 Open ID Connect - 개요 및 특징</a>
- <a href="/note/04장 - OAuth 2.0 Open ID Connect/4.2 OAuth 2.0 Open ID Connect - ID Token & Scope.md" target="_blank">4.2 OAuth 2.0 Open ID Connect - ID Token & Scope</a>

---

# 05장: OAuth 2.0 Client


- <a href="/note/05장 - OAuth 2.0 Client/5.1 스프링 시큐리티 - OAuth 2.0 관련 프로젝트들.md" target="_blank">5.1 스프링 시큐리티 - OAuth 2.0 관련 프로젝트들</a>
- <a href="/note/05장 - OAuth 2.0 Client/5.2 OAuth 2.0 Client 모듈.md" target="_blank">5.2 OAuth 2.0 Client 모듈</a>

---

# 06장: OAuth 2.0 Client 기초

- <a href="/note/06장 - OAuth 2.0 Client 기초/6.1 클라이언트 앱 시작하기 - application.yml & OAuth2ClientProperties.md" target="_blank">6.1 클라이언트 앱 시작하기 - application.yml & OAuth2ClientProperties</a>
- <a href="/note/06장 - OAuth 2.0 Client 기초/6.2 ClientRegistration 이해 및 활용.md" target="_blank">6.2 ClientRegistration 이해 및 활용</a>
- <a href="/note/06장 - OAuth 2.0 Client 기초/6.3 ClientRegistrationRepository 이해 및 활용.md" target="_blank">6.3 ClientRegistrationRepository 이해 및 활용</a>
- 자동설정에 의한 초기화 과정 이해

---

# 07장: OAuth 2.0 Client - oauth2Login()
- OAuth2LoginConfigurer 초기화 이해
- OAuth2 로그인 구현 - OAuth 2.0 Login Page 생성
- OAuth2 로그인 구현 - Authorization Code 요청하기
- OAuth2 로그인 구현 - Access Token 교환하기
- OAuth2 로그인 구현 - Oauth 2.0 User 모델 소개(1)
- OAuth2 로그인 구현 - Oauth 2.0 User 모델 소개(2)
- OAuth2 로그인 구현 - UserInfo 엔드포인트 요청하기
- OAuth2 로그인 구현 - OpenID Connect 로그아웃
- OAuth2 로그인 구현 - Spring MVC 인증 객체 참조하기
- API 커스텀 구현 -Authorization BaseUrl & Redirection BaseUrl
- API 커스텀 구현 - OAuth2AuthorizationRequestResolver(1)
- API 커스텀 구현 - OAuth2AuthorizationRequestResolver(2)

---

# 08장: OAuth 2.0 Client - oauth2Client()
- OAuth2ClientConfigurer 초기화 이해
- OAuth2AuthorizedClient 이해 및 활용
- DefaultOAuth2AuthorizedClientManager 소개 및 특징
- DefaultOAuth2AuthorizedClientManager 기본 환경 구성
- DefaultOAuth2AuthorizedClientManager - Resource Owner Password 권한 부여 구현하기(1)
- DefaultOAuth2AuthorizedClientManager - Resource Owner Password 권한 부여 구현하기(2)
- DefaultOAuth2AuthorizedClientManager - Client Credentials 권한 부여 구현하기
- DefaultOAuth2AuthorizedClientManager - Refresh Token 권한 부여 구현하기
- DefaultOAuth2AuthorizedClientManager -필터 기반으로 구현하기
- @RegisteredOAuth2AuthorizedClient 이해 및 활용

---

# 09장: OAuth 2.0 Client - Social Login (Google, Naver, KaKao) + FormLogin
- OAuth 2.0 Social Login 연동 구현 (1)
- OAuth 2.0 Social Login 연동 구현 (2)
- OAuth 2.0 Social Login 연동 구현 (3)
- OAuth 2.0 Social Login 연동 구현 (4)
- OAuth 2.0 Social Login 연동 구현 (5)
- OAuth 2.0 Social Login 연동 구현 (6)

---

# 10장: OAuth 2.0 Resource Server
- OAuth 2.0 Resource Server 소개 및 프로젝트 생성
- Resource Server 시작하기 - application.yml / OAuth2ResourceServerProperties
- AuthenticationEntryPoint
- 자동설정에 의한 초기화 과정

---

# 11장: OAuth 2.0 Resource Server API - jwt()
- JWT API 설정 및 검증 프로세스 이해
- JwtDecoder 소개 및 세부 흐름
- JwtDecoder 생성 방법

---

# 12장: OAuth 2.0 Resource Server - 검증 기초

---

# 13장: OAuth 2.0 Resource Server - MAC & RSA 토큰 검증

---

# 14장: OAuth 2.0 Resource Server - 리소스 서버 권한 구현

---

# 15장: OAuth 2.0 Resource Server - opaque()

---

# 16장: OAuth 2.0 Client + OAuth 2.0 Resource Server 연동

---

# 17장: Spring Authorization Server

---

# 18장: Spring Authorization Server - 주요 도메인 클래스

---

# 19장: Spring Authorization Server - 엔드포인트 프로토콜

---

# 20장: OAuth 2.0 Client + Resource Server + Authorization Server 연동

---

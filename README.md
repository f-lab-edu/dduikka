# 뛰까

## 프로젝트 소개

위치 기반 날씨를 제공하는 러너들을 위한 커뮤니티, 뛰까 입니다.

사용자는 자신이 저장한 위치를 기반으로 날씨와 대기정보를 조회할 수 있습니다.

오늘 자신이 뛸지, 말지 투표할 수 있으며 투표한 내역은 익명 채팅에 표현됩니다.

익명 채팅을 통해 같이 달리는 친구들과 동질감을 느껴보세요!

---

## 목표
- 유지보수에 용이하고 확장하기 쉬운 구조로 구현하고자 합니다.
- 애플리케이션 구현에 집중하기 위해 백엔드에 집중하였습니다.
- 냄새 나는 코드는 지양합니다. 읽기 좋은 코드를 고려합니다.
- 현 프로젝트 상황에 맞는 성능 개선 방법을 지향합니다.
- 테스트 코드를 통해 프로젝트의 전반적인 품질을 향상시키고자 합니다.
  
---

## Stack

**BACKEND**

![](https://img.shields.io/badge/-java-3776AB?logo=java&logoColor=white&style=for-the-badge)
![](https://img.shields.io/badge/-SPRINGBOOT-6DB33F?logo=springboot&logoColor=white&style=for-the-badge)
![](https://img.shields.io/badge/-spring_rest_docs-6DB33F?logo=springboot&logoColor=white&style=for-the-badge)
![](https://img.shields.io/badge/-mysql-4479A1?logo=mysql&logoColor=white&style=for-the-badge)
![](https://img.shields.io/badge/-junit5-25A162?logo=junit5&logoColor=white&style=for-the-badge)
![](https://img.shields.io/badge/-mockito-25A162?logo=mockito&logoColor=white&style=for-the-badge)

**INFRA**

![](https://img.shields.io/badge/-NAVER_CLOUD-6DB33F?logo=navercloud&logoColor=white&style=for-the-badge)
![](https://img.shields.io/badge/-jenkins-D24939?logo=jenkins&logoColor=white&style=for-the-badge)

<br>

## ERD

![ERD](/dduikkaImg/dduikka.png)

<br>

## Flow
![github_flow](/dduikkaImg/github_flow.png)

짧은 주기로 배포하기에 좋으며 빠른 피드백을 받을 수 있어, 1개의 릴리즈 버전만이 존재하는 소규모 프로젝트 특성 상 github flow를 사용하였습니다.

<br>

## Convention
### commit message convention
|커밋 유형|의미|
|------|---|
|Feat|새로운 기능 추가|
|Fix|버그 수정|
|Docs|문서 수정|
|Style|코드 formatting, 세미콜론 누락, 코드 자체의 변경이 없는 경우|
|Refactor|코드 리팩토링|
|Test|테스트 코드, 리팩토링 테스트 코드 추가|
|Chore|패키지 매니저 수정, 그 외 기타 수정 ex) .gitignore|
|Comment|필요한 주석 추가 및 변경|
|Rename|파일 또는 폴더 명을 수정하거나 옮기는 작업만인 경우|
|Remove|파일을 삭제하는 작업만 수행한 경우|



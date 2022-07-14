# weato_backend-SpringBoot-


### 20220705
<strong>변경사항</strong>
<ul>
  <li>like 분리 -> PostLike, CommentLike</li>
  <li>tag class 정리 - Tag(class), TagType(enum)을 통한 멤버 - 태그 - 뉴스레터 간 연관관계 설정</li>
  <li> Domain layer 거의 마무리, </li>
  <li>기타 각종 엔티티의 메소드들 정리. </li>
</ul>
아쉬운 점 : DDD에 대한 이해 부족. Aggregate가 뭐지 정확히..? 이걸 어떻게 정확히 분류하고 Root 을 정하지??

아마존 s3 이용에 대하여 -> html을 파일이 아닌 body 부분을 string으로 저장할 계획. 굳이 s3에 html(정적파일) 저장..?

DB관련 -> 현재 고민중인 부분은 그래서 EC2에 DB를 MySQL로 바로 올릴지, AWS Aurora DB를 사용할 지 고민중입니다.

AWS 적극적으로 이용 예정
AWS EC2, S3, Lambda, Route53, (AuroraDB), (Docker), Cloudwatch, SES, 

SES는 현재 샌드박스 내부. 24시간 200통/ 각 메일 당 전송시간 1초 -> 사례를 조금 쌓아서 Bounces and complaints control 하고 샌드박스 해제 재신청




### 220715

리드미 제대로 작성 안하고 살았던 것 반성

진행 사항들

1. 웹사이트 api 관련 구현 거의 완료 -> 기능적 구현만을 의미. 쿼리 최적화 및 페이징 혹은 더보기 구현 안함

2. 소셜로그인 방식 SPA스럽게 변경해야함..

3. DB 실제로 쓸 MYSQL 변경 준비하기

4. 뉴스레터를 다루는 방식에 대해

5. 기타 사항들 잠깐씩 수정
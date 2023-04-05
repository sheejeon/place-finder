# place-finder
- place-finder는 장소를 검색하고, 검색 키워드 목록을 제공합니다.

# Installation

To install the library to your local Maven repository, simply execute:

```shell
mvn clean install
```

## Business Service
- findAll \
장소와 결과 목록 개수(default: 10)를 입력 받아 kakaoAPI, naverAPI를 각각 호출하여 검색 결과를 구성합니다. \
이 때, 검색 결과는 kakaoAPI, naverAPI에서 받아온 주소를 동일한 장소인지 판별합니다. \
현재 장소이름으로 동일 여부를 판단하고 있으나, \
application.yml 파일 내 place-finder.api.google.use 설정을 true로 변경할 경우 주소값으로 googleAPI를 통해 동일한 장소인지 체크할 수 있습니다.
동일한 장소일 경우 우선 배치되어 반환됩니다.
- ranking \
가장 많이 검색된 순서대로 키워드 목록을 반환합니다. 이 때, 목록 개수(default: 10)를 입력 받을 수 있습니다.

## API Test
PlaceFinder.http 파일을 이용하여 api 테스트를 수행할 수 있습니다.

## develop
### 확장성
- 장소명 뿐만 아니라 추가적으로 장소에 대한 정보를 반환하고 싶을 경우를 대비하여 Place 도메인을 정의하여 반환 값을 손쉽게 수정할 수 있습니다.
- size를 10로 지정해놓지 않고, 입력값으로 받아 해당 size만큼 반환합니다.

### 동시성
여러 사용자가 동시에 검색 서비스를 이용하면 검색 로그와 검색어 정보가 계속해서 쌓이게 되고, \
하나의 테이블에 모든 정보가 기록된다면 테이블 락 등의 문제로 인해 검색 서비스의 성능이 저하될 가능성이 있습니다. \
따라서 검색 로그와 검색어 정보를 별도의 테이블로 분리하여 안정성을 높였습니다.

### 추상화
kakao, naver API 등 API가 추가됨에 따라 호출코드가 중복됩니다. \
이를 WebClientService에 추상화 시켜 호출 부분을 share 영역으로 분리하였습니다. \
WebClient 호출 시 필요한 url, headers, parameters 등의 정보도 ApiRequest라는 모델에 담을 수 있도록 추가하였습니다.

### 캡슐화
ArrayList<Place>를 PlaceList로 캡슐화 하여 다음과 같은 기대효과를 얻을 수 있습니다.
- 코드의 가독성을 향상시킵니다.
- PlaceList 클래스에 새로운 기능을 추가할 때 유연한 코드를 작성할 수 있습니다.
- 코드에서 ArrayList<Place>를 사용하는 부분이 여러 군데에 분산되어 있다면, 이를 수정해야 하는 경우 중복된 작업을 해야합니다. 하지만 PlaceList를 사용하면, 중복된 코드를 제거할 수 있습니다.
- 클래스 이름으로 더욱 명시적으로 사용 목적을 알 수 있습니다. 이를 통해 코드를 보다 명확하게 이해하고, 관리할 수 있습니다.

### 가독성
- value object를 사용하여 이름만으로도 어떤 값인지 명확하게 알 수 있도록 하였습니다.
- 코드를 간결하게 하여 메소드 내에서 어떤일을 하는지 한눈에 알 수 있게 개발하였습니다.
- 명확하고 간결한 변수, 함수 이름을 사용하였습니다.
- 코드 일관성을 위해 @JsonProperty를 이용하여 Camel case로 통일시켰습니다.

### dependency 버전 관리 및 사용
#### 버전 관리
- property를 이용하여 사용하는 라이브러리에 대한 버전을 명시하여 관리합니다.
#### 사용
- spring-web: HTTP 요청 및 응답 처리를 위해 사용
- spring-boot-starter-webflux: kakao, naver API 연동을 위해 사용
- spring-boot-starter-data-jpa: 검색 목록 제공 시 객체와 데이터베이스를 매핑시키기 위해 사용
- spring-boot-starter-test: 테스트 코드 작성을 위해 사용
- lombok: 반복적인 코드 작성과 실수를 줄이기 위해 사용, 개발 시간을 단축시킬 수 있음
- h2: 검색 목록 데이터를 저장하기 위해 사용
- google-maps-services: google API를 사용하기 위해 사용(같은 장소인지 판단할 때 사용)
- junit: 테스트 코드 작성을 위해 사용

### 예외 처리
#### global 예외 처리
- @RestControllerAdvice 어노테이션으로 전역 예외 처리를 수행하는 클래스를 생성하였습니다.
- 예외 처리 결과를 ResponseEntity 객체로 감싸서 반환하여 클라이언트에서 HTTP 응답으로 예외 처리 결과를 받을 수 있습니다.

#### webClient 예외 처리
- onErrorResume() 메소드를 사용하여 HTTP 요청 중 발생한 예외를 처리하고, 적절한 Mono를 반환합니다.
- 응답을 받았지만, statusCode가 200이 아닐 경우에도 예외 처리 합니다.



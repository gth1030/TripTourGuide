# TripTourGuide

컴퓨터 구성 / 필수 조건 안내 (Prerequisites)

Android OS
Android 9.0 이상





설치 안내 (Installation Process)

사용법 (Getting Started)




파일 정보 및 목록 (File Manifest)

저작권 및 사용권 정보 (Copyright / End User License)

Copyright 2019 

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


배포자 및 개발자의 연락처 정보 (Contact Information)

김기태 010-4817-9203
하혜성 010-2083-5880


알려진 버그 (Known Issues)

Google Youtube API Quota exceed error - 앱 실행후 앱이 유투브에서 곡 이름을 검색하여 가져오는데 Youtube API가 사용됩니다. 다만 매일 할당된 Quota양이 상당히 작기 때문에 Quota가 넘어가면 검색이 안되고
새로운 국가의 노래에 대한 Youtube 비디오 id를 가져오지 못하는 상황이 발생합니다.
해결방법: 현재 앱에서는 하나의 국가에 대해 한번이라도 API 콜을 실시하면 그것을 내부 SQLite 데이터 베이스에 저장을 하여 반복된 콜로 인한 quota 낭비를 방지합니다. 이것은 추후 별개의 서버를 구축하여 
거기에 국가별 비디오 id를 저장함으로서 quota에 상관없이 최신 뮤직비디오의 비디오 ID를 가져올수 있게 됩니다.

데이터 부족으로 인한 서비스 제한구역존재 - 현재 앱에 존재하는 데이터의 부족으로 미국, 캐나다, 싱가포르 몇 도시를 제외한 다른 지역은 서비스를 재대로 제공하지 않습니다. 
해결방법: 이 문제는 추가적인 데이터를 수집하여 앱에 접목함으로 쉽게 해결될 수 있습니다.




문제 발생에 대한 해결책 (Troubleshooting)



크레딧 (Credit)

업데이트 정보 (Change Log)


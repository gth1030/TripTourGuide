# TripTourGuide

##�Ұ�
TripTourTravel��
- ������� ������ ������ ������ ��, ��, �Ŀ� ������ ������ �ִ� �ȵ���̵� App�Դϴ�.
- ����ڰ� ������ ������ �� �Ͼ �� �ִ� ������ ��Ȳ(���� ��ǰ ����, ì���� ���� �غ� ��)�� �������ݴϴ�.
- ���� ������ �α� ���� Top 20�� ���������ν� ������ ���� ���� ��ȭ�� �� ���� ������ �޾Ƶ帱 �� �ְ� �����ݴϴ�.
- �ٳ�� ���ø� ������ ǥ���� �� ���� �����־� ����ڿ��� ������ �ǵ��ƺ��� �ð��� �����մϴ�.


## ���߹��
![koreatourchart](./img/koreatourchart.jpg)
���ѹα��� �ؿ� ������ ��ġ�� �ų� ��� 10%�� ������� ���̰� �ֽ��ϴ�.
![traveler](./img/traveler.jpg)
�̷� ���並 �������� ���� �پ��� ���� ���� TV ���α׷����� �����Դϴ�.
![searchresult](./img/searchresult.jpg)
���� ���忡�� �̷��� �帧�� ���� ���� ������ �������� ��κ��� ������ '����'���� �����̸� ������� ���� ������ �����ϰ� �������ִ� ������ ������ �ʾҽ��ϴ�.
![medicinejail](./img/medicinejail.jpg)
���౹������ ��� ������ ������ �Ұ����� �ľ��� ���Ͽ� ������ ���� �� �������� ���� ��ʰ� �����ϰ� �ֽ��ϴ�.

���� ����� ������� ������ ��� �� �ְ� �����ϴ� ������ �����ϰ� �Ǿ����ϴ�.

## ��ǻ�� ���� / �ʼ� ���� �ȳ� (Prerequisites)

�ּ� SDK ���� 26
Android OS
Android 9.0 �̻�





## ��ġ �ȳ� (Installation Process)
### Android Studio�� �ִ°��
#### 1. ������Ʈ Ŭ��
- ` $ git clone https://github.com/osam2019/APP_TripTourTravel.git`
- ` $ cd TripTourGuide`

#### 2. Android Studio�� ���� APK ���� ����
- Android Studio�� ������Ʈ ������ ���� Build > Build Bundle(s) / APK(s) > Build APK(s) �� �̿��Ͽ� APK ������ �����մϴ�

#### 3. APK ���� ����
- `.\TripTourGuide\TripTourGuide\app\build\outputs\apk\release` ���� APK ������ Android ����� ����� ����̽��� �����ϰ� ��ġ�Ͻø� ���� �����Ͻ� �� �ֽ��ϴ�

### Android Studio�� ���°��
#### 1. ������Ʈ Ŭ��
- ` $ git clone https://github.com/osam2019/APP_TripTourTravel.git`
- ` $ cd TripTourGuide`
#### 2. gradlew �ٿ�ε�
[Gradle ���� ����Ʈ](https://docs.gradle.org/current/userguide/installation.html) ���� ���̵���ο� ���� Gradle�� ��ġ �մϴ�.
#### 3. gradlew �� �̿��Ͽ� APK ���ϻ���
- ������Ʈ ������ �� �� `.\TripTourGuide` 
- ������ gradle ��ɾ �����մϴ� `gradlew assembleRelease`
- ������ APK ������ Android ����� ����� ����̽��� �����ϰ� ��ġ�Ͻø� ���� �����Ͻ� �� �ֽ��ϴ�


##���� (Getting Started)
![main](./img/main.jpg)
������ �������� �� �ʱ�ȭ���Դϴ�. ����� ������� ����Ʈ�� Ȯ���� �� �ֽ��ϴ�. '+new Trip'�� Ŭ���Ͽ� ���ο� ������ �߰��� �� �ֽ��ϴ�.
![beforeaddcity](./img/beforeaddcity.jpg)
'+new Tip'�� Ŭ������ �� ����Ǵ� ���� �߰��� ���� ����ȭ���Դϴ�. '+Add City!'�� Ŭ���Ͽ� ������ ���� ���ø� �����ϸ� �˴ϴ�.
![addingcity](./img/addingcity.jpg)
'+Add City'�� Ŭ������ �� ����Ǵ� ���� ������ �����ϴ� ȭ���Դϴ�. ���� ��� �� ���� ��¥�� �����մϴ�. �� ���� ���౹��, ��, ���� ������ ������ �ϸ� �ش� ���ÿ��� ��� �� �ִ� Ȱ��(Activity)�� ǥ�õ˴ϴ�. ���ϴ� Ȱ���� ��� ������ �� '���ο� ���� �߰��ϱ�!'�� Ŭ���Ͽ� ������ �������մϴ�.
![addedcity](./img/addedcity.jpg)
������ ���� ���ø� ��� �߰����� ���� ȭ���Դϴ�. ���� ���� �߰��� ���ƴٸ� '�� ���� �����!'�� Ŭ���Ͽ� ���� ������ ������ �����մϴ�.
![namingtrip](./img/namingtrip.jpg)
'�� ���� �����!'�� Ŭ������ �� ����Ǵ� dialog�Դϴ�. ������ �ۼ��� �� 'OK'��ư�� ���� ���� �߰��� �������մϴ�.
![preparingsupply](./img/preparingsupply.jpg)
���� ������ Ŭ���Ͽ��� �� ����Ǵ� ì�ܾ� �� �غ� ����Ʈ ���� ȭ���Դϴ�. Ȯ���ϰ� ì�� �غ��� ������ �����̵��Ͽ� '�غ��' ���·� ������ �� �ֽ��ϴ�. ����� �ؽ�Ʈ�� ���� �� ���� �غ��� ���Ҵ��� �ľ��� �� �ֽ��ϴ�.
![completepreparingsupply](./img/completepreparingsupply.jpg)
��� �غ��� ì���� �� ����� �غ� ȭ���Դϴ�. �غ��� ��� '�غ��' ���·� ������ �� ����Ʈ�� �׸���� �����ϰ� ���ϸ� ���� ��� �׸��� ��Ÿ���� �˴ϴ�. ���� ����� �ؽ�Ʈ�� ������ ����Ǹ� �ؽ�Ʈ�� Ŭ���� �� ���� �������� ������ �����ϰ� �ִ� ��ǰ���� �� �� �ֽ��ϴ�.
![prohibiteditems](./img/prohibiteditems.jpg)
�غ� �غ� ��ġ�� ����� �ؽ�Ʈ�� Ŭ���� �� ����Ǵ� �ش� ������ ���� ���� ��ǰ ����Ʈ�Դϴ�. �����ܵ��� Ŭ���� �� ��� ��ǰ�� �� ������ �Ұ��� ���� ���� ������ �����˴ϴ�.
![musicplaying](./img/musicplaying.jpg)
����� ActionBar���� �ι�° �������� Ŭ���� �� ����Ǵ� ���� �α� ���� ��� ȭ���Դϴ�. �� ȭ�鿡���� ������ ���� ������ �α� ���� ��Ʈ�� 'Last.fm'���� API�� ���� �޾ƿɴϴ�. �� ��Ʈ�� �Ƿ��ִ� ������ 'Youtube'�� ���� �������� ���� �� �������� ����մϴ�.
![consulateinfo](./img/consulateinfo.jpg)
����� ActionBar���� ������ �������� Ŭ���� �� ����Ǵ� dialog�Դϴ�.  ������ ���� ���ÿ��� ������ ��Ȳ�� �߻����� �� ������ û�� �� �ִ� ������� ������ �����մϴ�.
![mappingcity](./img/mappingcity.jpg)
����� ActionBar���� ����° �������� Ŭ���� �� ����Ǵ� ���� ���ø� ������ ǥ���� �ִ� ����Դϴ�.  'Google Map'�� API�� Ȱ���� ����̸� � ���ø� �����ߴ��� ������ ���� ����ڿ��� �Ѵ��� ǥ�����ݴϴ�.




## ���� ���� �� ��� (File Manifest)
- MarkedMapFragment - Google Map ���񽺸� �����ϴ� �����׸�Ʈ �Դϴ�
- PrepItemFragment - ������ �غ� ����Ʈ�� �並 �����ϴ� �����׸�Ʈ �Դϴ�.
- ProhebitItemFragment - ���� ���� ��ǰ ����Ʈ�� �����ϴ� �����׸�Ʈ �Դϴ�
- YoutubeFragmentX - �������� ����� ���� ��Ʃ�θ� �����ϴ� �����׸�Ʈ�Դϴ�
- ActivityListGriViewAdapter - ���������� ������ ���� Ȱ������ �̹����� �����ϴ� ����Դϴ�
- TripCreatorListAdapter - ���ο� ������ ���鶧 ���� ����� ���ø���� �����ϴ� ����Դϴ�
- YtListener - ��Ʃ�� �÷��̾�� ���� �÷��� ����Ʈ ������Ʈ Ȱ���� �����ϰ� �������� ����Ʈ �並 �����ϴ� ������ �Դϴ�
- YtPlayerDtateChangeListener - ��Ʃ�� ���� ���º�ȭ�� �����ϰ� �������� ����Ʈ �並 ������Ʈ �ϴ� ������ �Դϴ�
- DBopenHelper - SQLite �����ͺ��̽��� �����ϰ� �۽��࿡�� ������ ���̽� ����� ���õ� �Լ����� �����ϴ� Ŭ�����Դϴ�
- MainActivity - ���� �⺻ȭ���� �����ϰ� ���� �������� ������� �����ݴϴ�
- MusicRankCollector - �� ������ ���� ��ŷ ��Ʈ�� API�� ���� �����ɴϴ�
- TripCreator - ���ο� ������ ���鶧 �ʿ��� ��� ��ɵ��� �����մϴ�
- TripNameClickedListener - ���ο� ������ ���鶧 �����̸��� ���޹޴� ������ �Դϴ�
- TripServiceProvider - ���� �����ϴ� ���� ���񽺵��� ��ü�� �Ǵ� Activity�Դϴ�
- TripSettings - ���ο� ������ ����� ������ ���ø� �����ϴ� ��ü�Դϴ�
- TripUtils - �ۿ��� �ʿ��� ��� ��ƿ ��ɵ��� �����մϴ�.


## ���۱� �� ���� ���� (Copyright / End User License)

��߸�ǥ�� ���¼ҽ���ī������ ��ŭ ���, ����, ������ �����մϴ�.

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


## ������ �� �������� ����ó ���� (Contact Information)

[�����](https://github.com/gth1030) 
010-4817-9203

[������](https://github.com/minicomet)
010-2083-5880


## �˷��� ���� (Known Issues)

Google Youtube API Quota exceed error - �� ������ ���� �����꿡�� �� �̸��� �˻��Ͽ� �������µ� Youtube API�� ���˴ϴ�. �ٸ� ���� �Ҵ�� Quota���� ����� �۱� ������ Quota�� �Ѿ�� �˻��� �ȵǰ�
���ο� ������ �뷡�� ���� Youtube ���� id�� �������� ���ϴ� ��Ȳ�� �߻��մϴ�.
�ذ���: ���� �ۿ����� �ϳ��� ������ ���� �ѹ��̶� API ���� �ǽ��ϸ� �װ��� ���� SQLite ������ ���̽��� ������ �Ͽ� �ݺ��� �ݷ� ���� quota ���� �����մϴ�. �̰��� ���� ������ ������ �����Ͽ� 
�ű⿡ ������ ���� id�� ���������μ� quota�� ������� �ֽ� ���������� ���� ID�� �����ü� �ְ� �˴ϴ�.

������ �������� ���� ���� ���ѱ������� - ���� �ۿ� �����ϴ� �������� �������� �̱�, ĳ����, �̰����� �� ���ø� ������ �ٸ� ������ ���񽺸� ���� �������� �ʽ��ϴ�. 
�ذ���: �� ������ �߰����� �����͸� �����Ͽ� �ۿ� ���������� ���� �ذ�� �� �ֽ��ϴ�.



## ���� �߻��� ���� �ذ�å (Troubleshooting)
Google Youtube API Quota exceed error - �� ������ ���� �ذ����� �ϴ� Quota�� �������� ���� ���� ������ ���̽��� ����Ǿ��ִ� ���� �̿��Ͽ� ���� �ּҸ� ������ ���ų� �װ͸��� ���ٸ� �ۿ� �̸� ������� 
�⺻���� ������ �ͼ� ���������� �����մϴ�. Quota�� ���������� ��Ʈ��ũ�� ���� �������� ���ؼ��� �� �������� �Ǿ� ���ۿ��� ���ο� Quota�� ������ �� ������ ��ٷ��� �մϴ�.

������ �������� ���� ���� ���ѱ������� - ���� ���� ������Ʈ �Ҷ����� ���� ������ ���ѵ˴ϴ�.




## ũ���� (Credit)

## ������Ʈ ���� (Change Log)


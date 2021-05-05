각자 해야할 것
- Line을 추가하는 작업
1. 라인은     
     "color": "bg-red-600", <br>
     "name": "신분당선", <br>
     "upStationId": "1", <br>
     "downStationId": "2", <br>
     "distance": "10" <br>
     을 요청 받는다.
   
2. station 아이디가 있는지 확인한다. (validate)
3. 라인을 추가한다(db에).
4. section에 up, down, distance를 추가한다. (라인 아이디와)
5. 라인은 Stations를 보여준다.


- 알고리즘   
List\<Section> 을 List\<Station>으로 가져올 때 sorting된 상태로 어떻게 가져오나. (아니면 디비에 저장을 아예 순서로 저장 하는 방법도 생각 가능 예를 들면 order 숫자를 준다.)

- 지하철 구간 추가 API 구현   
  맨 앞역 추가할 때   
  중간 역 추가할 때   
  맨 뒤역 추가할 때
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

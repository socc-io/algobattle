# algobattle
Algorithm battle game


http://beaver.hp100.net/algobattle 
에서 확인할 수 있다. (아직 자동 업데이트기능 없음, git으로 pull 해야함!)
직접 접속해서 수정하기바람.



## 변경내역

add.php  ->  commit.php 로 바뀜 (용준&서희가 하던것)

index.php -> battle.php 로 바뀜 (용준&서희가 하던것)

source 안에는 사용자가 제출한 코드들이 모임

result 안에는 battle 결과를 넣어야 함



core 디렉토리안에는

template , generate, starter 디렉토리가가있음

template 디렉토리에는  알고리즘 배틀 게임 종류가 들어감 (지수가 하던것이 이곳에)

generate 에는 template 소스와 source에 있는 사용자가 제출한 소스와 합쳐진 battle source가 들어감 (서현이가 하던것)
(예를들면 AAA와 BBB가 싸우는 소스는 AAA_BBB.c 로 이곳에 저장됨)

starter에는 generate된 소스를 컴파일하고 실행하는 역할을 하는 프로그램이 들어감. (준석이가 하던것)






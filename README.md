# algobattle
Algorithm battle game for online judge. (version 0.0.1)

본 프로젝트는 '쏙' 알고리즘 스터디에서 만든 알고리즘 배틀 게임입니다.

사용하는 언어는 php(웹서버), html5(웹페이지), c(템플릿 작성,컴파일러), nodejs(코드 제너레이트) 를 쓰고 있습니다.

왜 이렇게 중구난방으로 언어를 정했냐면, 언어 공부도 하기위해서 그렇습니다.

각기 다른 언어를 하나로 통합하는 스킬 향상을 도모할수 있죠. (파워억지)

## How to install

Ubuntu 12.04 기준

```
$sudo apt-get install apache2 php5 nodejs 
$git clone https://github.com/becxer/algobattle.git
$cd algobattle
$sudo cp -r ./* /var/www
브라우저 http://localhost/

```

## 동작방식 설명

commit 페이지에서 사용자가 코드를 제출하면, source 디렉토리에 사용자의 코드가 저장된다.

battle 페이지에서 두명의 사용자 코드를 선택 후 싸움을 붙이면,

사용자의 소스코드 두개를 메인 템플릿 코드와 합쳐서 컴파일 한후, 실행한다. (실행되며 두 코드가 싸우게됨)

결과는 result 디렉토리의 [사용자]_[사용자].result 파일에 작성되며, 이것을 웹에서 순차적으로 보여준다.


## 변경내역

1. add.php  ->  commit.php 로 바뀜 (용준&서희가 하던것)

2. index.php -> battle.php 로 바뀜 (용준&서희가 하던것)

3. source 안에는 사용자가 제출한 코드들이 모임

4. result 안에는 battle 결과를 넣어야 함

5. core 디렉토리안에는 template , generate, starter 디렉토리가가있음

6. template 디렉토리에는알고리즘 배틀 게임 종류가 들어감 (지수가 하던것이 이곳에)

7. generate 에는 template 소스와 source에 있는 사용자가 제출한 소스와 합쳐진 battle source가 들어감 (서현이가 하던것)
(예를들면 AAA와 BBB가 싸우는 소스는 AAA_BBB.c 로 이곳에 저장됨)

8. starter에는 generate된 소스를 컴파일하고 실행하는 역할을 하는 프로그램이 들어감. (준석이가 하던것)
(준석이는, 실행파일명을 c_starter로 하면 될듯! algobattle.js는 그것을 ./c_starter [소스코드이름] 으로 실행함)


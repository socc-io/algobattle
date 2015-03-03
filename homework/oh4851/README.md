# Test방법
---
- src/*.java 컴파일 -> target: bin/
- bin/ChattingServer 실행
 - 실행 예: java ChattingServer 5000
- bin/ChattingClient 실행
 - 실행 예: java ChattingClient localhost 5000 userid1
- 2개 이상의 Client를 접속시키고 각각의 콘솔에서 채팅 입력 할 경우 채팅 가능
- logout을 원할 경우 /logout을 입력

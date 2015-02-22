#pragma warning(disable: 4996)
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <Windows.h>

#define MOOK 1
#define ZZI 2
#define BBA 3
#define true 1
#define false 0
#define period 1000


//this area will be fill with A,B's field variables

//use here for global variable
//don't remove comments

int song = 85858;


//use here for global variable
//don't remove comments
int count = 0;



typedef struct _state {
   char name[10];//사용자의 이름을 입력한다.
   int score;     //사용자의 점수가 입력된다.
   int son;     //사용자가 어떤 손을 내밀었는지 저장하는 변수
}STAT;

int play(STAT A, STAT B, char *file_name, int *turn, FILE *result);
int A_logic(char, int, int);
int B_logic(char, int, int);

/*name에는 현재 사용자의 이름을 입력한다.
*name은 함수가 맨 처음에 호출될 때만 이용되므로 그 이후로는 NULL값이 매개변수로 들어간다.
*turn은 현재 게임이 몇 턴인지 알려준다(손을 몇 번 내밀었는지).
*other_son은 상대방이 내민 손이 무엇인지 알려준다. 만약 0이 들어오면 정보를 주지 않는 것이다. */
int A_logic(char name[10], int turn, int other_last_hand){
      // 자동으로 코드를 채워줍니다.
      
      //this area will be fill with A's logic
      if (name != NULL) strcpy(name, "aaa");

    //fill here
    //don't remove comments
    song --;
    
}

int B_logic(char name[10], int turn, int other_last_hand){
      // 자동으로 코드를 채워줍니다.
      
      //this area will be fill with B's logic
      if (name != NULL) strcpy(name, "aaa");

    //fill here
    //don't remove comments
    count ++;
    
}

int main(){
   int turn = 0;
   STAT A, B;
   FILE *result;
   char *file_name[30] = { 0 },
      *file_end[30] = { 0 };

   A.son = A_logic(A.name, turn, 0);
   B.son = B_logic(B.name, turn, 0);
   //파일 이름 짓기
   strcpy(file_name, "./result/");
   strcat(file_name, A.name);
   strcat(file_name, "_");
   strcat(file_name, B.name);
   strcpy(file_end, file_name);
   strcat(file_name, ".txt");
   strcat(file_end, "_end.txt");
   while (turn < 100){
      if (turn != 0){
         A.son = A_logic(NULL, turn, 0);
         B.son = B_logic(NULL, turn, 0);
      }
      //결과 파일 오픈
      if ((result = fopen(file_name, "a+")) == NULL){
         fprintf(stderr, "error opening %s\n", file_name);
      }
      //사용자 함수 리턴값이 잘못 됐을 때의 예외처리
      if (A.son > 3 || A.son < 1){
         fprintf(result, "A함수 리턴값 오류.\n");
      }
      if (B.son > 3 || B.son < 1){
         fprintf(result, "B함수 리턴값 오류.\n");
      }
      //입력받은 값을 가지고 비교
      if (A.son == B.son){
         if (A.son == MOOK)     fprintf(result, "둘 다 묵을 내밀었습니다. 한번 더!\n");
         else if (A.son == ZZI) fprintf(result, "둘 다 찌를 내밀었습니다. 한번 더!\n");
         else                   fprintf(result, "둘 다 빠를 내밀었습니다. 한번 더!\n");
         fclose(result); Sleep(period);
         A.son = A_logic(NULL, turn, 0);
         B.son = B_logic(NULL, turn, 0);
         turn++;
      }
      else {
         play(A, B, file_name, &turn, result);
         rename(file_name, file_end);
         fprintf(result, "\n새 판 시작\n");
         fclose(result);   turn++;
      }
   }
}

//직접 묵찌빠를 플레이하는 부분. 각 턴은 1초마다 진행된다.
int play(STAT A, STAT B, char *file_name, int *turn, FILE *result){
   int winner;      //winner가 1인경우 A, 2인경우 B가 승자
   char win_son[5];

   while (A.son != B.son){
      result = fopen(file_name, "a+");
      if (A.son == MOOK && B.son == BBA){
         fprintf(result, "%s가 묵을, %s가 빠를 내밀었습니다.\n", A.name, B.name); winner = 2;
      }
      else if (A.son == MOOK && B.son == ZZI){
         fprintf(result, "%s가 묵을, %s가   찌를 내밀었습니다.\n", A.name, B.name); winner = 1;
      }
      else if (A.son == ZZI && B.son == MOOK){
         fprintf(result, "%s가 찌를, %s가   묵을 내밀었습니다.\n", A.name, B.name); winner = 2;
      }
      else if (A.son == ZZI && B.son == BBA){
         fprintf(result, "%s가 찌를, %s가   빠를 내밀었습니다.\n", A.name, B.name); winner = 1;
      }
      else if (A.son == BBA && B.son == MOOK){
         fprintf(result, "%s가 빠를, %s가   묵을 내밀었습니다.\n", A.name, B.name); winner = 1;
      }
      else if (A.son == BBA && B.son == ZZI){
         fprintf(result, "%s가 빠를, %s가   찌를 내밀었습니다.\n", A.name, B.name); winner = 2;
      }
      (*turn)++; Sleep(period);
      A.son = A_logic(NULL, turn, B.son);
      B.son = B_logic(NULL, turn, A.son);
   }

   if (A.son == MOOK)     strcpy(win_son, "묵");
   else if (A.son == ZZI) strcpy(win_son, "찌");
   else               strcpy(win_son, "빠");

   if (winner == 1) fprintf(result, "%s가 낸 %s에 %s가 걸려들어 %d턴만에 %s가 이겼습니다!\n", A.name, win_son, B.name, (*turn)++, A.name);
   else          fprintf(result, "%s가 낸 %s에 %s가 걸려들어 %d턴만에 %s가 이겼습니다!\n", B.name, win_son, A.name, (*turn)++, B.name);
   fclose(result);

   return 0;
}

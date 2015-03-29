#include <string.h>
#include <stdio.h>
#include <stdlib.h>

#define MOOK 0
#define ZZI 1
#define BBA 2

#define true 1
#define false 0

#define MAX_TURN 100
#define MAX_PENALTY 5
#define MAX_DEFEAT 10



//this area will be fill with A,B's field variables


//use here for global variable
//don't remove comments



//use here for global variable
//don't remove comments
int count = 0;


typedef struct _state {
	char name[100];
	int score;
	int son;
} STATE;

int A_logic(char name[100], int turn, int other_last_hand){
	
	//this area will be fill with A's logic
	if (name != NULL) sprintf(name, "baekseohyeon");

//fill here and don't remove comments

int your_hand = MOOK;

return BBA;


	return 0;
}

int B_logic(char name[100], int turn, int other_last_hand){
	
	//this area will be fill with B's logic
	if (name != NULL) sprintf(name, "Jisu");

//fill here and don't remove comments
int your_hand = MOOK;

count ++;
if( count % 2 == 1){
  return JJI;
}else{
  return BBA;
}


	return 0;	
}

int fight(int son1, int son2){
	if(son1 == son2) return 0;
	if(son1 == MOOK && son2 == BBA) return 2;
	if(son1 == MOOK && son2 == ZZI) return 1;
	if(son1 == ZZI && son2 == MOOK) return 2;
	if(son1 == ZZI && son2 == BBA) return 1;
	if(son1 == BBA && son2 == MOOK) return 1;
	if(son1 == BBA && son2 == ZZI) return 2;
}

int valid(int a, int b){
	if(a >= 0 && a < 3 && b >= 0 && b < 3)
		return true;
	else
		return false;
}

int play(STATE* A, STATE* B, FILE* result, int turn){
	int attacker = -99;
	int now_A = 0;
	int now_B = 0;
	int defeat = 0;
	STATE* player[3];
	player[1] = A;
	player[2] = B;
	// if same, trying until not same
	while(true){
		if(attacker != -99) break;
		now_A = A_logic(NULL,turn,B->son);
		now_B = B_logic(NULL,turn,A->son);

		if(!valid(now_A,now_B)) return 0; 

		if(fight(now_A,now_B) != 0){
			attacker = fight(now_A,now_B);
			fprintf(result, "%s is First Attacker!! \n",player[attacker]->name);
		}else{
			fprintf(result, "Their son is same! \n");
			defeat++;
			if(defeat > MAX_DEFEAT)
				attacker = turn % 2 + 1;
		}
		A->son = now_A;
		B->son = now_B;
	}

	defeat = 0;
	// if attacker setting, trying until same.
	// if someone win, attacker change.
	while(true){
		now_A = A_logic(NULL,turn,B->son);
		now_B = B_logic(NULL,turn,A->son);
		
		if(!valid(now_A,now_B)) return 0; 

		if(now_A == now_B){
			fprintf(result, "turn end, %s is win\n",player[attacker]->name); 
			return attacker;
		}else{
			if (attacker != fight(now_A,now_B)){
			attacker = fight(now_A,now_B);
			fprintf(result,"Attacker changed, now %s is attack!\n", player[attacker]->name);
			}
			defeat++;
			if(defeat > MAX_DEFEAT * MAX_DEFEAT)
				fprintf(result, "They are defeat. no wins\n");
				return 0;
		}
		A->son = now_A;
		B->son = now_B;
	}
	//if turn end winner ID return a is 1 b is 2
	//else return 0 --> this will be penalty
}

int main(void){
	int turn = 0;
	int penalty = 0;
	int win[3] = {0,};
	int winner = 0;
	STATE A, B;
	FILE *result;
	char file_name[300];
	//initiate A,B's name
	A.son = A_logic(A.name, turn, 0);
	B.son = B_logic(B.name, turn, 0);

	//result file name A-name_B-name.result
    strcpy(file_name, "./result/");
	strcat(file_name, A.name);
	strcat(file_name, "_");
	strcat(file_name, B.name);
	strcat(file_name, ".result");

	//making result file
	result = fopen(file_name,"w");
	if(result == NULL){
		printf("error opening result file : %s\n",file_name);
	}

	//start game loof
	while(true){
		if(!valid(A.son,B.son)) break;

		if(turn == MAX_TURN || penalty == MAX_PENALTY)
			break;
		winner = play(&A,&B,result,turn);
		
		if(winner ==1 || winner == 2){
			turn++;
			win[winner]++;
			fprintf(result,"Turn %d end, trying next game....\n", turn);
		}else{
			penalty ++;
			fprintf(result, "Penalty Game Occured in %d turn....\n",penalty);	
		}
	}

	fprintf(result, "Game end, result is %s's score %d, %s's score %d\n",A.name,win[1],B.name,win[2]);
	if(win[2] > win[1]){
		fprintf(result,"The winner is %s !!! congratuation!!\n",B.name);
	}else if(win[1] > win[2]){
		fprintf(result,"The winner is %s !!! congratuation!!\n",A.name);
	}else {
		fprintf(result,"DEFEAT !! no winner \n");
	}
	fclose(result);
	return 0;
}


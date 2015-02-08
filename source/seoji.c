	#include <stdio.h>

#define MOOK 0
#define ZZI 1
#define BBA 2

//*************
//use here for global variable
//don't remove comments
int flag1;
int flag2;
int flag3;
int n1;
int n2;
int n3;
int win1;
int win2;
int win3;

//*************

int logic(int turn, int other_last_hand){
//*************
//fill here and don't remove comments
int your_hand = MOOK;
if(turn ==1){
	flag1 = other_last_hand;
	if(flag1 == MOOK) win1 = ZZI;
	else if(flag1 == ZZI) win1 = MOOK;
	else win1 = BBA;
}
else if(turn ==2){
	flag2 = other_last_hand;
	if(flag2 == MOOK) win2 = ZZI;
	else if(flag2 == ZZI) win2 = MOOK;
	else win2 = BBA;
}
else if(turn ==3){
	flag3 = other_last_hand;
	if(flag3 == MOOK) win3 = ZZI;
	else if(flag3 == ZZI) win3 = MOOK;
	else win3 = BBA;
}

if(turn % 3 == 1){
	n1 ++; 
	n2 = 0;
	n3 = 0;
}
else if(turn % 3 == 2){
	n1 = 0; 
	n2 ++;
	n3 = 0;
}
else if(turn % 3 == 0){
	n1 = 0; 
	n2 = 0;
	n3 ++;
}


if(turn % 3 == 1 && n1 == 1 && n2 == 0){
   your_hand = win1;
}
else if(turn % 3 == 1 && n1 != 1 && n2 == 0){
   your_hand = flag1;
}
else if(turn % 3 == 2 && n2 == 1 && n3 == 0){
   your_hand = win2;
}
else if(turn % 3 == 2 && n2 != 1 && n3 == 0){
   your_hand = flag2;
}
else if(turn % 3 == 3 && n3 == 1 && n2 == 0){
   your_hand = win3;
}
else if(turn % 3 == 3 && n3 != 1 && n2 == 0){
   your_hand = flag3;
}


//*************
return your_hand;
}


//this is simulator main for your testing
int main(void){
int n = 100;
while(n-->0){
logic(100-n, n%3);

}
return 0;
}


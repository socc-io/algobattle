	#include <stdio.h>

#define MOOK 0
#define ZZI 1
#define BBA 2

//*************
//use here for global variable
//don't remove comments

int count = 0;

//*************

int logic(int turn, int other_last_hand){
//*************
//fill here and don't remove comments
int your_hand = MOOK;

count++;
if(count %2 ==1)
 return ZZI;
else
 return BBA;


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


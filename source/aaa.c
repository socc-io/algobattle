#include <stdio.h>

#define MOOK 0
#define JJI 1
#define BBA 2

//*************
//use here for global variable
//don't remove comments

int song = 85858;

//*************

int logic(int turn, int other_last_hand){
    int your_hand = -99;
    printf("turn : %d , other_last_hand : %d\n", turn, other_last_hand);
    //*************
    //fill here
    //don't remove comments
    song --;
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

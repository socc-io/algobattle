#include <stdio.h>

#define MOOK 0
#define ZZI 1
#define BBA 2

//*************
//use here for global variable
//don't remove comments
int hist_arr[3];
int count = 0;
//*************

int logic(int turn, int other_last_hand){
//*************
//fill here and don't remove comments
int max = 0;
int max_avg = 0;
int i = 0;
if(count++ == 0){ 
    for(i = 0 ; i< 3; i++){
        hist_arr[i] = 0;
    }   
}
hist_arr[other_last_hand]++;

for(i = 0 ; i < 3 ; i++){
    if(max_avg < hist_arr[i]){
        max_avg = hist_arr[i];
        max = i;
    }   
}
return (max + (count%2)+1)%3;
//*************
}


//this is simulator main for your testing
int main(void){
int n = 100;
while(n-->0){
logic(100-n, n%3);
}
return 0;
}
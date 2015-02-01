#include<stdio.h>
#include<stdlib.h>
#include<string.h>

int system(const char *string);

void main(int argc, char *argv[]){
		char p[100];//path
		char *DIRECTORY="dir";       //시스템명령어 dir
		//strcpy(p,argv[0]);
        strcpy(p,"aaa_bbb.c");
		system("gcc -o aaa_bbb.out ./result/aaa_bbb.c");
		system("./aaa_bbb.out");
		
}        

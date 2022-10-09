#include<stdio.h>

void print(char *str) {
  printf("%s", str);
}

void println(char *str) {
  printf("%s\n", str);
}

void printInt(int n) {
  printf("%d", n);
}

void printlnInt(int n) {
  printf("%d\n", n);
}

char *getString(){
    char *str = malloc(sizeof(char) * 256);
    scanf("%s", str);
    return str;
}

char *_malloc(i32 mallocSize) {
    return malloc(mallocSize);
}
#include <stdio.h>
#include <stdlib.h>

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

int getInt() {
  int n;
  scanf("%d", &n);
  return n;
}

char *getString(){
  char *str = malloc(sizeof(char) * 256);
  scanf("%s", str);
  return str;
}

char *toString(int n) {
  char *str = malloc(20);
  sprintf(str, "%d", n);
  return str;
}

char *_malloc(int mallocSize) {
    return malloc(mallocSize);
}
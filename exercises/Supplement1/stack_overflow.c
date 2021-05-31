#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct _Node {
  struct _node *next;
  unsigned char data[8];
} Node;

void send_data(unsigned char *data, int len) {
  for (int i=0; i < len; i++) {
    printf("%2x ", data[i]);
  }
  printf("\n");
}

int main() {
  Node first, second, third;
  first.next = &second;
  strcpy(first.data, "Hello");
  second.next = &third;
  strcpy(second.data, "World");
  third.next = NULL;
  strcpy(second.data, "!");

  printf("second.next = %p\n", second.next);
  send_data(first.data, 16);
  return 0;
}

class Oving_4_1 {

  static class Node {
    int data;
    Node next;
  };

  static Node push(Node head_ref, int data) {
    Node ptr1 = new Node();
    Node temp = head_ref;
    ptr1.data = data;
    ptr1.next = head_ref;

    if (head_ref != null) {
      while (temp.next != head_ref)
        temp = temp.next;
      temp.next = ptr1;
    } else
      ptr1.next = ptr1;

    head_ref = ptr1;

    return head_ref;
  }

  static void printList(Node head) {
    Node temp = head;
    if (head != null) {
      do {
        System.out.print(temp.data + " ");
        temp = temp.next;
      } while (temp != head);
    }
    System.out.println();
  }

  static Node KillNode(Node head_ref, int k) {
    Node head = head_ref;
    if (head == null) {
      return null;
    }
    Node curr = head, prev = null;
    boolean counter = true;
    while (true) {
      if (curr.next == head && curr == head) {
        break;
      }
      printList(head);
      if(counter){
        for (int i = 0; i < k-1; i++) {
          prev = curr;
          curr = curr.next;
        }
        counter = false;
      } else {
        for (int i = 0; i < k; i++) {
          prev = curr;
          curr = curr.next;
        }
      }
      if (curr == head) {
        prev = head;
        while (prev.next != head){
          prev = prev.next;
        }
        head = curr.next;
        prev.next = head;
        head_ref = head;
      }
      else if (curr.next == head) {
        prev.next = head;
      } else {
        prev.next = curr.next;
      }
    }
    return head;
  }

  public static void main(String args[]) {
    Node head = null;
    for (int i = 41; i > 0; i--) {
      head = push(head, i);
    }
    head = KillNode(head, 3);
    System.out.print("Josephus burde stå på plass: ");
    printList(head);
  }
}

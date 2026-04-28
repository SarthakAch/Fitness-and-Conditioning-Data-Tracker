# AVL Tree Rotation Algorithms

This document visualizes the self-balancing logic implemented in `AVLTree.java` for Week 3.

## 1. Single Right Rotation (`rotateRight`)
**Case:** Left-Left Imbalance.


```text
      Y (Pivot)                X
     / \                      / \
    X   C        ----->      A   Y
   / \                          / \
  A   B                        B   C

  X (Pivot)                  Y
   / \                        / \
  A   Y          ----->      X   Z
     / \                    / \
    B   Z                  A   B

    Y                Y               Z
    / \              / \            /   \
   X   D   --->     Z   D   --->   X     Y
  / \              / \            / \   / \
 A   Z            X   C          A   B C   D
    / \          / \
   B   C        A   B

X                X                Z
  / \              / \             /   \
 A   Y     --->   A   Z    --->   X     Y
    / \              / \         / \   / \
   Z   D            B   Y       A   B C   D
  / \                  / \
 B   C                C   D

 ---

### 3. How to view it in VS Code
Once you've created the file and pasted the code:
1.  Open `ROTATION_LOGIC.md`.
2.  Press **Cmd + Shift + V** (Mac) or **Ctrl + Shift + V** (Windows).
3.  VS Code will open a "Preview" tab that turns that code into a clean, formatted document with headers and diagrams.

### 4. Why put it here?
Putting this in your root directory makes it part of your project's documentation. When you push this to GitHub, anyone (including your instructor or teammates) can click the file and see exactly how your balancing logic works without having to dig through the Java code.

**Since you're wrapping up Week 3, do you want to double-check if your `delete` method in `AVLTree.java` is also triggering these rotations correctly?**
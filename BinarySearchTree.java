/** @author Tej Patel
 *  Binary search tree
 **/

package txp172630;

import java.util.Scanner;

public class BinarySearchTree<T extends Comparable<? super T>> {
	static class Entry<T> {
		T element;
		Entry<T> left, right;

		public Entry(T x, Entry<T> left, Entry<T> right) {
			this.element = x;
			this.left = left;
			this.right = right;
		}
	}

	Entry<T> root;
	int size;

	public BinarySearchTree() {
		root = null;
		size = 0;
	}

	// Returns true if BST contains element x.
	public boolean contains(T x) {
		return get(x) != null;
	}

	// Returns element is it is present in BST else null.
	public T get(T x) {
		Entry<T> node = root;
		while (node != null) {
			int compare = node.element.compareTo(x);
			if (compare == 0) {
				return node.element;
			} else if (compare < 0) {
				node = node.right;
			} else {
				node = node.left;
			}
		}
		return null;
	}

	// Adds new element at leaf and returns true if element was not already
	// present else replaces the element and returns false.
	public boolean add(T x) {
		if (root == null) {
			root = new Entry<>(x, null, null);
			size++;
		}
		Entry<T> node = root;
		Entry<T> parent = null;
		while (node != null) {
			parent = node;
			int compare = node.element.compareTo(x);
			if (compare == 0) {
				node.element = x;
				return false;
			} else if (compare < 0) {
				node = node.right;
			} else {
				node = node.left;
			}
		}
		if (parent.element.compareTo(x) > 0) {
			parent.left = new Entry<>(x, null, null);
		} else {
			parent.right = new Entry<>(x, null, null);
		}
		size++;
		return true;
	}

	// Removes and returns the element if present else returns null.
	public T remove(T x) {
		Entry<T> node = root;
		Entry<T> parent = null;
		while (node != null) {
			int compare = node.element.compareTo(x);
			if (compare == 0) {
				T result = node.element;
				// At least one child is null
				if (node.left == null || node.right == null) {
					bypass(parent, node);
				} else {
					// Replace element of node by minimum value in right subtree
					Entry<T> minRight = node.right;
					Entry<T> minRightParent = node;
					while (minRight.left != null) {
						minRightParent = minRight;
						minRight = minRight.left;
					}
					node.element = minRight.element;
					if (minRightParent.left == minRight) {
						minRightParent.left = null;
					} else {
						minRightParent.right = null;
					}
				}
				size--;
				return result;
			} else {
				parent = node;
				if (compare < 0) {
					node = node.right;
				} else {
					node = node.left;
				}
			}
		}
		return null;
	}

	// Precondition: node has at-least one child null
	private void bypass(Entry<T> parent, Entry<T> node) {
		Entry<T> child = node.left != null ? node.left : node.right;
		if (parent == null) {
			root = child;
		} else {
			if (parent.left == node) {
				parent.left = child;
			} else {
				parent.right = child;
			}
		}
	}

	// Get the minimum value in BST
	public T min() {
		Entry<T> node = root;
		if (node == null) {
			return null;
		}
		while (node.left != null) {
			node = node.left;
		}
		return node.element;
	}

	// Get the maximum value in BST
	public T max() {
		Entry<T> node = root;
		if (node == null) {
			return null;
		}
		while (node.right != null) {
			node = node.right;
		}
		return node.element;
	}

	private Object[] arr;
	private int index;

	@SuppressWarnings("unchecked")
	public Comparable<T>[] toArray() {
		this.arr = new Comparable[size];
		index = 0;
		inorder(root);
		return (Comparable<T>[]) this.arr;
	}

	// Inorder traversal to fill the array
	public void inorder(Entry<T> root) {
		if (root != null) {
			inorder(root.left);
			arr[index++] = root.element;
			inorder(root.right);
		}
	}

	public static void main(String[] args) {
		BinarySearchTree<Integer> t = new BinarySearchTree<>();
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int x = in.nextInt();
			if (x > 0) {
				System.out.print("Add " + x + " : ");
				t.add(x);
				t.printTree();
			} else if (x < 0) {
				System.out.print("Remove " + x + " : ");
				t.remove(-x);
				t.printTree();
			} else {
				Object[] arr = t.toArray();
				System.out.print("Final: ");
				for (int i = 0; i < t.size; i++) {
					System.out.print(arr[i] + " ");
				}
				System.out.println();
				return;
			}
		}
	}

	public void printTree() {
		System.out.print("[" + size + "]");
		printTree(root);
		System.out.println();
	}

	// Inorder traversal of tree
	void printTree(Entry<T> node) {
		if (node != null) {
			printTree(node.left);
			System.out.print(" " + node.element);
			printTree(node.right);
		}
	}
}
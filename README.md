# eMDS
Multi-dimensional search engine

Environment: JAVA, IDE: Eclipse

This Project is implemented by combining two different data structures, Hash map and Red black tree. To make search efficient, the data is organized using appropriate data structures, such as Red black trees and Hash map. 

The idea used in this project is we have a Node class which has three attributes id, name and price. Node object reference is stored in both Red Black tree and Hash Map. The values are inserted in Hash map is by id and Red Black tree by price.
The following functions are implemented: 

a.	Insert(id,price,name): This function inserts a new item.  If an entry with the  same id already exists, its name and price are replaced by the  new values.  If name is empty, then just the price is updated. Returns 1 if the item is new, and 0 otherwise.
This operation inserts value in both hash Map and red black tree.

b.	Find(id): return price of item with given id (or 0, if not found).This functioned searches the element from hash map and returns the price of the respective ids.

c.	Delete(id): delete item from storage.  Returns the sum of the long ints that are in the name of the item deleted (or 0, if such an id did not exist).This function deletes the value from both hash map and Red black tree.

d.	FindMinPrice(n): given a long int n, find items whose name contains n (exact match with one of the long ints in the item's name), and return lowest price of those items.This function finds the minimum price from the red black tree by doing the inorder traversal of the tree.

e.	FindMaxPrice(n): given a long int n, find items whose name contains n, and return highest price of those items. This 
function finds the minimum price from the red black tree by doing the inorder traversal of the tree in reserve order.

f.	FindPriceRange(n,low,high): given a long int n, find the number of items whose name contains n, and if in addition their prices fall within the given range, [low, high].This function finds the range of the price in the red black tree and returns the count of the names that exists in the price range.

g.	PriceHike(l,h,r): increase the price of every product, whose id is in the range [l,h], by r%  Discard any fractional pennies in the new prices of items.  Returns the sum of the net increases of the prices. This uses the Hash map to find the ids and the increases the price of every node which falls in between the range and increase the price of them by given percentage. And then red black tree in getting updated by the price value. DecimalFormat is used to set the precision of the decimal values.

Input specification:
Program takes a file as input and then performs operations as specified and then prints the output in the console.
References:
http://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html


Note: These Operations are implemented in DS.java File and other are helper classes.

# eMDS
Multi-dimensional search engine

It is implemented with much faster results using generics which allow users to search for given set of attributes of products.
find below further details

Consider the web site of a seller like Amazon.  
They carry tens of thousands of products, and each product has many
attributes (Name, Size, Description, Keywords, Manufacturer, Price, etc.).  

The search engine allows users to specify attributes of products that
they are seeking, and shows products that have most of those
attributes.  To make search efficient, the data is organized using
appropriate data structures, such as balanced trees.  But, if products
are organized by Name, how can search by price implemented efficiently?
The solution, called indexing in databases, is to create a new set of
references to the objects for each search field, and organize them to
implement search operations on that field efficiently.  As the objects
change, these access structures have to be kept consistent.

In this project, each object has 3 attributes: id (long int), name
(one or more long ints), and price (dollars and cents).  The following
operations are performed:

   a. Insert(id,price,name): insert a new item.  If an entry with the
      same id already exists, its name and price are replaced by the
      new values.  If name is empty, then just the price is updated.
      Returns 1 if the item is new, and 0 otherwise.

   b. Find(id): return price of item with given id (or 0, if not found).

   c. Delete(id): delete item from storage.  Returns the sum of the
      long ints that are in the name of the item deleted (or 0, if
      such an id did not exist).

   d. FindMinPrice(n): given a long int n, find items whose name
      contains n (exact match with one of the long ints in the item's
      name), and return lowest price of those items.

   e. FindMaxPrice(n): given a long int n, find items whose name
      contains n, and return highest price of those items.

   f. FindPriceRange(n,low,high): given a long int n, find the number
      of items whose name contains n, and their prices fall within the
      given range, [low, high].

   g. PriceHike(l,h,r): increase the price of every product, whose id is 
      in the range [l,h], by r%  Discard any fractional pennies in the new 
      prices of items.  Returns the sum of the net increases of the prices.

Note: These Operations are implemented in Node.java File and other are helper classes.

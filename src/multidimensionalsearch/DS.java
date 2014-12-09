package multidimensionalsearch;



import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class DS {
	HashMap<Long, Node> hmap = new HashMap<Long, Node>();
	DecimalFormat df = new DecimalFormat("#0.00");
	RedBlackBST rblack = new RedBlackBST();

	public static void main(String[] args) {
		Node node;
		DS ds = new DS();
		int counter = 0;
		Double finalsum = 0.0;
		File inFile = new File("p6-in5-ck.txt");
		try {

			Scanner sc = new Scanner(inFile);
			while (sc.hasNext()) {
				String op = sc.next();
				if (op.equals("#")) {
					String line = sc.nextLine();
					// System.out.println(line);
					continue;

				}
				counter++;
				if(counter==86526){
					System.out.println("Reached ::");
				}
				if (op.equalsIgnoreCase("Insert")) {
					ArrayList<Long> arr = new ArrayList<Long>();
					node = new Node();
					node.setId(sc.nextLong());
					node.setPrice(sc.nextDouble());
					while (true) {
						Long num = sc.nextLong();
						if (num == 0) {
							break;
						}
						arr.add(num);
					}
					node.setName(arr);

					int returnVal = ds.insert(node);
					// System.out.println("name: "+node.getName());
					System.out.println(counter + " : " + " Insert: "
							+ returnVal);
					finalsum = finalsum + returnVal;
				} else if (op.equalsIgnoreCase("Find")) {
					long idCheck = sc.nextLong();
					double price = ds.find(idCheck);

					System.out.println(counter + " : " + " Find: " + price);
					finalsum = finalsum + price;

				} else if (op.equalsIgnoreCase("Delete")) {
					long idDelete = sc.nextLong();
					long sum = ds.delete(idDelete);
					System.out.println(counter + " : " + " Delete: " + sum);
					finalsum = finalsum + sum;
				} else if (op.equalsIgnoreCase("PriceHike")) {
					long start_id, end_id, hike_rate;
					start_id = sc.nextLong();
					end_id = sc.nextLong();
					hike_rate = sc.nextLong();
					double price_sum = 0.0;
					price_sum = ds.priceHike(start_id, end_id, hike_rate);
					finalsum = finalsum + price_sum;
					System.out.println(counter + " : " + "PriceHike: "
							+ price_sum);
				} else if (op.equalsIgnoreCase("FindPriceRange")) {
					long name;
					double lo, hi;
					int num;
					name = sc.nextLong();
					lo = sc.nextDouble();
					hi = sc.nextDouble();
					num = ds.FindPriceRange(name, lo, hi);
					finalsum = finalsum + num;
					System.out.println(counter + " : " + "FindPriceRange: "
							+ num);
				} else if (op.equalsIgnoreCase("FindMinPrice")) {
					long name = sc.nextLong();
					double price = ds.FindMinPrice(name);
					finalsum = finalsum + price;
					System.out.println(counter + " : " + "FindMinPrice: "
							+ price);
				} else if (op.equalsIgnoreCase("FindMaxPrice")) {
					long name = sc.nextLong();
					double price = ds.FindMaxPrice(name);
					finalsum = finalsum + price;
					System.out.println(counter + " : " + "FindMaxPrice: "
							+ price);
				}

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("total: " + ds.df.format(finalsum));
		//ds.rblack.printTree();
	}

	/**
	 * This method is used to return price of item with given id (or 0, if not
	 * found).
	 * */
	private double find(long idCheck) {
		// TODO Auto-generated method stub
		if (hmap.containsKey(idCheck)) {
			return hmap.get(idCheck).getPrice();
		} else {
			return 0.0;

		}
	}

	/**
	 * given a long int n, find items whose name contains n (exact match with
	 * one of the long ints in the item's name), and return lowest price of
	 * those items.
	 * */
	private double FindMinPrice(long name) {
		// TODO Auto-generated method stub
		double price = 0.0;
		price = rblack.findMinContainN(name);

		return price;
	}

	/**
	 * given a long int n, find items whose name contains n, and return highest
	 * price of those items.
	 */

	private double FindMaxPrice(long name) {
		// TODO Auto-generated method stub
		double price = 0.0;
		price = rblack.findMaxContainN(name);

		return price;
	}

	/**
	 * given a long int n, find the number of items whose name contains n, and
	 * if in addition their prices fall within the given range, [low, high].
	 */

	private int FindPriceRange(long name, double lo, double hi) {
		// TODO Auto-generated method stub
		int num = 0;
		num = rblack.FindPriceRange(name, lo, hi);
		return num;
	}

	/**
	 * increase the price of every product, whose id is in the range [l,h], by
	 * r% Discard any fractional pennies in the new prices of items. Returns the
	 * sum of the net increases of the prices.
	 */
	private double priceHike(long start_id, long end_id, double hike_rate) {
		/*
		 * Iterator<Map.Entry<Long,Node>> itr1 = hmap.entrySet().iterator();
		 * while(itr1.hasNext()) { Map.Entry<Long,Node> entry = itr1.next();
		 * entry.getKey(); entry.getValue(); }
		 */

		double sum_price = 0.0;
		for (long i = start_id; i <= end_id; i++) {
			double t_price = 0.0;
			double increase = 0.0;
			if (hmap.containsKey(i)) {
				t_price = hmap.get(i).getPrice();
				increase = t_price * (hike_rate / 100);
				t_price = t_price + increase;
				rblack.delete(hmap.get(i));
				hmap.get(i).setPrice(Double.parseDouble(df.format((t_price))));
				
				rblack.put(hmap.get(i));
				sum_price = sum_price + increase;
			}

		}

		return sum_price;
	}

	/**
	 * delete item from storage. Returns the sum of the long ints that are in
	 * the name of the item deleted (or 0, if such an id did not exist).
	 */

	private long delete(long idDelete) {
		long name;
		long sum = 0;
		int x = 1;

		if (hmap.containsKey(idDelete)) {
			Node node = hmap.get(idDelete);
			x = rblack.delete(node);
			if (x == 0)
				return 0;
			for (int i = 0; i < hmap.get(idDelete).getName().size(); i++) {
				name = hmap.get(idDelete).getName().get(i);
				sum = sum + name;
			}
			hmap.remove(idDelete);
			return sum;

		} else {
			return 0;
		}

	}

	/**
	 * insert a new item. If an entry with the same id already exists, its name
	 * and price are replaced by the new values. If name is empty, then just the
	 * price is updated. Returns 1 if the item is new, and 0 otherwise.
	 */

	private int insert(Node node) {
		double price = find(node.getId());
		if (price > 0.0) {
			rblack.delete(hmap.get(node.getId()));
			if ((node.getName() == null) || (node.getName().isEmpty())) {
				Node oldVal = hmap.put(node.getId(), node);
				node.setName(oldVal.getName());
			}
			hmap.remove(node.getId());
			hmap.put(node.getId(), node);
			rblack.put(node);
			return 0;

		} else {
			hmap.put(node.getId(), node);
			rblack.put(node);
			return 1;
		}
		
	}
	
}

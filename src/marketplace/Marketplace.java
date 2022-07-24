package marketplace;

import java.util.Arrays;
import java.util.Scanner;

public class Marketplace {
	private static Product[] products = new Product[3];
	private static User[] users = new User[3];
	private static int[] us = new int[3];
	private static int[] pr = new int[3];
	private static int count = 0;
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		users[0] = new User(1, "Jack", "Smith", 400f);
		users[1] = new User(2, "Ella", "Greenfield", 800f);
		users[2] = new User(3, "Garry", "Winchester", 1500f);
		products[0] = new Product(1, "Kettle", 30.50f);
		products[1] = new Product(2, "Fridge", 450f);
		products[2] = new Product(3, "Monitor", 149.99f);
		boolean ok = true;
		while (ok) {
			System.out.println("Choose option:\n" + "1.Show all users\n" + "2.Show all products\n" + "3.Shopping\n"
					+ "4.List of user products\n" + "5.List of users that bought product\n" + "6.Leave marketplace\n");
			int option = scan.nextInt();
			switch (option) {
			case 1: {
				System.out.println(usersToString());
				break;
			}
			case 2: {
				System.out.println(productsToString());
				break;
			}
			case 3: {
				try {
					shopping();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
			case 4: {
				System.out.println(userList());
				break;
			}
			case 5: {
				System.out.println(productList());
				break;
			}
			case 6: {
				ok = false;
				break;
			}
			}
		}
	}

	// Returns enum of users
	private static String usersToString() {
		String result = "";
		for (User user : users) {
			String information = String.valueOf(user.id) + " " + user.firstName + " " + user.lastName + " "
					+ String.valueOf(user.cash);
			result += information;
			result += "\n";
		}
		return result;
	}

	// Returns enum of products
	private static String productsToString() {
		String result = "";
		for (Product product : products) {
			String information = String.valueOf(product.id) + " " + product.name + " " + String.valueOf(product.price);
			result += information;
			result += "\n";
		}
		return result;
	}

	// Imitates shopping process
	private static void shopping() throws Exception {
		System.out.println("Enter user id:");
		int userId = scan.nextInt();

		// Cheking if there is a user with such id
		User client = null;
		for (User user : users) {
			if (user.id == userId) {
				client = user;
				break;
			}
		}
		if (client == null) {
			System.out.println("No user with such id\n");
			return;
		}

		System.out.println("Enter product id:");
		int productId = scan.nextInt();

		// Cheking if there is a product with such id
		Product prod = null;
		for (Product product : products) {
			if (product.id == productId) {
				prod = product;
				break;
			}
		}
		if (prod == null) {
			System.out.println("No product with such id\n");
			return;
		}

		// Cheking if have enough money to buy product
		if (client.cash < prod.price)
			throw new Exception(client.cash + " is not enough money.\n" + "Must be at least " + prod.price);
		// Cheking if there is enough space in array to add new purchase
		if (us.length == count) {
			us = Arrays.copyOf(us, count + count / 2);
			pr = Arrays.copyOf(pr, count + count / 2);
		}

		us[count] = userId;
		pr[count] = productId;
		count++;
		client.cash -= prod.price;
		System.out.println("Successful purchase!");
	}

	// Bubble sort for two arrays
	public void bubbleSort(int[] arrUser, int[] arrProd) {
		boolean isSorted = false;
		int buf;
		while (!isSorted) {
			isSorted = true;
			for (int i = 0; i < arrUser.length - 1; i++) {
				if (arrUser[i] > arrUser[i + 1]) {
					isSorted = false;

					buf = arrUser[i];
					arrUser[i] = arrUser[i + 1];
					arrUser[i + 1] = buf;

					buf = arrProd[i];
					arrProd[i] = arrProd[i + 1];
					arrProd[i + 1] = buf;
				}
			}
		}
	}

	// Returns list of user purchases
	private static String userList() {
		String result = "";
		System.out.println("Enter user id");
		int userId = scan.nextInt();
		// Cheking if there is a user with such id
		User client = null;
		for (User user : users) {
			if (user.id == userId) {
				client = user;
				break;
			}
		}
		if (client == null) {
			return "No user with such id";
		}

		// Finding products
		for (int i = 0; i < count; i++) {
			if (us[i] == client.id) {
				for (Product product : products) {
					if (product.id == pr[i]) {
						String information = String.valueOf(product.id) + " " + product.name + " "
								+ String.valueOf(product.price);
						result += information;
						result += "\n";
					}
				}
			}
		}
		if (result.isEmpty()) {
			return "User " + client.firstName + " " + client.lastName + " hasn't bought anything yet";
		}
		String res = client.firstName + " " + client.lastName + " purchases\n";
		res += result;
		return res;
	}

	// Returns list of product and users, who'd bought it
	private static String productList() {
		String result = "";
		System.out.println("Enter product id");
		int productId = scan.nextInt();

		// Cheking if there is a product with such id
		Product prod = null;
		for (Product product : products) {
			if (product.id == productId) {
				prod = product;
				break;
			}
		}
		if (prod == null) {
			return "No product with such id";
		}
		
		// Finding users
		for (int i = 0; i < count; i++) {
			if (us[i] == prod.id) {
				for (User user : users) {
					if (user.id == us[i]) {
						String information = String.valueOf(user.id) + " " + user.firstName + " " + user.lastName + " "
								+ String.valueOf(user.cash);
						result += information;
						result += "\n";
					}
				}
			}
		}
		
		if (result.isEmpty()) {
			return "No one have bought " + (prod.name).toLowerCase();
		}
		String res = prod.name + " " + String.valueOf(prod.price) + " was bought buy:\n";
		res += result;
		return res;	
	}
	
	
}

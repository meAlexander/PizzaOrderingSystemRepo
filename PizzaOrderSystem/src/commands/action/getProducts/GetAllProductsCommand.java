package commands.action.getProducts;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import commands.Command;

public class GetAllProductsCommand implements Command {
	private Connection connection;
	private PrintStream printOut;

	public GetAllProductsCommand(Connection connection, PrintStream printOut) {
		this.connection = connection;
		this.printOut = printOut;
	}

	@Override
	public Command execute(Command parent) {
		printOut.println("That`s all available products");
		List<String> allProducts = new ArrayList<String>();
		try {
			allProducts.addAll(getPizzas());
			allProducts.addAll(getSalads());
			allProducts.addAll(getDrinks());

			for (String product : allProducts) {
				printOut.println(product);
			}
			printOut.flush();
			return parent;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getPizzas() throws SQLException {
		ResultSet resultSet = connection.prepareStatement("SELECT * FROM pizzas").executeQuery();
		List<String> pizzasList = new ArrayList<>();
		
		while (resultSet.next()) {
			String pizza = String.format("Pizza name: %s, Size: %s, Ingredients: %s, Price: %.2f",
					resultSet.getString("pizza_name"),
					resultSet.getString("size"),
					resultSet.getString("ingredients"),
					resultSet.getDouble("price"));
			pizzasList.add(pizza);
		}
		return pizzasList;
	}

	public List<String> getSalads() throws SQLException {
		ResultSet resultSet = connection.prepareStatement("SELECT * FROM salads").executeQuery();
		List<String> saladsList = new ArrayList<>();
		
		while (resultSet.next()) {
			String salad = String.format("Salad name: %s, Ingredients: %s, Price: %.2f",
					resultSet.getString("salad_name"),
					resultSet.getString("ingredients"),
					resultSet.getDouble("price"));
			saladsList.add(salad);
		}
		return saladsList;
	}

	public List<String> getDrinks() throws SQLException {
		ResultSet resultSet = connection.prepareStatement("SELECT * FROM drinks").executeQuery();
		List<String> drinksList = new ArrayList<String>();
		
		while (resultSet.next()) {
			String drinks = String.format("Drink type: %s, Brand: %s, Quantity: %d, Price: %.2f",
					resultSet.getString("drink_type"),
					resultSet.getString("brand"),
					resultSet.getInt("quantity"),
					resultSet.getDouble("price"));
			drinksList.add(drinks);
		}
		return drinksList;
	}
}
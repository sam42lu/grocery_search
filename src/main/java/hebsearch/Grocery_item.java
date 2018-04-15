package hebsearch;

public class Grocery_item {
    private String id, description, lastSold, shelfLife, department, price, unit, xFor, cost;

    public Grocery_item(String id, String description, String lastSold, String shelfLife, String department, String price, String unit, String xFor, String cost) {
        this.id= id;
        this.description=description;
        this.lastSold=lastSold;
        this.shelfLife=shelfLife;
        this.department=department;
        this.price=price;
        this.unit=unit;
        this.xFor=xFor;
        this.cost=cost;
    }

    @Override
    public String toString() {
        return String.format(
                "id is %s, descriptionis %s, lastSold is %s, shelfLife is %s, department is %s, price is %s, unit is %s, xFor is %s, cost is %s",
                id, description, lastSold, shelfLife, department, price, unit, xFor, cost);
    }

    // intentionally leaving out getters & setters do to time constrains
}

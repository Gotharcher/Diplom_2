package practicum.model;

public class OrderResponse {
    private boolean success;
    private Order[] orders;
    private String total;
    private String totalToday;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Order[] getOrders() {
        return orders;
    }

    public void setOrders(Order[] orders) {
        this.orders = orders;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalToday() {
        return totalToday;
    }

    public void setTotalToday(String totalToday) {
        this.totalToday = totalToday;
    }
}

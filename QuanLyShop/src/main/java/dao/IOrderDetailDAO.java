package dao;

import model.OrderDetail;

public interface IOrderDetailDAO {
    void addOrderDetail(OrderDetail detail) throws Exception;
}

package com.example.task.service;

import com.example.task.data.entity.Goods;
import jakarta.ejb.Stateless;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GoodsService {
  @PersistenceContext
  private EntityManager entityManager;

  public void saveGoods(Goods goods) {
    entityManager.persist(goods);
  }

  public List<Goods> getAllGoods() {
    return entityManager.createQuery("SELECT g FROM Goods g", Goods.class).getResultList();
  }
}

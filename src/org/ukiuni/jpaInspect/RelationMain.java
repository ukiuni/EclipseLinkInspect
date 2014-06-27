package org.ukiuni.jpaInspect;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.ukiuni.jpaInspect.entity.Cart;
import org.ukiuni.jpaInspect.entity.Item;

public class RelationMain {
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("myUnitInPersistenceXML");
		EntityManager em = factory.createEntityManager();
		{
			System.out.println("insert start");

			EntityTransaction entityTransaction = em.getTransaction();
			entityTransaction.begin();

			Cart cart = new Cart();

			cart.addItem(new Item("ITEM1"));
			cart.addItem(new Item("ITEM2"));

			cart.setKey(UUID.randomUUID().toString());

			em.persist(cart);
			entityTransaction.commit();
			System.out.println("insert end generated id = " + cart.getId());
		}
		EntityTransaction entityTransaction = em.getTransaction();
		entityTransaction.begin();
		{
			System.out.println("query cart start");
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Cart> cq = cb.createQuery(Cart.class);
			Root<Cart> r = cq.from(Cart.class);
			List<Cart> carts = em.createQuery(cq.select(r)).getResultList();
			System.out.println("carts length = " + carts.size());
			for (Cart cart : carts) {
				System.out.println("# cart id = " + cart.getId());
				List<Item> items = cart.getItems();
				for (Item item : items) {
					System.out.println("\t item id = " + item.getId() + ", name = " + item.getName() + ", cartId");
				}
			}
		}

		{
			System.out.println("query item start");
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Item> query = cb.createQuery(Item.class);
			Root<Item> r = query.from(Item.class);
			List<Item> items = em.createQuery(query.select(r)).getResultList();
			System.out.println("items length = " + items.size());
			for (Item item : items) {
				System.out.println("item cartId = " + item.getCart().getId() + " id = " + item.getId() + ", name = " + item.getName());
			}
		}
		em.close();
	}
}

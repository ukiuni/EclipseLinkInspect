package org.ukiuni.jpaInspect;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.ukiuni.jpaInspect.entity.MyEntity;

public class Main {
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("myUnitInPersistenceXML");
		EntityManager em = factory.createEntityManager();
		
		{
			System.out.println("insert start");
			EntityTransaction entityTransaction = em.getTransaction();
			entityTransaction.begin();
			MyEntity myEntity = new MyEntity();
			myEntity.setText("This is a Test text");
			myEntity.setCreatedAt(new Date());
			em.persist(myEntity);
			entityTransaction.commit();
			System.out.println("insert end generated id = " + myEntity.getId());
		}
		{
			System.out.println("query start");
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<MyEntity> cq = cb.createQuery(MyEntity.class);
			Root<MyEntity> r = cq.from(MyEntity.class);
			List<MyEntity> myEntities = em.createQuery(cq.select(r)).getResultList();
			for (MyEntity myEntity : myEntities) {
				System.out.println(myEntity.getId() + ":" + myEntity.getText() + ":" + new SimpleDateFormat("mm ss").format(myEntity.getCreatedAt()));
			}
			System.out.println("query end Size: " + myEntities.size());
		}
		em.close();
	}
}
